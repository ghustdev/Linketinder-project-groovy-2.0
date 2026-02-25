package repository

import model.Candidato
import model.Empresa

class Repository {
    static List<Candidato> arrayCandidatos = []
    static List<Empresa> arrayEmpresas = []

    static {
        /* Candidatos */
        // 1. Gustavo
        arrayCandidatos.add(Candidato.builder()
                .name("Gustavo Cardoso")
                .email("gustavo@exemplo.com")
                .cpf("123.456.789-01")
                .old(25)
                .state("GO")
                .cep("74000-000")
                .description("Desenvolvedor apaixonado por inovação e Java.")
                .skills(["Java", "Groovy", "Linux", "Competitive Programming"])
                .build())

        // 2. Ana
        arrayCandidatos.add(Candidato.builder()
                .name("Ana Silva")
                .email("ana.silva@tech.com")
                .cpf("234.567.890-02")
                .old(28)
                .state("SP")
                .cep("01001-000")
                .description("Especialista em Front-end e UX Design.")
                .skills(["React", "TypeScript", "Figma"])
                .build())

        // 3. Bruno
        arrayCandidatos.add(Candidato.builder()
                .name("Bruno Souza")
                .email("bruno.souza@dev.com")
                .cpf("345.678.901-03")
                .old(30)
                .state("RJ")
                .cep("20040-002")
                .description("Engenheiro de Software focado em Cloud Computing.")
                .skills(["AWS", "Docker", "Kubernetes", "Go"])
                .build())

        // 4. Carla
        arrayCandidatos.add(Candidato.builder()
                .name("Carla Oliveira")
                .email("carla.oli@work.com")
                .cpf("456.789.012-04")
                .old(24)
                .state("MG")
                .cep("30130-010")
                .description("Cientista de dados com foco em IA.")
                .skills(["Python", "Pandas", "Scikit-learn", "SQL"])
                .build())

        // 5. Diego
        arrayCandidatos.add(Candidato.builder()
                .name("Diego Santos")
                .email("diego.santos@code.com")
                .cpf("567.890.123-05")
                .old(27)
                .state("BA")
                .cep("40020-000")
                .description("Desenvolvedor Mobile Android.")
                .skills(["Kotlin", "Jetpack Compose", "Firebase"])
                .build())

        // 6. Elena
        arrayCandidatos.add(Candidato.builder()
                .name("Elena Martins")
                .email("elena.m@sys.com")
                .cpf("678.912.345-06")
                .old(32)
                .state("PR")
                .cep("80010-000")
                .description("Analista de Sistemas e Gestora de Projetos.")
                .skills(["Scrum", "Kanban", "Jira", "Groovy"])
                .build())

        // 7. Fabio
        arrayCandidatos.add(Candidato.builder()
                .name("Fabio Rocha")
                .email("fabio.rocha@web.com")
                .cpf("789.012.345-07")
                .old(22)
                .state("SC")
                .cep("88010-000")
                .description("Entusiasta de Segurança da Informação.")
                .skills(["Cybersecurity", "Linux", "Python", "Networking"])
                .build())

        /* Empresas */
        // 1. ZG Soluções
        arrayEmpresas.add(Empresa.builder()
                .name("ZG Soluções")
                .email("rh@zg.com.br")
                .cnpj("12.30001-01")
                .country("Brasil")
                .state("GO")
                .cep("74000-000")
                .description("Líder em automação de processos financeiros para saúde.")
                .skills(["Java", "Groovy", "SQL", "Spring Boot", "Hibernate", "JUnit"])
                .build()
        )

        // 2. Tech Flow
        arrayEmpresas.add(Empresa.builder()
                .name("Tech Flow")
                .email("contato@techflow.io")
                .description("Consultoria especializada em Cloud e DevOps.")
                .skills(["AWS", "Kubernetes", "Docker", "Terraform", "Ansible", "Linux"])
                .cnpj("23.456.789/0001-02")
                .state("SP")
                .cep("01122-000")
                .country("Brasil")
                .build()
        )

        // 3. Inova IA
        arrayEmpresas.add(Empresa.builder()
                .name("Inova IA")
                .email("talentos@inovaia.com")
                .description("Startup focada em soluções de Inteligência Artificial.")
                .skills(["Python", "TensorFlow", "Data Science", "PyTorch", "NumPy", "Pandas"])
                .cnpj("34.567.890/0001-03")
                .state("MG")
                .cep("30150-000")
                .country("Brasil")
                .build()
        )

        // 4. Global Connect
        arrayEmpresas.add(Empresa.builder()
                .name("Global Connect")
                .email("hiring@globalconnect.com")
                .description("Multinacional de telecomunicações e redes.")
                .skills(["Networking", "Cisco", "Security", "C++", "Python", "BGP"])
                .cnpj("45.678.901/0001-04")
                .state("NY")
                .cep("10001")
                .country("USA")
                .build()
        )

        // 5. Code Masters
        arrayEmpresas.add(Empresa.builder()
                .name("Code Masters")
                .email("jobs@codemasters.com.br")
                .description("Fábrica de software focada em metodologias ágeis.")
                .skills(["Scrum", "JavaScript", "React", "Node.js", "TypeScript", "Clean Code"])
                .cnpj("56.789.012/0001-05")
                .state("RJ")
                .cep("20031-000")
                .country("Brasil")
                .build()
        )

        // 6. Green Energy
        arrayEmpresas.add(Empresa.builder()
                .name("Green Energy")
                .email("sustentabilidade@green.energy")
                .description("Empresa de tecnologia para energia renovável.")
                .skills(["IoT", "Embedded Systems", "C", "Analytics", "Arduino", "MQTT"])
                .cnpj("67.890.123/0001-06")
                .state("SC")
                .cep("88015-000")
                .country("Brasil")
                .build()
        )

        // 7. Byte Bank
        arrayEmpresas.add(Empresa.builder()
                .name("Byte Bank")
                .email("carreira@bytebank.com")
                .description("Fintech inovadora no setor de pagamentos digitais.")
                .skills(["Java", "Kotlin", "Swift", "Microservices", "Kafka", "PostgreSQL"])
                .cnpj("78.901.234/0001-07")
                .state("DF")
                .cep("70040-000")
                .country("Brasil")
                .build()
        )
    }
}

