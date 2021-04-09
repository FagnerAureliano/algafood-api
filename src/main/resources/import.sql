INSERT INTO cozinha ( nome ) VALUES ( 'Tailandesa');
INSERT INTO cozinha ( nome ) VALUES ( 'Indiana');

insert into  restaurante (id, nome,  taxa_frete, cozinha_id ) values (default , 'Tailandia Gourmet', 211, 1);
insert into  restaurante (id, nome,  taxa_frete, cozinha_id ) values (default , 'Japonesa Gourmet', 121, 2);

INSERT INTO cidade (nome) VALUES ('Queimados');
INSERT INTO cidade (nome) VALUES ('Nova Iguaçu');
INSERT INTO cidade (nome) VALUES ('Seropédica');
INSERT INTO cidade (nome) VALUES ('Campo Grande');

INSERT INTO estado (nome) VALUES ('Rio de Janeiro');
INSERT INTO estado (nome) VALUES ('São Paulo');
INSERT INTO estado (nome) VALUES ('Bahia');
INSERT INTO estado (nome) VALUES ('Minas Gerais');

INSERT INTO permissao (nome,descricao) VALUES ('Permitido', 'Permite usuario a fazer algo');
INSERT INTO permissao (nome,descricao) VALUES ('Não Permitido', 'Não permite usuario a fazer algo');