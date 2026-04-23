package repositories

import entities.Vaga

interface IVagaRepository {
    Long inserir(Long empresaId, String nome, String descricao, String estado, String cidade)

    void adicionarCompetencias(Long vagaId, List<Long> competenciaIds)

    Vaga buscarPorId(Long id)

    Vaga buscarPorEmpresaENome(Long empresaId, String nome)

    List<Vaga> listarTodos()
}
