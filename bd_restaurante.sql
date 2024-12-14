create database bd_restaurante;

create table usuarios(
codigous varchar(15) primary key,
nombreus varchar(50),
contraseña varchar(50),
rol varchar(20),
indicador varchar(1),
nombreem varchar(30) -- clave foranea
);

create table clientes(
codigocl varchar(15) primary key,
dni int(10),
nombrecl varchar(30),
apellidos varchar(50),
indicador varchar(1)
);

create table empresa(
nombreem varchar(30) primary key,
ruc bigint,
direccion varchar(100),
razonSocial varchar(60),
telefono int(25)
);

create table productos(
codigopr varchar(15) primary key,
descripcion varchar(100),
preciou decimal(10,2),
stock int,
indicador varchar(1),
codigocl varchar(15) -- clave foranea
);

create table ventas(
codigove varchar(15) primary key,
cliente varchar(100),
vendedor varchar(100),
subtotal decimal(10,2),
igv decimal(10,2),
total decimal(10,2),
fecha datetime,
codigous varchar(15) -- clave foranea
);

create table detalleventa(
cod_ve varchar(15),
cod_pro varchar(15),
desc_pro varchar(100),
cantidad int,
precio decimal(15,2),
codigopr varchar(15),-- clave foranea
codigove varchar(15),
primary key(cod_ve,cod_pro)
);

create table auditoriaProductos(
idapr int primary key auto_increment,
accionpr varchar(20),
autorpr varchar(20),
idpr varchar(15),
descpr varchar(100),
preciopr decimal(10,2),
stockpr int,
fecha datetime
);

create table auditoriaClientes(
idacl int primary key auto_increment,
accioncl varchar(20),
autorcl varchar(20),
idcl varchar(15),
dnicl int,
nomcl varchar(60),
apcl varchar(50),
fecha datetime
);

create table auditoriaUsuarios(
idaus int primary key auto_increment,
accionus varchar(20),
autorus varchar(20),
idus varchar(15),
nomus varchar(50),
contus varchar(50),
rolus varchar(30),
fecha datetime
);

-- relaciones de claves foraneas
alter table usuarios
add constraint fk_1
foreign key (nombreem)
references empresa(nombreem);

alter table productos
add constraint fk_2
foreign key(codigocl)
references clientes(codigocl);

alter table ventas
add constraint fk_3
foreign key(codigous)
references usuarios(codigous);

alter table detalleventa
add constraint fk_4
foreign key(codigove)
references ventas(codigove);

alter table detalleventa
add constraint fk_5
foreign key(codigopr)
references productos(codigopr);

insert into usuarios(codigous,nombreus,contraseña,rol,indicador)
values('ADM0001','Ricardo Perez','123','ADMINISTRADOR','A');
insert into auditoriaUsuarios(accionus,autorus,idus,nomus,contus,rolus,fecha)
values('AGREGAR','UNKNOW','ADM0001','Ricardo Perez','123','ADMINISTRADOR',now());
insert into usuarios(codigous,nombreus,contraseña,rol,indicador)
values('EMP0001','Ricardo Mendoza','1234','EMPLEADO','A');

insert into clientes(codigocl,dni,nombrecl,apellidos,indicador)
values('CL0001','87656745','Ricardo','Chavez Benitez','A');
insert into clientes(codigocl,dni,nombrecl,apellidos,indicador)
values('CL0002','75767434','Roberto','Crisostomo Juarez','A');

insert into productos(codigopr,descripcion,preciou,stock,indicador)
values('PR0001','Pizza Americana','45','3','A');
insert into productos(codigopr,descripcion,preciou,stock,indicador)
values('PR0002','Inka Kola 1L','5','10','A');

insert into empresa(nombreem,ruc,direccion,razonSocial,telefono)
values('LOGISTICA LIMATAMBO',20428727615,'Av. Alfredo Mendiola 234','Logística Limatambo S.A. de C.V.','123456789');

insert into ventas(codigove,cliente,vendedor,subtotal,igv,total,fecha)
values('VNT0001','CL0002','ADM0001','45.00','17','45',now());

DELETE FROM usuarios WHERE codigous = 'ADM0006';
DELETE FROM usuarios WHERE codigous = 'EMP0003';


select * from usuarios;
select * from clientes;
select * from productos;
select * from empresa;
select* from detalleventa;
select * from ventas;
select * from auditoriaProductos;
select * from auditoriaClientes;
select * from auditoriaUsuarios;

drop table detalleventa;
drop table ventas;

select codigocl,dni,nombrecl,apellidos from clientes where indicador='A' and nombrecl like '%r%';

select codigous,contraseña from usuarios;
select codigous,nombreus,contraseña,rol from usuarios;
select * from usuarios;
select codigous,nombreus,contraseña,rol from usuarios where rol like 'ADMINISTRADOR';
update usuarios set nombreus='PEDRITO',contraseña='GIL' where codigous='ADM0001';
update productos set stock=7 where codigopr='PR0002';

select codigove,cliente,vendedor,subtotal,igv,total,date(fecha) from ventas where vendedor='EMP0001';
drop database bd_restaurante;

INSERT INTO productos (codigopr, descripcion, preciou, stock, indicador) VALUES
('PR0001', 'Cereal Integral', '20', '50', 'A'),
('PR0002', 'Arroz Blanco', '15', '200', 'A'),
('PR0003', 'Leche Entera', '10', '150', 'A'),
('PR0004', 'Agua Mineral', '8', '300', 'A'),
('PR0005', 'Jugo de Manzana', '12', '100', 'A'),
('PR0006', 'Pan de Molde', '5', '120', 'A'),
('PR0007', 'Galletas de Chocolate', '18', '80', 'A'),
('PR0008', 'Atún en Lata', '25', '60', 'A'),
('PR0009', 'Aceite de Oliva', '30', '40', 'A'),
('PR0010', 'Harina de Trigo', '7', '90', 'A'),
('PR0011', 'Café Molido', '22', '70', 'A'),
('PR0012', 'Té Verde', '15', '110', 'A'),
('PR0013', 'Detergente en Polvo', '25', '60', 'A'),
('PR0014', 'Jabón Líquido', '10', '150', 'A'),
('PR0015', 'Papel Higiénico', '8', '200', 'A');

INSERT INTO clientes (codigocl, dni, nombrecl, apellidos, indicador) VALUES
('CL0004', '65748392', 'Laura', 'Mendoza García', 'A'),
('CL0005', '84563729', 'Andrés', 'López Fernández', 'A'),
('CL0006', '75938465', 'María', 'Hernández Ruiz', 'A'),
('CL0007', '83746291', 'Carlos', 'Pérez Jiménez', 'A'),
('CL0008', '93847562', 'Lucía', 'Sánchez Martínez', 'A'),
('CL0009', '84736259', 'David', 'Ramírez Torres', 'A'),
('CL0010', '73948562', 'Ana', 'Gómez Morales', 'A'),
('CL0011', '94857623', 'Jorge', 'Díaz Ortiz', 'A');







