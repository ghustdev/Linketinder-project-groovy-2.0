package view

class CliCreateCandidatoAction {
    static void cliCreateCandidato(Cli cli) {
        try {
            println("+================================================+")
            println("|               Cadastrar candidato              |")
            println("+================================================+")
            print("Nome: ")
            String name = cli.scanner.nextLine()
            print("Email: ")
            String email = cli.scanner.nextLine()
            print("CPF: ")
            String cpf = cli.scanner.nextLine()
            Integer old = cli.readInt("Idade: ")
            if (old == null || old < 0) {
                println("+================================================+")
                println("Idade inválida.")
                println("+================================================+")
                cli.pause()
                return
            }
            print("Estado: ")
            String state = cli.scanner.nextLine()
            print("CEP: ")
            String cep = cli.scanner.nextLine()
            print("Descrição: ")
            String description = cli.scanner.nextLine()
            print("Lista de habilidades (separado por ','): ")
            String input = cli.scanner.nextLine()
            List<String> skills = cli.parseSkills(input)

            cli.pessoaServices.createCandidato(name, email, cpf, old, state, cep, description, skills)

            println("+================================================+")
            println("Candidato cadastrado com sucesso!")
            println("+================================================+")
            cli.pause()
        }
        catch (Exception e) {
            println("+================================================+")
            println("Falha ao cadastrar candidato. Erro: ${e}")
            println("+================================================+")
            cli.pause()
        }
    }
}

