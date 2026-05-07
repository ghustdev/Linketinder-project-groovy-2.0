package config

import dao.*
import services.*
import repositories.*
import javax.servlet.ServletContextListener
import javax.servlet.ServletContextEvent
import javax.servlet.annotation.WebListener

@WebListener
class DependencyInjectionListener implements ServletContextListener {
	
	@Override
	// ServletContextEvent = mensageiro global de dependências
	void contextInitialized(ServletContextEvent sce) {
		try {
			ConexaoDB.definirFabrica(new ConexaoJDBCFactory());
			
			CompetenciaDao competenciaDao = new CompetenciaDao()
			CandidatoDao candidatoDao = new CandidatoDao()
			EmpresaDao empresaDao = new EmpresaDao()
			VagaDao vagaDao = new VagaDao(empresaDao)
			
			EmpresaService empresaService = new EmpresaService(empresaDao, competenciaDao)
			CandidatoService candidatoService = new CandidatoService(candidatoDao, competenciaDao)
			VagaService vagaService = new VagaService(vagaDao, competenciaDao)
			IMatchRepository matchRepositorio = new MatchRepositoryParaMock()
			CurtidaService curtidaService = new CurtidaService(matchRepositorio)
			
			def ctx = sce.servletContext
			ctx.setAttribute("candidatoService", candidatoService)
			ctx.setAttribute("curtidaService", curtidaService)
			ctx.setAttribute("empresaService", empresaService)
			ctx.setAttribute("vagaService", vagaService)
			
		} catch (Exception e) {
			println "Erro ao configurar dependências: ${e.message}"
		}
	}
	
	@Override
	void contextDestroyed(ServletContextEvent sce) {
		try {
			ConexaoDB.fecharConexao()
			println "Conexão com banco encerrada."
		} catch (Exception ignored) {}
	}
}