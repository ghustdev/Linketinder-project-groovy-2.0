package model

import groovy.transform.CompileDynamic
import groovy.transform.ToString
import groovy.transform.builder.Builder

import java.time.LocalDateTime

@Builder
@ToString(includeSuper = true, includeNames = true)
class Candidato extends Pessoa {
    String name
    String email
    String description
    String state
    String cep
    List<String> skills = []
    // Specific fields
    String cpf
    int old
    // Likes
    List<Curtida> vagasCurtidas = []

    Curtida curtirVaga(Vaga vaga) {
        if (vagasCurtidas == null) {
            vagasCurtidas = []
        }

        if (jaCortiuVaga(vaga)) {
            throw new IllegalStateException("Erro: ${name} já curtiu a vaga '${vaga.title}'.")
        }

        LocalDateTime date = LocalDateTime.now()

        def curtida = new Curtida(candidato: this, vaga: vaga, empresa: vaga.empresa, date: date)
        vagasCurtidas.add(curtida)
        return curtida
    }

    boolean jaCortiuVaga(Vaga vaga) {
        vagasCurtidas.any { it.vaga.id == vaga.id }
    }

    List<Curtida> listCurtidas() {
        return vagasCurtidas
    }
}
