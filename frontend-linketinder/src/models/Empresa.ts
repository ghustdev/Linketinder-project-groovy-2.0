export class Empresa {
    id: string;
    nome: string;
    email: string;
    cnpj: string;
    pais: string;
    estado: string;
    cep: string;
    descricao: string;

    constructor(
        nome: string,
        email: string,
        cnpj: string,
        pais: string,
        estado: string,
        cep: string,
        descricao: string
    ) {
        this.id = Date.now().toString();
        this.nome = nome;
        this.email = email;
        this.cnpj = cnpj;
        this.pais = pais;
        this.estado = estado;
        this.cep = cep;
        this.descricao = descricao;
    }
}
