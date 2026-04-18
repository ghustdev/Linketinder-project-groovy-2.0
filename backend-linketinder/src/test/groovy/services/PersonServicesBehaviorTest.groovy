package services

import dao.CandidateDao
import dao.CompanyDao
import dao.SkillDao
import spock.lang.Specification

class PersonServicesBehaviorTest extends Specification {

    def "listagem de candidatos e empresas deve executar sem excecao"() {
        given:
        def candidateDao = Mock(CandidateDao)
        def companyDao = Mock(CompanyDao)
        def skillDao = Mock(SkillDao)
        def service = new PersonServices(candidateDao, companyDao, skillDao)
        candidateDao.listAll() >> []
        companyDao.listAll() >> []

        when:
        service.listCandidates()
        service.listCompanies()

        then:
        noExceptionThrown()
    }
}
