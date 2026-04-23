package repositories

import entities.Curtida
import entities.Match

interface IMatchRepository {
    List<Curtida> obterTodasCurtidasCandidatos()

    List<Match> obterTodosMatches()

    void adicionarCurtidaCandidato(Curtida curtida)

    void adicionarMatchSeAusente(Match match)
}
