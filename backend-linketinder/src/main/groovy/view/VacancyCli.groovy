package view

class VacancyCli {
    static void createVacancy(MainCli cli) {
        println("+================================================+")
        println("|                 Cadastrar Vaga                 |")
        println("+================================================+")
        try {
            def company = cli.chooseCompany()
            if (company == null) return

            println("+================================================+")
            println("${company.name}, preencha as informações da vaga: ")
            print("Título: ")
            String name = cli.scanner.nextLine()
            print("Descrição da vaga: ")
            String description = cli.scanner.nextLine()
            print("Estado: ")
            String state = cli.scanner.nextLine()
            print("Cidade: ")
            String city = cli.scanner.nextLine()
            print("Lista de habilidades requeridas (separado por ','): ")
            String input = cli.scanner.nextLine()
            List<String> requiredSkills = cli.parseSkills(input)

            cli.vacancyServices.createVacancy(name, description, state, city, company, requiredSkills)

            println("+================================================+")
            println("Vaga cadastrada com sucesso!")
            println("+================================================+")
            cli.pause()
        }
        catch (Exception e) {
            println("+================================================+")
            println("Falha ao cadastrar vaga. Erro: ${e}")
            println("+================================================+")
            cli.pause()
        }
    }

    static void listVacancies(MainCli cli) {
        println("+================================================+")
        println("|                Visualizar Vagas                |")
        println("+================================================+")
        def candidate = cli.chooseCandidate()
        if (candidate == null) return

        println("+================================================+")
        println("Seja bem vindo(a), ${candidate.name}.")
        println("+================================================+")
        cli.pause()

        try {
            cli.vacancyServices.listVacancies()

            print("${candidate.name}, deseja curtir alguma vaga? (s/n): ")
            def answer = cli.scanner.nextLine().toLowerCase().trim()

            while (answer == "s") {
                println("+================================================+")
                Integer idInput = cli.readInt("Escolha o Id da vaga que deseja curtir: ")
                if (idInput == null) {
                    println("+================================================+")
                    println("Id inválido.")
                    println("+================================================+")
                    cli.pause()
                    return
                }

                Long id = idInput as Long
                def vacancy = cli.vacancyServices.searchVacancyById(id)

                if (vacancy == null) {
                    println("+================================================+")
                    println("Essa vaga não existe!")
                    println("+================================================+")
                    cli.pause()
                    return
                }

                cli.likeServices.candidateLikesVacancy(candidate, vacancy)

                println("+================================================+")
                println("${candidate.name} curtiu a vaga '${vacancy.name}' da Empresa ${vacancy.company.name}. Vaga CURTIDA com sucesso!")
                println("+================================================+")

                print("Deseja curtir outra vaga? (s/n): ")
                answer = cli.scanner.nextLine().toLowerCase().trim()
            }
        }
        catch (Exception e) {
            println("+================================================+")
            println("Falha ao listar vagas ou ao curtir. Erro: ${e}")
            println("+================================================+")
            cli.pause()
        }
    }
}
