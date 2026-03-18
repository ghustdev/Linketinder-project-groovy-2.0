package view

class CliCreateEmpresaAction {
    static void cliCreateEmpresa(Cli cli) {
        try {
            println("+================================================+")
            println("|                Cadastrar empresa               |")
            println("+================================================+")
            print("Nome: ")
            String name = cli.scanner.nextLine()
            print("Email: ")
            String email = cli.scanner.nextLine()
            print("CNPJ: ")
            String cnpj = cli.scanner.nextLine()
            print("País: ")
            String country = cli.scanner.nextLine()
            print("Estado: ")
            String state = cli.scanner.nextLine()
            print("CEP: ")
            String cep = cli.scanner.nextLine()
            print("Descrição: ")
            String description = cli.scanner.nextLine()
            print("Lista de habilidades (separado por ','): ")
            String input = cli.scanner.nextLine()
            List<String> skills = cli.parseSkills(input)

            cli.pessoaServices.createEmpresa(name, email, cnpj, country, state, cep, description, skills)

            println("+================================================+")
            println("Empresa cadastrada com sucesso!")
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

