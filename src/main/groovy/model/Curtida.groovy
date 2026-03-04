package model


import java.time.LocalDateTime

class Curtida {
    Candidato candidato
    Vaga vaga
    Empresa empresa
    LocalDateTime date

    boolean isMatch() {
        return empresa != null
    }
}
