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
-- CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE SEQUENCE issues_id_seq START 1000;
CREATE TABLE issues (
    -- id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
    id INTEGER DEFAULT nextval('issues_id_seq') PRIMARY KEY,
    title VARCHAR(255) NOT NULL DEFAULT '',
    description TEXT NOT NULL DEFAULT '',
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW(),
    version INTEGER NOT NULL DEFAULT 1
);

INSERT INTO issues(title, description) VALUES('Issue1', 'This is a test issue.');
INSERT INTO issues(title, description) VALUES('Issue2', 'This is a test issue.');
INSERT INTO issues(title, description) VALUES('Issue3', 'This is a test issue.');
