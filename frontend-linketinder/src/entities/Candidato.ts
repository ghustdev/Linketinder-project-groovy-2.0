export class Candidato {
    public readonly id: string;

    public nome: string;
    public email: string;
    public cpf: string;
    public idade: number;
    public estado: string;
    public cep: string;
    public descricao: string;
    public formacao: string;
    public competencias: string[];
    public linkedin: string;

    constructor(props: Omit<Candidato, 'id'>, id?: string) {
        this.id = id ? id : crypto.randomUUID();
        
        this.nome = props.nome;
        this.email = props.email;
        this.cpf = props.cpf;
        this.idade = props.idade;
        this.estado = props.estado;
        this.cep = props.cep;
        this.descricao = props.descricao;
        this.formacao = props.formacao;
        this.competencias = props.competencias;
        this.linkedin = props.linkedin;
    }
}
