package br.com.alura.forum.security


import JWTUtil
import br.com.alura.forum.model.Credentials
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

class JWTLoginFilter(private val authManeger: AuthenticationManager, private val jwtUtil: JWTUtil) :
  UsernamePasswordAuthenticationFilter() {

  override fun attemptAuthentication(request: HttpServletRequest?, response: HttpServletResponse?): Authentication {
    val (username, password) = ObjectMapper().readValue(request?.inputStream, Credentials::class.java)
    val token = UsernamePasswordAuthenticationToken(username, password)
    return authManeger.authenticate(token)
  }

  override fun successfulAuthentication(
    request: HttpServletRequest?,
    response: HttpServletResponse?,
    chain: FilterChain?,
    authResult: Authentication?
  ) {
    val username = (authResult?.principal as UserDetails)
    val token = jwtUtil.generateToken(username.username, username.authorities)
    response?.addHeader("Authorization", "Bearer $token")
  }
}
