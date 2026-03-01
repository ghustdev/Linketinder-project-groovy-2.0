package view

// Future implementation (modularization)
class CliCreateEmpresa {

    Scanner scanner = new Scanner(System.in)

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
}
