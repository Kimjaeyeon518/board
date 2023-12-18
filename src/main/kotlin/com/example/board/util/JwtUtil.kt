package com.example.board.util

import com.example.board.exception.BadRequestException
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import io.jsonwebtoken.*
import io.jsonwebtoken.security.Keys
import java.nio.charset.StandardCharsets
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*
import javax.crypto.SecretKey


object JwtUtil {

    //1000 밀리세컨트= 1초, 60초 = 1분, 60분 = 1시간, 24시간 = 하루
    private const val ONE_DAY = 1000L * 60L * 60L * 24L
    private const val EXPIRATION_TIME = ONE_DAY
    private const val SECRET_KEY = "your-secret-keyasdfadsfadsfadsfadsfadsfasdfasdfadsfasdfasdfadsfasdfasdfasdf"
    private var secretKey: SecretKey? = null

    init {
        secretKey = Keys.hmacShaKeyFor(SECRET_KEY.toByteArray(StandardCharsets.UTF_8))
            ?: throw IllegalStateException("Token을 발급하기 위한 Key가 적절하게 생성되지 않음")
    }

    fun generateToken(
        username: String,
        expirationInMillisecond: Long = EXPIRATION_TIME
    ): String {
        val now = Date()
        val expiration = Date(now.time + expirationInMillisecond)
        val claims = generateClaims(now, expiration)
        return Jwts.builder()
            .setClaims(claims)
            .setSubject(username)
            .setIssuedAt(now)
            .setExpiration(expiration)
            .signWith(secretKey, SignatureAlgorithm.HS256)
            .compact()
    }

    /**
     * LocalDateTime을 직렬화 하기 위해서는 JavaTimeModule이 등록되어 있어야 합니다.
     * SerializationFeature.WRITE_DATES_AS_TIMESTAMPS 설정을 통해 직렬화를 보기 쉽게 만듭니다.
     * */
    private fun generateClaims(now: Date, expiration: Date): Map<String, String> {
        val nowLocalDateTime = LocalDateTime.ofInstant(now.toInstant(), ZoneId.systemDefault())
        val expirationLocalDateTime = LocalDateTime.ofInstant(expiration.toInstant(), ZoneId.systemDefault())

        val mapper = ObjectMapper()
        mapper.registerModule(JavaTimeModule())
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        return mapOf(
            "issuedAt" to mapper.writeValueAsString(nowLocalDateTime),
            "expiredAt" to mapper.writeValueAsString(expirationLocalDateTime),
        )
    }

    fun getUsernameFromToken(token: String): String {
        try {
            return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .body
                .subject
        } catch (e: ExpiredJwtException) {
            e.printStackTrace()
            throw BadRequestException("토큰이 만료되었습니다.")
        } catch (e: JwtException) {
            e.printStackTrace()
            throw BadRequestException("토큰이 유효하지 않습니다.")
        } catch (e: Exception) {
            e.printStackTrace()
            throw BadRequestException("잠시 후 재시도해주세요.")
        }
    }
}