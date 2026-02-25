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
    PessoaServices(Repository repo) {
        this.repository = repo
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
}