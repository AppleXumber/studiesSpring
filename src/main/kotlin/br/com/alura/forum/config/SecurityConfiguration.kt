package br.com.alura.forum.config

import br.com.alura.forum.security.JWTAuthenticatorFilter
import br.com.alura.forum.security.JWTLoginFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.filter.OncePerRequestFilter

@Configuration
@EnableWebSecurity
class SecurityConfiguration(private val userDetails: UserDetailsService, private val jwtUtil: JWTUtil) {

  @Bean
  fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
    return http
      .csrf { it.disable() }
      .authorizeHttpRequests {
        //it.requestMatchers("/topicos").hasAnyAuthority("LEITURA_ESCRITA")
        it.requestMatchers(HttpMethod.POST, "/login").permitAll()
        it.anyRequest().authenticated()
      }.addFilterBefore(
        JWTLoginFilter(authenticationManager(http), jwtUtil),
        UsernamePasswordAuthenticationFilter().javaClass,
      ).addFilterBefore(
        JWTAuthenticatorFilter(jwtUtil), OncePerRequestFilter::class.java
      )
      .sessionManagement {
        it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      }.build()
  }

  fun authenticationManager(http: HttpSecurity): AuthenticationManager {
    val authBuilder = http.getSharedObject(AuthenticationManagerBuilder::class.java)
    authBuilder.userDetailsService(userDetails).passwordEncoder(encoder())
    return authBuilder.build()
  }

  @Bean
  fun encoder(): PasswordEncoder = BCryptPasswordEncoder()
}