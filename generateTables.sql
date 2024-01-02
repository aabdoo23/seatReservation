CREATE DATABASE seatReservation;
use seatReservation;

CREATE TABLE Seat (
	seatID int not null,
	isBooked boolean,
	_row int,
	_column int,
	price double,
	hallID int,
    
	primary key (seatID)
);

CREATE TABLE Hall(
	hallID int not null, 
    numberOfRows int,
    numberOfColumns int,
    hallName varchar(50),
    
    primary key (hallID)
);

CREATE TABLE Ticket(
	ticketID int not null,
    issueTime datetime,
    
    primary key (ticketId)
);

CREATE TABLE Party(
	partyID int not null,
    numberOfBookedSeats int,
    endTime datetime,
    startTime datetime,
    
    primary key (partyID)
);

CREATE TABLE _User(
	userID int not null,
    email varchar (40) not null,
    password varchar(20) not null,
	firstName varchar(20),
    lastName varchar(20),
    
    primary key (userID)
);

CREATE TABLE UserPhone(
	phoneNumber varchar(20)
);

CREATE TABLE Movie(
	movieID int not null,
	_name varchar(25),
    _description TEXT,
    genre varchar(25),
    duration INT,
    releaseDate datetime,
    posterLink TEXT,
    
    primary key (movieID)
);

CREATE TABLE CreditCard(
	creditCardID int not null,
    expireDate datetime,
    cardNumber varchar(20) not null,
    cardHolderName varchar(30),
    cvv int not null,

	primary key (creditCardID)
);

ALTER TABLE Seat ADD FOREIGN KEY (hallID) REFERENCES Hall(hallID);

ALTER TABLE Ticket ADD column (partyID int, seatID int, userID int);
ALTER TABLE Ticket ADD FOREIGN KEY (partyID) REFERENCES Party(partyID);
ALTER TABLE Ticket ADD FOREIGN KEY (seatID) REFERENCES Seat(seatID);
ALTER TABLE Ticket ADD FOREIGN KEY (userID) REFERENCES _User(userID);

ALTER TABLE Party ADD column (hallID int, movieID int);
ALTER TABLE Party ADD FOREIGN KEY (hallID) REFERENCES Hall(hallID);
ALTER TABLE Party ADD FOREIGN KEY (movieID) REFERENCES Movie(movieID);

ALTER TABLE _User ADD column (creditCardID int);
ALTER TABLE _User ADD FOREIGN KEY (creditCardID) REFERENCES CreditCard(CreditCardID);

ALTER TABLE UserPhone ADD (userID int);
ALTER TABLE UserPhone ADD FOREIGN KEY (userID) REFERENCES _User(userID);