export class Empresa {
    public readonly id: string;

    public nome: string;
    public email: string;
    public cnpj: string;
    public pais: string;
    public estado: string;
    public cep: string;
    public descricao: string;

    constructor(props: Omit<Empresa, 'id'>, id?: string) {
        this.id = id ? id : crypto.randomUUID();
        

        this.nome = props.nome;
        this.email = props.email;
        this.cnpj = props.cnpj;
        this.pais = props.pais;
        this.estado = props.estado;
        this.cep = props.cep;
        this.descricao = props.descricao;
    }
}
