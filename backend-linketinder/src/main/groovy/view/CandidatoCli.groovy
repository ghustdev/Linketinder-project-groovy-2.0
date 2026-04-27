package view

import controllers.CandidatoController
import exceptions.ExecucaoException
import entities.Candidato
import entities.Competencia

import java.time.LocalDate

class CandidatoCli {
    private final IEntradaConsolePadrao io
    private final CandidatoController candidatoController

    CandidatoCli(IEntradaConsolePadrao io, CandidatoController candidatoController) {
        this.io = io
        this.candidatoController = candidatoController
    }

    void criarCandidato() {
        try {
            println("+================================================+")
            println("|               Cadastrar Candidato              |")
            println("+================================================+")
            print("Nome: "); String nome = io.lerLinha()
            print("Sobrenome: "); String sobrenome = io.lerLinha()
            print("Data de nascimento (YYYY-MM-DD): "); String dataNascimento = io.lerLinha()
            print("Email: "); String email = io.lerLinha()
            print("CPF: "); String cpf = io.lerLinha()
            print("País: "); String pais = io.lerLinha()
            print("CEP: "); String cep = io.lerLinha()
            print("Descrição: "); String descricao = io.lerLinha()
            print("Formação: "); String formacao = io.lerLinha()
            print("LinkedIn: "); String linkedin = io.lerLinha()
            print("Lista de competências (separado por ','): "); String entrada = io.lerLinha()

            List<String> competencias = EditarEntradaCompetencia.formatarCompetencias(entrada)

            def nascimento = java.sql.Date.valueOf(LocalDate.parse(dataNascimento))
            def comps = competencias.collect { new Competencia(nome: it) }
            Candidato candidatoEntrada = new Candidato(
                    nome: nome,
                    sobrenome: sobrenome,
                    email: email,
                    cpf: cpf,
                    nascimento: nascimento,
                    pais: pais,
                    cep: cep,
                    descricao: descricao,
                    formacao: formacao,
                    linkedin: linkedin,
                    competencias: comps
            )

            Candidato candidato = candidatoController.criarCandidato(candidatoEntrada)

            println("+================================================+")
            println("Candidato, ${candidato.nome}, cadastrado com sucesso!")
            println("+================================================+")
            io.pausar()
        } catch (ExecucaoException e) {
            println("+================================================+")
            println("Falha ao cadastrar candidato. Erro: ${e.message}")
            println("+================================================+")
            io.pausar()
        } catch (Exception e) {
            println("+================================================+")
            println("Falha ao cadastrar candidato. Tente novamente. Erro: ${e.message}")
            println("+================================================+")
            io.pausar()
        }
    }

    void listarCandidatos() {
        try {
            println("+================================================+")
            println("|              Listagem de Candidatos            |")
            println("+================================================+")
            candidatoController.listarCandidatos().each { candidato ->
                println "Candidato: ${candidato.id}"
                println "CPF: ${candidato.cpf}"
                println "Nome: ${candidato.nome} ${candidato.sobrenome}"
                println "Email: ${candidato.email}"
                println "Descrição: ${candidato.descricao}"
                println "Nascimento: ${candidato.nascimento}"
                println "País: ${candidato.pais}"
                println "CEP: ${candidato.cep}"
                println("-----------------------")
                println "Competências:"
                candidato.competencias.each { println "   - ${it.nome}" }
                println("-----------------------")
                println("+================================================+")
            }
            io.pausar()
            if (!candidatoController.listarCandidatos() || candidatoController.listarCandidatos() == null) {
                println "Não há candidatos cadastrados"
                println("+================================================+")
                io.pausar()
                return
            }
        } catch (ExecucaoException e) {
            println("+================================================+")
            println("Falha ao listar candidatos. Erro: ${e.message}")
            println("+================================================+")
            io.pausar()
        } catch (Exception e) {
            println("+================================================+")
            println("Falha ao listar candidatos. Tente novamente. Erro: ${e.message}")
            println("+================================================+")
            io.pausar()
        }
    }

