package view

import model.Company
import model.Vacancy

class CompanyCli {
    static void createCompany(MainCli cli) {
        try {
            println("+================================================+")
            println("|                Cadastrar Empresa               |")
            println("+================================================+")
            print("Nome: ")
            String name = cli.scanner.nextLine()
            print("Email: ")
            String email = cli.scanner.nextLine()
            print("CNPJ: ")
            String cnpj = cli.scanner.nextLine()
            print("País: ")
            String country = cli.scanner.nextLine()
            print("CEP: ")
            String cep = cli.scanner.nextLine()
            print("Descrição: ")
            String description = cli.scanner.nextLine()
            print("Senha (6+ dígitos): ")
            String password = cli.scanner.nextLine()

            Company company = cli.personServices.createCompany(name, email, cnpj, country, cep, description, password)

            println("+================================================+")
            println("Empresa, ${company.name}, cadastrada com sucesso!")
            println("+================================================+")

            println("+================================================+")
            println("Agora cadastre uma vaga para a empresa ${company.name}:")
            print("Título: ")
            String vacancyName = cli.scanner.nextLine()
            print("Descrição da vaga: ")
            String vacancyDescription = cli.scanner.nextLine()
            print("Estado: ")
            String state = cli.scanner.nextLine()
            print("Cidade: ")
            String city = cli.scanner.nextLine()
            print("Lista de habilidades requeridas (separado por ','): ")
            String input = cli.scanner.nextLine()

            List<String> requiredSkills = cli.parseSkills(input)

            Vacancy vacancy = cli.vacancyServices.createVacancy(vacancyName, vacancyDescription, state, city, company, requiredSkills)

            println("+================================================+")
            println("Vaga, ${vacancy.name}, cadastrada com sucesso!")
            println("+================================================+")
            cli.pause()
        }
        catch (Exception e) {
            println("+================================================+")
            println("Falha ao cadastrar candidato - ${e.printStackTrace()}")
            println("+================================================+")
            cli.pause()
        }
    }

    static void listCompanies(MainCli cli) {
        try {
            println("+================================================+")
            println("|              Listagem de empresas              |")
            println("+================================================+")

            cli.personServices.listCompanies()

            cli.pause()
        }
        catch (Exception e) {
            println("+================================================+")
            println("Falha ao listar empresas - ${e.printStackTrace()}")
            println("+================================================+")
            cli.pause()
        }
    }

    static void reviewCompanyLikes(MainCli cli) {
        println("+================================================+")
        println("|            Candidatos que curtiram a           |")
        println("|                    Empresa                     |")
        println("+================================================+")

        def company = cli.chooseCompany()
        if (company == null) return

        try {
            def receivedLikes = cli.likeServices.allCandidateLikes.findAll { it.vacancy.company.cnpj == company.cnpj }

            if (receivedLikes.isEmpty()) {
                println("+================================================+")
                println("Nenhum candidato curtiu suas vagas ainda.")
                println("+================================================+")
                cli.pause()
                return
            }

            println("+================================================+")
            cli.likeServices.listCompanyReceivedLikes(receivedLikes)

            print("Deseja curtir algum candidato de volta? (s/n): ")
            def answer = cli.scanner.nextLine().toLowerCase().trim()

            while (answer == "s") {
                println("+================================================+")
                print("Digite o CPF do candidato da lista acima: ")
                String cpf = cli.scanner.nextLine().trim()

                Integer idVagaInput = cli.readInt("Digite o ID da vaga que o candidato curtiu: ")
                if (idVagaInput == null) {
                    println("+================================================+")
                    println("ID de vaga inválido.")
                    println("+================================================+")
                    cli.pause()
                    continue
                }

                Long vacancyId = (idVagaInput as Long)
                def vacancySearch = cli.vacancyServices.searchVacancyById(vacancyId)

                if (vacancySearch == null) {
                    println("+================================================+")
                    println("Essa vaga não existe!")
                    println("+================================================+")
                    cli.pause()
                    return
                }

                def selectedLike = receivedLikes.find {
                    it.candidate.cpf == cpf && it.vacancy.id == vacancyId
                }
                if (selectedLike == null) {
                    println("+================================================+")
                    println("Candidato inválido. Use somente CPF e vaga exibidos na lista acima.")
                    println("+================================================+")
                    cli.pause()
                    continue
                }

                def candidate = selectedLike.candidate
                def vacancy = selectedLike.vacancy
                def matchResult = cli.likeServices.companyLikesCandidate(company, candidate, vacancy)
                if (matchResult == null) {
                    println("+================================================+")
                    println("Não foi possível gerar match para essa seleção.")
                    println("+================================================+")
                    cli.pause()
                    continue
                }

                println("+================================================+")
                println("É UM MATCH! Você e ${candidate.name} agora estão conectados.")
                println("${company.name} curtiu o candidato ${candidate.name} para a vaga: ${vacancy.name}")
                println("+================================================+")

                print("Deseja curtir outro candidato? (s/n): ")
                answer = cli.scanner.nextLine().toLowerCase().trim()
            }

            cli.pause()
        }
        catch (Exception e) {
            println("+================================================+")
            println("Erro ao processar curtidas da empresa - ${e.printStackTrace()}")
            println("+================================================+")
            cli.pause()
        }
    }

    static void listCompanyMatches(MainCli cli) {
        println("+================================================+")
        println("|             Matches da Empresa                 |")
        println("+================================================+")

        def company = cli.chooseCompany()
        if (company == null) return

        def companyMatches = cli.likeServices.allMatches.findAll { it.company?.cnpj == company.cnpj }

        if (companyMatches.isEmpty()) {
            println("Nenhum match para ${company.name}.")
            println("+================================================+")
            cli.pause()
            return
        }

        companyMatches.eachWithIndex { match, index ->
            println("Match: ${index + 1}")
            println("Candidato: ${match.candidate.name} (${match.candidate.cpf})")
            println("Vaga: ${match.vacancy.name} [id=${match.vacancy.id}]")
            println("Data: ${match.matchDate}")
            println("+================================================+")
        }
        cli.pause()
    }

    static Company chooseCompany(MainCli cli) {
        println("+================================================+")
        println("|            Entre com uma Empresa               |")
        println("+================================================+")
        Company company = null

        try {
            cli.personServices.listCompanies()

            println("+================================================+")
            print("Escolha uma empresa (pelo CNPJ): ")
            def cnpj = cli.scanner.nextLine()

            company = cli.vacancyServices.searchCompany(cnpj)

            if (company == null) {
                println("+================================================+")
                println("Essa empresa não existe!")
                println("+================================================+")
                cli.pause()
                return company
            }
        }
        catch (Exception e) {
            println("+================================================+")
            println("Falha ao confirmar empresa. Erro: ${e}")
            println("+================================================+")
            cli.pause()
        }
        return company
    }
}
