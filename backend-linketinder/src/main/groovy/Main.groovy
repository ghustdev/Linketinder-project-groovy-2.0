import controllers.CandidatoController
import controllers.EmpresaController
import controllers.MatchController
import controllers.VagaController
import dao.CandidatoDao
import dao.CompetenciaDao
import dao.ConexaoDB
import dao.ConexaoJDBCFactory
import dao.EmpresaDao
import dao.VagaDao
import exceptions.ExecucaoException
import repositories.MatchRepositoryParaMock
import repositories.IMatchRepository
import services.CandidatoService
import services.CurtidaService
import services.EmpresaService
import services.VagaService
import view.CandidatoCli
import view.EmpresaCli
import view.IEntradaConsolePadrao
import view.EntradaConsolePadrao
import view.MatchCli
import view.MenuCli
import view.VagaCli

class Main {
    static void main(String[] args) {
        try {
            try {
                ConexaoDB.definirFabrica(new ConexaoJDBCFactory())

                CompetenciaDao competenciaDao = new CompetenciaDao()
                CandidatoDao candidatoDao = new CandidatoDao()
                EmpresaDao empresaDao = new EmpresaDao()
                VagaDao vagaDao = new VagaDao(empresaDao)

                EmpresaService empresaServico = new EmpresaService(empresaDao, competenciaDao)
                CandidatoService candidatoServico = new CandidatoService(candidatoDao, competenciaDao)
                VagaService vagaServico = new VagaService(vagaDao, competenciaDao)
                IMatchRepository matchRepositorio = new MatchRepositoryParaMock()
                CurtidaService curtidaServico = new CurtidaService(matchRepositorio)

                CandidatoController candidatoController = new CandidatoController(candidatoServico, curtidaServico)
                VagaController vagaController = new VagaController(vagaServico, curtidaServico)
                EmpresaController empresaController = new EmpresaController(empresaServico, curtidaServico)
                MatchController matchController = new MatchController(curtidaServico)

                IEntradaConsolePadrao io = new EntradaConsolePadrao()

                CandidatoCli candidatoCli = new CandidatoCli(io, candidatoController)
                EmpresaCli empresaCli = new EmpresaCli(io, empresaController, vagaController)
                MatchCli matchCli = new MatchCli(io, matchController)
                VagaCli vagaCli = new VagaCli(io, vagaController, empresaCli, candidatoCli)

                MenuCli menuCli = new MenuCli(io, candidatoCli, empresaCli, matchCli, vagaCli)

                menuCli.menuPrincipal()
            } finally {
                try {
                    ConexaoDB.fecharConexao()
                } catch (Exception ignored) {}
            }
        } catch (ExecucaoException e) {
            System.err.println("Erro ao iniciar aplicação. Erro: ${e.message}")
        } catch (Exception e) {
            System.err.println("Erro inesperado ao iniciar aplicação. Erro: ${e.message}")
        }
    }
}
