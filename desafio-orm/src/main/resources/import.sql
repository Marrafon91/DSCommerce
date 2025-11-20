-- Seed da Categoria
INSERT INTO tb_categoria (descricao) VALUES ('Curso');
INSERT INTO tb_categoria (descricao) VALUES ('Oficina');

-- Seed da Atividade
INSERT INTO tb_atividade (nome, descricao, preco) VALUES ('Curso de HTML', 'Aprenda HTML de forma prática', 80.00);
INSERT INTO tb_atividade (nome, descricao, preco) VALUES ('Oficina de Github', 'Controle versões de seus projetos', 50.00);

-- Seed do Bloco (esta com FusoHorario de 3h)
INSERT INTO tb_bloco (inicio, fim, atividade_id) VALUES ('2017-09-25T08:00:00Z', '2017-09-25T11:00:00Z',1);
INSERT INTO tb_bloco (inicio, fim, atividade_id) VALUES ('2017-09-25T14:00:00Z', '2017-09-25T18:00:00Z',2);
INSERT INTO tb_bloco (inicio, fim, atividade_id) VALUES ('2017-09-26T08:00:00Z', '2017-09-25T11:00:00Z',2);
