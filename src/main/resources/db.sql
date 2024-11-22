DROP DATABASE IF EXISTS machine_renting;

CREATE DATABASE machine_renting;

USE machine_renting;

CREATE TABLE clients (
    id INT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    phone_number VARCHAR(20) NOT NULL,
    address VARCHAR(255) NOT NULL
);

CREATE TABLE machines (
    id INT AUTO_INCREMENT PRIMARY KEY,
    model VARCHAR(255) NOT NULL,
    serial_number VARCHAR(255) NOT NULL UNIQUE,
    status ENUM('AVAILABLE', 'RENTED') NOT NULL
);

CREATE TABLE rentings (
    id INT AUTO_INCREMENT PRIMARY KEY,
    starting_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    disabled_at DATETIME,
    client_id INT NOT NULL,
    machine_id INT NOT NULL,
    CONSTRAINT fk_client_id FOREIGN KEY (client_id) REFERENCES clients(id),
    CONSTRAINT fk_machine_id FOREIGN KEY (machine_id) REFERENCES machines(id)
);