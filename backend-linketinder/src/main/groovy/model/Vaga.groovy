package model

import groovy.transform.ToString
import groovy.transform.builder.Builder

@Builder
@ToString(includeNames = true)
class Vaga {
    Long id
    Long empresa_id
    String nome
    String descricao
    String estado
    String cidade
    Empresa empresa
    List<String> skillsRequests = []
}
