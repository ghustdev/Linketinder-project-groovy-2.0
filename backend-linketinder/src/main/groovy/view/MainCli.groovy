package view

import model.Candidate
import model.Company
import repository.Repository
import services.LikeServices
import services.PersonServices
import services.VacancyServices

class MainCli {
    PersonServices personServices
    VacancyServices vacancyServices
    LikeServices likeServices
    Repository repository

    static Scanner scanner = new Scanner(System.in)

    MainCli(PersonServices personServices, Repository repository, VacancyServices vacancyServices, LikeServices likeServices) {
        this.personServices = personServices
        this.repository = repository
        this.vacancyServices = vacancyServices
        this.likeServices = likeServices
    }

    void cliMenu() {
        MenuCli.cliMainMenu(this)
    }

    void createCompany() {
        CompanyCli.createCompany(this)
    }

    void createCandidate() {
        CandidateCli.createCandidate(this)
    }

    void listCandidates() {
        CandidateCli.listCandidates(this)
    }

    void listCompanies() {
        CompanyCli.listCompanies(this)
    }

    Company chooseCompany() {
        return CompanyCli.chooseCompany(this)
    }

    Candidate chooseCandidate() {
        return CandidateCli.chooseCandidate(this)
    }

    void createVacancy() {
        VacancyCli.createVacancy(this)
    }

    void listVacancies() {
        VacancyCli.listVacancies(this)
    }

    void reviewCompanyLikes() {
        CompanyCli.reviewCompanyLikes(this)
    }

    void listCandidateLikes() {
        CandidateCli.listCandidateLikes(this)
    }

    void listMatches() {
        MatchesCli.listMatches(this)
    }

    void listCandidateMatches() {
        CandidateCli.listCandidateMatches(this)
    }

    void listCompanyMatches() {
        CompanyCli.listCompanyMatches(this)
    }

    static List<String> parseSkills(String input) {
        if (input == null || input.trim().isEmpty()) {
            return []
        }
        return input.split(",")
                .collect { it.trim() }
                .findAll { !it.isEmpty() }
    }

    static Integer readInt(String prompt) {
        print(prompt)
        String value = scanner.nextLine()?.trim()
        if (value == null || value.isEmpty()) {
            return null
        }
        try {
            return Integer.parseInt(value)
        } catch (NumberFormatException ignored) {
            return null
        }
    }

    static void pause(MainCli cli) {
        print("Aperte \"Enter\" para continuar")
        cli.scanner.nextLine()
    }
}
