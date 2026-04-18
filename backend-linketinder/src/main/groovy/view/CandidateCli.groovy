package view

import model.Candidate

class CandidateCli {
    static void createCandidate(MainCli cli) {
        try {
            println("+================================================+")
            println("|               Cadastrar Candidato              |")
            println("+================================================+")
            print("Nome: ")
            String name = cli.scanner.nextLine()
            print("Sobrenome: ")
            String lastName = cli.scanner.nextLine()
            print("Data de nascimento (YYYY-MM-DD): ")
            String birthDate = cli.scanner.nextLine()
            print("Email: ")
            String email = cli.scanner.nextLine()
            print("CPF: ")
            String cpf = cli.scanner.nextLine()
            print("País: ")
            String country = cli.scanner.nextLine()
            print("CEP: ")
            String cep = cli.scanner.nextLine()
            print("Descrição: ")
            String description = cli.scanner.nextLine()
            print("Senha (6+ dígitos): ")
            String password = cli.scanner.nextLine()
            print("Lista de habilidades (separado por ','): ")
            String input = cli.scanner.nextLine()

            List<String> skills = cli.parseSkills(input)

            Candidate candidate = cli.personServices.createCandidate(name, lastName, birthDate, email, cpf, country, cep, description, password, skills)

            println("+================================================+")
            println("Candidato, ${candidate.name}, cadastrado com sucesso!")
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

    static void listCandidates(MainCli cli) {
        try {
            println("+================================================+")
            println("|              Listagem de Candidatos            |")
            println("+================================================+")

            cli.personServices.listCandidates()

            cli.pause()
        }
        catch (Exception e) {
            println("+================================================+")
            println("Falha ao listar candidatos - ${e.printStackTrace()}")
            println("+================================================+")
            cli.pause()
        }
    }

    static void listCandidateLikes(MainCli cli) {
        println("+================================================+")
        println("|             Curtidas do Candidato              |")
        println("+================================================+")

        def candidate = cli.chooseCandidate()
        if (candidate == null) return

        try {
            def likedVacancies = candidate.listLikes()
            if (likedVacancies == null || likedVacancies.isEmpty()) {
                println("+================================================+")
                println("${candidate.name} não possui curtidas")
                println("+================================================+")
            } else {
                println("+================================================+")
                cli.likeServices.listCandidateLikes(likedVacancies)
            }

            cli.pause()
        }
        catch (Exception e) {
            println("+================================================+")
            println("Falha ao listar candidatos - ${e.printStackTrace()}")
            println("+================================================+")
            cli.pause()
        }
    }

    static void listCandidateMatches(MainCli cli) {
        println("+================================================+")
        println("|            Matches do Candidato                |")
        println("+================================================+")

        def candidate = cli.chooseCandidate()
        if (candidate == null) return

        def candidateMatches = cli.likeServices.allMatches.findAll { it.candidate?.cpf == candidate.cpf }

        if (candidateMatches.isEmpty()) {
            println("Nenhum match para ${candidate.name}.")
            println("+================================================+")
            cli.pause()
            return
        }

        candidateMatches.eachWithIndex { match, index ->
            println("Match: ${index + 1}")
            println("Empresa: ${match.company.name} (${match.company.cnpj})")
            println("Vaga: ${match.vacancy.name} [id=${match.vacancy.id}]")
            println("Data: ${match.matchDate}")
            println("+================================================+")
        }
        cli.pause()
    }

    static Candidate chooseCandidate(MainCli cli) {
        println("+================================================+")
        println("|            Entre com um Candidato              |")
        println("+================================================+")
        Candidate candidate = null

        try {
            cli.personServices.listCandidates()

            println("+================================================+")
            print("Escolha o candidato (pelo CPF): ")
            def cpf = cli.scanner.nextLine()

            candidate = cli.vacancyServices.searchCandidate(cpf)

            if (candidate == null) {
                println("+================================================+")
                println("Esse candidato não existe!")
                println("+================================================+")
                cli.pause()
                return null
            }
        }
        catch (Exception e) {
            println("+================================================+")
            println("Falha ao confirmar candidato - ${e.printStackTrace()}")
            println("+================================================+")
            cli.pause()
        }
        return candidate
    }
}
