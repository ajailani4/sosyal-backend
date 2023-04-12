package com.sosyal.api.routes

import com.sosyal.api.data.dto.FavoriteDto
import com.sosyal.api.data.dto.PostDto
import com.sosyal.api.data.dto.response.BaseResponse
import com.sosyal.api.data.repository.FavoriteRepository
import com.sosyal.api.data.repository.PostRepository
import com.sosyal.api.data.repository.UserRepository
import com.sosyal.api.util.Connection
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import org.koin.ktor.ext.inject

fun Route.configurePostRoutes(connections: MutableSet<Connection>) {
    val postRepository by inject<PostRepository>()
    val userRepository by inject<UserRepository>()
    val favoriteRepository by inject<FavoriteRepository>()

    authenticate("auth-jwt") {
        webSocket("/post") {
            val principal = call.principal<JWTPrincipal>()
            val username = principal!!.payload.getClaim("username").asString()
            val connection = Connection(
                session = this,
                username = username
            )
            connections += connection

            try {
                postRepository.getAllPosts().forEach { postDto ->
                    val isLiked = favoriteRepository.isPostFavorite(
                        username = username,
                        postId = postDto.id!!
                    )

                    connection.session.send(
                        Json.encodeToString(
                            postDto.copy(
                                likes = favoriteRepository.getFavoriteByPostId(postDto.id),
                                isLiked = isLiked
                            )
                        )
                    )
                }

                for (frame in incoming) {
                    frame as? Frame.Text ?: continue
                    val postDtoText = frame.readText()
                    var postDto = Json.decodeFromString<PostDto>(postDtoText)
                    val userDto = userRepository.getUser(postDto.username)
                    postDto = postDto.copy(userAvatar = userDto?.avatar)

                    val id = if (postDto.isEdited == true) {
                        if (postDto.isLiked == true &&
                            !favoriteRepository.isPostFavorite(
                                username = username,
                                postId = postDto.id!!
                            )
                        ) {
                            favoriteRepository.addFavorite(
                                FavoriteDto(
                                    username = username,
                                    postId = postDto.id!!
                                )
                            )
                        }

                        if (postDto.isLiked == false &&
                            favoriteRepository.isPostFavorite(
                                username = username,
                                postId = postDto.id!!
                            )
                        ) {
                            favoriteRepository.deleteFavorite(
                                username = username,
                                postId = postDto.id!!
                            )
                        }

                        postDto = postDto.copy(likes = favoriteRepository.getFavoriteByPostId(postDto.id!!))
                        postRepository.editPost(
                            id = postDto.id!!,
                            postDto = postDto
                        )
                    } else {
                        postDto = postDto.copy(isLiked = false)
                        postRepository.addPost(postDto)
                    }

                    connections.forEach { conn ->
                        conn.session.send(Json.encodeToString(postDto.copy(id = id)))
                    }
                }
            } catch (e: Exception) {
                println(e.localizedMessage)
            } finally {
                println("Remove $connection")
                connections -= connection
            }
        }

        get("/posts/{id?}") {
            val id = call.parameters["id"].toString()

            val postDto = postRepository.getPost(id) ?: return@get call.respond(
                status = HttpStatusCode.NotFound,
                message = BaseResponse<JsonObject>(
                    message = "Post is not found"
                )
            )

            call.respond(
                status = HttpStatusCode.OK,
                message = BaseResponse(
                    message = "Post has been retrieved successfully",
                    data = postDto
                )
            )
        }

        delete("/posts/{id?}") {
            val id = call.parameters["id"].toString()

            val result = postRepository.deletePost(id)

            if (!result) {
                return@delete call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = BaseResponse<JsonObject>(
                        message = "Cannot delete the post"
                    )
                )
            }

            call.respond(
                status = HttpStatusCode.OK,
                message = BaseResponse<JsonObject>(
                    message = "Post has been deleted successfully"
                )
            )
        }
    }
}