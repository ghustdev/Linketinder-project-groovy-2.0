package view

class EntradaConsolePadrao implements IEntradaConsolePadrao {
    private final Scanner scanner = new Scanner(System.in)

    @Override
    String lerLinha() {
        return scanner.nextLine()
    }

    @Override
    void pausar() {
        println("Aperte 'Enter' ou qualquer tecla para continuar ...")
        lerLinha()
    }

    @Override
    Integer lerInteiro(String prompt) {
        print(prompt)
        String valor = lerLinha()?.trim()
        if (!valor) return null
        try {
            return Integer.parseInt(valor)
        } catch (NumberFormatException ignored) {
            return null
        }
    }
}
