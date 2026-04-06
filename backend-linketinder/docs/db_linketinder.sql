BEGIN;

CREATE TABLE IF NOT EXISTS candidatos (
                                          id BIGSERIAL PRIMARY KEY,
                                          nome VARCHAR(100) NOT NULL,
    sobrenome VARCHAR(100) NOT NULL,
    data_nascimento DATE NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    cpf CHAR(11) NOT NULL UNIQUE,
    pais VARCHAR(100) NOT NULL,
    cep VARCHAR(20) NOT NULL,
    descricao TEXT,
    senha_hash VARCHAR(255) NOT NULL,
    criado_em TIMESTAMP NOT NULL DEFAULT NOW()
    );

CREATE TABLE IF NOT EXISTS empresas (
                                        id BIGSERIAL PRIMARY KEY,
                                        nome VARCHAR(150) NOT NULL,
    cnpj CHAR(14) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    descricao TEXT,
    pais VARCHAR(100) NOT NULL,
    cep VARCHAR(20) NOT NULL,
    senha_hash VARCHAR(255) NOT NULL,
    criado_em TIMESTAMP NOT NULL DEFAULT NOW()
    );

CREATE TABLE IF NOT EXISTS competencias (
                                            id BIGSERIAL PRIMARY KEY,
                                            nome VARCHAR(100) NOT NULL UNIQUE
    );

-- Relacao 1:N empresas <-> vagas
CREATE TABLE IF NOT EXISTS vagas (
                                     id BIGSERIAL PRIMARY KEY,
                                     empresa_id BIGINT NOT NULL REFERENCES empresas(id) ON DELETE CASCADE,
    nome VARCHAR(150) NOT NULL,
    descricao TEXT NOT NULL,
    estado VARCHAR(100) NOT NULL,
    cidade VARCHAR(100) NOT NULL,
    criado_em TIMESTAMP NOT NULL DEFAULT NOW()
    );

-- Relacao N:N candidatos <-> competencias
CREATE TABLE IF NOT EXISTS candidato_competencia (
                                                     candidato_id BIGINT NOT NULL REFERENCES candidatos(id) ON DELETE CASCADE,
    competencia_id BIGINT NOT NULL REFERENCES competencias(id) ON DELETE CASCADE,
    PRIMARY KEY (candidato_id, competencia_id)
    );

-- Relacao N:N vagas <-> competencias
CREATE TABLE IF NOT EXISTS vaga_competencia (
                                                vaga_id BIGINT NOT NULL REFERENCES vagas(id) ON DELETE CASCADE,
    competencia_id BIGINT NOT NULL REFERENCES competencias(id) ON DELETE CASCADE,
    PRIMARY KEY (vaga_id, competencia_id)
    );

COMMIT;

BEGIN;

INSERT INTO candidatos (nome, sobrenome, data_nascimento, email, cpf, pais, cep, descricao, senha_hash)
VALUES
    ('Sandubinha', 'Silva', '1999-05-10', 'sandu@example.com', '12345678901', 'Brasil', '01001000',
     'Dev em busca de oportunidades.', 'hash123'),
    ('Carla', 'Souza', '1996-03-22', 'carla@example.com', '23456789012', 'Brasil', '02002000',
     'Front-end com foco em Angular.', 'hash789')
    ON CONFLICT DO NOTHING;

INSERT INTO empresas (nome, cnpj, email, descricao, pais, cep, senha_hash)
VALUES
    ('Pastelsoft', '12345678000199', 'rh@pastelsoft.com', 'ERP para restaurantes e distribuidores.', 'Brasil',
     '04000000', 'hash456')
    ON CONFLICT DO NOTHING;

INSERT INTO vagas (empresa_id, nome, descricao, estado, cidade)
SELECT e.id, 'Dev Backend', 'Atuar com Spring e PostgreSQL.', 'SP', 'Sao Paulo'
FROM empresas e
WHERE e.cnpj = '12345678000199';

INSERT INTO vagas (empresa_id, nome, descricao, estado, cidade)
SELECT e.id, 'Dev Frontend', 'Atuar com Angular em apps web.', 'SP', 'Sao Paulo'
FROM empresas e
WHERE e.cnpj = '12345678000199';

