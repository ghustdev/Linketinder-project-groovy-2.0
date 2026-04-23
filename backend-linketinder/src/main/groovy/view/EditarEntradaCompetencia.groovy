package view

class EditarEntradaCompetencia {
    static List<String> formatarCompetencias(String entrada) {
        if (!entrada?.trim()) return []
        return entrada.split(",")
                .collect { it.trim() }
                .findAll { it }
    }
}
