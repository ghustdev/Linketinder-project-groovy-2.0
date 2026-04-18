import { Candidato } from "../entities/Candidato.js";
import { Empresa } from "../entities/Empresa.js";
import { Vaga } from "../entities/Vaga.js";

interface ILocalStorageRepository {
  adicionarCandidato(candidato: Candidato): void;
  adicionarEmpresa(empresa: Empresa): void;
  adicionarVaga(vaga: Vaga): void;
  obterCandidatos(): Candidato[];
  obterEmpresas(): Empresa[];
  obterVagas(): Vaga[];
  excluirCandidato(id: string): void;
  excluirEmpresa(id: string, cnpj: string): void;
  excluirVaga(id: string): void;
  empresaExiste(cnpj: string): boolean;
  candidatoExiste(cpf: string): boolean;
}

export class LocalStorageRepository implements ILocalStorageRepository {
  private salvarCandidatos(candidatos: Candidato[]): void {
    localStorage.setItem("candidatos", JSON.stringify(candidatos));
  }

  private salvarEmpresas(empresas: Empresa[]): void {
    localStorage.setItem("empresas", JSON.stringify(empresas));
  }

  private salvarVagas(vagas: Vaga[]): void {
    localStorage.setItem("vagas", JSON.stringify(vagas));
  }

  obterCandidatos(): Candidato[] {
    const data = localStorage.getItem("candidatos");
    return data ? JSON.parse(data) : [];
  }

  obterEmpresas(): Empresa[] {
    const data = localStorage.getItem("empresas");
    return data ? JSON.parse(data) : [];
  }

  obterVagas(): Vaga[] {
    const data = localStorage.getItem("vagas");
    return data ? JSON.parse(data) : [];
  }

  adicionarCandidato(candidato: Candidato): void {
    const candidatos = this.obterCandidatos();
    candidatos.push(candidato);
    this.salvarCandidatos(candidatos);
  }

  adicionarEmpresa(empresa: Empresa): void {
    const empresas = this.obterEmpresas();
    empresas.push(empresa);
    this.salvarEmpresas(empresas);
  }

  adicionarVaga(vaga: Vaga): void {
    const vagas = this.obterVagas();
    vagas.push(vaga);
    this.salvarVagas(vagas);
  }

  excluirCandidato(id: string): void {
    this.salvarCandidatos(this.obterCandidatos().filter((c) => c.id !== id));
  }

  excluirEmpresa(id: string, cnpj: string): void {
    this.obterVagas()
      .filter((v) => v.cnpj === cnpj)
      .forEach((v) => this.excluirVaga(v.id));
    this.salvarEmpresas(this.obterEmpresas().filter((e) => e.id !== id));
  }

  excluirVaga(id: string): void {
    this.salvarVagas(this.obterVagas().filter((v) => v.id !== id));
  }

  empresaExiste(cnpj: string): boolean {
    return this.obterEmpresas().some((e) => e.cnpj === cnpj);
  }

  candidatoExiste(cpf: string): boolean {
    return this.obterCandidatos().some((c) => c.cpf === cpf);
  }
}
