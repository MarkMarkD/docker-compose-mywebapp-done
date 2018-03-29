CREATE USER dmitriy;
CREATE DATABASE mytestdb;
GRANT ALL PRIVILEGES ON DATABASE mytestdb TO dmitriy;
\c mytestdb;
CREATE TABLE public.listofparts(pnumber varchar, pname varchar, vendor varchar, qty int2, shipped date, received date);
INSERT INTO listofparts (pnumber, pname, vendor, qty, shipped, received) VALUES ('ACE', 'BLADE', 'Umbrella', 7, '2017-12-07', '2017-12-15');
INSERT INTO listofparts (pnumber, pname, vendor, qty, shipped, received) VALUES ('ASC', 'BLADE', 'Umbrella', 45, '2017-12-07', '2017-09-22');
INSERT INTO listofparts (pnumber, pname, vendor, qty, shipped, received) VALUES ('XTL', 'EDGE', 'Corp', 11, '2017-09-07', CURRENT_DATE);
INSERT INTO listofparts (pnumber, pname, vendor, qty, shipped, received) VALUES ('XTL', 'EDGE', 'Corp', 99, '2018-02-15', '2018-02-28');