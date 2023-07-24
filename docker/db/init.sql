-- create user
DROP USER IF EXISTS kanban;
CREATE USER kanban WITH PASSWORD 'kanban1234';
-- ALTER USER kb set serach_path to public;

-- create db
DROP DATABASE IF EXISTS kanban;
CREATE DATABASE kanban;

ALTER DATABASE kanban OWNER TO kanban;
GRANT ALL PRIVILEGES ON DATABASE kanban TO kanban;

-- create schema
-- CREATE SCHEMA kanban;

-- change db & user
\c kanban kanban

-- create table
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE TABLE tasks (
    id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
    title varchar(255)  NOT NULL DEFAULT '',
    description TEXT  NOT NULL DEFAULT '',
    created_at TIMESTAMP DEFAULT NOW()
);

INSERT INTO tasks(title, description) VALUES('test1', 'this is test task.');
INSERT INTO tasks(title, description) VALUES('test2', 'this is test task.');
INSERT INTO tasks(title, description) VALUES('test3', 'this is test task.');
