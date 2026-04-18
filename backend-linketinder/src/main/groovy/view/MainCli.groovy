package view

import model.Candidate
import model.Company
import repository.Repository
import services.LikeSystem
import services.PersonServices
import services.VacancyServices

class Cli {
    PersonServices personServices
    VacancyServices vacancyServices
    Repository repository
    LikeSystem likeSystem

    Scanner scanner = new Scanner(System.in)

    Cli(PersonServices personServices, Repository repository, VacancyServices vacancyServices, LikeSystem likeSystem) {
        this.personServices = personServices
        this.repository = repository
        this.vacancyServices = vacancyServices
        this.likeSystem = likeSystem
    }

    void cliMenu() {
        CliMenuAction.cliMenu(this)
    }

    void createCompany() {
        CliCreateCompanyAction.createCompany(this)
    }

    void createCandidate() {
        CliCreateCandidateAction.createCandidate(this)
    }

    void listCandidates() {
        CliListCandidatesAction.listCandidates(this)
    }

    void listCompanies() {
        CliListCompaniesAction.listCompanies(this)
    }

    Company chooseCompany() {
        return CliChooseCompanyAction.chooseCompany(this)
    }

    Candidate chooseCandidate() {
        return CliChooseCandidateAction.chooseCandidate(this)
    }

    void createVacancy() {
        CliCreateVacancyAction.createVacancy(this)
    }

    void listVacancies() {
        CliListVacanciesAction.listVacancies(this)
    }

    void reviewCompanyLikes() {
        CliReviewCompanyLikesAction.reviewCompanyLikes(this)
    }

    void listCandidateLikes() {
        CliListCandidateLikesAction.listCandidateLikes(this)
    }

    void listMatches() {
        CliListMatchesAction.listMatches(this)
    }

    void listCandidateMatches() {
        CliListCandidateMatchesAction.listCandidateMatches(this)
    }

    void listCompanyMatches() {
        CliListCompanyMatchesAction.listCompanyMatches(this)
    }

    // Auxiliar method
    Integer readInt(String prompt) {
        return CliReadIntAction.readInt(this, prompt)
    }

    // Auxiliar method
    List<String> parseSkills(String input) {
        return CliParseSkillsAction.parseSkills(input)
    }

    // Auxiliar method
    void pause() {
        CliPauseAction.pause(this)
    }
}
