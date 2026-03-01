package view

import repository.Repository
import services.PessoaServices

class Cli {
    PessoaServices pessoaServices
    Repository repository

    Scanner scanner = new Scanner(System.in)

    Cli(PessoaServices pessoaServices, Repository repo) {
        this.pessoaServices = pessoaServices
        this.repository = repo
    }

    void cliMenu() {
        while (true) {
            try {
                println("+================================================+")
                println("|                   Linketinder                  |")
                println("+================================================+")
                println("[1] - Adicionar empresa")
                println("[2] - Adicionar candidato")
                println("[3] - Listar empresas")
                println("[4] - Listar candidatos")
                println("[5] - Cadastrar vaga (Empresa)")
                println("[6] - Verificar curtidas (Empresa)")
                println("[7] - Vizualizar vagas (Candidato)")
                println("[8] - Listar Matches")
                println("[0] - Encerrar programa")
                println("+================================================+")
                print("Selecione uma opção: ")

                def optionMenu = scanner.nextInt()
                scanner.nextLine()

                if (optionMenu == 1) {
                    cliCreateEmpresa()
                } else if (optionMenu == 2) {
                    cliCreateCandidato()
                } else if (optionMenu == 3) {
                    cliListEmpresas()
                } else if (optionMenu == 4) {
                    cliListCandidatos()
                } else if (optionMenu == 5) {
                    cliCreateVaga()
                } else if (optionMenu == 6) {
                    cliVerifyLikes()
                } else if (optionMenu == 7) {
                    cliListVagas()
                } else if (optionMenu == 8) {
                    clirListMacthes()
                } else if (optionMenu == 0) {
                    scanner.close()
                    break
                } else {
                    println("+================================================+")
                    println("Insira uma opção válida (números de 0 - 6). Erro: opção fora do limite")
                    println("+================================================+")
                    println("Aperte \"Enter\" para continuar")
                    scanner.nextLine()
                }
            } catch (Exception e) {
                println("+================================================+")
                println("Adicione a entrada correta (números de 0 - 4). Erro: ${e}")
                println("+================================================+")
                println("Aperte \"Enter\" para continuar")
                scanner.nextLine()
            }
        }
    }

    void cliCreateEmpresa() {
        try {
            println("+================================================+")
            println("|                Cadastrar empresa               |")
            println("+================================================+")
            println("Nome: ")
            String name = scanner.nextLine()
            println("Email: ")
            String email = scanner.nextLine()
            println("CNPJ: ")
            String cnpj = scanner.nextLine()
            println("País: ")
            String country = scanner.nextLine()
            println("Estado: ")
            String state = scanner.nextLine()
            println("CEP: ")
            String cep = scanner.nextLine()
            println("Descrição: ")
            String description = scanner.nextLine()
            println("Lista de habilidades (separado por ','): ")
            String input = scanner.nextLine()
            List<String> skills = input.split(",").collect { it.trim() }

            pessoaServices.createEmpresa(name, email, cnpj, country, state, cep, description, skills)

            println("+================================================+")
            println("Empresa cadastrada com sucesso!")
            println("+================================================+")
            println("Aperte \"Enter\" para continuar")
            scanner.nextLine()
        }
        catch (Exception e) {
            println("+================================================+")
            println("Falha ao cadastrar candidato. Erro: ${e}")
            println("+================================================+")
            println("Aperte \"Enter\" para continuar")
            scanner.nextLine()
        }
    }

    void cliCreateCandidato() {
        try {
            println("+================================================+")
            println("|               Cadastrar candidato              |")
            println("+================================================+")
            println("Nome: ")
            String name = scanner.nextLine()
            println("Email: ")
            String email = scanner.nextLine()
            println("CPF: ")
            String cpf = scanner.nextLine()
            println("Idade: ")
            int old = scanner.nextInt()
            scanner.nextLine()
            println("Estado: ")
            String state = scanner.nextLine()
            println("CEP: ")
            String cep = scanner.nextLine()
            println("Descrição: ")
            String description = scanner.nextLine()
            println("Lista de habilidades (separado por ','): ")
            String input = scanner.nextLine()
            List<String> skills = input.split(",").collect { it.trim() }

            pessoaServices.createCandidato(name, email, cpf, old, state, cep, description, skills)

            println("+================================================+")
            println("Candidato cadastrado com sucesso!")
            println("+================================================+")
            println("Aperte \"Enter\" para continuar")
            scanner.nextLine()
        }
        catch (Exception e) {
            println("+================================================+")
            println("Falha ao cadastrar candidato. Erro: ${e}")
            println("+================================================+")
            println("Aperte \"Enter\" para continuar")
            scanner.nextLine()
        }
    }

    void cliListCandidatos() {
        try {
            println("+================================================+")
            println("|              Listagem de candidatos             |")
            println("+================================================+")
            int id = 1
            repository.arrayCandidatos.each { c ->
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
            println("Aperte \"Enter\" para continuar")
            scanner.nextLine()
        }
        catch (Exception e) {
            println("+================================================+")
            println("Falha ao listar candidatos. Erro: ${e}")
            println("+================================================+")
            println("Aperte \"Enter\" para continuar")
            scanner.nextLine()
        }
    }

    void cliListEmpresas() {
        try {
            println("+================================================+")
            println("|              Listagem de empresas              |")
            println("+================================================+")
            int id = 1
            repository.arrayEmpresas.each {e ->
                println "Empresa: ${id}"
                println "CNPJ: ${e.cnpj}"
                println "Nome empresa: ${e.name}"
                println "Email corporativo: ${e.email}"
                println "Descrição: ${e.description}"
                println "País: ${e.country}"
                println "Estado: ${e.state}"
                println "CEP: ${e.cep}"
                println "Habilidades que buscamos: "
                e.skills.each {s ->
                    println "   ${s}"
                }
                id++
                println("+================================================+")
            }
            println("Aperte \"Enter\" para continuar")
            scanner.nextLine()
        }
        catch (Exception e) {
            println("+================================================+")
            println("Falha ao listar candidatos. Erro: ${e}")
            println("+================================================+")
            println("Aperte \"Enter\" para continuar")
            scanner.nextLine()
        }
    }

    void cliCreateVaga() {
        try {
            println("+================================================+")
            println("|                 Cadastrar vaga                 |")
            println("+================================================+")
            println("Título: ")
            String title = scanner.nextLine()
            println("Descrição da vaga: ")
            String description = scanner.nextLine()
            println("Lista de habilidades requeridas (separado por ','): ")
            String input = scanner.nextLine()
            List<String> skillsRequests = input.split(",").collect { it.trim() }

            pessoaServices.createCandidato(name, email, cpf, old, state, cep, description, skills)

            println("+================================================+")
            println("Candidato cadastrado com sucesso!")
            println("+================================================+")
            println("Aperte \"Enter\" para continuar")
            scanner.nextLine()
        }
        catch (Exception e) {
            println("+================================================+")
            println("Falha ao cadastrar candidato. Erro: ${e}")
            println("+================================================+")
            println("Aperte \"Enter\" para continuar")
            scanner.nextLine()
        }
    }

    void cliVerifyLikes() {

    }

    void cliListVagas() {

    }

    void clirListMacthes() {

    }
}