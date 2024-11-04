package br.com.alura.forum.config

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.util.Date
import javax.crypto.SecretKey

@Component
class JWTUtil {
  private val expiration: Long = 60000

  @Value("\${jwt.secret}")
  private lateinit var secret: String

  fun generateToken(username: String): String {
    val key: SecretKey = Keys.hmacShaKeyFor(secret.toByteArray())

    return Jwts.builder()
      .subject(username)
      .expiration(Date(System.currentTimeMillis() + expiration))
      .signWith(key, SignatureAlgorithm.HS512)
      .compact()
  }

  fun isValid(jwt: String?): Boolean {
    return try {
//      Jwts.parser().setSigningKey(secret.toByteArray()).parseClaimsJws(jwt)
      val key: SecretKey = Keys.hmacShaKeyFor(secret.toByteArray())
      Jwts.parser().setSigningKey(key).build().parseClaimsJws(jwt)
      true
    } catch (e: IllegalArgumentException) {
      false
    }
  }

  fun getAuthentication(jwt: String?): Authentication {
    val key: SecretKey = Keys.hmacShaKeyFor(secret.toByteArray())
    val username = Jwts.parser().setSigningKey(key).build().parseClaimsJws(jwt).body.subject
    return UsernamePasswordAuthenticationToken(username, null, null,)
  }
}