DROP DATABASE IF EXISTS RentalSystem;
CREATE DATABASE IF NOT EXISTS RentalSystem;
SHOW DATABASES;
USE RentalSystem;
#------------------------------------------------
DROP TABLE IF EXISTS Insurence;
CREATE TABLE IF NOT EXISTS Insurence(
    insurenceId VARCHAR(10),
    companyName VARCHAR(50),
    contact VARCHAR(20),
    detail VARCHAR(50),
    CONSTRAINT PRIMARY KEY (insurenceId)
    );
SHOW TABLES;
DESCRIBE Insurence;
#------------------------------------------------
DROP TABLE IF EXISTS License;
CREATE TABLE IF NOT EXISTS  License(
    licenseId VARCHAR(10),
    licenseNo VARCHAR(15),
    expireDate DATE,
    CONSTRAINT PRIMARY KEY (licenseId)
    );
SHOW TABLES;
DESCRIBE License;
#------------------------------------------------
DROP TABLE IF EXISTS Client;
CREATE TABLE IF NOT EXISTS Client(
    clientId VARCHAR(10),
    clientName VARCHAR(25) NOT NULL DEFAULT 'Unknown',
    clientAddress VARCHAR(50),
    clientNIC VARCHAR(20),
    clientContact VARCHAR(15),
    CONSTRAINT PRIMARY KEY (clientId)
    );
SHOW TABLES;
DESCRIBE Client;
#------------------------------------------------
DROP TABLE IF EXISTS Vehicle;
CREATE TABLE IF NOT EXISTS Vehicle(
    vehicleId VARCHAR(10),
    vehicleNumber VARCHAR(15) NOT NULL,
    vehicleType VARCHAR(10) NOT NULL DEFAULT 'Empty',
    vehicleBrand VARCHAR(25),
    vehicleColour VARCHAR(20),
    insurenceId VARCHAR(10),
    rentalCost DOUBLE NOT NULL DEFAULT 0.00,
    taxiCost DOUBLE NOT NULL DEFAULT 0.00,
    vStatus VARCHAR(20),
    discount DOUBLE,
    CONSTRAINT PRIMARY KEY (vehicleId),
    CONSTRAINT FOREIGN KEY (insurenceId) REFERENCES Insurence(insurenceId) ON DELETE CASCADE ON UPDATE CASCADE
);
SHOW TABLES;
DESCRIBE Vehicle;
#----------------------------------------------------
DROP TABLE IF EXISTS Driver;
CREATE TABLE IF NOT EXISTS Driver(
    driverId VARCHAR(10),
    driverName VARCHAR(20) NOT NULL DEFAULT 'Unknown',
    driverAddress VARCHAR(50),
    driverContact VARCHAR(15),
    driverNIC VARCHAR(20),
    licenseId VARCHAR(10),
    dStatus VARCHAR(20),
    CONSTRAINT PRIMARY KEY (driverId),
    CONSTRAINT FOREIGN KEY (licenseId) REFERENCES License(licenseId) ON DELETE CASCADE ON UPDATE CASCADE
    );
SHOW TABLES;
DESCRIBE Driver;
#-----------------------------------------------------
DROP TABLE IF EXISTS Rent;
CREATE TABLE IF NOT EXISTS Rent(
    rentId VARCHAR(10),
    clientId VARCHAR(10),
    vehicleId VARCHAR(10),
    rentDate DATE,
    rentedDate Date,
    rentTime VARCHAR(20),
    noOfDays INT NOT NULL DEFAULT 0,
    advance DOUBLE DEFAULT 0.00,
    CONSTRAINT PRIMARY KEY (rentId,clientId,vehicleId),
    CONSTRAINT FOREIGN KEY (clientId) REFERENCES Client(clientId) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FOREIGN KEY (vehicleId) REFERENCES Vehicle(vehicleId) ON DELETE CASCADE ON UPDATE CASCADE
    );
SHOW TABLES;
DESCRIBE Rent;
#-----------------------------------------------------
DROP TABLE IF EXISTS RentPayment;
CREATE TABLE IF NOT EXISTS RentPayment(
    rentInvoiceNo VARCHAR(10),
    rentId VARCHAR(10),
    clientId VARCHAR(10),
    vehicleId VARCHAR(10),
    returnDate DATE,
    damageDetail VARCHAR(100),
    damageCost DOUBLE,
    discount DOUBLE,
    total DOUBLE DEFAULT 0.00,
    CONSTRAINT PRIMARY KEY (rentInvoiceNo,rentId, clientId, vehicleId),
    CONSTRAINT FOREIGN KEY (rentId) REFERENCES Rent(rentId) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FOREIGN KEY (clientId) REFERENCES Client(clientId) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FOREIGN KEY (vehicleId) REFERENCES Vehicle(vehicleId) ON DELETE CASCADE ON UPDATE CASCADE
    );
SHOW TABLES;
DESCRIBE RentPayment;
#---------------------------------------------------------
DROP TABLE IF EXISTS Taxi;
CREATE TABLE IF NOT EXISTS Taxi(
    taxiId VARCHAR(10),
    driverId VARCHAR(10),
    vehicleId VARCHAR(10),
    CONSTRAINT PRIMARY KEY (taxiId, driverId, vehicleId),
    CONSTRAINT FOREIGN KEY (driverId) REFERENCES Driver(driverId) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FOREIGN KEY (vehicleId) REFERENCES Vehicle(vehicleId) ON DELETE CASCADE ON UPDATE CASCADE
    );
SHOW TABLES;
DESCRIBE Taxi;
#----------------------------------------------------------
DROP TABLE IF EXISTS Hire;
CREATE TABLE IF NOT EXISTS Hire(
    hireId VARCHAR(10),
    clientId VARCHAR(10),
    taxiId VARCHAR(10),
    date DATE,
    time VARCHAR(20),
    initialMileage DOUBLE DEFAULT 0.00,
    CONSTRAINT PRIMARY KEY (hireId,clientId,taxiId),
    CONSTRAINT FOREIGN KEY (clientId) REFERENCES Client(clientId) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FOREIGN KEY (taxiId) REFERENCES Taxi(taxiId) ON DELETE CASCADE ON UPDATE CASCADE
    );
SHOW TABLES;
DESCRIBE Hire;
#--------------------------------------------------------------
DROP TABLE IF EXISTS HirePayment;
CREATE TABLE IF NOT EXISTS HirePayment(
    hireInvoiceNo VARCHAR(10),
    hireId VARCHAR(10),
    clientId VARCHAR(10),
    vehicleId VARCHAR(10),
    driverId VARCHAR(10),
    arrivalTime VARCHAR(20),
    returnMileage DOUBLE DEFAULT 0.00,
    total DOUBLE DEFAULT 0.00,
    CONSTRAINT PRIMARY KEY (hireInvoiceNo,hireId, clientId, vehicleId,driverId),
    CONSTRAINT FOREIGN KEY (hireId) REFERENCES Hire(hireId) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FOREIGN KEY (clientId) REFERENCES Client(clientId) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FOREIGN KEY (vehicleId) REFERENCES Vehicle(vehicleId) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FOREIGN KEY (driverId) REFERENCES Driver(driverId) ON DELETE CASCADE ON UPDATE CASCADE
    );
SHOW TABLES;
DESCRIBE HirePayment;
