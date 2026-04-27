package services

import entities.Empresa
import exceptions.OperacaoPersistenciaException
import exceptions.RecursoDuplicadoException
import repositories.IEmpresaRepository
import repositories.ICompetenciaRepository
import spock.lang.Specification
import spock.lang.Subject

class EmpresaServiceTest extends Specification {

    IEmpresaRepository empresaDao = Mock()
    ICompetenciaRepository competenciaDao = Mock()

    @Subject
    EmpresaService service = new EmpresaService(empresaDao, competenciaDao)

    def "cria empresa com sucesso"() {
        given:
        String cnpj = "11"
        String email = "tech@email.com"
        Empresa empresa = new Empresa(nome: "Tech", cnpj: cnpj)
        Empresa entrada = new Empresa(nome: "Tech", email: email, cnpj: cnpj, pais: "BR", cep: "74000", descricao: "Desc")

        when:
        def resultado = service.criarEmpresa(entrada)

        then:
        resultado == empresa
        1 * empresaDao.buscarPorCnpj(cnpj) >> null
        1 * empresaDao.inserir(_ as Empresa) >> 1L
        1 * empresaDao.buscarPorCnpj(cnpj) >> empresa
    }

    def "lanca excecao quando CNPJ ja cadastrado"() {
        given:
        String cnpj = "11"
        Empresa entrada = new Empresa(cnpj: cnpj)

        when:
        service.criarEmpresa(entrada)

        then:
        1 * empresaDao.buscarPorCnpj(cnpj) >> new Empresa(cnpj: cnpj)
        thrown(RecursoDuplicadoException)
    }

    def "lanca excecao quando dao nao persiste empresa"() {
        given:
        String cnpj = "11"
        String email = "tech@email.com"
        Empresa entrada = new Empresa(nome: "Tech", email: email, cnpj: cnpj, pais: "BR", cep: "74000", descricao: "Desc")

        when:
        service.criarEmpresa(entrada)

        then:
        1 * empresaDao.buscarPorCnpj(cnpj) >> null
        1 * empresaDao.inserir(_ as Empresa) >> null
        1 * empresaDao.buscarPorCnpj(cnpj) >> null
        thrown(OperacaoPersistenciaException)
    }

    def "listarEmpresas delega ao dao"() {
        when:
        def lista = service.listarEmpresas()

        then:
        1 * empresaDao.listarTodos() >> [new Empresa(nome: "Tech")]
        lista.size() == 1
    }

    def "buscarEmpresa retorna empresa pelo cnpj"() {
        given:
        String cnpj = "11"
        Empresa empresa = new Empresa(cnpj: cnpj)

        when:
        def resultado = service.buscarEmpresa(cnpj)

        then:
        1 * empresaDao.buscarPorCnpj(cnpj) >> empresa
        resultado == empresa
    }

    def "buscarEmpresa retorna null para cnpj inexistente"() {
        given:
        String cnpj = "000"

        when:
        def resultado = service.buscarEmpresa(cnpj)

        then:
        1 * empresaDao.buscarPorCnpj(cnpj) >> null
        resultado == null
    }
}
