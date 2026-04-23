package services

import exceptions.RegraDeNegocioException
import exceptions.RecursoDuplicadoException
import exceptions.OperacaoPersistenciaException
import entities.Empresa
import entities.Vaga
import repositories.ICompetenciaRepository
import repositories.IVagaRepository

class VagaService {
    IVagaRepository vagaDao
    ICompetenciaRepository competenciaDao

    VagaService(IVagaRepository vagaDao, ICompetenciaRepository competenciaDao) {
        this.vagaDao = vagaDao
        this.competenciaDao = competenciaDao
    }

    Vaga buscarVagaPorId(Long id) {
        return vagaDao.buscarPorId(id)
    }

    Vaga criarVaga(String nome, String descricao, String estado, String cidade, Empresa empresa, List<String> competenciasRequeridas) {
        if (empresa == null) {
            throw new RegraDeNegocioException("Empresa inválida para criação de vaga.")
        }
        if (!competenciasRequeridas) {
            throw new RegraDeNegocioException("A vaga deve possuir ao menos uma competência requerida.")
        }
        if (vagaDao.buscarPorEmpresaENome(empresa.id, nome) != null) {
            throw new RecursoDuplicadoException("Vaga já cadastrada para esta empresa.")
        }

        Long vagaId = vagaDao.inserir(empresa.id, nome, descricao, estado, cidade)
        if (vagaId == null) {
            throw new OperacaoPersistenciaException("Não foi possível criar a vaga.")
        }

        List<Long> competenciaIds = []
        competenciasRequeridas.each { competencia ->
            Long id = competenciaDao.inserir(competencia)
            if (id == null) {
                throw new OperacaoPersistenciaException("Não foi possível persistir a competência '${competencia}'.")
            }
            competenciaIds.add(id)
        }
        vagaDao.adicionarCompetencias(vagaId, competenciaIds)
        return vagaDao.buscarPorId(vagaId)
    }

    List<Vaga> listarVagas() {
        return vagaDao.listarTodos()
    }
}
