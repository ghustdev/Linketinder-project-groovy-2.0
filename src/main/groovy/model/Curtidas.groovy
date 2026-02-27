package model

import model.Candidato
import model.Vaga
import model.Empresa
import java.time.LocalDateTime

class Curtidas {
    Candidato candidato
    Vaga vaga
    Empresa empresa // null enquanto não houver match
    LocalDateTime dateMatch

    void completeMatch(Empresa empresa) {
        if (this.empresa != null) {
            throw new IllegalStateException(
                    "Este match já foi realizado: ${candidato.name} <-> ${this.empresa.name}"
            )
        }
        this.empresa   = empresa
        this.dateMatch = LocalDateTime.now()

        println ""
        println "🎉 ══════════════════════════════════════════"
        println "   MATCH! ${candidato.name} ↔ ${empresa.name}"
        println "   Vaga: '${vaga.title}'"
        println "   Data: ${dateMatch}"
        println "   Agora ambos podem se comunicar!"
        println "🎉 ══════════════════════════════════════════"
        println ""
    }

    boolean isMatch() {
        return empresa != null
    }

    @Override
    String toString() {
        if (isMatch()) {
            return "Curtida[MATCH | ${candidato.name} ↔ ${empresa.name} | Vaga: '${vaga.title}' | ${dateMatch}]"
        }
        return "Curtida[PENDENTE | ${candidato.name} → Vaga: '${vaga.title}' (${vaga.empresa.name})]"
    }
}
