-- Created by Vertabelo (http://vertabelo.com)
-- Last modification date: 2020-12-29 14:47:30.86

-- tables
-- Table: Address
CREATE TABLE Address (
    id_address int NOT NULL AUTO_INCREMENT,
    city varchar(30) NOT NULL,
    street varchar(50) NOT NULL,
    building int NOT NULL,
    postcode int NOT NULL,
    CONSTRAINT Address_pk PRIMARY KEY (id_address)
);

-- Table: Manufacturer
CREATE TABLE Manufacturer (
    id_manufacturer int NOT NULL AUTO_INCREMENT,
    telefon varchar(15) NOT NULL,
    id_address int NOT NULL,
    CONSTRAINT Manufacturer_pk PRIMARY KEY (id_manufacturer)
);

-- Table: Medicine
CREATE TABLE Medicine (
    id_medicine int NOT NULL AUTO_INCREMENT,
    name varchar(50) NOT NULL,
    expiration_date date NOT NULL,
    price double(6,2) NOT NULL,
    structure varchar(200) NOT NULL,
    company_name varchar(20) NOT NULL,
    CONSTRAINT Medicine_pk PRIMARY KEY (id_medicine)
);

-- Table: Pharmacy
CREATE TABLE Pharmacy (
    id_pharmacy int NOT NULL AUTO_INCREMENT,
    name varchar(50) NOT NULL,
    id_address int NOT NULL,
    CONSTRAINT Pharmacy_pk PRIMARY KEY (id_pharmacy)
);

-- Table: Request
CREATE TABLE Request (
    id_order int NOT NULL AUTO_INCREMENT,
    date date NOT NULL,
    count int NOT NULL,
    id_pharmacy int NOT NULL,
    id_storage int NOT NULL,
    id_medicine int NOT NULL,
    CONSTRAINT Request_pk PRIMARY KEY (id_order)
);

-- Table: Storage
CREATE TABLE Storage (
    id_storage int NOT NULL AUTO_INCREMENT,
    id_medicine int NOT NULL,
    count int NOT NULL,
    id_address int NOT NULL,
    CONSTRAINT Storage_pk PRIMARY KEY (id_storage,id_medicine)
);

-- Table: Supply
CREATE TABLE Supply (
    id_supply int NOT NULL AUTO_INCREMENT,
    date date NOT NULL,
    count int NOT NULL,
    id_manufacturer int NOT NULL,
    id_medicine int NOT NULL,
    id_storage int NOT NULL,
    CONSTRAINT Supply_pk PRIMARY KEY (id_supply)
);

-- foreign keys
-- Reference: Manufacturer_Address (table: Manufacturer)
ALTER TABLE Manufacturer ADD CONSTRAINT Manufacturer_Address FOREIGN KEY Manufacturer_Address (id_address)
    REFERENCES Address (id_address)
    ON DELETE RESTRICT
    ON UPDATE CASCADE;

-- Reference: Order_Pharmacy (table: Request)
ALTER TABLE Request ADD CONSTRAINT Order_Pharmacy FOREIGN KEY Order_Pharmacy (id_pharmacy)
    REFERENCES Pharmacy (id_pharmacy)
    ON DELETE RESTRICT
    ON UPDATE CASCADE;

-- Reference: Order_Storage (table: Request)
ALTER TABLE Request ADD CONSTRAINT Order_Storage FOREIGN KEY Order_Storage (id_storage,id_medicine)
    REFERENCES Storage (id_storage,id_medicine)
    ON DELETE RESTRICT
    ON UPDATE CASCADE;

-- Reference: Pharmacy_Address (table: Pharmacy)
ALTER TABLE Pharmacy ADD CONSTRAINT Pharmacy_Address FOREIGN KEY Pharmacy_Address (id_address)
    REFERENCES Address (id_address)
    ON DELETE RESTRICT
    ON UPDATE CASCADE;

-- Reference: Storage_Address (table: Storage)
ALTER TABLE Storage ADD CONSTRAINT Storage_Address FOREIGN KEY Storage_Address (id_address)
    REFERENCES Address (id_address)
    ON DELETE RESTRICT
    ON UPDATE CASCADE;

-- Reference: Storage_Medicine (table: Storage)
ALTER TABLE Storage ADD CONSTRAINT Storage_Medicine FOREIGN KEY Storage_Medicine (id_medicine)
    REFERENCES Medicine (id_medicine)
    ON DELETE RESTRICT
    ON UPDATE CASCADE;

-- Reference: Supply_Manufacturer (table: Supply)
ALTER TABLE Supply ADD CONSTRAINT Supply_Manufacturer FOREIGN KEY Supply_Manufacturer (id_manufacturer)
    REFERENCES Manufacturer (id_manufacturer)
    ON DELETE RESTRICT
    ON UPDATE CASCADE;

-- Reference: Supply_Medicine (table: Supply)
ALTER TABLE Supply ADD CONSTRAINT Supply_Medicine FOREIGN KEY Supply_Medicine (id_medicine)
    REFERENCES Medicine (id_medicine)
    ON DELETE RESTRICT
    ON UPDATE CASCADE;

-- Reference: Supply_Storage (table: Supply)
ALTER TABLE Supply ADD CONSTRAINT Supply_Storage FOREIGN KEY Supply_Storage (id_storage,id_medicine)
    REFERENCES Storage (id_storage,id_medicine)
    ON DELETE RESTRICT
    ON UPDATE CASCADE;

-- End of file.

