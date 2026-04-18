package services

import dao.CandidateDao
import dao.CompanyDao
import dao.SkillDao
import dao.VacancyDao
import exceptions.DuplicateResourceException
import exceptions.PersistenceOperationException
import groovy.transform.CompileDynamic
import model.Candidate
import model.Company
import model.Vacancy

class VacancyServices {
    VacancyDao vacancyDao
    CompanyDao companyDao
    CandidateDao candidateDao
    SkillDao skillDao

    @CompileDynamic
    VacancyServices(VacancyDao vacancyDao, CompanyDao companyDao, CandidateDao candidateDao, SkillDao skillDao) {
        this.vacancyDao = vacancyDao
        this.companyDao = companyDao
        this.candidateDao = candidateDao
        this.skillDao = skillDao
    }

    @CompileDynamic
    Company searchCompany(String cnpj) {
        return companyDao.findByCnpj(cnpj)
    }

    @CompileDynamic
    Vacancy searchVacancyById(Long id) {
        return vacancyDao.findById(id)
    }

    @CompileDynamic
    Candidate searchCandidate(String cpf) {
        return candidateDao.findByCpf(cpf)
    }

    @CompileDynamic
    Vacancy createVacancy(String name,
                          String description,
                          String state,
                          String city,
                          Company company,
                          List<String> requiredSkills) {
        if (vacancyDao.findByCompanyAndName(company.id, name) != null) {
            throw new DuplicateResourceException("Vaga já cadastrada para esta empresa.")
        }

        Long vacancyId = vacancyDao.insert(company.id, name, description, state, city)
        if (vacancyId == null) {
            throw new PersistenceOperationException("Não foi possível criar a vaga.")
        }

        skillDao.insert(requiredSkills)
        Map<String, Long> ids = skillDao.findIdsByNames(requiredSkills)
        vacancyDao.addSkills(vacancyId, ids.values() as List<Long>)

        return vacancyDao.findById(vacancyId)
    }

    @CompileDynamic
    void listVacancies() {
        vacancyDao.listAll().each { vacancy ->
            println "Id vaga: ${vacancy.id}"
            println "Empresa: ${vacancy.company?.name}"
            println "CNPJ: ${vacancy.company?.cnpj}"
            println "Título: ${vacancy.name}"
            println "Descrição: ${vacancy.description}"
            println "Estado: ${vacancy.state}"
            println "Cidade: ${vacancy.city}"
            println "Requisitos: "
            vacancy.requiredSkills.each { skill ->
                println " - ${skill.name}"
            }
            println("+================================================+")
        }
    }
}
