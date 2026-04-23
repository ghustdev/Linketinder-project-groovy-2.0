package entities

import groovy.transform.ToString
import groovy.transform.builder.Builder

@Builder
@ToString(includeNames = true)
class Competencia {
    Long id
    String nome
}
