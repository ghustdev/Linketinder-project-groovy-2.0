package model

import exceptions.DuplicateResourceException
import exceptions.ValidationException
import groovy.transform.ToString
import groovy.transform.builder.Builder

import java.time.LocalDateTime

@Builder
@ToString(includeSuper = true, includeNames = true)
class Company extends Person {
    Long id
    String name
    String email
    String description
    String cep
    String country
    String password
    String cnpj
    List<Like> likedCandidates = []

    Match likeCandidate(Candidate candidate, Vacancy vacancy, List<Like> allCandidateLikes) {
        if (likedCandidates == null) {
            likedCandidates = []
        }
        if (candidate == null || vacancy == null) {
            throw new ValidationException("Candidato e vaga são obrigatórios.")
        }
        if (vacancy.company.cnpj != this.cnpj) {
            throw new ValidationException("A vaga '${vacancy.name}' não pertence à empresa ${name}.")
        }
        if (hasLikedCandidate(candidate, vacancy)) {
            throw new DuplicateResourceException("Erro: ${name} já curtiu o candidato ${candidate.cpf}.")
        }

        likedCandidates.add(new Like(
                candidate: candidate,
                vacancy: vacancy,
                company: this,
                likeDate: LocalDateTime.now()
        ))

        def match = new Match(
                candidate: candidate,
                company: this,
                vacancy: vacancy,
                matchDate: LocalDateTime.now()
        )

        return match.isMatch(likedCandidates, allCandidateLikes) ? match : null
    }

    boolean hasLikedCandidate(Candidate candidate, Vacancy vacancy) {
        if (likedCandidates == null) {
            likedCandidates = []
        }
        likedCandidates.any {
            it.candidate.cpf == candidate.cpf && it.vacancy.id == vacancy.id
        }
    }

    List<Like> listLikes() {
        return likedCandidates
    }
}
