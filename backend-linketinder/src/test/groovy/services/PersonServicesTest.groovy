package services

import dao.CandidateDao
import dao.CompanyDao
import dao.SkillDao
import model.Candidate
import model.Company
import spock.lang.Specification
import spock.lang.Subject

class PersonServicesTest extends Specification {
    def candidateDao = Mock(CandidateDao)
    def companyDao = Mock(CompanyDao)
    def skillDao = Mock(SkillDao)

    @Subject
    def service = new PersonServices(candidateDao, companyDao, skillDao)

    def "deve criar um candidato usando os daos"() {
        given:
        def candidate = Candidate.builder().name("Gustavo").cpf("123").build()
        candidateDao.findByCpf("123") >>> [null, candidate]
        candidateDao.insert(*_) >> 10L
        skillDao.findIdsByNames(["Java", "Groovy"]) >> [Java: 1L, Groovy: 2L]

        when:
        def result = service.createCandidate("Gustavo", "Silva", "1990-01-01", "gustavo@email.com", "123", "BR", "74000", "Dev", "secret", ["Java", "Groovy"])

        then:
        result == candidate
        1 * skillDao.insert(["Java", "Groovy"])
        1 * candidateDao.addSkills(10L, [1L, 2L])
    }

    def "deve criar uma empresa usando os daos"() {
        given:
        def company = Company.builder().name("ZG").cnpj("123/0001").build()
        companyDao.findByCnpj("123/0001") >>> [null, company]
        companyDao.findByEmail("zg@email.com") >> null
        companyDao.insert("ZG", "123/0001", "zg@email.com", "Saúde", "BR", "74000", "secret") >> 20L

        when:
        def result = service.createCompany("ZG", "zg@email.com", "123/0001", "BR", "74000", "Saúde", "secret")

        then:
        result == company
    }
}
