package model

import groovy.transform.ToString
import groovy.transform.builder.Builder

@Builder
@ToString(includeNames = true)
class Vacancy {
    Long id
    Long companyId
    String name
    String description
    String state
    String city
    Company company
    List<Skill> requiredSkills = []
}
