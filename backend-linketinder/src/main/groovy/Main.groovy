import dao.CandidatoDao
import dao.CompetenciaDao
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
            CompetenciaDao competenciaDao = new CompetenciaDao()
            CandidatoDao candidatoDao = new CandidatoDao()
            EmpresaDao empresaDao = new EmpresaDao()
            VagaDao vagaDao = new VagaDao(empresaDao)

            EmpresaService empresaServico = new EmpresaService(empresaDao, competenciaDao)
            CandidatoService candidatoServico = new CandidatoService(candidatoDao, competenciaDao)
            VagaService vagaServico = new VagaService(vagaDao, competenciaDao)
            IMatchRepository matchRepositorio = new MatchRepositoryParaMock()
            CurtidaService curtidaServico = new CurtidaService(matchRepositorio)

            IEntradaConsolePadrao io = new EntradaConsolePadrao()

            CandidatoCli candidatoCli = new CandidatoCli(io, candidatoServico, vagaServico, curtidaServico)
            EmpresaCli empresaCli = new EmpresaCli(io, empresaServico, vagaServico, curtidaServico)
            MatchCli matchCli = new MatchCli(io, curtidaServico)
            VagaCli vagaCli = new VagaCli(io, vagaServico, curtidaServico, empresaCli, candidatoCli)

            MenuCli menuCli = new MenuCli(io, candidatoCli, empresaCli, matchCli, vagaCli)

            menuCli.menuPrincipal()
        } catch (ExecucaoException e) {
            System.err.println("Erro ao iniciar aplicação. Erro: ${e.message}")
        } catch (Exception e) {
            System.err.println("Erro inesperado ao iniciar aplicação.")
        }
    }
}
