package view

import model.Candidato

class CliChoiceCandidatoAction {
    static Candidato cliChoiceCandidato(Cli cli) {
        Candidato candidato = null

        try {
            cli.pessoaServices.listCandidatos()

            print("Escolha o candidato (pelo CPF): ")
            def cpf = cli.scanner.nextLine()

            candidato = cli.vagaServices.searchCandidato(cpf)

            if (candidato == null) {
                println("+================================================+")
                println("Esse candidato não existe!")
                println("+================================================+")
                cli.pause()
                return null
            }
        }
        catch (Exception e) {
            println("+================================================+")
            println("Falha ao confirmar candidato. Erro: ${e}")
            println("+================================================+")
            cli.pause()
        }
        return candidato
    }
}