COMMIT;

-- ------------------------------------------------------------
-- Caso real simples: cadastro do candidato + competencias
-- ------------------------------------------------------------

BEGIN;

WITH novo_candidato AS (
INSERT INTO candidatos (nome, sobrenome, data_nascimento, email, cpf, pais, cep, descricao, senha_hash)
VALUES ('Joao', 'Pereira', '1998-09-15', 'joao@example.com', '34567890123', 'Brasil', '03003000', 'Back-end com foco em cloud.', 'hash999')
    RETURNING id
    ),

    novas_competencias AS (
INSERT INTO competencias (nome) VALUES
    ('Docker'),
    ('Kubernetes'),
    ('SQL')
ON CONFLICT (nome) DO NOTHING
    RETURNING id
    ),

    competencias_ids AS (
SELECT id FROM competencias
WHERE nome IN ('Docker', 'Kubernetes', 'SQL')
    )

INSERT INTO candidato_competencia (candidato_id, competencia_id)
SELECT novo_candidato.id, competencias_ids.id
FROM novo_candidato, competencias_ids
    ON CONFLICT DO NOTHING;

COMMIT;

-- ------------------------------------------------------------
-- Caso real simples: cadastro de vaga + competencias
-- ------------------------------------------------------------
BEGIN;

WITH nova_vaga AS (
INSERT INTO vagas (empresa_id, nome, descricao, estado, cidade)
SELECT e.id, 'DevOps', 'Atuar com CI/CD e cloud.', 'SP', 'Sao Paulo'
FROM empresas e
WHERE e.cnpj = '12345678000199'
    RETURNING id
),

novas_competencias AS (
INSERT INTO competencias (nome) VALUES
    ('CI/CD'),
    ('AWS'),
    ('Terraform')
ON CONFLICT (nome) DO NOTHING
    RETURNING id
    ),

    competencias_ids AS (
SELECT id FROM competencias
WHERE nome IN ('CI/CD', 'AWS', 'Terraform')
    )

INSERT INTO vaga_competencia (vaga_id, competencia_id)
SELECT nova_vaga.id, competencias_ids.id
FROM nova_vaga, competencias_ids
    ON CONFLICT DO NOTHING;

COMMIT;

-- Listar candidatos e suas competencias
SELECT c.nome, c.sobrenome, c.cpf, comp.nome AS competencia
FROM candidatos c, candidato_competencia cc, competencias comp
WHERE cc.candidato_id = c.id
  AND comp.id = cc.competencia_id
ORDER BY c.nome, comp.nome;

-- Listar vagas e competencias exigidas
SELECT v.nome AS vaga, comp.nome AS competencia
FROM vagas v, vaga_competencia vc, competencias comp
WHERE vc.vaga_id = v.id
  AND comp.id = vc.competencia_id
ORDER BY v.nome, comp.nome;

-- Listar vagas de uma empresa
SELECT e.nome AS empresa, v.nome AS vaga, v.estado, v.cidade
FROM empresas e, vagas v
WHERE v.empresa_id = e.id
  AND e.cnpj = '12345678000199'
ORDER BY v.nome;

-- Buscar candidatos que tenham a competencia 'Angular'
SELECT c.nome, c.sobrenome, c.email
FROM candidatos c, candidato_competencia cc, competencias comp
WHERE cc.candidato_id = c.id
  AND comp.id = cc.competencia_id
  AND comp.nome = 'Angular'
ORDER BY c.nome;

-- Busca competencias de uma vaga específica
SELECT
    v.id           AS vaga_id,
    v.nome         AS vaga_nome,
    v.descricao    AS vaga_descricao,
    v.estado,
    v.cidade,
    v.criado_em,
    e.nome         AS empresa_nome,
    e.cnpj         AS empresa_cnpj,
    c.nome         AS competencia
FROM vagas v, empresas e, vaga_competencia vc, competencias c
WHERE v.empresa_id   = e.id
  AND vc.vaga_id     = v.id
  AND c.id           = vc.competencia_id
  AND v.id           = 1;