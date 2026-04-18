import dao.CandidateDao
import dao.CompanyDao
import dao.SkillDao
import dao.VacancyDao
import repository.Repository
import services.LikeServices
import services.PersonServices
import services.VacancyServices
import view.MainCli

class Main {
    static void main(String[] args) {
        try {
            def repository = new Repository()

            def skillDao = new SkillDao()
            def candidateDao = new CandidateDao()
            def companyDao = new CompanyDao()
            def vacancyDao = new VacancyDao(companyDao)

            def personServices = new PersonServices(candidateDao, companyDao, skillDao)
            def vacancyServices = new VacancyServices(vacancyDao, companyDao, candidateDao, skillDao)
            def likeServices = new LikeServices()

            def cli = new MainCli(personServices, repository, vacancyServices, likeServices)

            repository.loadFromDao(candidateDao, companyDao, vacancyDao)

            cli.cliMenu()
        } catch (Exception e) {
            e.printStackTrace()
        }
    }
}
