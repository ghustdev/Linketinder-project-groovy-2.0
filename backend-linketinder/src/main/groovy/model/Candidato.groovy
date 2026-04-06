package model


import groovy.transform.ToString
import groovy.transform.builder.Builder

import java.time.LocalDateTime

@Builder
@ToString(includeSuper = true, includeNames = true)
class Candidato extends Pessoa {
    Long id
    String nome
    String sobrenome
    String email
    String descricao
    String pais
    String cep
    String senha_hash
    List<Competencia> skills = []
    // Specific fields
    String cpf
    Date data_nascimento
    // Likes
    List<Curtida> vagasCurtidas = []

    Curtida curtirVaga(Vaga vaga) {
        if (vagasCurtidas == null) {
            vagasCurtidas = []
        }

        if (jaCortiuVaga(vaga)) {
            throw new IllegalStateException("Erro: ${nome} já curtiu a vaga '${vaga.nome}'.")
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
