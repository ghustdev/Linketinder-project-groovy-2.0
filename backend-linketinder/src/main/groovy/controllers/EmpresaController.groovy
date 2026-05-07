package controllers

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import entities.Empresa
import services.CurtidaService
import services.EmpresaService

import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet(name = "empresas", value = "/empresas")
class EmpresaController extends HttpServlet {

    private EmpresaService empresaService
    private CurtidaService curtidaService
    private final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create()

    @Override
    void init() {
        def ctx = getServletContext()
        this.empresaService = (EmpresaService) ctx.getAttribute("empresaService")
        this.curtidaService = (CurtidaService) ctx.getAttribute("curtidaService")
    }

    // Criar empresa
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        request.characterEncoding = "UTF-8"
        response.contentType = "application/json"
        response.characterEncoding = "UTF-8"

        try {
            Empresa empresa = gson.fromJson(request.reader, Empresa.class)

            empresaService.criarEmpresa(empresa)

            response.status = HttpServletResponse.SC_CREATED
            response.writer.print(gson.toJson([mensagem: "Empresa cadastrada!"]))
        } catch (Exception e) {
            response.status = HttpServletResponse.SC_BAD_REQUEST
            response.writer.print(gson.toJson([erro: "Erro ao criar empresa - " + e.message]))
        }
    }

    // Listar todas ou buscar uma única empresa
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        response.contentType = "application/json"
        response.characterEncoding = "UTF-8"

        try {
            String cnpj = request.getParameter("cnpj")

            if (cnpj != null) {
                Empresa empresa = empresaService.buscarEmpresa(cnpj)
                response.writer.print(gson.toJson(empresa))
            } else {
                List<Empresa> lista = empresaService.listarEmpresas()
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
    import entities.Empresa
    import entities.Match
    import entities.Vaga
    import services.CurtidaService
    import services.EmpresaService

    class EmpresaController {
        private final EmpresaService empresaService
        private final CurtidaService curtidaService

        EmpresaController(EmpresaService empresaService, CurtidaService curtidaService) {
            this.empresaService = empresaService
            this.curtidaService = curtidaService
        }

        Empresa criarEmpresa(Empresa empresa) {
            return empresaService.criarEmpresa(empresa)
        }

        List<Empresa> listarEmpresas() {
            return empresaService.listarEmpresas()
        }

        Empresa buscarEmpresaPorCnpj(String cnpj) {
            return empresaService.buscarEmpresa(cnpj)
        }

        List<Curtida> listarCurtidasRecebidas(Empresa empresa) {
            return curtidaService.listarCurtidasRecebidasEmpresa(empresa)
        }

        Match empresaCurteCandidato(Empresa empresa, Candidato candidato, Vaga vaga) {
            return curtidaService.empresaCurteCandidato(empresa, candidato, vaga)
        }

        List<Match> listarMatchesDaEmpresa(Empresa empresa) {
            return curtidaService.obterTodosMatches().findAll { it.empresa?.cnpj == empresa?.cnpj }
        }
    }
    */
}
