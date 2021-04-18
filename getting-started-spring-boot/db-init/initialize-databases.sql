CREATE ROLE shikanga WITH LOGIN PASSWORD 'shikanga';
CREATE ROLE shaka WITH LOGIN PASSWORD 'shaka' NOSUPERUSER INHERIT NOCREATEDB NOCREATEROLE NOREPLICATION;

CREATE DATABASE student;

GRANT ALL PRIVILEGES ON DATABASE student TO shikanga;
GRANT ALL PRIVILEGES ON DATABASE student TO postgres;

GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO shikanga;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO shaka;