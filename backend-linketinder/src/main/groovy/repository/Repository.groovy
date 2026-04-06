package repository

import model.Candidato
import model.Curtida
import model.Empresa
import model.Match
import model.Vaga

class Repository {
    List<Candidato> arrayCandidatos = []
    List<Empresa> arrayEmpresas = []
    List<Vaga> arrayVagas = []
    List<Curtida> allCurtidasCandidatos = []
    List<Match> allMatches = []

    Repository() {
    }
}
