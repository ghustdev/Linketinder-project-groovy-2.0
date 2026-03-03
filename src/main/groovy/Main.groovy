import repository.Repository
import services.PessoaServices
import services.VagaServices
import view.Cli

class Main {
    static void main(String[] args) {
        // Constructors / Dependency Injections
        def repository = new Repository()
        def pessoaServices = new PessoaServices(repository)
        def vagaServices = new VagaServices(repository)
        def cli = new Cli(pessoaServices, repository, vagaServices)

        cli.cliMenu()
    }
}