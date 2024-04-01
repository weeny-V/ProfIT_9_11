-- create table CLIENT(
--     ID bigint not null AUTO_INCREMENT,
--     NAME varchar(255) not null,
--     SURNAME varchar(255) not null,
--     BIRTH_DATE date not null,
--     TOUR_ID bigint,
--     PRIMARY KEY ( ID )
-- );
--
-- create table TOUR(
--      ID bigint not null AUTO_INCREMENT,
--      NAME varchar(255) not null,
--      PRIMARY KEY ( ID )
-- );

INSERT INTO TOUR (name) values ( 'New-York-Lviv' );
INSERT INTO TOUR (NAME) values ( 'New-York-Kiev' );
INSERT INTO CLIENT (NAME, SURNAME, BIRTH_DATE, TOUR_ID) VALUES ( 'Vlad', 'Kruhlov', '2017-01-13', SELECT TOUR.ID FROM TOUR where TOUR.NAME='New-York-Kiev');