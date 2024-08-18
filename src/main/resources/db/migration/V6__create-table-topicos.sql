
create table topicos(

id bigint not null auto_increment,
titulo varchar(200) not null,
mensaje varchar(400) not null,
fecha_de_creacion DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ,
status tinyint not null,
autor_id bigint not null,

primary key(id),

CONSTRAINT fk_autor FOREIGN KEY (autor_id) REFERENCES usuarios(id)
);