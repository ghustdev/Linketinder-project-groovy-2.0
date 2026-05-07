package controllers

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import entities.Candidato
import services.CandidatoService
import services.CurtidaService

import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet(name = "candidatos",value = "/candidatos")
class CandidatoController extends HttpServlet {

    private CandidatoService candidatoService
    private CurtidaService curtidaService
    private final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create()

    @Override
    void init() {
        def ctx = getServletContext()
        this.candidatoService = (CandidatoService) ctx.getAttribute("candidatoService")
        this.curtidaService = (CurtidaService) ctx.getAttribute("curtidaService")
    }

    // Criar candidato
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        request.characterEncoding = "UTF-8"
        response.contentType = "application/json"
        response.characterEncoding = "UTF-8"

        try {
            Candidato candidato = gson.fromJson(request.reader, Candidato.class)

            candidatoService.criarCandidato(candidato)

            response.status = HttpServletResponse.SC_CREATED
            response.writer.print(gson.toJson([mensagem: "Candidato cadastrado!"]))
        }
        catch (Exception e) {
            response.status = HttpServletResponse.SC_BAD_REQUEST
            response.writer.print(gson.toJson([erro: "Erro ao criar candidato - " + e.message] + " - " + e.printStackTrace()))
        }
    }

    // Listar todos ou buscar um único candidato
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        response.contentType = "application/json"
        response.characterEncoding = "UTF-8"

        try {
            String cpf = request.getParameter("cpf")

            if (cpf != null) {
                Candidato candidato = candidatoService.buscarCandidato(cpf)
                response.writer.print(gson.toJson(candidato))
            } else {
                List<Candidato> lista = candidatoService.listarCandidatos()
                response.writer.print(gson.toJson(lista))
            }

            response.status = HttpServletResponse.SC_OK
        } catch (Exception e) {
            response.status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR
            response.writer.print(gson.toJson([erro: "Erro no Servlet: " + e.message]))
        }
    }

//    Candidato criarCandidato(Candidato candidato) {
//        return candidatoService.criarCandidato(candidato)
//    }
//
//    List<Candidato> listarCandidatos() {
//        return candidatoService.listarCandidatos()
//    }
//
//    Candidato buscarCandidatoPorCpf(String cpf) {
//        return candidatoService.buscarCandidato(cpf)
//    }
//
//    List<Curtida> listarCurtidasDoCandidato(Candidato candidato) {
//        return curtidaService.listarCurtidasCandidato(candidato)
//    }
//
//    List<Match> listarMatchesDoCandidato(Candidato candidato) {
//        return curtidaService.obterTodosMatches().findAll { it.candidato?.cpf == candidato?.cpf }
//    }
}
