create TABLE curso(
    id BIGINT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(50) NOT NULL,
    categoria VARCHAR(50) NOT NULL,
    PRIMARY KEY(id)
);

insert into curso values (1, 'Kotlin', 'Programação');
insert into curso values (2, 'HTML', 'Front-end');