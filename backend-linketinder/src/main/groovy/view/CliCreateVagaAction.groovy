package view

class CliCreateVagaAction {
    static void cliCreateVaga(Cli cli) {
        println("+================================================+")
        println("|                 Cadastrar vaga                 |")
        println("+================================================+")
        try {
            def empresa = cli.cliChoiceEmpresa()
            if (empresa == null) return

            println("+================================================+")
            println("${empresa.nome}, preencha as informações da vaga: ")
            print("Título: ")
            String title = cli.scanner.nextLine()
            print("Descrição da vaga: ")
            String description = cli.scanner.nextLine()
            print("Estado: ")
            String estado = cli.scanner.nextLine()
            print("Cidade: ")
            String cidade = cli.scanner.nextLine()
            print("Lista de habilidades requeridas (separado por ','): ")
            String input = cli.scanner.nextLine()
            List<String> skillsRequests = cli.parseSkills(input)

            cli.vagaServices.createVaga(title, description, estado, cidade, empresa, skillsRequests)

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
}
