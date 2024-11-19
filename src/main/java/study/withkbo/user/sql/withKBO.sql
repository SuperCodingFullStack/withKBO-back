use withKBO;

create table user(
    id int primary key auto_increment,
    team_id int not null,
    uEmail varchar(100) not null,
    uPwd varchar(30) not null,
    uName varchar(30) not null,
    uNickname varchar(30),
    uPhone varchar(30) not null,
    uPhoneAuth boolean not null,
    uAddress varchar(100) not null,
    uStatus varchar(30) not null,
    profileImg varchar(150) not null
);