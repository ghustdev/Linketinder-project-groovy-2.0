package model

import groovy.transform.ToString
import groovy.transform.builder.Builder

import java.time.LocalDateTime

@Builder
@ToString(includeSuper = true, includeNames = true)
class Candidato extends Pessoa {
    Long id
    String name
    String last_name
    String email
    String description
    String country
    String cep
    String password
    List<Competencia> skills = []
    String cpf
    Date birth
    List<Curtida> likeVacancies = []

    Curtida curtirVaga(Vaga vaga) {
        if (likeVacancies == null) {
            likeVacancies = []
        }

        if (jaCortiuVaga(vaga)) {
            throw new IllegalStateException("Erro: ${name} já curtiu a vaga '${vaga.name}'.")
        }

        LocalDateTime date = LocalDateTime.now()

        def curtida = new Curtida(candidate: this, vacancy: vaga, enterprise: vaga.enterprise, likeDate: date)
        likeVacancies.add(curtida)
        return curtida
    }

    boolean jaCortiuVaga(Vaga vaga) {
        likeVacancies.any { it.vacancy.id == vaga.id }
    }

    List<Curtida> listCurtidas() {
        return likeVacancies
    }
}
