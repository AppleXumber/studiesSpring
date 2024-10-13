package br.com.alura.forum.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/usuarios")
class UsuariosController {

  @GetMapping("/{nome}")
  fun buscarPorNome(@PathVariable name: String): String {
    return name
  }
}