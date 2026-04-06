package model

import groovy.transform.ToString
import groovy.transform.builder.Builder

import java.time.LocalDateTime

@Builder
@ToString(includeSuper = true, includeNames = true)
class Empresa extends Pessoa {
    Long id
    String nome
    String email
    String descricao
    String cep
    String pais
    String senha_hash
    // Specific fields
    String cnpj
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
            throw new IllegalArgumentException("A vaga '${vaga.nome}' não pertence à empresa ${nome}.")
        }
        if (jaCurtiuCandidato(candidato, vaga)) {
            throw new IllegalStateException("Erro: ${nome} já curtiu o candidato ${candidato.cpf}.")
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
