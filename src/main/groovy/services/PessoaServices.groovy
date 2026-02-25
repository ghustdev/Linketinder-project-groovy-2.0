package services

import model.Candidato
import model.Empresa
import repository.Repository

class PessoaServices {
    void createCandidato(String name, String email, String cpf, int old, String state, String cep, String description, List<String> skills) {
        Repository.arrayCandidatos.add(Candidato.builder()
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

    void createEmpresa(String name, String email, String cnpj, String country, String state, String cep, String description, List<String> skills) {
        Repository.arrayEmpresas.add(Empresa.builder()
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