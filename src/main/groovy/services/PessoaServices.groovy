package services

import groovy.transform.CompileDynamic
import groovy.transform.CompileStatic
import model.Candidato
import model.Empresa
import repository.Repository

@CompileStatic
class PessoaServices {

    Repository repository

    @CompileDynamic
    PessoaServices(Repository repository) {
        this.repository = repository
    }

    @CompileDynamic
    void createCandidato(String name, String email, String cpf, int old, String state, String cep, String description, List<String> skills) {
        repository.arrayCandidatos.add(Candidato.builder()
                .name(name)
                .email(email)
                .cpf(cpf)
                .old(old)
                .state(state)
                .cep(cep)
                .description(description)
                .skills(skills)
                .build())
    }

    @CompileDynamic
    void createEmpresa(String name, String email, String cnpj, String country, String state, String cep, String description, List<String> skills) {
        repository.arrayEmpresas.add(Empresa.builder()
                .name(name)
                .email(email)
                .cnpj(cnpj)
                .country(country)
                .state(state)
                .cep(cep)
                .description(description)
                .skills(skills)
                .build())
    }

    @CompileDynamic
    void listEmpresas() {
        int id = 1
        repository.arrayEmpresas.each {e ->
            println("+================================================+")
            println "Empresa: ${id}"
            println "CNPJ: ${e.cnpj}"
            println "Nome empresa: ${e.name}"
            println "Email corporativo: ${e.email}"
            println "Descrição: ${e.description}"
            println "País: ${e.country}"
            println "Estado: ${e.state}"
            println "CEP: ${e.cep}"
            id++
            println("+================================================+")
        }
    }

    @CompileDynamic
    void listCandidatos() {
        int id = 1
        repository.arrayCandidatos.each { c ->
            println("+================================================+")
            println "Candidato: ${id}"
            println "CPF: ${c.cpf}"
            println "Nome candidato: ${c.name}"
            println "Email pessoal: ${c.email}"
            println "Descrição: ${c.description}"
            println "Idade: ${c.old}"
            println "Estado: ${c.state}"
            println "CEP: ${c.cep}"
            println "Habilidades: "
            c.skills.each {s ->
                println "   ${s}"
            }
            id++
            println("+================================================+")
        }
    }
}