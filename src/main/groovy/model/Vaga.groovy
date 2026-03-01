package model

class Vaga {
    int id
    String title
    String description
    Empresa empresa
    List<String> skilssRequests = []

    Vaga(int id, String titulo, String description, Empresa empresa, List<String> skilssRequests = []) {
        this.id = id
        this.title = titulo
        this.description = description
        this.empresa = empresa
        this.skilssRequests = skilssRequests
    }

    @Override
    String toString() {
        return "Vaga { id='${id}', titulo='${title}', empresa='${empresa.name}', CNPJ='${empresa.cnpj}', skills=${skilssRequests} }"
    }
}
