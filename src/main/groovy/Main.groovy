import repository.Repository
import services.PessoaServices
import view.Interface

class Main {
    static void main(String[] args) {
        // Constructors / Dependency Injections
        def repository = new Repository()
        def pessoaServices = new PessoaServices(repository)
        def cli = new Interface(pessoaServices, repository)

        cli.cliMenu()
    }
}