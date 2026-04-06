package services

import dao.CandidatoDao
import dao.CompetenciaDao
import dao.EmpresaDao
import groovy.transform.CompileDynamic
import groovy.transform.CompileStatic
import model.Candidato
import model.Empresa

import java.sql.Date
import java.time.LocalDate

@CompileStatic
class PessoaServices {

    CandidatoDao candidatoDao
    EmpresaDao empresaDao
    CompetenciaDao competenciaDao

    @CompileDynamic
    PessoaServices(CandidatoDao candidatoDao, EmpresaDao empresaDao, CompetenciaDao competenciaDao) {
        this.candidatoDao = candidatoDao
        this.empresaDao = empresaDao
        this.competenciaDao = competenciaDao
    }

    @CompileDynamic
    Candidato createCandidato(String nome,
                              String sobrenome,
                              String dataNascimento,
                              String email,
                              String cpf,
                              String pais,
                              String cep,
                              String descricao,
                              String senhaHash,
                              List<String> skills) {
        Candidato existing = candidatoDao.findByCpf(cpf)
        if (existing != null) {
            throw new IllegalStateException("CPF já cadastrado.")
        }

        Date date = Date.valueOf(LocalDate.parse(dataNascimento))
        Long candidatoId = candidatoDao.insert(nome, sobrenome, date, email, cpf, pais, cep, descricao, senhaHash)

        if (candidatoId == null) {
            throw new IllegalStateException("Não foi possível criar o candidato.")
        }

        competenciaDao.insert(skills)
        Map<String, Long> ids = competenciaDao.findIdsByNames(skills)
        candidatoDao.addCompetencias(candidatoId, ids.values() as List<Long>)

        return candidatoDao.findByCpf(cpf)
    }

    @CompileDynamic
    Empresa createEmpresa(String nome,
                            String email,
                            String cnpj,
                            String pais,
                            String cep,
                            String descricao,
                            String senhaHash) {
        if (empresaDao.findByCnpj(cnpj) != null) {
            throw new IllegalStateException("CNPJ já cadastrado.")
        }
        if (empresaDao.findByEmail(email) != null) {
            throw new IllegalStateException("Email já cadastrado.")
        }

        Long empresaId = empresaDao.insert(nome, cnpj, email, descricao, pais, cep, senhaHash)
        if (empresaId == null) {
            Empresa existing = empresaDao.findByCnpj(cnpj)
            if (existing == null) {
                throw new IllegalStateException("Não foi possível criar a empresa.")
            }
        }
        return empresaDao.findByCnpj(cnpj)
    }

    @CompileDynamic
    void listEmpresas() {
        int id = 1
        empresaDao.listAll().each { e ->
            println "Empresa: ${id}"
            println "CNPJ: ${e.cnpj}"
            println "Nome empresa: ${e.nome}"
            println "Email corporativo: ${e.email}"
            println "Descrição: ${e.descricao}"
            println "País: ${e.pais}"
            println "CEP: ${e.cep}"
            id++
            println("+================================================+")
        }
    }

    @CompileDynamic
    void listCandidatos() {
        int id = 1
        candidatoDao.listAll().each { c ->
            println "Candidato: ${id}"
            println "CPF: ${c.cpf}"
            println "Nome candidato: ${c.nome} ${c.sobrenome}"
            println "Email pessoal: ${c.email}"
            println "Descrição: ${c.descricao}"
            println "Data de nascimento: ${c.data_nascimento}"
            println "País: ${c.pais}"
            println "CEP: ${c.cep}"
            println "Habilidades: "
            c.skills.each { s ->
                println "   ${s}"
            }
            id++
            println("+================================================+")
        }
    }
}
