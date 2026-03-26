export class Candidato {
    id: string;
    nome: string;
    email: string;
    cpf: string;
    idade: number;
    estado: string;
    cep: string;
    descricao: string;
    formacao: string;
    competencias: string[];
    linkedin: string;

    constructor(
        nome: string,
        email: string,
        cpf: string,
        idade: number,
        estado: string,
        cep: string,
        descricao: string,
        formacao: string,
        competencias: string[],
        linkedin: string
    ) {
        this.id = Date.now().toString();
        this.nome = nome;
        this.email = email;
        this.cpf = cpf;
        this.idade = idade;
        this.estado = estado;
        this.cep = cep;
        this.descricao = descricao;
        this.formacao = formacao;
        this.competencias = competencias;
        this.linkedin = linkedin;
    }
}
