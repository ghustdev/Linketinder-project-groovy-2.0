package model

import exceptions.DuplicateResourceException
import groovy.transform.ToString
import groovy.transform.builder.Builder

import java.time.LocalDateTime

@Builder
@ToString(includeSuper = true, includeNames = true)
class Candidate extends Person {
    Long id
    String name
    String lastName
    String email
    String description
    String country
    String cep
    String password
    List<Skill> skills = []
    String cpf
    Date birth
    List<Like> likedVacancies = []

    Like likeVacancy(Vacancy vacancy) {
        if (likedVacancies == null) {
            likedVacancies = []
        }

        if (hasLikedVacancy(vacancy)) {
            throw new DuplicateResourceException("Erro: ${name} já curtiu a vaga '${vacancy.name}'.")
        }

        LocalDateTime likeTimestamp = LocalDateTime.now()

        def like = new Like(candidate: this, vacancy: vacancy, company: vacancy.company, likeDate: likeTimestamp)
        likedVacancies.add(like)
        return like
    }

    boolean hasLikedVacancy(Vacancy vacancy) {
        likedVacancies.any { it.vacancy.id == vacancy.id }
    }

    List<Like> listLikes() {
        return likedVacancies
    }
}
