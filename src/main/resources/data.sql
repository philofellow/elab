grant usage on schema public to postgres;
grant create on schema public to postgres;
alter role postgres set search_path to public;
set search_path to public;

INSERT INTO registrant (email, password_hash, apikey, name, phone, fax, organization, addressline1, addressline2, addressline3,
                        city, stateprovince, postalcode, countrycode, role)
VALUES ('demo@verisign.com', 'demo1234', 'demo1234', 'demo', '123456', '123456', 'demo inc', '123 main st', '', '',
                             'fairfax', 'va', '20033', 'US', 'USER');
INSERT INTO registrant (email, password_hash, apikey, name, phone, fax, organization, addressline1, addressline2, addressline3,
                        city, stateprovince, postalcode, countrycode, role)
VALUES ('zhao@verisign.com', 'zhao1234', 'zhao1234', 'zhao', '123456', '123456', 'zhao inc', '123 main st', '', NULL,
                             'fairfax', 'va', '20033', 'US', 'USER');
INSERT INTO domain (domain_name, registrant_id, created_date, updated_date, expired_date)
VALUES ('a.com', 1, '1/1/2000', '1/1/2001', '1/1/2010');
INSERT INTO domain (domain_name, registrant_id, created_date, updated_date, expired_date)
VALUES ('b.com', 1, '1/1/2000', '1/1/2001', '1/1/2010');

INSERT INTO contact (email, name, phone, phoneext, fax, faxext, organization, addressline1, addressline2, addressline3,
                        city, stateprovince, postalcode, countrycode, registrant_id)
VALUES ('aaaa@verisign.com', 'aaaa', '+1.123456', '123', '+1.123456', '123', 'demo inc', '123 main st', '', NULL,
                             'fairfax', 'va', '20033', 'US', '1');
INSERT INTO contact (email, name, phone, phoneext, fax, faxext, organization, addressline1, addressline2, addressline3,
                        city, stateprovince, postalcode, countrycode, registrant_id)
VALUES ('bbbb@verisign.com', 'bbbb', '+1.123456', '123', '+1.123456', '123', 'demo inc', '123 main st', '', NULL,
                             'fairfax', 'va', '20033', 'US', '1');

