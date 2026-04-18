package repository

import dao.CandidateDao
import dao.CompanyDao
import dao.VacancyDao
import model.Candidate
import model.Company
import model.Like
import model.Match
import model.Vacancy

class Repository {
    List<Candidate> candidates = []
    List<Company> companies = []
    List<Vacancy> vacancies = []
    List<Like> allCandidateLikes = []
    List<Match> allMatches = []

    Repository() {
    }

    void loadFromDao(CandidateDao candidateDao, CompanyDao companyDao, VacancyDao vacancyDao) {
        candidates = candidateDao.listAll()
        companies = companyDao.listAll()
        vacancies = vacancyDao.listAll()
    }
}
