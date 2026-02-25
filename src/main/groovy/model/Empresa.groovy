package model

import groovy.transform.ToString
import groovy.transform.builder.Builder

@Builder
@ToString(includeSuper = true, includeNames = true)
class Empresa extends Pessoa {
    String name
    String email
    String description
    String state
    String cep
    List<String> skills = []

    // Campos específicos
    String cnpj
    String country
}