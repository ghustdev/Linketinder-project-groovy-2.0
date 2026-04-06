package services

import dao.CandidatoDao
import dao.CompetenciaDao
import dao.EmpresaDao
import dao.VagaDao
import groovy.transform.CompileDynamic
import model.Candidato
import model.Empresa
import model.Vaga

class VagaServices {
    VagaDao vagaDao
    EmpresaDao empresaDao
    CandidatoDao candidatoDao
    CompetenciaDao competenciaDao

    @CompileDynamic
    VagaServices(VagaDao vagaDao, EmpresaDao empresaDao, CandidatoDao candidatoDao, CompetenciaDao competenciaDao) {
        this.vagaDao = vagaDao
        this.empresaDao = empresaDao
        this.candidatoDao = candidatoDao
        this.competenciaDao = competenciaDao
    }

    @CompileDynamic
    Empresa searchEmpresa(String cnpj) {
        return empresaDao.findByCnpj(cnpj)
    }

    @CompileDynamic
    Vaga searchIdVaga(Long id) {
        return vagaDao.findById(id)
    }

    @CompileDynamic
    Candidato searchCandidato(String cpf) {
        return candidatoDao.findByCpf(cpf)
    }

    @CompileDynamic
    Vaga createVaga(String title,
                    String description,
                    String estado,
                    String cidade,
                    Empresa empresa,
                    List<String> skillsRequests) {
        if (vagaDao.findByEmpresaAndNome(empresa.id, title) != null) {
            throw new IllegalStateException("Vaga já cadastrada para esta empresa.")
        }

        Long vagaId = vagaDao.insert(empresa.id, title, description, estado, cidade)
        if (vagaId == null) {
            throw new IllegalStateException("Não foi possível criar a vaga.")
        }

        competenciaDao.insert(skillsRequests)
        Map<String, Long> ids = competenciaDao.findIdsByNames(skillsRequests)
        vagaDao.addCompetencias(vagaId, ids.values() as List<Long>)

        return vagaDao.findById(vagaId)
    }

    @CompileDynamic
    void listVagas() {
        vagaDao.listAll().each { v ->
            println "Id vaga: ${v.id}"
            println "Empresa: ${v.empresa?.nome}"
            println "CNPJ: ${v.empresa?.cnpj}"
            println "Título: ${v.nome}"
            println "Descrição: ${v.descricao}"
            println "Estado: ${v.estado}"
            println "Cidade: ${v.cidade}"
            println "Requisitos: "
            v.skillsRequests.each { s ->
                println " - ${s}"
            }
            println("+================================================+")
        }
    }
}
