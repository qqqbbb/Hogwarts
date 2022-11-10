create table Cars (Id serial primary key, Make text UNIQUE not NULL, Model text UNIQUE not NULL, Price BIGINT CHECK (Price > 0));
create table Persons (Id serial primary key, Name text not NULL, Age SMALLINT CHECK (Age > 0), DriverLicense bool default false, CarId INTEGER REFERENCES Cars (Id) );

