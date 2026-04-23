package entities

import groovy.transform.ToString
import groovy.transform.builder.Builder

@Builder
@ToString(includeNames = true)
class Vaga {
    Long id
    Long empresaId
    String titulo
    String descricao
    String estado
    String cidade
    Empresa empresa
    List<Competencia> competenciasRequeridas = []
}
