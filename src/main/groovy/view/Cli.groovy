package view

import repository.Repository
import services.PessoaServices
import services.SistemaCurtidas
import services.VagaServices
import model.Empresa
import model.Candidato

class Cli {
    PessoaServices pessoaServices
    VagaServices vagaServices
    Repository repository
    SistemaCurtidas sistemaCurtidas

    Scanner scanner = new Scanner(System.in)

    Cli(PessoaServices pessoaServices, Repository repository, VagaServices vagaServices, SistemaCurtidas sistemaCurtidas) {
        this.pessoaServices = pessoaServices
        this.repository = repository
        this.vagaServices = vagaServices
        this.sistemaCurtidas = sistemaCurtidas
    }

    void cliMenu() {
        CliMenuAction.cliMenu(this)
    }

    void cliCreateEmpresa() {
        CliCreateEmpresaAction.cliCreateEmpresa(this)
    }

    void cliCreateCandidato() {
        CliCreateCandidatoAction.cliCreateCandidato(this)
    }

    void cliListCandidatos() {
        CliListCandidatosAction.cliListCandidatos(this)
    }

    void cliListEmpresas() {
        CliListEmpresasAction.cliListEmpresas(this)
    }

    Empresa cliChoiceEmpresa() {
        return CliChoiceEmpresaAction.cliChoiceEmpresa(this)
    }

    Candidato cliChoiceCandidato() {
        return CliChoiceCandidatoAction.cliChoiceCandidato(this)
    }

    void cliCreateVaga() {
        CliCreateVagaAction.cliCreateVaga(this)
    }

    void cliListVagas() {
        CliListVagasAction.cliListVagas(this)
    }

    void cliVerifyLikesEmpresa() {
        CliVerifyLikesEmpresaAction.cliVerifyLikesEmpresa(this)
    }

    void cliListLikesCandidato() {
        CliListLikesCandidatoAction.cliListLikesCandidato(this)
    }

    void cliListMatches() {
        CliListMatchesAction.cliListMatches(this)
    }

    void cliListMatchesCandidato() {
        CliListMatchesCandidatoAction.cliListMatchesCandidato(this)
    }

    void cliListMatchesEmpresa() {
        CliListMatchesEmpresaAction.cliListMatchesEmpresa(this)
    }

    // Auxiliar method
    Integer readInt(String prompt) {
        return CliReadIntAction.readInt(this, prompt)
    }

    // Auxiliar method
    List<String> parseSkills(String input) {
        return CliParseSkillsAction.parseSkills(input)
    }

    // Auxiliar method
    void pause() {
        CliPauseAction.pause(this)
    }
}
