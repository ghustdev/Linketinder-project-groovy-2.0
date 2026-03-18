package model

import groovy.transform.ToString
import groovy.transform.builder.Builder
import model.Candidato
import model.Curtida

import java.time.LocalDateTime

@Builder
@ToString(includeSuper = true, includeNames = true)
class Empresa extends Pessoa {
    String name
    String email
    String description
    String state
    String cep
    List<String> skills = []
    // Specific fields
    String cnpj
    String country
    // Curtidas da empresa (array/lista)
    List<Curtida> candidatosCurtidos = []

    Match curtirCandidato(Candidato candidato, Vaga vaga, List<Curtida> allCurtidasCandidatos) {
        if (candidatosCurtidos == null) {
            candidatosCurtidos = []
        }
        if (candidato == null || vaga == null) {
            throw new IllegalArgumentException("Candidato e vaga são obrigatórios.")
        }
        if (vaga.empresa.cnpj != this.cnpj) {
            throw new IllegalArgumentException("A vaga '${vaga.title}' não pertence à empresa ${name}.")
        }
        if (jaCurtiuCandidato(candidato, vaga)) {
            throw new IllegalStateException("Erro: ${name} já curtiu o candidato ${candidato.cpf}.")
        }

        candidatosCurtidos.add(new Curtida(
                candidato: candidato,
                vaga: vaga,
                empresa: this,
                date: LocalDateTime.now()
        ))

        def match = new Match(
                candidato: candidato,
                empresa: this,
                vaga: vaga,
                dateMatch: LocalDateTime.now()
        )

        return match.isMatch(candidatosCurtidos, allCurtidasCandidatos) ? match : null
    }

    boolean jaCurtiuCandidato(Candidato candidato, Vaga vaga) {
        if (candidatosCurtidos == null) {
            candidatosCurtidos = []
        }
        candidatosCurtidos.any {
            it.candidato.cpf == candidato.cpf && it.vaga.id == vaga.id
        }
    }

    List<Curtida> listCurtidas() {
        return candidatosCurtidos
    }
}
