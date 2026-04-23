package entities

import groovy.transform.ToString
import groovy.transform.builder.Builder

@Builder
@ToString(includeSuper = true, includeNames = true)
class Empresa extends Pessoa {
    Long id
    String nome
    String email
    String cnpj
    String pais
    String cep
    String descricao
}
