package services

import dao.CandidateDao
import dao.CompanyDao
import dao.SkillDao
import dao.VacancyDao
import model.Company
import model.Vacancy
import spock.lang.Specification
import spock.lang.Subject

class VacancyServicesTest extends Specification {
    def vacancyDao = Mock(VacancyDao)
    def companyDao = Mock(CompanyDao)
    def candidateDao = Mock(CandidateDao)
    def skillDao = Mock(SkillDao)

    @Subject
    def service = new VacancyServices(vacancyDao, companyDao, candidateDao, skillDao)

    def "deve buscar empresa por cnpj"() {
        given:
        def company = Company.builder().cnpj("ABC").build()
        companyDao.findByCnpj("abc") >> company

        expect:
        service.searchCompany("abc") == company
    }

    def "deve retornar null para empresa e candidato inexistentes"() {
        expect:
        service.searchCompany("nao-existe") == null
        service.searchCandidate("nao-existe") == null
    }

    def "deve buscar vaga por id existente e retornar null para id inexistente"() {
        given:
        def vacancy = Vacancy.builder().id(1L).build()
        vacancyDao.findById(1L) >> vacancy
        vacancyDao.findById(999L) >> null

        expect:
        service.searchVacancyById(1L) == vacancy
        service.searchVacancyById(999L) == null
    }

    def "deve criar vaga usando os daos"() {
        given:
        def company = Company.builder().id(7L).build()
        def vacancy = Vacancy.builder().id(8L).name("Nova Vaga").build()
        vacancyDao.findByCompanyAndName(7L, "Nova Vaga") >> null
        vacancyDao.insert(7L, "Nova Vaga", "Desc", "GO", "Goiania") >> 8L
        skillDao.findIdsByNames(["Groovy"]) >> [Groovy: 3L]
        vacancyDao.findById(8L) >> vacancy

        when:
        def result = service.createVacancy("Nova Vaga", "Desc", "GO", "Goiania", company, ["Groovy"])

        then:
        result == vacancy
        1 * skillDao.insert(["Groovy"])
        1 * vacancyDao.addSkills(8L, [3L])
    }

    def "listagem de vagas deve executar sem lançar excecao"() {
        given:
        vacancyDao.listAll() >> []

        when:
        service.listVacancies()

        then:
        noExceptionThrown()
    }
}
