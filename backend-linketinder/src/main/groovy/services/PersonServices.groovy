package services

import dao.CandidateDao
import dao.CompanyDao
import dao.SkillDao
import exceptions.DuplicateResourceException
import exceptions.PersistenceOperationException
import groovy.transform.CompileDynamic
import groovy.transform.CompileStatic
import model.Candidate
import model.Company

import java.sql.Date
import java.time.LocalDate

@CompileStatic
class PersonServices {

    CandidateDao candidateDao
    CompanyDao companyDao
    SkillDao skillDao

    @CompileDynamic
    PersonServices(CandidateDao candidateDao, CompanyDao companyDao, SkillDao skillDao) {
        this.candidateDao = candidateDao
        this.companyDao = companyDao
        this.skillDao = skillDao
    }

    @CompileDynamic
    Candidate createCandidate(String firstName,
                              String lastName,
                              String birthDate,
                              String email,
                              String cpf,
                              String country,
                              String cep,
                              String description,
                              String passwordHash,
                              List<String> skills) {
        Candidate existing = candidateDao.findByCpf(cpf)
        if (existing != null) {
            throw new DuplicateResourceException("CPF já cadastrado.")
        }

        Date date = Date.valueOf(LocalDate.parse(birthDate))
        Long candidateId = candidateDao.insert(firstName, lastName, date, email, cpf, country, cep, description, passwordHash)

        if (candidateId == null) {
            throw new PersistenceOperationException("Não foi possível criar o candidato.")
        }

        skillDao.insert(skills)
        Map<String, Long> ids = skillDao.findIdsByNames(skills)
        candidateDao.addSkills(candidateId, ids.values() as List<Long>)

        return candidateDao.findByCpf(cpf)
    }

    @CompileDynamic
    Company createCompany(String name,
                          String email,
                          String cnpj,
                          String country,
                          String cep,
                          String description,
                          String passwordHash) {
        if (companyDao.findByCnpj(cnpj) != null) {
            throw new DuplicateResourceException("CNPJ já cadastrado.")
        }
        if (companyDao.findByEmail(email) != null) {
            throw new DuplicateResourceException("Email já cadastrado.")
        }

        Long companyId = companyDao.insert(name, cnpj, email, description, country, cep, passwordHash)
        if (companyId == null) {
            Company existing = companyDao.findByCnpj(cnpj)
            if (existing == null) {
                throw new PersistenceOperationException("Não foi possível criar a empresa.")
            }
        }
        return companyDao.findByCnpj(cnpj)
    }

    @CompileDynamic
    void listCompanies() {
        int id = 1
        companyDao.listAll().each { company ->
            println "Empresa: ${id}"
            println "CNPJ: ${company.cnpj}"
            println "Nome empresa: ${company.name}"
            println "Email corporativo: ${company.email}"
            println "Descrição: ${company.description}"
            println "País: ${company.country}"
            println "CEP: ${company.cep}"
            id++
            println("+================================================+")
        }
    }

    @CompileDynamic
    void listCandidates() {
        candidateDao.listAll().each { candidate ->
            println "Candidato: ${candidate.id}"
            println "CPF: ${candidate.cpf}"
            println "Nome candidato: ${candidate.name} ${candidate.lastName}"
            println "Email pessoal: ${candidate.email}"
            println "Descrição: ${candidate.description}"
            println "Data de nascimento: ${candidate.birth}"
            println "País: ${candidate.country}"
            println "CEP: ${candidate.cep}"
            println "Habilidades: "
            candidate.skills.each { skill ->
                println "   ${skill.name}"
            }
            println("+================================================+")
        }
    }
}
