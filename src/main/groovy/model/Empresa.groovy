package model

import groovy.transform.ToString
import groovy.transform.builder.Builder
import model.Candidato
import model.Curtida

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
    // Likes
    List<Curtida> candidatosCurtidos = []

    Curtida curtirCandidato(Candidato candidato, List<Curtida> allCurtidas) {
        if (candidatosCurtidos == null) {
            candidatosCurtidos = []
        }
        if (jaCurtiuCandidato(candidato)) {
            throw new IllegalStateException("Erro: ${name} já curtiu o candidato ${candidato.cpf}.")
        }
        candidatosCurtidos.add(candidato)
        /*println "💙 [EMPRESA] ${nome} curtiu o candidato (skills: ${candidato.skills})"*/

        // Verifica se existe curtida desse candidato em alguma vaga desta empresa
        def curtidaMatch = allCurtidas.find { curtida ->
            curtida.candidato == candidato && curtida.vaga.empresa == this && !curtida.isMatch()
        }

        if (curtidaMatch) {
            curtidaMatch.completarMatch(this)
            return curtidaMatch
        }
        return null
    }

    boolean jaCurtiuCandidato(Candidato candidato) {
        candidatosCurtidos.contains(candidato)
    }

    List<Candidato> listCurtidas() {
        return candidatosCurtidos
    }
}