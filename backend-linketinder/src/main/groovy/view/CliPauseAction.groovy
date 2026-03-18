package view

class CliPauseAction {
    static void pause(Cli cli) {
        print("Aperte \"Enter\" para continuar")
        cli.scanner.nextLine()
    }
}
