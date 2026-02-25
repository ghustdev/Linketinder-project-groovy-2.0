package model

import groovy.transform.ToString
import groovy.transform.builder.Builder

@Builder
@ToString(includeSuper = true, includeNames = true)
class Candidato extends Pessoa {
    String name
    String email
    String description
    String state
    String cep
    List<String> skills = []

    // Campos específicos
    String cpf
    int old
}