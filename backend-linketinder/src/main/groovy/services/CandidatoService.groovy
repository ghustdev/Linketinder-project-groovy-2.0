package services

import exceptions.RecursoDuplicadoException
import exceptions.OperacaoPersistenciaException
import entities.Candidato
import repositories.ICandidatoRepository
import repositories.ICompetenciaRepository

class CandidatoService {
    ICandidatoRepository candidatoDao
    ICompetenciaRepository competenciaDao

    CandidatoService(ICandidatoRepository candidatoDao, ICompetenciaRepository competenciaDao) {
        this.candidatoDao = candidatoDao
        this.competenciaDao = competenciaDao
    }

    Candidato criarCandidato(Candidato candidato) {
        if (candidatoDao.buscarPorCpf(candidato.cpf) != null) {
            throw new RecursoDuplicadoException("CPF já cadastrado.")
        }

        Long candidatoId = candidatoDao.inserir(candidato)
        if (candidatoId == null) {
            throw new OperacaoPersistenciaException("Não foi possível criar o candidato.")
        }

        List<Long> competenciaIds = []
        (candidato.competencias ?: []).each { competencia ->
            Long id = competenciaDao.inserir(competencia?.nome)
            if (id == null) {
                throw new OperacaoPersistenciaException("Não foi possível persistir a competência '${competencia?.nome}'.")
            }
            competenciaIds.add(id)
        }
        candidatoDao.adicionarCompetencias(candidatoId, competenciaIds)
        return candidatoDao.buscarPorCpf(candidato.cpf)
    }

    List<Candidato> listarCandidatos() {
        return candidatoDao.listarTodos()
    }

    Candidato buscarCandidato(String cpf) {
        return candidatoDao.buscarPorCpf(cpf)
    }

}
