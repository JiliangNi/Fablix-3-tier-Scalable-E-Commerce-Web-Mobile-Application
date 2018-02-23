DROP DATABASE IF EXISTS `moviedb`;
CREATE DATABASE `moviedb`;
USE `moviedb`;

create table movies(
	id varchar(10) 	NOT NULL,
    title varchar(100) NOT NULL,
	year int NOT NULL,
    director varchar(100) NOT NULL,
	primary key (id)
);

create table stars(
	id varchar(10) 	NOT NULL,
    name varchar(100) NOT NULL,
	birthYear int,
	primary key (id)
);

create table stars_in_movies(
	starId varchar(10) NOT NULL,
	movieId varchar(10) NOT NULL,
	foreign key (starId) references stars(id),
	foreign key (movieId) references movies(id)
);

create table genres(
	id int NOT NULL AUTO_INCREMENT,
    name varchar(32) NOT NULL,
	primary key (id)
);

create table genres_in_movies(
	genreId int NOT NULL,
	movieId varchar(10) NOT NULL,
    foreign key (genreId) references genres (id),
    foreign key (movieId) references movies (id)
);



create table creditcards(
	id varchar(20) NOT NULL,
    firstName varchar(50) NOT NULL,
    lastName varchar(50) NOT NULL,
    expiration date NOT NULL,
    primary key (id)
);

create table ratings(
	movieId varchar(10) NOT NULL,
	rating float NOT NULL,
	numVotes int NOT NULL,
    foreign key (movieId) references movies (id)
);

create table customers(
	id int NOT NULL AUTO_INCREMENT,
	firstName varchar(50) NOT NULL,
    lastName varchar(50) NOT NULL,
	ccId varchar(20) NOT NULL,
    address varchar(200) NOT NULL,
    email varchar(50) NOT NULL,
    password varchar(20) NOT NULL,
    primary key (id),
    foreign key (ccId) references creditcards (id)
);

create table sales(
	id int NOT NULL AUTO_INCREMENT,
	customerId int NOT NULL,
	movieId varchar(10) NOT NULL,
    saleDate date NOT NULL,
    primary key (id),
    foreign key (customerId) references customers (id),
	foreign key (movieId) references movies (id)
);

create table employees(
	email varchar(50),
    password varchar(20) not null,
    fullname varchar(100),
    primary key (email)
);


DELIMITER $$ 

CREATE PROCEDURE add_movie (IN t varchar(100), IN y int, IN d varchar(100), IN s varchar(100), IN g varchar(32), out valid int, out staradd int, out genreadd int)

BEGIN
	declare movieid varchar(10);
    declare starid varchar(10);
    declare genreid int;
    
    set movieid = (select concat('tt0', cast(substring_index(max(id), 't',-1) as unsigned) + 1 ) as m_max from movies);
    set starid = (select concat('nm', cast(substring_index(max(id), 'm',-1) as unsigned) + 1 ) as s_max from stars);
	IF(exists (select * from movies m where m.title = t and m.year = y and m.director = d)) then
		select 0 as valid, 'This Movie Already Exists!' as result;
        select 0 into valid;
        select 0 into staradd;
        select 0 into genreadd;
	ELSE
        INSERT INTO movies VALUES(movieid, t,y,d);
        if(exists (select * from stars ss where ss.name = s)) then
			select 0 into staradd;
            set starid = (select ss.id from stars ss where ss.name = s);
			INSERT INTO stars_in_movies VALUES(starid,movieid);
		else
			INSERT INTO stars (id, name) VALUES(starid,s);
			select 1 into staradd;
			INSERT INTO stars_in_movies VALUES(starid,movieid);
		end if;
            
        if(exists (select * from genres gs where gs.name = g)) then
			select 0 into genreadd;
			set genreid = (select gs.id from genres gs where gs.name = g);
            INSERT INTO genres_in_movies VALUES(genreid,movieid);
        else
			INSERT INTO genres (name) VALUES(g);
            select 1 into genreadd;
            set genreid = (select gs.id from genres gs where gs.name = g);
			INSERT INTO genres_in_movies VALUES(genreid,movieid);
        end if;
        
        select 1 as valid, 1 as movieadd, staradd as staradd, genreadd as genreadd;
        select 1 into valid;
        
	END IF;
END


$$
DELIMITER ; 






DELIMITER $$
CREATE PROCEDURE add_star(fullname varchar(100),byear int (11))
BEGIN
	DECLARE starid char(10) default NULL;
	IF (select id from stars where fullname = name and byear = birthYear) IS NOT NULL THEN
		select concat(fullname, "is already in database!");
	else
		SET starid = (SELECT CONCAT('nm',CAST(CONVERT(SUBSTRING_INDEX(max(id),'m',-1),UNSIGNED INTEGER)+1 as char(10))) AS num FROM moviedb.stars);
		INSERT INTO stars VALUES(starid,fullname,byear);
    END IF;
END
$$



























