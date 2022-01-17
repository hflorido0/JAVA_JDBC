create schema NombreSchemaBaseDeDatos;
use NombreSchemaBaseDeDatos;

create table persona (
	id int primary key auto_increment,
	nombre varchar(50),
	apellido varchar(50)
);