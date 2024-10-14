package br.com.alura.forum.service

import br.com.alura.forum.dto.NovoTopicoDto
import br.com.alura.forum.model.Topico
import org.springframework.stereotype.Service

@Service
class TopicoService(private var topicos: List<Topico> = ArrayList(),
  private val cursoService: CursoService,
  private val autorService: UsuarioService
) {

  fun listar(): List<Topico> {
    return topicos
  }

  fun buscarPorId(id: Long): Topico {
    return topicos.stream().filter { t -> t.id == id }.findFirst().get()
  }

  fun cadastrar(dto: NovoTopicoDto) {
    topicos.plus(Topico(
      id = topicos.size.toLong() + 1,
      titulo = dto.titulo,
      mensagem = dto.mensagem,
      curso = cursoService.buscarPorId(dto.idCurso),
      autor = autorService.buscarPorId(dto.idAutor)
    ))
  }

}