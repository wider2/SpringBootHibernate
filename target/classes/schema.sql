DROP TABLE IF EXISTS BITPAY;
 
CREATE TABLE BITPAY (
  id INT(10) AUTO_INCREMENT PRIMARY KEY,
  from_currency VARCHAR(3) NOT NULL,
  code VARCHAR(3) NOT NULL,
  name VARCHAR(50) NOT NULL,
  rate NUMERIC(10,2) NOT NULL,
  date_pub datetime DEFAULT NULL
);
