INSERT INTO candidatos (nome, sobrenome, data_nascimento, email, cpf, pais, cep, descricao)
VALUES
    ('Sandubinha', 'Silva', '1999-05-10', 'sandu@example.com', '12345678901', 'Brasil', '01001000', 'Dev em busca de oportunidades.'),
    ('Carla', 'Souza', '1996-03-22', 'carla@example.com', '23456789012', 'Brasil', '02002000', 'Front-end com foco em Angular.')
    ON CONFLICT DO NOTHING;

INSERT INTO empresas (nome, cnpj, email, descricao, pais, cep)
VALUES
    ('Pastelsoft', '12345678000199', 'rh@pastelsoft.com', 'ERP para restaurantes e distribuidores.', 'Brasil',
     '04000000')
    ON CONFLICT DO NOTHING;

INSERT INTO vagas (empresa_id, nome, descricao, estado, cidade)
SELECT e.id, 'Dev Backend', 'Atuar com Spring e PostgreSQL.', 'SP', 'Sao Paulo'
FROM empresas e
WHERE e.cnpj = '12345678000199'
    ON CONFLICT DO NOTHING;

INSERT INTO vagas (empresa_id, nome, descricao, estado, cidade)
SELECT e.id, 'Dev Frontend', 'Atuar com Angular em apps web.', 'SP', 'Sao Paulo'
FROM empresas e
WHERE e.cnpj = '12345678000199'
    ON CONFLICT DO NOTHING;

-- ------------------------------------------------------------
-- Caso simples: cadastro do candidato + competencias
-- ------------------------------------------------------------
BEGIN;

WITH novo_candidato AS (
INSERT INTO candidatos (nome, sobrenome, data_nascimento, email, cpf, pais, cep, descricao)
VALUES ('Joao', 'Pereira', '1998-09-15', 'joao@example.com', '34567890123', 'Brasil', '03003000', 'Back-end com foco em cloud.')
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
-- Caso simples: cadastro de vaga + competencias
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
