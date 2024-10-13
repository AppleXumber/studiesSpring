package br.com.alura.forum.service

import br.com.alura.forum.model.Curso
import br.com.alura.forum.model.Topico
import br.com.alura.forum.model.Usuario
import org.springframework.stereotype.Service

@Service
class TopicoService(private var topicos: List<Topico>) {

  init{
    val topico1 = Topico(
      1,
      "Duvida Kotlin 1",
      "Variaveis no Kotlin",
      curso = Curso(1,"Kotlin","Programação"),
      autor = Usuario(1,"Ana da Silva","ana@email.com")
    )
    val topico2 = Topico(
      2,
      "Duvida Kotlin 2",
      "Variaveis no Kotlin",
      curso = Curso(1,"Kotlin","Programação"),
      autor = Usuario(1,"Ana da Silva","ana@email.com")
    )
    val topico3 = Topico(
      3,
      "Duvida Kotlin 3",
      "Variaveis no Kotlin",
      curso = Curso(1,"Kotlin","Programação"),
      autor = Usuario(1,"Ana da Silva","ana@email.com")
    )

    topicos = listOf(topico1, topico2, topico3)
  }

  fun listar(): List<Topico> {
    return topicos
  }

  fun buscarPorId(id: Long): Topico {
    return topicos.stream().filter { t -> t.id == id }.findFirst().get()
  }

}