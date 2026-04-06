package view

import model.Empresa

class CliCreateEmpresaAction {
    static void cliCreateEmpresa(Cli cli) {
        try {
            println("+================================================+")
            println("|                Cadastrar empresa               |")
            println("+================================================+")
            print("Nome: ")
            String nome = cli.scanner.nextLine()
            print("Email: ")
            String email = cli.scanner.nextLine()
            print("CNPJ: ")
            String cnpj = cli.scanner.nextLine()
            print("País: ")
            String pais = cli.scanner.nextLine()
            print("CEP: ")
            String cep = cli.scanner.nextLine()
            print("Descrição: ")
            String description = cli.scanner.nextLine()
            print("Senha (6+ dígitos): ")
            String senha = cli.scanner.nextLine()

            Empresa empresa = cli.pessoaServices.createEmpresa(nome, email, cnpj, pais, cep, description, senha)

            println("+================================================+")
            println("Empresa, ${empresa.nome}, cadastrada com sucesso!")
            println("+================================================+")

            println("+================================================+")
            println("Agora cadastre uma vaga para a empresa ${empresa.nome}:")
            print("Título: ")
            String title = cli.scanner.nextLine()
            print("Descrição da vaga: ")
            String vagaDescription = cli.scanner.nextLine()
            print("Estado: ")
            String estado = cli.scanner.nextLine()
            print("Cidade: ")
            String cidade = cli.scanner.nextLine()
            print("Lista de habilidades requeridas (separado por ','): ")
            String input = cli.scanner.nextLine()
            List<String> skillsRequests = cli.parseSkills(input)

            cli.vagaServices.createVaga(title, vagaDescription, estado, cidade, empresa, skillsRequests)

            println("+================================================+")
            println("Vaga cadastrada com sucesso!")
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
