package controllers

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import entities.Vaga
import services.CurtidaService
import services.VagaService

import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet(name = "vagas", value = "/vagas")
class VagaController extends HttpServlet {

    private VagaService vagaService
    private CurtidaService curtidaService
    private final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create()

    @Override
    void init() {
        def ctx = getServletContext()
        this.vagaService = (VagaService) ctx.getAttribute("vagaService")
        this.curtidaService = (CurtidaService) ctx.getAttribute("curtidaService")
    }

    // Criar vaga
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        request.characterEncoding = "UTF-8"
        response.contentType = "application/json"
        response.characterEncoding = "UTF-8"

        try {
            Vaga vaga = gson.fromJson(request.reader, Vaga.class)

            vagaService.criarVaga(vaga)

            response.status = HttpServletResponse.SC_CREATED
            response.writer.print(gson.toJson([mensagem: "Vaga cadastrada!"]))
        } catch (Exception e) {
            response.status = HttpServletResponse.SC_BAD_REQUEST
            response.writer.print(gson.toJson([erro: "Erro ao criar vaga - " + e.message]))
        }
    }

    // Listar todas ou buscar uma única vaga
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        response.contentType = "application/json"
        response.characterEncoding = "UTF-8"

        try {
            String idParam = request.getParameter("id")

            if (idParam != null) {
                Long id = Long.valueOf(idParam)
                Vaga vaga = vagaService.buscarVagaPorId(id)
                response.writer.print(gson.toJson(vaga))
            } else {
                List<Vaga> lista = vagaService.listarVagas()
                response.writer.print(gson.toJson(lista))
            }

            response.status = HttpServletResponse.SC_OK
        } catch (Exception e) {
            response.status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR
            response.writer.print(gson.toJson([erro: "Erro no Servlet: " + e.message]))
        }
    }

    /*
    CODIGO ANTIGO (CLI) - MANTIDO APENAS COMO REFERENCIA

    import entities.Candidato
    import entities.Curtida
    import entities.Vaga
    import services.CurtidaService
    import services.VagaService

    class VagaController {
        private final VagaService vagaService
        private final CurtidaService curtidaService

        VagaController(VagaService vagaService, CurtidaService curtidaService) {
            this.vagaService = vagaService
            this.curtidaService = curtidaService
        }

        Vaga criarVaga(Vaga vaga) {
            return vagaService.criarVaga(vaga)
        }

        List<Vaga> listarVagas() {
            return vagaService.listarVagas()
        }

        Vaga buscarVagaPorId(Long id) {
            return vagaService.buscarVagaPorId(id)
        }

        Curtida candidatoCurteVaga(Candidato candidato, Vaga vaga) {
            return curtidaService.candidatoCurteVaga(candidato, vaga)
        }
    }
    */
}
