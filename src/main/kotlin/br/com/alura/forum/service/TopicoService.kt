package br.com.alura.forum.service

import br.com.alura.forum.dto.AtualizacaoTopicoForm
import br.com.alura.forum.dto.NovoTopicoForm
import br.com.alura.forum.dto.TopicoView
import br.com.alura.forum.exception.NotFoundException
import br.com.alura.forum.mapper.TopicoFormMapper
import br.com.alura.forum.mapper.TopicoViewMapper
import br.com.alura.forum.model.Topico
import org.springframework.stereotype.Service
import java.util.stream.Collectors

@Service
class TopicoService(
  private var topicos: MutableList<Topico> = ArrayList(),
  private val topicoViewMapper: TopicoViewMapper,
  private val topicoFormMapper: TopicoFormMapper,
  private val notFoundMessage: String = "Topico não encontrado",
) {

  fun listar(): List<TopicoView> {
    return topicos.stream().map { t ->
      topicoViewMapper.map(t)
    }.collect(Collectors.toList())
  }

  fun buscarPorId(id: Long): TopicoView {
    val topico = topicos.stream().filter { t -> t.id == id }.findFirst().get()
    val index = topicos.indexOfFirst { it.id == id }
    if(index == -1) throw NotFoundException(notFoundMessage)
    return topicoViewMapper.map(topico)
  }

  fun cadastrar(form: NovoTopicoForm): TopicoView {
    val topico = topicoFormMapper.map(form)
    topico.id = topicos.size.toLong() + 1
    topicos.add(topico)
    return topicoViewMapper.map(topico)
  }

  fun atualizar(form: AtualizacaoTopicoForm): TopicoView {
    val topico = topicos.stream().filter { t -> t.id == form.id }.findFirst().get()
    val index = topicos.indexOfFirst { it.id == form.id }
    if (index == -1) throw NotFoundException(notFoundMessage)
    topicos[index] = Topico(
      form.id,
      form.titulo,
      form.mensagem,
      topico.dataCriacao,
      topico.curso,
      topico.autor,
      topico.status,
      topico.respostas
    )
    return topicoViewMapper.map(topicos[index])
  }

  fun deletar(id: Long) {
    val index = topicos.indexOfFirst { it.id == id }
    if (index == -1) throw NotFoundException(notFoundMessage)
    topicos.removeAt(index)
  }

}