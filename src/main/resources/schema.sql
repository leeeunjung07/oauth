create table IF NOT EXISTS user(
--    no int(10) NOT NULL AUTO_INCREMENT,
    client varchar(10),
    id varchar(50) PRIMARY KEY,
    name varchar(10),
    image varchar(200),
    email varchar(50) UNIQUE
);
