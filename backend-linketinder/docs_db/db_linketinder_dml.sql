INSERT INTO competencias (nome) VALUES
                                    ('Java'),
                                    ('Groovy'),
                                    ('Spring'),
                                    ('Angular'),
                                    ('PostgreSQL')
    ON CONFLICT DO NOTHING;

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
WHERE e.cnpj = '12345678000199'
    ON CONFLICT DO NOTHING;

INSERT INTO vagas (empresa_id, nome, descricao, estado, cidade)
SELECT e.id, 'Dev Frontend', 'Atuar com Angular em apps web.', 'SP', 'Sao Paulo'
FROM empresas e
WHERE e.cnpj = '12345678000199'
    ON CONFLICT DO NOTHING;

INSERT INTO candidato_competencia (candidato_id, competencia_id)
SELECT c.id, comp.id
FROM candidatos c
         JOIN competencias comp ON comp.nome IN ('Java', 'Groovy', 'PostgreSQL')
WHERE c.cpf = '12345678901'
    ON CONFLICT DO NOTHING;

INSERT INTO candidato_competencia (candidato_id, competencia_id)
SELECT c.id, comp.id
FROM candidatos c
         JOIN competencias comp ON comp.nome IN ('Angular')
WHERE c.cpf = '23456789012'
    ON CONFLICT DO NOTHING;

INSERT INTO vaga_competencia (vaga_id, competencia_id)
SELECT v.id, comp.id
FROM vagas v
         JOIN competencias comp ON comp.nome IN ('Spring', 'PostgreSQL')
WHERE v.nome = 'Dev Backend'
    ON CONFLICT DO NOTHING;

INSERT INTO vaga_competencia (vaga_id, competencia_id)
SELECT v.id, comp.id
FROM vagas v
         JOIN competencias comp ON comp.nome IN ('Angular')
WHERE v.nome = 'Dev Frontend'
    ON CONFLICT DO NOTHING;