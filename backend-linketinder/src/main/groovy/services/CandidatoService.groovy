package services

import exceptions.RecursoDuplicadoException
import exceptions.OperacaoPersistenciaException
import entities.Candidato
import repositories.ICandidatoRepository
import repositories.ICompetenciaRepository

import java.sql.Date
import java.time.LocalDate

class CandidatoService {
    ICandidatoRepository candidatoDao
    ICompetenciaRepository competenciaDao

    CandidatoService(ICandidatoRepository candidatoDao, ICompetenciaRepository competenciaDao) {
        this.candidatoDao = candidatoDao
        this.competenciaDao = competenciaDao
    }

    Candidato criarCandidato(String nome, String sobrenome, String dataNascimento, String email, String cpf, String pais, String cep, String descricao, String formacao, String linkedin, List<String> competencias) {
        if (candidatoDao.buscarPorCpf(cpf) != null) {
            throw new RecursoDuplicadoException("CPF já cadastrado.")
        }

        Date nascimento = Date.valueOf(LocalDate.parse(dataNascimento))
        Long candidatoId = candidatoDao.inserir(nome, sobrenome, nascimento, email, cpf, pais, cep, descricao, formacao, linkedin)
        if (candidatoId == null) {
            throw new OperacaoPersistenciaException("Não foi possível criar o candidato.")
        }

        List<Long> competenciaIds = []
        competencias.each { competencia ->
            Long id = competenciaDao.inserir(competencia)
            if (id == null) {
                throw new OperacaoPersistenciaException("Não foi possível persistir a competência '${competencia}'.")
            }
            competenciaIds.add(id)
        }
        candidatoDao.adicionarCompetencias(candidatoId, competenciaIds)
        return candidatoDao.buscarPorCpf(cpf)
    }

    List<Candidato> listarCandidatos() {
        return candidatoDao.listarTodos()
    }

    Candidato buscarCandidato(String cpf) {
        return candidatoDao.buscarPorCpf(cpf)
    }

}
