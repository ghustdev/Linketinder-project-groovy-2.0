import repository.Repository
import services.PessoaServices
import services.SistemaCurtidas
import services.VagaServices
import view.Cli
import model.Candidato
import model.Empresa

class Main {
    static void main(String[] args) {
        try {
            // Constructors / Dependency Injections
            def empresa = new Empresa()
            def candidato = new Candidato()
            def repository = new Repository()
            def pessoaServices = new PessoaServices(repository)
            def vagaServices = new VagaServices(repository, empresa)
            def sistemaCurtidas = new SistemaCurtidas()
            def cli = new Cli(pessoaServices, repository, vagaServices, sistemaCurtidas)

            cli.cliMenu()
        } catch (Throwable t) {
            t.printStackTrace()
        }
    }
}