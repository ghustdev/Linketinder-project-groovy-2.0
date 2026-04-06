package view

import model.Candidato

class CliCreateCandidatoAction {
    static void cliCreateCandidato(Cli cli) {
        try {
            println("+================================================+")
            println("|               Cadastrar candidato              |")
            println("+================================================+")
            print("Nome: ")
            String nome = cli.scanner.nextLine()
            print("Sobrenome: ")
            String sobrenome = cli.scanner.nextLine()
            print("Data de nascimento (YYYY-MM-DD): ")
            String dataNascimento = cli.scanner.nextLine()
            print("Email: ")
            String email = cli.scanner.nextLine()
            print("CPF: ")
            String cpf = cli.scanner.nextLine()
            print("País: ")
            String pais = cli.scanner.nextLine()
            print("CEP: ")
            String cep = cli.scanner.nextLine()
            print("Descrição: ")
            String description = cli.scanner.nextLine()
            print("Senha (6+ dígitos): ")
            String senha = cli.scanner.nextLine()
            print("Lista de habilidades (separado por ','): ")
            String input = cli.scanner.nextLine()
            List<String> skills = cli.parseSkills(input)

            Candidato candidato = cli.pessoaServices.createCandidato(nome, sobrenome, dataNascimento, email, cpf, pais, cep, description, senha, skills)

            println("+================================================+")
            println("Candidato, ${candidato.nome}, cadastrado com sucesso!")
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
