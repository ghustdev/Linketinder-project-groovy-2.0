import { Candidato } from '../models/Candidato.js';
import { Empresa } from '../models/Empresa.js';
import { Vaga } from '../models/Vaga.js';

export class StorageService {
    static salvarCandidatos(candidatos: Candidato[]): void {
        localStorage.setItem('candidatos', JSON.stringify(candidatos));
    }

    static salvarEmpresas(empresas: Empresa[]): void {
        localStorage.setItem('empresas', JSON.stringify(empresas));
    }

    static salvarVagas(vagas: Vaga[]): void {
        localStorage.setItem('vagas', JSON.stringify(vagas));
    }

    static obterCandidatos(): Candidato[] {
        const data = localStorage.getItem('candidatos');
        return data ? JSON.parse(data) : [];
    }

    static obterEmpresas(): Empresa[] {
        const data = localStorage.getItem('empresas');
        return data ? JSON.parse(data) : [];
    }

    static obterVagas(): Vaga[] {
        const data = localStorage.getItem('vagas');
        return data ? JSON.parse(data) : [];
    }

    static adicionarCandidato(candidato: Candidato): void {
        const candidatos = this.obterCandidatos();
        candidatos.push(candidato);
        this.salvarCandidatos(candidatos);
    }

    static adicionarEmpresa(empresa: Empresa): void {
        const empresas = this.obterEmpresas();
        empresas.push(empresa);
        this.salvarEmpresas(empresas);
    }

    static adicionarVaga(vaga: Vaga): void {
        const vagas = this.obterVagas();
        vagas.push(vaga);
        this.salvarVagas(vagas);
    }

    static excluirCandidato(id: string): void {
        const candidatos = this.obterCandidatos().filter(c => c.id !== id);
        this.salvarCandidatos(candidatos);
    }

    static excluirEmpresa(id: string): void {
        const empresas = this.obterEmpresas().filter(e => e.id !== id);
        this.salvarEmpresas(empresas);
    }

    static excluirVaga(id: string): void {
        const vagas = this.obterVagas().filter(e => e.id !== id);
        this.salvarVagas(vagas);
    }

    static empresaExiste(cnpj: string): boolean {
        const empresa = this.obterEmpresas().some(e => e.cnpj === cnpj); 
        return empresa;
    }
}
