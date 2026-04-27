package repositories

import entities.Vaga

interface IVagaRepository {
    Long inserir(Vaga vaga)

    void adicionarCompetencias(Long vagaId, List<Long> competenciaIds)

    Vaga buscarPorId(Long id)

    Vaga buscarPorEmpresaENome(Long empresaId, String nome)

    List<Vaga> listarTodos()
}
