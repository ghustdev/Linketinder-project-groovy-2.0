package view

import repository.Repository
import services.PessoaServices
import services.VagaServices
import model.Empresa
import model.Candidato

class Cli {
    PessoaServices pessoaServices
    VagaServices vagaServices
    Repository repository

    Scanner scanner = new Scanner(System.in)

    Cli(PessoaServices pessoaServices, Repository repository, VagaServices vagaServices) {
        this.pessoaServices = pessoaServices
        this.repository = repository
        this.vagaServices = vagaServices
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
                println("[6] - Visualizar curtidas (Empresa)")
                println("[7] - Visualizar vagas (Candidato)")
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

            pessoaServices.listCandidatos()

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

            pessoaServices.listEmpresas()

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

    Empresa cliChoiceEmpresa() {
        Empresa empresa = null

        try {
            println("+================================================+")
            println("|                 Cadastrar vaga                 |")
            println("+================================================+")

            pessoaServices.listEmpresas()

            println("+================================================+")
            println("Escolha a empresa da vaga (pelo CNPJ): ")
            def cnpj = scanner.nextLine()

            empresa = vagaServices.searchEmpresa(cnpj)

            if (empresa == null) {
                println("+================================================+")
                println("Essa empresa não existe!")
                println("+================================================+")
                println("Aperte \"Enter\" para continuar")
                scanner.nextLine()
                return empresa
            }
        }
        catch (Exception e) {
            println("+================================================+")
            println("Falha ao confirmar empresa. Erro: ${e}")
            println("+================================================+")
            println("Aperte \"Enter\" para continuar")
            scanner.nextLine()
        }
        return empresa
    }

    Candidato cliChoiceCandidato() {
        Candidato candidato = null

        try {
            println("+================================================+")
            println("|                Visualizar vagas                |")
            println("+================================================+")

            pessoaServices.listCandidatos()

            println("+================================================+")
            println("Escolha o candidato para ver as vagas (pelo CPF): ")
            def cpf = scanner.nextLine()

            candidato = vagaServices.searchCandidato(cpf)

            if (candidato == null) {
                println("+================================================+")
                println("Esse candidato não existe!")
                println("+================================================+")
                println("Aperte \"Enter\" para continuar")
                scanner.nextLine()
                return null
            }
        }
        catch (Exception e) {
            println("+================================================+")
            println("Falha ao confirmar candidato. Erro: ${e}")
            println("+================================================+")
            println("Aperte \"Enter\" para continuar")
            scanner.nextLine()
        }
        return candidato
    }

    void cliCreateVaga() {
        try {
            def empresa = cliChoiceEmpresa()
            if (empresa == null) return

            println("+================================================+")
            println("|                 Cadastrar vaga                 |")
            println("+================================================+")
            println("${empresa.name} preencha as informações: ")
            println("Título: ")
            String title = scanner.nextLine()
            println("Descrição da vaga: ")
            String description = scanner.nextLine()
            println("Lista de habilidades requeridas (separado por ','): ")
            String input = scanner.nextLine()
            List<String> skillsRequests = input.split(",").collect { it.trim() }

            vagaServices.createVaga(title, description, empresa, skillsRequests)

            println("+================================================+")
            println("Vaga cadastrada com sucesso!")
            println("+================================================+")
            println("Aperte \"Enter\" para continuar")
            scanner.nextLine()
        }
        catch (Exception e) {
            println("+================================================+")
            println("Falha ao cadastrar vaga. Erro: ${e}")
            println("+================================================+")
            println("Aperte \"Enter\" para continuar")
            scanner.nextLine()
        }
    }

    void cliVerifyLikes() {

    }

    void cliListVagas() {
        def candidato = cliChoiceCandidato()
        if (candidato == null) return

        try {
            println("+================================================+")
            println("|                Visualizar vagas                |")
            println("+================================================+")

            vagaServices.listVagas()

            println("+================================================+")

            println("${candidato.name}, deseja curtir alguma vaga? (s/n)")
            def answer = scanner.nextLine()

            while (answer == "s") {
                println("+================================================+")
                println("Escolha o ID da vaga que deseja curtir: ")
                // criar lógica da curtida - apenas converter para curtidas
                def cnpj = scanner.nextLine().toLowerCase().trim()

                def empresa = vagaServices.searchEmpresa(cnpj)

                if (empresa == null) {
                    println("+================================================+")
                    println("Essa empresa não existe!")
                    println("+================================================+")
                    println("Aperte \"Enter\" para continuar")
                    scanner.nextLine()
                    return
                }

                println("+================================================+")
                println("Vaga CURTIDA com sucesso, ${candidato.name}!")
                println("+================================================+")
                println("Aperte \"Enter\" para continuar")
                scanner.nextLine()

                println("Deseja curtir outra vaga? (s/n)")
                answer = scanner.nextLine()
            }
        }
        catch (Exception e) {
            println("+================================================+")
            println("Falha ao listar vagas. Erro: ${e}")
            println("+================================================+")
            println("Aperte \"Enter\" para continuar")
            scanner.nextLine()
        }
    }

    void clirListMacthes() {

    }
}