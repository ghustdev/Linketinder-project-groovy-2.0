package view

class CliReadIntAction {
    static Integer readInt(Cli cli, String prompt) {
        print(prompt)
        String value = cli.scanner.nextLine()?.trim()
        if (value == null || value.isEmpty()) {
            return null
        }
        try {
            return Integer.parseInt(value)
        } catch (NumberFormatException ignored) {
            return null
        }
    }
}

