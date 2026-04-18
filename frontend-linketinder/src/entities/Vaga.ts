export class Vaga {
    public readonly id: string;

    public titulo: string;
    public descricao: string;
    public cnpj: string;
    public competencias: string[];

    constructor(props: Omit<Vaga, 'id'>, id?: string) {
        this.id = id ? id : crypto.randomUUID();

        this.titulo = props.titulo;
        this.descricao = props.descricao;
        this.cnpj = props.cnpj;
        this.competencias = props.competencias;
    }
}
