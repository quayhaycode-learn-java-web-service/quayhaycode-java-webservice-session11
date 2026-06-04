CREATE DATABASE IF NOT EXISTS student_db;

USE student_db;

CREATE TABLE students (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    score DOUBLE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO students (name, email, score, created_at) VALUES
('Nguyen Van A', 'nguyenvana@example.com', 8.5, CURRENT_TIMESTAMP),
('Tran Thi B', 'tranthib@example.com', 9.2, CURRENT_TIMESTAMP),
('Le Van C', 'levanc@example.com', 7.0, CURRENT_TIMESTAMP),
('Pham Minh D', 'phamminhd@example.com', 6.5, CURRENT_TIMESTAMP),
('Hoang Thi E', 'hoangthie@example.com', 10.0, CURRENT_TIMESTAMP);