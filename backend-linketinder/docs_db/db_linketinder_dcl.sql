-- 1) Listar candidatos e suas competencias
SELECT c.nome, c.sobrenome, comp.nome AS competencia
FROM candidatos c
         JOIN candidato_competencia cc ON cc.candidato_id = c.id
         JOIN competencias comp ON comp.id = cc.competencia_id
ORDER BY c.nome, comp.nome;

-- 2) Listar vagas e competencias exigidas
SELECT v.nome AS vaga, comp.nome AS competencia
FROM vagas v
         JOIN vaga_competencia vc ON vc.vaga_id = v.id
         JOIN competencias comp ON comp.id = vc.competencia_id
ORDER BY v.nome, comp.nome;

-- 3) Listar vagas de uma empresa
SELECT e.nome AS empresa, v.nome AS vaga, v.estado, v.cidade
FROM empresas e
         JOIN vagas v ON v.empresa_id = e.id
WHERE e.cnpj = '12345678000199'
ORDER BY v.nome;

-- 4) Buscar candidatos que tenham a competencia 'Angular'
SELECT c.nome, c.sobrenome, c.email
FROM candidatos c
         JOIN candidato_competencia cc ON cc.candidato_id = c.id
         JOIN competencias comp ON comp.id = cc.competencia_id
WHERE comp.nome = 'Angular'
ORDER BY c.nome;