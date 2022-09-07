drop database if exists mimuseum;

create database mimuseum;
use mimuseum;

create table tbArt(
IDArte int primary key auto_increment,
NomeArte varchar(50) not null,
NomeArtista varchar(50) not null,
AnoArte smallint,
EstiloArte varchar(30) not null,
UrlArte varchar(500) not null
);

create table tbUser(
IDUser int primary key auto_increment,
Username varchar(50) not null unique,
PassUser varchar(25) not null
);

create table tbLike(
IDUser int not null,
IDArte int not null,
foreign key (IDUser) references tbUser(IDUser),
foreign key (IDArte) references tbArt(IDArte)
);

insert into tbArt (NomeArte, NomeArtista, AnoArte, EstiloArte, UrlArte) values
("A Grande Onda de Kanagawa", "Katsushika Hokusai", 1831, "Gravura", "https://artsandculture.google.com/asset/the-great-wave-off-the-coast-of-kanagawa/fAFp7yddSAtcTQ?hl=pt-BR"),

("Mona Lisa", "Leonardo da Vinci", 1530, "Pintura a óleo", "https://cdn.pariscityvision.com/library/image/5449.jpg"),

("A Noite Estrelada", "Vincent Van Gogh", 1889, "Pintura a óleo", "https://artsandculture.google.com/asset/the-starry-night/bgEuwDxel93-Pg?hl=pt-BR"),

("A Persistência da Memória", "Salvador Dali", 1931, "Pintura a óleo/bronze", "https://cdn.culturagenial.com/imagens/clocks-cke.jpg"),

("Meisje met de parel", "Johannes Vermeer", 1665, "Pintura a óleo", "https://artsandculture.google.com/asset/girl-with-a-pearl-earring/3QFHLJgXCmQm2Q?hl=pt-BR&avm=2"),

("American Gothic", "Grant Wood", 1930, "Pintura a óleo", "https://artsandculture.google.com/asset/american-gothic/5QEPm0jCc183Aw?hl=pt-BR");

insert into tbUser (Username, PassUser) values
("PearGus", "granOuvre22"),
("BrunoB", "senha123");

insert into tbLike values
(1,1),
(1,2),
(1,3),
(1,5),
(2,5),
(2,6);

select * from tbArt;

/*"insert into tbArt (NomeArte,NomeArtista,AnoArte,EstiloArte,UrlArte) values ('Spring Morning in the Han Palace', 'Qiu Ying', 1552, 
'Pergaminho', 'https://www.comuseum.com/wp-content/uploads/2015/10/qiu-ying_spring-morning-in-the-han-palace_part.jpg');";

/*update tbArt set NomeArte = 'Manhã de Primavera no Palácio Han', NomeArtista = 'Qiu Ying', AnoArte = 1552, EstiloArte = 'Pergaminho',
 UrlArte = 'https://www.comuseum.com/wp-content/uploads/2015/10/qiu-ying_spring-morning-in-the-han-palace_part.jpg' where IDArte = 7;*/
 
/*delete from tbArt where IDArte = 7;*/

select * from tbArt where NomeArte like 'A%';

select * from tbUser;
select * from tbLike;
