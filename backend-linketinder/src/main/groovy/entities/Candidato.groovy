package entities

import groovy.transform.ToString
import groovy.transform.builder.Builder

import java.time.LocalDateTime

@Builder
@ToString(includeSuper = true, includeNames = true)
class Candidato extends Pessoa {
    Long id
    String nome
    String sobrenome
    String email
    String cpf
    Date nascimento
    String pais
    String cep
    String descricao
    String formacao
    List<Competencia> competencias = []
    String linkedin
}
