-- Linketinder - Parte 1 (Modelagem + SQL)
-- PostgreSQL DDL + exemplos de DML/queries para testar relacoes

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

-- Curtidas do candidato em vaga
CREATE TABLE IF NOT EXISTS candidato_curte_vaga (
  candidato_id BIGINT NOT NULL REFERENCES candidatos(id) ON DELETE CASCADE,
  vaga_id BIGINT NOT NULL REFERENCES vagas(id) ON DELETE CASCADE,
  curtido_em TIMESTAMP NOT NULL DEFAULT NOW(),
  PRIMARY KEY (candidato_id, vaga_id)
);

-- Curtidas da empresa em candidato (para uma vaga)
CREATE TABLE IF NOT EXISTS empresa_curte_candidato (
  empresa_id BIGINT NOT NULL REFERENCES empresas(id) ON DELETE CASCADE,
  candidato_id BIGINT NOT NULL REFERENCES candidatos(id) ON DELETE CASCADE,
  vaga_id BIGINT NOT NULL REFERENCES vagas(id) ON DELETE CASCADE,
  curtido_em TIMESTAMP NOT NULL DEFAULT NOW(),
  PRIMARY KEY (empresa_id, candidato_id, vaga_id),
  CONSTRAINT empresa_curte_candidato_vaga_ck
    CHECK (vaga_id IS NOT NULL)
);

-- Match ocorre quando ha curtida mutua para a mesma vaga
CREATE TABLE IF NOT EXISTS matches (
  candidato_id BIGINT NOT NULL REFERENCES candidatos(id) ON DELETE CASCADE,
  empresa_id BIGINT NOT NULL REFERENCES empresas(id) ON DELETE CASCADE,
  vaga_id BIGINT NOT NULL REFERENCES vagas(id) ON DELETE CASCADE,
  criado_em TIMESTAMP NOT NULL DEFAULT NOW(),
  PRIMARY KEY (candidato_id, empresa_id, vaga_id)
);

COMMIT;

-- ------------------------------------------------------------
-- DADOS DE EXEMPLO (para testar as relacoes)
-- ------------------------------------------------------------

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
   'Dev em busca de oportunidades.', 'hash123')
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

INSERT INTO candidato_competencia (candidato_id, competencia_id)
SELECT c.id, comp.id
FROM candidatos c
JOIN competencias comp ON comp.nome IN ('Java', 'Groovy', 'PostgreSQL')
WHERE c.cpf = '12345678901'
ON CONFLICT DO NOTHING;

INSERT INTO vaga_competencia (vaga_id, competencia_id)
SELECT v.id, comp.id
FROM vagas v
JOIN competencias comp ON comp.nome IN ('Spring', 'PostgreSQL')
WHERE v.nome = 'Dev Backend'
ON CONFLICT DO NOTHING;

-- Candidato curte vaga
INSERT INTO candidato_curte_vaga (candidato_id, vaga_id)
SELECT c.id, v.id
FROM candidatos c
JOIN vagas v ON v.nome = 'Dev Backend'
WHERE c.cpf = '12345678901'
ON CONFLICT DO NOTHING;

-- Empresa curte candidato para a vaga
INSERT INTO empresa_curte_candidato (empresa_id, candidato_id, vaga_id)
SELECT e.id, c.id, v.id
FROM empresas e
JOIN candidatos c ON c.cpf = '12345678901'
JOIN vagas v ON v.nome = 'Dev Backend'
WHERE e.cnpj = '12345678000199'
ON CONFLICT DO NOTHING;

-- Registrar match quando ha curtida mutua
INSERT INTO matches (candidato_id, empresa_id, vaga_id)
SELECT c.id, e.id, v.id
FROM candidatos c
JOIN empresas e ON e.cnpj = '12345678000199'
JOIN vagas v ON v.nome = 'Dev Backend'
WHERE EXISTS (
  SELECT 1
  FROM candidato_curte_vaga cv
  WHERE cv.candidato_id = c.id AND cv.vaga_id = v.id
)
AND EXISTS (
  SELECT 1
  FROM empresa_curte_candidato ec
  WHERE ec.empresa_id = e.id AND ec.candidato_id = c.id AND ec.vaga_id = v.id
)
ON CONFLICT DO NOTHING;

-- ------------------------------------------------------------
-- QUERIES PARA TESTE
-- ------------------------------------------------------------

-- 1) Competencias do candidato
SELECT c.nome, c.sobrenome, comp.nome AS competencia
FROM candidatos c
JOIN candidato_competencia cc ON cc.candidato_id = c.id
JOIN competencias comp ON comp.id = cc.competencia_id
WHERE c.cpf = '12345678901';

-- 2) Competencias exigidas pela vaga
SELECT v.nome AS vaga, comp.nome AS competencia
FROM vagas v
JOIN vaga_competencia vc ON vc.vaga_id = v.id
JOIN competencias comp ON comp.id = vc.competencia_id
WHERE v.nome = 'Dev Backend';

-- 3) Curtidas recebidas pela empresa nas suas vagas
SELECT e.nome AS empresa, v.nome AS vaga, c.nome AS candidato
FROM empresas e
JOIN vagas v ON v.empresa_id = e.id
JOIN candidato_curte_vaga cv ON cv.vaga_id = v.id
JOIN candidatos c ON c.id = cv.candidato_id
WHERE e.cnpj = '12345678000199';

-- 4) Matches da empresa
SELECT e.nome AS empresa, c.nome AS candidato, v.nome AS vaga, m.criado_em
FROM matches m
JOIN empresas e ON e.id = m.empresa_id
JOIN candidatos c ON c.id = m.candidato_id
JOIN vagas v ON v.id = m.vaga_id
WHERE e.cnpj = '12345678000199';
