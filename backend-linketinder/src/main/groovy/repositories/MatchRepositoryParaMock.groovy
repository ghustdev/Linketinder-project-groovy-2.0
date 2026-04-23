package repositories

import entities.Curtida
import entities.Match

class MatchRepositoryParaMock implements IMatchRepository {
    private final List<Curtida> todasCurtidasCandidatos = []
    private final List<Match> todosMatches = []

    @Override
    List<Curtida> obterTodasCurtidasCandidatos() {
        return todasCurtidasCandidatos
    }

    @Override
    List<Match> obterTodosMatches() {
        return todosMatches
    }

    @Override
    void adicionarCurtidaCandidato(Curtida curtida) {
        todasCurtidasCandidatos.add(curtida)
    }

    @Override
    void adicionarMatchSeAusente(Match match) {
        if (match == null) {
            return
        }
        boolean jaExiste = todosMatches.any {
            it.candidato.cpf == match.candidato.cpf &&
                    it.empresa.cnpj == match.empresa.cnpj &&
                    it.vaga?.id == match.vaga?.id
        }
        if (!jaExiste) {
            todosMatches.add(match)
        }
    }
}
