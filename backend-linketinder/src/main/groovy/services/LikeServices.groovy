package services

import model.Candidate
import model.Company
import model.Like
import model.Match
import model.Vacancy

class LikeServices {

    List<Like> allCandidateLikes = []
    List<Match> allMatches = []

    void listCandidateLikes(List<Like> likedVacancies) {
        likedVacancies.each { like ->
            println("Vaga: ${like.vacancy.name}")
            println("Candidato: ${like.candidate.name}")
            println("CPF: ${like.candidate.cpf}")
            println("Empresa: ${like.company.name}")
            println("CNPJ: ${like.company.cnpj}")
            println("+================================================+")
        }
    }

    void listCompanyReceivedLikes(List<Like> receivedLikes) {
        receivedLikes.each { like ->
            def candidate = like.candidate
            def status = "Pendente"
            if (like.company.hasLikedCandidate(candidate, like.vacancy)) {
                def match = new Match(candidate: candidate, company: like.company, vacancy: like.vacancy)
                status = match.isMatch(like.company.likedCandidates, allCandidateLikes) ? "MATCH!" : "Pendente"
            }
            println("Candidato: ${candidate.name}")
            println("CPF: ${candidate.cpf}")
            println("Id Vaga: ${like.vacancy.id}")
            println("Skills: ${candidate.skills.collect { it.name }.join(', ')}")
            println("Vaga: ${like.vacancy.name}")
            println("--------------------------------------------------")
            println("Status: ${status}")
            println("+================================================+")
        }
    }

    Like candidateLikesVacancy(Candidate candidate, Vacancy vacancy) {
        Like like = candidate.likeVacancy(vacancy)
        allCandidateLikes.add(like)
        return like
    }

    Match companyLikesCandidate(Company company, Candidate candidate, Vacancy vacancy) {
        def match = company.likeCandidate(candidate, vacancy, allCandidateLikes)
        if (match != null && !allMatches.any {
            it.candidate.cpf == match.candidate.cpf &&
                    it.company.cnpj == match.company.cnpj &&
                    it.vacancy?.id == match.vacancy?.id
        }) {
            allMatches.add(match)
        }
        return match
    }
}
