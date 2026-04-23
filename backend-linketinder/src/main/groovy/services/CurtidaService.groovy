package services

import entities.Candidato
import entities.Curtida
import entities.Empresa
import entities.Match
import entities.Vaga
import exceptions.RecursoDuplicadoException
import exceptions.ValidacaoException
import repositories.IMatchRepository

import java.time.LocalDateTime

class CurtidaService {
    private final IMatchRepository matchRepositorio

    CurtidaService(IMatchRepository matchRepositorio) {
        this.matchRepositorio = matchRepositorio
    }

    List<Curtida> obterTodasCurtidasCandidatos() {
        return matchRepositorio.obterTodasCurtidasCandidatos()
    }

    List<Match> obterTodosMatches() {
        return matchRepositorio.obterTodosMatches()
    }

    Curtida candidatoCurteVaga(Candidato candidato, Vaga vaga) {
        boolean jaCurtiu = obterTodasCurtidasCandidatos().any {
            it.candidato?.cpf == candidato?.cpf && it.vaga?.id == vaga.id
        }
        if (jaCurtiu) {
            throw new RecursoDuplicadoException("${candidato.nome} já curtiu a vaga '${vaga.titulo}'.")
        }
        Curtida curtida = new Curtida(candidato: candidato, vaga: vaga, empresa: vaga.empresa, dataCurtida: LocalDateTime.now())
        matchRepositorio.adicionarCurtidaCandidato(curtida)
        return curtida
    }

    Match empresaCurteCandidato(Empresa empresa, Candidato candidato, Vaga vaga) {
        if (candidato == null || vaga == null) {
            throw new ValidacaoException("Candidato e vaga são obrigatórios.")
        }
        if (vaga.empresa.cnpj != empresa.cnpj) {
            throw new ValidacaoException("A vaga '${vaga.titulo}' não pertence à empresa ${empresa.nome}.")
        }
        boolean jaTemMatch = obterTodosMatches().any {
            it.candidato?.cpf == candidato.cpf && it.empresa?.cnpj == empresa.cnpj && it.vaga?.id == vaga.id
        }
        if (jaTemMatch) {
            throw new RecursoDuplicadoException("${empresa.nome} já curtiu o candidato ${candidato.cpf}.")
        }

        boolean candidatoJaCurtiu = obterTodasCurtidasCandidatos().any {
            it.candidato.cpf == candidato.cpf && it.vaga.id == vaga.id
        }

        if (!candidatoJaCurtiu) return null

        Match match = new Match(candidato: candidato, empresa: empresa, vaga: vaga, dataMatch: LocalDateTime.now())
        matchRepositorio.adicionarMatchSeAusente(match)
        return match
    }

    List<Curtida> listarCurtidasCandidato(Candidato candidato) {
        return obterTodasCurtidasCandidatos().findAll {
            it.candidato?.cpf == candidato?.cpf
        }
    }

    List<Curtida> listarCurtidasRecebidasEmpresa(Empresa empresa) {
        return obterTodasCurtidasCandidatos().findAll { it.vaga.empresa.cnpj == empresa.cnpj }
    }
}
