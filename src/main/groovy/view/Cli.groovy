package view

import repository.Repository
import services.PessoaServices
import services.SistemaCurtidas
import services.VagaServices
import model.Empresa
import model.Candidato

class Cli {
    PessoaServices pessoaServices
    VagaServices vagaServices
    Repository repository
    SistemaCurtidas sistemaCurtidas

    Scanner scanner = new Scanner(System.in)

    Cli(PessoaServices pessoaServices, Repository repository, VagaServices vagaServices, SistemaCurtidas sistemaCurtidas) {
        this.pessoaServices = pessoaServices
        this.repository = repository
        this.vagaServices = vagaServices
        this.sistemaCurtidas = sistemaCurtidas
    }

    void cliMenu() {
        while (true) {
            try {
                println("+================================================+")
                println("|                   Linketinder                  |")
                println("+================================================+")
                /* println("[1] - Empresa")
                println("[2] - Candidato")
                println("[3] - Admin")
                println("+================================================+")*/
                println("[1] - Adicionar empresa")
                println("[2] - Adicionar candidato")
                println("[3] - Listar empresas")
                println("[4] - Listar candidatos")
                println("[5] - [Empresa] Cadastrar vaga")
                println("[6] - [Empresa] Visualizar curtidas (curtir Candidato)")
                println("[7] - [Candidato] Visualizar feed de vagas (curtir Vaga)")
                println("[8] - [Candidato] Visualizar curtidas")
                println("[9] - Listar Matches")
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
                    cliCreateVaga() // Done
                } else if (optionMenu == 6) {
                    cliVerifyLikesEmpresa() // Doing 2
                } else if (optionMenu == 7) {
                    cliListVagas() // Done
                } else if (optionMenu == 8) {
                    cliListLikesCandidato() // Doing 1
                }else if (optionMenu == 9) {
                    cliListMacthes() // Doing 3
                } else if (optionMenu == 0) {
                    scanner.close()
                    break
                } else {
                    println("+================================================+")
                    println("Insira uma opção válida (números de 0 - 9). Erro: opção fora do limite")
                    println("+================================================+")
                    println("Aperte \"Enter\" para continuar")
                    scanner.nextLine()
                }
            } catch (Exception e) {
                println("+================================================+")
                println("Adicione a entrada correta (números de 0 - 9). Erro: ${e}")
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
        println("+================================================+")
        println("|                 Cadastrar vaga                 |")
        println("+================================================+")
        try {
            def empresa = cliChoiceEmpresa()
            if (empresa == null) return

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

    void cliListVagas() {
        println("+================================================+")
        println("|                Visualizar vagas                |")
        println("+================================================+")
        def candidato = cliChoiceCandidato()
        if (candidato == null) return

        try {
            vagaServices.listVagas()

            println("${candidato.name}, deseja curtir alguma vaga? (s/n)")
            def answer = scanner.nextLine()

            while (answer == "s") {
                println("Escolha o Id da vaga que deseja curtir: ")
                def id = scanner.nextInt()
                scanner.nextLine()

                def vaga = vagaServices.searchIdVaga(id)

                if (vaga == null) {
                    println("+================================================+")
                    println("Essa vaga não existe!")
                    println("+================================================+")
                    println("Aperte \"Enter\" para continuar")
                    scanner.nextLine()
                    return
                }

                // Lógica das curtidas
                sistemaCurtidas.candidatoCurteVaga(candidato, vaga)

                println("+================================================+")
                println("${candidato.name} curtiu a vaga '${vaga.title}' (${vaga.empresa.name}). Vaga CURTIDA com sucesso!")
                println("+================================================+")
                println("Aperte \"Enter\" para continuar")
                scanner.nextLine()

                println("Deseja curtir outra vaga? (s/n)")
                answer = scanner.nextLine()
            }
        }
        catch (Exception e) {
            println("+================================================+")
            println("Falha ao listar vagas ou ao curtir. Erro: ${e}")
            println("+================================================+")
            println("Aperte \"Enter\" para continuar")
            scanner.nextLine()
        }
    }

    void cliVerifyLikesEmpresa() {
        println("+================================================+")
        println("|          CANDIDATOS QUE CURTIRAM VOCÊ          |")
        println("|                     Empresa                    |")
        println("+================================================+")

        def empresa = cliChoiceEmpresa()
        if (empresa == null) return

        try {
            def curtidasRecebidas = sistemaCurtidas.allCurtidas.findAll { it.vaga.empresa.cnpj == empresa.cnpj }

            if (curtidasRecebidas.isEmpty()) {
                println("+================================================+")
                println("Nenhum candidato curtiu suas vagas ainda.")
                println("+================================================+")
                println("Aperte \"Enter\" para continuar")
                scanner.nextLine()
                return
            }

            println("+================================================+")
            println "Candidatos interessados:"
            curtidasRecebidas.eachWithIndex { curtida, index ->
                def c = curtida.candidato
                println "[${index + 1}] - Nome: ${c.name} | Skills: ${c.skills.join(', ')}"
                println "      Vaga: ${curtida.vaga.title} | Status: ${curtida.isMatch() ? '🔥 MATCH!' : '⌛ Pendente'}"
                println("+================================================+")
            }

            println("Deseja curtir algum candidato de volta? (s/n)")
            def answer = scanner.nextLine().toLowerCase().trim()

            while (answer == "s") {
                println("Digite o número do candidato da lista acima: ")
                def inputIndex = scanner.nextInt()
                scanner.nextLine()

                if (inputIndex > 0 && inputIndex <= curtidasRecebidas.size()) {
                    def curtidaSelecionada = curtidasRecebidas[inputIndex - 1]
                    def candidatoAlvo = curtidaSelecionada.candidato

                    // 3. Lógica de curtir e verificar Match
                    // Usando o método que já existe na sua classe Empresa
                    def resultadoMatch = empresa.curtirCandidato(candidatoAlvo, sistemaCurtidas.allCurtidas)

                    if (resultadoMatch != null && resultadoMatch.isMatch()) {
                        println("+================================================+")
                        println("🔥 É UM MATCH! Você e ${candidatoAlvo.name} agora estão conectados.")
                        println("+================================================+")
                    } else {
                        println("+================================================+")
                        println("Você curtiu ${candidatoAlvo.name} com sucesso!")
                        println("+================================================+")
                    }
                } else {
                    println("Índice inválido!")
                }

                println("Deseja curtir outro candidato? (s/n)")
                answer = scanner.nextLine().toLowerCase()
            }

            println("Aperte \"Enter\" para continuar")
            scanner.nextLine()
        }
        catch (Exception e) {
            println("+================================================+")
            println("Erro ao processar curtidas da empresa: ${e.message}")
            println("+================================================+")
            println("Aperte \"Enter\" para continuar")
            scanner.nextLine()
        }
    }

    void cliListLikesCandidato() {
        println("+================================================+")
        println("|              Visualizar curtidas               |")
        println("|                    Candidato                   |")
        println("+================================================+")

        def candidato = cliChoiceCandidato()
        if (candidato == null) return

        try {
            def vagasCurtidas = candidato.listCurtidas()
            if (vagasCurtidas == null) {
                println("+================================================+")
                println("${candidato.name} não possui curtidas")
                println("+================================================+")
            } else {
                sistemaCurtidas.listCurtidasCandidato(vagasCurtidas)
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

    void cliListMacthes() {
        // listar todos os matches
    }

    void cliListMacthesCandidato() {
        // listar todos os matches de um candidato
    }

    void cliListMacthesEmpresa() {
        // listar todos os matches de uma empresa
    }
}