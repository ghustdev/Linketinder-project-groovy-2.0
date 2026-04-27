package services

import entities.Candidato
import entities.Competencia
import exceptions.OperacaoPersistenciaException
import exceptions.RecursoDuplicadoException
import repositories.ICandidatoRepository
import repositories.ICompetenciaRepository
import spock.lang.Specification
import spock.lang.Subject

class CandidatoServiceTest extends Specification {

    ICandidatoRepository candidatoDao = Mock()
    ICompetenciaRepository competenciaDao = Mock()

    @Subject
    CandidatoService service = new CandidatoService(candidatoDao, competenciaDao)

    def "cria candidato com sucesso"() {
        given:
        String cpf = "123"
        Candidato candidato = new Candidato(nome: "Ana", cpf: cpf)
        Candidato entrada = new Candidato(
                nome: "Ana",
                sobrenome: "Silva",
                email: "ana@email.com",
                cpf: cpf,
                nascimento: java.sql.Date.valueOf("1990-01-01"),
                pais: "BR",
                cep: "74000",
                descricao: "Dev",
                formacao: "UFMG",
                linkedin: "linkedin.com/ana",
                competencias: [new Competencia(nome: "Java")]
        )

        when:
        def resultado = service.criarCandidato(entrada)

        then:
        resultado == candidato
        1 * candidatoDao.buscarPorCpf(cpf) >> null
        1 * candidatoDao.inserir(_ as Candidato) >> 1L
        1 * competenciaDao.inserir("Java") >> 1L
        1 * candidatoDao.adicionarCompetencias(1L, [1L])
        1 * candidatoDao.buscarPorCpf(cpf) >> candidato
    }

    def "lanca excecao quando CPF ja cadastrado"() {
        given:
        String cpf = "123"
        Candidato entrada = new Candidato(cpf: cpf)

        when:
        service.criarCandidato(entrada)

        then:
        1 * candidatoDao.buscarPorCpf(cpf) >> new Candidato(cpf: cpf)
        thrown(RecursoDuplicadoException)
    }

    def "lanca excecao quando dao nao retorna id"() {
        given:
        String cpf = "123"
        Candidato entrada = new Candidato(cpf: cpf, competencias: [new Competencia(nome: "Java")])

        when:
        service.criarCandidato(entrada)

        then:
        1 * candidatoDao.buscarPorCpf(cpf) >> null
        1 * candidatoDao.inserir(_ as Candidato) >> null
        thrown(OperacaoPersistenciaException)
    }

    def "listarCandidatos delega ao dao"() {
        when:
        def lista = service.listarCandidatos()

        then:
        1 * candidatoDao.listarTodos() >> [new Candidato(nome: "Ana", cpf: "123")]
        lista.size() == 1
    }

    def "buscarCandidato retorna candidato pelo cpf"() {
        given:
        String cpf = "123"
        Candidato candidato = new Candidato(cpf: cpf)

        when:
        def resultado = service.buscarCandidato(cpf)

        then:
        1 * candidatoDao.buscarPorCpf(cpf) >> candidato
        resultado == candidato
    }

    def "buscarCandidato retorna null para cpf inexistente"() {
        given:
        String cpf = "000"

        when:
        def resultado = service.buscarCandidato(cpf)

        then:
        1 * candidatoDao.buscarPorCpf(cpf) >> null
        resultado == null
    }
}
