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

insert into tbArt (NomeArte, NomeArtista, AnoArte, EstiloArte, UrlArte) values
("A Grande Onda de Kanagawa", "Katsushika Hokusai", 1831, "Gravura", "https://artsandculture.google.com/asset/the-great-wave-off-the-coast-of-kanagawa/fAFp7yddSAtcTQ?hl=pt-BR"),

("Mona Lisa", "Leonardo da Vinci", 1530, "Pintura a óleo", "https://cdn.pariscityvision.com/library/image/5449.jpg"),

("A Noite Estrelada", "Vincent Van Gogh", 1889, "Pintura a óleo", "https://artsandculture.google.com/asset/the-starry-night/bgEuwDxel93-Pg?hl=pt-BR"),

("A Persistência da Memória", "Salvador Dali", 1931, "Pintura a óleo/bronze", "https://cdn.culturagenial.com/imagens/clocks-cke.jpg"),

("Meisje met de parel", "Johannes Vermeer", 1665, "Pintura a óleo", "https://artsandculture.google.com/asset/girl-with-a-pearl-earring/3QFHLJgXCmQm2Q?hl=pt-BR&avm=2"),

("American Gothic", "Grant Wood", 1930, "Pintura a óleo", "https://artsandculture.google.com/asset/american-gothic/5QEPm0jCc183Aw?hl=pt-BR");

select * from tbArt;
select * from tbArt where NomeArte like 'A%';