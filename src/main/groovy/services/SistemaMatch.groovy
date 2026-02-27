package services

import model.Curtidas
import model.Candidato
import model.Empresa
import model.Vaga
import java.time.LocalDateTime

class SistemaMatch {

    Candidato     candidato
    Empresa       empresa
    Vaga          vaga
    LocalDateTime dataMatch

    // Factory method — constrói o resultado a partir de uma Curtida completa
    static SistemaMatch fromCurtida(Curtidas curtida) {
        if (!curtida.isMatch()) {
            throw new IllegalArgumentException(
                    "Não é possível criar SistemaMatch: curtida ainda está pendente."
            )
        }
        return new SistemaMatch(
                candidato: curtida.candidato,
                empresa:   curtida.empresa,
                vaga:      curtida.vaga,
                dataMatch: curtida.dataMatch
        )
    }

    // Exibe os dados completos de ambas as partes (revelados após o match)
    void exibirDetalhes() {
        println "┌──────────────────────────────────────────────────┐"
        println "│              DETALHES DO MATCH                   │"
        println "├──────────────────────────────────────────────────┤"
        println "│  CANDIDATO                                       │"
        println "│  Nome   : ${candidato.nome}"
        println "│  CPF    : ${candidato.cpf}"
        println "│  Email  : ${candidato.email}"
        println "│  Skills : ${candidato.skills}"
        println "├──────────────────────────────────────────────────┤"
        println "│  EMPRESA                                         │"
        println "│  Nome   : ${empresa.nome}"
        println "│  CNPJ   : ${empresa.cnpj}"
        println "│  Email  : ${empresa.email}"
        println "├──────────────────────────────────────────────────┤"
        println "│  VAGA                                            │"
        println "│  Título : ${vaga.titulo}"
        println "│  Skills : ${vaga.skillsRequeridas}"
        println "├──────────────────────────────────────────────────┤"
        println "│  Data do Match : ${dataMatch}"
        println "└──────────────────────────────────────────────────┘"
    }

    @Override
    String toString() {
        return "SistemaMatch { ${candidato.nome} ↔ ${empresa.nome} | Vaga: '${vaga.titulo}' | ${dataMatch} }"
    }
}