    void listarCandidatosEmVagas() {
        try {
            println("+================================================+")
            println("|              Listagem de Candidatos            |")
            println("+================================================+")
            candidatoController.listarCandidatos().each { candidato ->
                println "Candidato: ${candidato.id}"
                println "CPF: ${candidato.cpf}"
                println "Nome: ${candidato.nome} ${candidato.sobrenome}"
                println "Email: ${candidato.email}"
                println "Descrição: ${candidato.descricao}"
                println "Nascimento: ${candidato.nascimento}"
                println "País: ${candidato.pais}"
                println "CEP: ${candidato.cep}"
                println("-----------------------")
                println "Competências:"
                candidato.competencias.each { println "   - ${it.nome}" }
                println("-----------------------")
                println("+================================================+")
            }
            if (!candidatoController.listarCandidatos() || candidatoController.listarCandidatos() == null) {
                println "Não há candidatos cadastrados"
                println("+================================================+")
                io.pausar()
                return
            }
        } catch (ExecucaoException e) {
            println("+================================================+")
            println("Falha ao listar candidatos. Erro: ${e.message}")
            println("+================================================+")
            io.pausar()
        } catch (Exception e) {
            println("+================================================+")
            println("Falha ao listar candidatos. Tente novamente. Erro: ${e.message}")
            println("+================================================+")
            io.pausar()
        }
    }

    void listarCurtidasCandidato() {
        println("+================================================+")
        println("|             Curtidas do Candidato              |")
        println("+================================================+")

        Candidato candidato = selecionarCandidatoPorCpf()
        if (candidato == null) return

        try {
            println("${candidato.nome} curtiu essas vagas: ")
            def vagasCurtidas = candidatoController.listarCurtidasDoCandidato(candidato)
            if (!vagasCurtidas) {
                println("+================================================+")
                println("${candidato.nome} não possui curtidas")
                println("+================================================+")
            } else {
                println("+================================================+")
                vagasCurtidas.each { curtida ->
                    println("Vaga: ${curtida.vaga.titulo}")
                    println("Empresa: ${curtida.empresa.nome}")
                    println("CNPJ: ${curtida.empresa.cnpj}")
                    println("+================================================+")
                }
            }
            io.pausar()
        } catch (ExecucaoException e) {
            println("+================================================+")
            println("Falha ao listar curtidas. Erro: ${e.message}")
            println("+================================================+")
            io.pausar()
        } catch (Exception e) {
            println("+================================================+")
            println("Falha ao listar curtidas. Tente novamente. Erro: ${e.message}")
            println("+================================================+")
            io.pausar()
        }
    }

    void listarMatchesCandidato() {
        println("+================================================+")
        println("|            Matches do Candidato                |")
        println("+================================================+")

        def candidato = selecionarCandidatoPorCpf()
        if (candidato == null) return

        def matchesCandidato = candidatoController.listarMatchesDoCandidato(candidato)

        if (matchesCandidato.isEmpty()) {
            println("Nenhum match para ${candidato.nome}.")
            println("+================================================+")
            io.pausar()
            return
        }

        matchesCandidato.eachWithIndex { match, index ->
            println("Match: ${index + 1}")
            println("Empresa: ${match.empresa.nome} (${match.empresa.cnpj})")
            println("Vaga: ${match.vaga.titulo} [id=${match.vaga.id}]")
            println("Data: ${match.dataMatch}")
            println("+================================================+")
        }
        io.pausar()
    }

    Candidato selecionarCandidatoPorCpf() {
        try {
            this.listarCandidatosEmVagas()

            println("+================================================+")
            println("|            Entre com um Candidato              |")
            println("+================================================+")
            println()
            print("Escolha o candidato (pelo CPF): ")
            def cpf = io.lerLinha()
            def candidato = candidatoController.buscarCandidatoPorCpf(cpf)
            if (candidato == null) {
                println("+================================================+")
                println("Esse candidato não existe!")
                println("+================================================+")
                io.pausar()
                return null
            }
            return candidato
        } catch (ExecucaoException e) {
            println("+================================================+")
            println("Falha ao confirmar candidato. Erro: ${e.message}")
            println("+================================================+")
            io.pausar()
        } catch (Exception e) {
            println("+================================================+")
            println("Falha ao confirmar candidato. Tente novamente. Erro: ${e.message}")
            println("+================================================+")
            io.pausar()
        }
        return null
    }
}
