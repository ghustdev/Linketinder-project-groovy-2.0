package model


import java.time.LocalDateTime

class Match {

    Candidato candidato
    Empresa empresa
    Vaga vaga
    LocalDateTime dateMatch

    boolean isMatch(List<Curtida> curtidasEmpresaCandidatosCurtidos, List<Curtida> curtidasCandidatosVagasCurtidas) {
        if (candidato == null || empresa == null || vaga == null || curtidasEmpresaCandidatosCurtidos == null || curtidasCandidatosVagasCurtidas == null) {
            return false
        }

        String target = buildKey(candidato.cpf, empresa.cnpj, vaga.id)

        List<String> likesEmpresa = curtidasEmpresaCandidatosCurtidos.collect {
            buildKey(it.candidato.cpf, it.empresa.cnpj, it.vaga.id)
        }
        List<String> likesCandidato = curtidasCandidatosVagasCurtidas.collect {
            buildKey(it.candidato.cpf, it.empresa.cnpj, it.vaga.id)
        }

        return likesEmpresa.contains(target) && likesCandidato.contains(target)
    }

    static String buildKey(String cpf, String cnpj, Integer vagaId) {
        return "${cpf}|${cnpj}|${vagaId}"
    }
}
