package services

import groovy.transform.CompileDynamic
import model.Candidato
import repository.Repository
import model.Empresa
import model.Vaga

class VagaServices {
    Repository repository
    Empresa empresa

    @CompileDynamic
    VagaServices(Repository repository, Empresa empresa) {
        this.repository = repository
        this.empresa = empresa
    }

    @CompileDynamic
    Empresa searchEmpresa(String cnpj) {
        try {
            def empresa = repository.arrayEmpresas.find {it.cnpj.equalsIgnoreCase(cnpj)}

            if (empresa != null) {
                return empresa
            }
        }
        catch (Exception e) {
            e.printStackTrace()
        }
        return null
    }

    @CompileDynamic
    Vaga searchIdVaga(int id) {
        try {
            def vaga = repository.arrayVagas.find { (it.id == id) }

            if (vaga != null) {
                return vaga
            }
        }
        catch (Exception e) {
            e.printStackTrace()
        }
        return null
    }

    @CompileDynamic
    Candidato searchCandidato(String cpf) {
        try {
            def candidato = repository.arrayCandidatos.find {it.cpf.equalsIgnoreCase(cpf)}

            if (candidato != null) {
                return candidato
            }
        }
        catch (Exception e) {
            e.printStackTrace()
        }
        return null
    }

    @CompileDynamic
    void createVaga(String title, String description, Empresa empresa, List<String> skillsRequests) {
        try {
            int maxId = repository.arrayVagas.stream().mapToInt(Vaga::getId).max().orElse(0) + 1

            repository.arrayVagas.add(Vaga.builder()
                    .id(maxId)
                    .title(title)
                    .description(description)
                    .empresa(empresa)
                    .skillsRequests(skillsRequests)
                    .build())
        }
        catch (Exception e) {
            e.printStackTrace()
        }
    }

    @CompileDynamic
    void listVagas() {
        repository.arrayVagas.each { v ->
            println "Id vaga: ${v.id}"
            println "Empresa: ${v.empresa.name}"
            println "CNPJ: ${v.empresa.cnpj}"
            println "Título: ${v.title}"
            println "Descrição: ${v.description}"
            println "Requisitos: "
            v.skillsRequests.each {s ->
                println " - ${s}"
            }
            println("+================================================+")
        }
    }
}
