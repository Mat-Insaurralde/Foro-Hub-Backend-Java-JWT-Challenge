
create table cursos(

id bigint not null auto_increment,
nombre varchar(50) not null,
descripcion varchar(200) not null,
fecha_de_creacion DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
status tinyint not null,

PRIMARY KEY (id)

);