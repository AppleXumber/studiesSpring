create TABLE usuario(
    id BIGINT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL,
    PRIMARY KEY(id)
);

insert into usuario values (1, 'Ana da Silva', 'ana@email.com');