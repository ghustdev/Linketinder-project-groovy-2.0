package repositories

import entities.Candidato

interface ICandidatoRepository {
    Long inserir(Candidato candidato)

    void adicionarCompetencias(Long candidatoId, List<Long> competenciaIds)

    Candidato buscarPorCpf(String cpf)

    List<Candidato> listarTodos()
}
