create database climateresourcetrackermain;
use climateresourcetrackermain;
show tables;

-- Create a new Location table with location_id as primary key and location_name as unique
CREATE TABLE Locations (
    location_id INT AUTO_INCREMENT PRIMARY KEY,
    location_name VARCHAR(100) NOT NULL UNIQUE,
    region VARCHAR(100),
    country VARCHAR(100)
);

CREATE TABLE ClimateData (
    data_id INT AUTO_INCREMENT PRIMARY KEY,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    temperature DECIMAL(5, 2),
    humidity DECIMAL(5, 2),
    rainfall DECIMAL(5, 2),
    air_quality VARCHAR(50),
    location_id INT,
    location_name VARCHAR(100),
    FOREIGN KEY (location_id) REFERENCES Location(location_id) ON DELETE CASCADE,
    FOREIGN KEY (location_name) REFERENCES Location(location_name) ON DELETE CASCADE
);

-- Modify ResourceUsage table to include both location_id and location_name references
CREATE TABLE ResourceUsage (
    usage_id INT AUTO_INCREMENT PRIMARY KEY,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    resource_type VARCHAR(50),
    usage_amount DECIMAL(10, 2),
    location_id INT,
    location_name VARCHAR(100),
    FOREIGN KEY (location_id) REFERENCES Location(location_id) ON DELETE CASCADE,
    FOREIGN KEY (location_name) REFERENCES Location(location_name) ON DELETE CASCADE
);

-- Modify SystemSettings table to include only location_id reference (optional)
CREATE TABLE SystemSettings (
    setting_id INT AUTO_INCREMENT PRIMARY KEY,
    setting_name VARCHAR(50),
    setting_value VARCHAR(255),
    location_id INT,
    FOREIGN KEY (location_id) REFERENCES Location(location_id) ON DELETE CASCADE
);


select * from climatedata;
select * from resourceusage;
select * from systemsettings;
select * from locations;


