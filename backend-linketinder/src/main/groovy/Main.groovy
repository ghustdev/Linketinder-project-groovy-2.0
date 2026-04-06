import dao.CandidatoDao
import dao.CompetenciaDao
import dao.EmpresaDao
import dao.VagaDao
import repository.Repository
import services.PessoaServices
import services.SistemaCurtidas
import services.VagaServices
import view.Cli

class Main {
    static void main(String[] args) {
        try {
            // Constructors / Dependency Injections
            def repository = new Repository()
            def competenciaDao = new CompetenciaDao()
            def candidatoDao = new CandidatoDao()
            def empresaDao = new EmpresaDao()
            def vagaDao = new VagaDao(empresaDao)

            def pessoaServices = new PessoaServices(candidatoDao, empresaDao, competenciaDao)
            def vagaServices = new VagaServices(vagaDao, empresaDao, candidatoDao, competenciaDao)
            def sistemaCurtidas = new SistemaCurtidas()
            def cli = new Cli(pessoaServices, repository, vagaServices, sistemaCurtidas)

            cli.cliMenu()
        } catch (Exception e) {
            e.printStackTrace()
        }
    }
}
