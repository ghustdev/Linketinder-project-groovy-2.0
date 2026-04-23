package services

import entities.Empresa
import entities.Vaga
import exceptions.OperacaoPersistenciaException
import exceptions.RecursoDuplicadoException
import exceptions.RegraDeNegocioException
import repositories.ICompetenciaRepository
import repositories.IVagaRepository
import spock.lang.Specification
import spock.lang.Subject

class VagaServiceTest extends Specification {

    IVagaRepository vagaDao = Mock()
    ICompetenciaRepository competenciaDao = Mock()

    @Subject
    VagaService service = new VagaService(vagaDao, competenciaDao)

    Empresa empresa = new Empresa(id: 1L, nome: "Tech", cnpj: "11")

    def "cria vaga com sucesso"() {
        given:
        List<String> competencias = ["Java"]
        Vaga vaga = new Vaga(id: 1L, titulo: "Dev")

        when:
        def resultado = service.criarVaga("Dev", "Desc", "GO", "Goiania", empresa, competencias)

        then:
        resultado == vaga
        1 * vagaDao.buscarPorEmpresaENome(empresa.id, "Dev") >> null
        1 * vagaDao.inserir(empresa.id, "Dev", "Desc", "GO", "Goiania") >> 1L
        1 * competenciaDao.inserir("Java") >> 1L
        1 * vagaDao.adicionarCompetencias(1L, [1L])
        1 * vagaDao.buscarPorId(1L) >> vaga
    }

    def "lanca excecao quando empresa e nula"() {
        when:
        service.criarVaga("Dev", "Desc", "GO", "Goiania", null, ["Java"])

        then:
        thrown(RegraDeNegocioException)
    }

    def "lanca excecao quando lista de competencias esta vazia"() {
        when:
        service.criarVaga("Dev", "Desc", "GO", "Goiania", empresa, [])

        then:
        thrown(RegraDeNegocioException)
    }

    def "lanca excecao quando vaga ja existe para a empresa"() {
        when:
        service.criarVaga("Dev", "Desc", "GO", "Goiania", empresa, ["Java"])

        then:
        1 * vagaDao.buscarPorEmpresaENome(empresa.id, "Dev") >> new Vaga(titulo: "Dev")
        thrown(RecursoDuplicadoException)
    }

    def "lanca excecao quando dao nao persiste vaga"() {
        when:
        service.criarVaga("Dev", "Desc", "GO", "Goiania", empresa, ["Java"])

        then:
        1 * vagaDao.buscarPorEmpresaENome(empresa.id, "Dev") >> null
        1 * vagaDao.inserir(*_) >> null
        thrown(OperacaoPersistenciaException)
    }

    def "buscarVagaPorId retorna vaga existente"() {
        given:
        Long vagaId = 1L
        Vaga vaga = new Vaga(id: vagaId)

        when:
        def resultado = service.buscarVagaPorId(vagaId)

        then:
        1 * vagaDao.buscarPorId(vagaId) >> vaga
        resultado == vaga
    }

    def "buscarVagaPorId retorna null para id inexistente"() {
        given:
        Long vagaId = 999L

        when:
        def resultado = service.buscarVagaPorId(vagaId)

        then:
        1 * vagaDao.buscarPorId(vagaId) >> null
        resultado == null
    }

    def "listarVagas delega ao dao"() {
        when:
        def lista = service.listarVagas()

        then:
        1 * vagaDao.listarTodos() >> [new Vaga(titulo: "Dev")]
        lista.size() == 1
    }
}
