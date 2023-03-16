package com.sosyal.api.util

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm

object JWTUtil {
    fun createJWT(
        secret: String,
        issuer: String,
        audience: String,
        username: String
    ): String =
        JWT.create()
            .withAudience(audience)
            .withIssuer(issuer)
            .withClaim("username", username)
            .sign(Algorithm.HMAC256(secret))
}