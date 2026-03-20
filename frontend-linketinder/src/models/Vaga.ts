export class Vaga {
    id: string;
    titulo: string;
    descricao: string;
    cnpjEmpresa: string;
    competencias: string[];

    constructor(
        titulo: string,
        descricao: string,
        cnpjEmpresa: string,
        competencias: string[]
    ) {
        this.id = Date.now().toString();
        this.titulo = titulo;
        this.descricao = descricao;
        this.cnpjEmpresa = cnpjEmpresa;
        this.competencias = competencias;
    }
}
