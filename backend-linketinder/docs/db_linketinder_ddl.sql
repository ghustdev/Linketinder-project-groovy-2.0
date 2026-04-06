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
