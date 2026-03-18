package model

import groovy.transform.ToString
import groovy.transform.builder.Builder

@Builder
@ToString(includeNames = true)
class Vaga {
    int id
    String title
    String description
    Empresa empresa
    List<String> skillsRequests = []
}
