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
                println("[1] - Adicionar empresa")
                println("[2] - Adicionar candidato")
                println("[3] - Listar empresas")
                println("[4] - Listar candidatos")
                println("[5] - [Empresa] Cadastrar vaga")
                println("[6] - [Empresa] Visualizar curtidas (curtir Candidato)")
                println("[7] - [Candidato] Visualizar feed de vagas (curtir Vaga)")
                println("[8] - [Candidato] Visualizar curtidas")
                println("[9] - Listar Matches")
                println("[10] - Listar Matches do Candidato")
                println("[11] - Listar Matches da Empresa")
                println("[0] - Encerrar programa")
                println("+================================================+")
                // Implementação futura: dividir am abas (Empresa, Cnadidato e Admin) simulando um login
                /* println("[1] - Empresa")
                println("[2] - Candidato")
                println("[3] - Admin")
                println("+================================================+")*/
                Integer optionMenu = readInt("Selecione uma opção: ")
                if (optionMenu == null) {
                    println("+================================================+")
                    println("Adicione a entrada correta (números de 0 - 11).")
                    println("+================================================+")
                    pause()
                    continue
                }

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
                    cliVerifyLikesEmpresa()
                } else if (optionMenu == 7) {
                    cliListVagas()
                } else if (optionMenu == 8) {
                    cliListLikesCandidato()
                } else if (optionMenu == 9) {
                    cliListMatches()
                } else if (optionMenu == 10) {
                    cliListMatchesCandidato()
                } else if (optionMenu == 11) {
                    cliListMatchesEmpresa()
                } else if (optionMenu == 0) {
                    scanner.close()
                    break
                } else {
                    println("+================================================+")
                    println("Insira uma opção válida (números de 0 - 11). Erro: opção fora do limite")
                    println("+================================================+")
                    pause()
                }
            } catch (Exception e) {
                println("+================================================+")
                println("Adicione a entrada correta (números de 0 - 11). Erro: ${e}")
                println("+================================================+")
                pause()
            }
        }
    }

    void cliCreateEmpresa() {
        try {
            println("+================================================+")
            println("|                Cadastrar empresa               |")
            println("+================================================+")
            print("Nome: ")
            String name = scanner.nextLine()
            print("Email: ")
            String email = scanner.nextLine()
            print("CNPJ: ")
            String cnpj = scanner.nextLine()
            print("País: ")
            String country = scanner.nextLine()
            print("Estado: ")
            String state = scanner.nextLine()
            print("CEP: ")
            String cep = scanner.nextLine()
            print("Descrição: ")
            String description = scanner.nextLine()
            print("Lista de habilidades (separado por ','): ")
            String input = scanner.nextLine()
            List<String> skills = parseSkills(input)

            pessoaServices.createEmpresa(name, email, cnpj, country, state, cep, description, skills)

            println("+================================================+")
            println("Empresa cadastrada com sucesso!")
            println("+================================================+")
            pause()
        }
        catch (Exception e) {
            println("+================================================+")
            println("Falha ao cadastrar candidato. Erro: ${e}")
            println("+================================================+")
            pause()
        }
    }

    void cliCreateCandidato() {
        try {
            println("+================================================+")
            println("|               Cadastrar candidato              |")
            println("+================================================+")
            print("Nome: ")
            String name = scanner.nextLine()
            print("Email: ")
            String email = scanner.nextLine()
            print("CPF: ")
            String cpf = scanner.nextLine()
            Integer old = readInt("Idade: ")
            if (old == null || old < 0) {
                println("+================================================+")
                println("Idade inválida.")
                println("+================================================+")
                pause()
                return
            }
            print("Estado: ")
            String state = scanner.nextLine()
            print("CEP: ")
            String cep = scanner.nextLine()
            print("Descrição: ")
            String description = scanner.nextLine()
            print("Lista de habilidades (separado por ','): ")
            String input = scanner.nextLine()
            List<String> skills = parseSkills(input)

            pessoaServices.createCandidato(name, email, cpf, old, state, cep, description, skills)

            println("+================================================+")
            println("Candidato cadastrado com sucesso!")
            println("+================================================+")
            pause()
        }
        catch (Exception e) {
            println("+================================================+")
            println("Falha ao cadastrar candidato. Erro: ${e}")
            println("+================================================+")
            pause()
        }
    }

    void cliListCandidatos() {
        try {
            println("+================================================+")
            println("|              Listagem de candidatos             |")
            println("+================================================+")

            pessoaServices.listCandidatos()

            pause()
        }
        catch (Exception e) {
            println("+================================================+")
            println("Falha ao listar candidatos. Erro: ${e}")
            println("+================================================+")
            pause()
        }
    }

    void cliListEmpresas() {
        try {
            println("+================================================+")
            println("|              Listagem de empresas              |")
            println("+================================================+")

            pessoaServices.listEmpresas()

            pause()
        }
        catch (Exception e) {
            println("+================================================+")
            println("Falha ao listar candidatos. Erro: ${e}")
            println("+================================================+")
            pause()
        }
    }

    Empresa cliChoiceEmpresa() {
        Empresa empresa = null

        try {
            pessoaServices.listEmpresas()

            print("Escolha uma empresa (pelo CNPJ): ")
            def cnpj = scanner.nextLine()

            empresa = vagaServices.searchEmpresa(cnpj)

            if (empresa == null) {
                println("+================================================+")
                println("Essa empresa não existe!")
                println("+================================================+")
                pause()
                return empresa
            }
        }
        catch (Exception e) {
            println("+================================================+")
            println("Falha ao confirmar empresa. Erro: ${e}")
            println("+================================================+")
            pause()
        }
        return empresa
    }

    Candidato cliChoiceCandidato() {
        Candidato candidato = null

        try {
            pessoaServices.listCandidatos()

            print("Escolha o candidato (pelo CPF): ")
            def cpf = scanner.nextLine()

            candidato = vagaServices.searchCandidato(cpf)

            if (candidato == null) {
                println("+================================================+")
                println("Esse candidato não existe!")
                println("+================================================+")
                pause()
                return null
            }
        }
        catch (Exception e) {
            println("+================================================+")
            println("Falha ao confirmar candidato. Erro: ${e}")
            println("+================================================+")
            pause()
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

            println("+================================================+")
            println("${empresa.name}, preencha as informações da vaga: ")
            print("Título: ")
            String title = scanner.nextLine()
            print("Descrição da vaga: ")
            String description = scanner.nextLine()
            print("Lista de habilidades requeridas (separado por ','): ")
            String input = scanner.nextLine()
            List<String> skillsRequests = parseSkills(input)

            vagaServices.createVaga(title, description, empresa, skillsRequests)

            println("+================================================+")
            println("Vaga cadastrada com sucesso!")
            println("+================================================+")
            pause()
        }
        catch (Exception e) {
            println("+================================================+")
            println("Falha ao cadastrar vaga. Erro: ${e}")
            println("+================================================+")
            pause()
        }
    }

    void cliListVagas() {
        println("+================================================+")
        println("|                Visualizar vagas                |")
        println("+================================================+")
        def candidato = cliChoiceCandidato()
        if (candidato == null) return

        println("+================================================+")
        println("Seja bem vindo(a), ${candidato.name}.")
        println("+================================================+")
        pause()

        try {
            vagaServices.listVagas()

            print("${candidato.name}, deseja curtir alguma vaga? (s/n): ")
            def answer = scanner.nextLine().toLowerCase().trim()

            while (answer == "s") {
                println("+================================================+")
                Integer id = readInt("Escolha o Id da vaga que deseja curtir: ")
                if (id == null) {
                    println("+================================================+")
                    println("Id inválido.")
                    println("+================================================+")
                    pause()
                    return
                }

                def vaga = vagaServices.searchIdVaga(id)

                if (vaga == null) {
                    println("+================================================+")
                    println("Essa vaga não existe!")
                    println("+================================================+")
                    pause()
                    return
                }

                // Lógica das curtidas
                sistemaCurtidas.candidatoCurteVaga(candidato, vaga)

                println("+================================================+")
                println("${candidato.name} curtiu a vaga '${vaga.title}' da Empresa ${vaga.empresa.name}. Vaga CURTIDA com sucesso!")
                println("+================================================+")

                print("Deseja curtir outra vaga? (s/n): ")
                answer = scanner.nextLine().toLowerCase().trim()
            }
        }
        catch (Exception e) {
            println("+================================================+")
            println("Falha ao listar vagas ou ao curtir. Erro: ${e}")
            println("+================================================+")
            pause()
        }
    }

    void cliVerifyLikesEmpresa() {
        println("+================================================+")
        println("|          Candidatos que curtiram você          |")
        println("|                     Empresa                    |")
        println("+================================================+")

        def empresa = cliChoiceEmpresa()
        if (empresa == null) return

        try {
            def curtidasRecebidas = sistemaCurtidas.allCurtidasCandidatos.findAll { it.vaga.empresa.cnpj == empresa.cnpj }

            if (curtidasRecebidas.isEmpty()) {
                println("+================================================+")
                println("Nenhum candidato curtiu suas vagas ainda.")
                println("+================================================+")
                pause()
                return
            }

            println("+================================================+")
            sistemaCurtidas.listCurtidasRecebidasEmpresa(curtidasRecebidas)

            print("Deseja curtir algum candidato de volta? (s/n): ")
            def answer = scanner.nextLine().toLowerCase().trim()

            while (answer == "s") {
                println("+================================================+")
                print("Digite o CPF do candidato da lista acima: ")
                String cpf = scanner.nextLine().trim()

                Integer idVaga = readInt("Digite o ID da vaga que o candidato curtiu: ")

                def vagaSearch = vagaServices.searchIdVaga(idVaga)

                if (idVaga == null) {
                    println("+================================================+")
                    println("ID de vaga inválido.")
                    println("+================================================+")
                    pause()
                    continue
                }

                if (vagaSearch == null) {
                    println("+================================================+")
                    println("Essa vaga não existe!")
                    println("+================================================+")
                    pause()
                    return
                }

                def curtidaSelecionada = curtidasRecebidas.find {
                    it.candidato.cpf == cpf && it.vaga.id == idVaga
                }
                if (curtidaSelecionada == null) {
                    println("+================================================+")
                    println("Candidato inválido. Use somente CPF e vaga exibidos na lista acima.")
                    println("+================================================+")
                    pause()
                    continue
                }

                def candidato = curtidaSelecionada.candidato
                def vaga = curtidaSelecionada.vaga
                def resultadoMatch = sistemaCurtidas.empresaCurteCandidato(empresa, candidato, vaga)
                if (resultadoMatch == null) {
                    println("+================================================+")
                    println("Não foi possível gerar match para essa seleção.")
                    println("+================================================+")
                    pause()
                    continue
                }

                println("+================================================+")
                println("É UM MATCH! Você e ${candidato.name} agora estão conectados.")
                println("${empresa.name} curtiu o candidato ${candidato.name} para a vaga: ${vaga.title}")
                println("+================================================+")

                print("Deseja curtir outro candidato? (s/n): ")
                answer = scanner.nextLine().toLowerCase().trim()
            }

            pause()
        }
        catch (Exception e) {
            println("+================================================+")
            println("Erro ao processar curtidas da empresa: ${e.message}")
            println("+================================================+")
            pause()
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
            if (vagasCurtidas == null || vagasCurtidas.isEmpty()) {
                println("+================================================+")
                println("${candidato.name} não possui curtidas")
                println("+================================================+")
            } else {
                println("+================================================+")
                sistemaCurtidas.listCurtidasCandidato(vagasCurtidas)
            }

            pause()
        }
        catch (Exception e) {
            println("+================================================+")
            println("Falha ao listar candidatos. Erro: ${e}")
            println("+================================================+")
            pause()
        }
    }

    void cliListMatches() {
        println("+================================================+")
        println("|                 Todos os matches               |")
        println("+================================================+")

        if (sistemaCurtidas.allMatches.isEmpty()) {
            println("Nenhum match registrado até agora.")
            println("+================================================+")
            pause()
            return
        }

        sistemaCurtidas.allMatches.eachWithIndex { m, index ->
            println("Match: ${index + 1}")
            println("Candidato: ${m.candidato.name} (${m.candidato.cpf})")
            println("Empresa: ${m.empresa.name} (${m.empresa.cnpj})")
            println("Vaga: ${m.vaga.title} [id=${m.vaga.id}]")
            println("Data: ${m.dateMatch}")
            println("+================================================+")
        }
        pause()
    }

    void cliListMatchesCandidato() {
        println("+================================================+")
        println("|            Matches do candidato                |")
        println("+================================================+")

        def candidato = cliChoiceCandidato()
        if (candidato == null) return

        def matchesCandidato = sistemaCurtidas.allMatches.findAll { it.candidato?.cpf == candidato.cpf }

        if (matchesCandidato.isEmpty()) {
            println("Nenhum match para ${candidato.name}.")
            println("+================================================+")
            pause()
            return
        }

        matchesCandidato.eachWithIndex { m, index ->
            println("Match: ${index + 1}")
            println("Empresa: ${m.empresa.name} (${m.empresa.cnpj})")
            println("Vaga: ${m.vaga.title} [id=${m.vaga.id}]")
            println("Data: ${m.dateMatch}")
            println("+================================================+")
        }
        pause()
    }

    void cliListMatchesEmpresa() {
        println("+================================================+")
        println("|             Matches da empresa                 |")
        println("+================================================+")

        def empresa = cliChoiceEmpresa()
        if (empresa == null) return

        def matchesEmpresa = sistemaCurtidas.allMatches.findAll { it.empresa?.cnpj == empresa.cnpj }

        if (matchesEmpresa.isEmpty()) {
            println("Nenhum match para ${empresa.name}.")
            println("+================================================+")
            pause()
            return
        }

        matchesEmpresa.eachWithIndex { m, index ->
            println("Match: ${index + 1}")
            println("Candidato: ${m.candidato.name} (${m.candidato.cpf})")
            println("Vaga: ${m.vaga.title} [id=${m.vaga.id}]")
            println("Data: ${m.dateMatch}")
            println("+================================================+")
        }
        pause()
    }

    // Auxiliar method
    Integer readInt(String prompt) {
        print(prompt)
        String value = scanner.nextLine()?.trim()
        if (value == null || value.isEmpty()) {
            return null
        }
        try {
            return Integer.parseInt(value)
        } catch (NumberFormatException ignored) {
            return null
        }
    }

    // Auxiliar method
    private List<String> parseSkills(String input) {
        if (input == null || input.trim().isEmpty()) {
            return []
        }
        return input.split(",")
                .collect { it.trim() }
                .findAll { !it.isEmpty() }
    }

    // Auxiliar method
    private void pause() {
        print("Aperte \"Enter\" para continuar")
        scanner.nextLine()
    }
}
