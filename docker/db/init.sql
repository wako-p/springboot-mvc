-- create user
DROP USER IF EXISTS kanban;
CREATE USER kanban WITH PASSWORD 'kanban1234';
-- ALTER USER kanban set serach_path to public;

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

-- プロジェクトテーブル
CREATE SEQUENCE projects_id_seq START 1000;
CREATE TABLE projects (
    id INTEGER DEFAULT nextval('projects_id_seq') PRIMARY KEY,
    name VARCHAR(255) NOT NULL DEFAULT '',
    description TEXT NOT NULL DEFAULT '',
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW(),
    version INTEGER NOT NULL DEFAULT 1
);

INSERT INTO projects(name, description) VALUES('ProjectA', 'This is a test project.');
INSERT INTO projects(name, description) VALUES('ProjectB', 'This is a test project.');
INSERT INTO projects(name, description) VALUES('ProjectC', 'This is a test project.');

-- 課題テーブル
-- CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE SEQUENCE issues_id_seq START 1000;
CREATE TABLE issues (
    -- id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
    id INTEGER DEFAULT nextval('issues_id_seq') PRIMARY KEY,
    project_id INTEGER NOT NULL REFERENCES projects(id),
    title VARCHAR(255) NOT NULL DEFAULT '',
    description TEXT NOT NULL DEFAULT '',
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW(),
    version INTEGER NOT NULL DEFAULT 1
);

INSERT INTO issues(project_id, title, description) VALUES(1000, 'Issue1', 'This is a test issue.');
INSERT INTO issues(project_id, title, description) VALUES(1000, 'Issue2', 'This is a test issue.');
INSERT INTO issues(project_id, title, description) VALUES(1000, 'Issue3', 'This is a test issue.');
INSERT INTO issues(project_id, title, description) VALUES(1001, 'Issue1', 'This is a test issue.');
INSERT INTO issues(project_id, title, description) VALUES(1002, 'Issue1', 'This is a test issue.');
