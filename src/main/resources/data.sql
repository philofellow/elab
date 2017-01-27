grant usage on schema public to postgres;
grant create on schema public to postgres;
alter role postgres set search_path to public;
set search_path to public;


INSERT INTO lab_user(name, email, password, organization, role)
VALUES ('zhao', 'zhao@zyans.com', 'zhao1234', 'virginia tech', 'USER');

