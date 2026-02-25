package view

import repository.Repository
import services.PessoaServices

class Interface {
    Scanner scanner = new Scanner(System.in)

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
                } else if (optionMenu == 0) {
                    scanner.close()
                    break
                } else {
                    println("+================================================+")
                    println("Insira uma opção válida (números de 0 - 4). Erro: opção fora do limite")
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
            String cpf = scanner.nextLine()
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

            PessoaServices.createEmpresa(name, email, cpf, country, state, cep, description, skills)

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

            PessoaServices.createCandidato(name, email, cpf, old, state, cep, description, skills)

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
            Repository.arrayCandidatos.each { c ->
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
            Repository.arrayEmpresas.each {e ->
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
}