package view

class CliParseSkillsAction {
    static List<String> parseSkills(String input) {
        if (input == null || input.trim().isEmpty()) {
            return []
        }
        return input.split(",")
                .collect { it.trim() }
                .findAll { !it.isEmpty() }
    }
}

