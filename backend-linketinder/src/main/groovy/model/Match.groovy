package model


import java.time.LocalDateTime

class Match {

    Candidate candidate
    Company company
    Vacancy vacancy
    LocalDateTime matchDate

    boolean isMatch(List<Like> companyLikedCandidates, List<Like> candidateLikedVacancies) {
        if (candidate == null || company == null || vacancy == null || companyLikedCandidates == null || candidateLikedVacancies == null) {
            return false
        }

        String target = buildKey(candidate.cpf, company.cnpj, vacancy.id)

        List<String> companyLikes = companyLikedCandidates.collect {
            buildKey(it.candidate.cpf, it.company.cnpj, it.vacancy.id)
        }
        List<String> candidateLikes = candidateLikedVacancies.collect {
            buildKey(it.candidate.cpf, it.company.cnpj, it.vacancy.id)
        }

        return companyLikes.contains(target) && candidateLikes.contains(target)
    }

    static String buildKey(String cpf, String cnpj, Long vacancyId) {
        return "${cpf}|${cnpj}|${vacancyId}"
    }
}
