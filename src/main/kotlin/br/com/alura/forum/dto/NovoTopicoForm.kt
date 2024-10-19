package br.com.alura.forum.dto

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

class NovoTopicoForm(
  @field:NotEmpty(message = "O Titulo não pode estar em branco!!") @field:Size(min = 5, max = 100, message = "O titulo deve ter no minimo 5 caracteres e o máximo de 100 caracteres") val titulo: String,
  @field:NotEmpty(message = "A mensagem não pode estar em branco") val mensagem: String,
  @field:NotNull val idCurso: Long,
  @field:NotNull val idAutor: Long
)