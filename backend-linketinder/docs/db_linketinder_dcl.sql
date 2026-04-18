-- Exemplos
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