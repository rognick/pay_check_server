# --- !Ups

CREATE TABLE users (
  id            INT                        NOT NULL AUTO_INCREMENT PRIMARY KEY,
  login_name    VARCHAR(30)                NOT NULL UNIQUE,
  password      VARCHAR(65)                NOT NULL,
  first_name    VARCHAR(30)                NOT NULL,
  last_name     VARCHAR(30)                NOT NULL,
  birthday      DATE,
  email         VARCHAR(20),
  mobile        VARCHAR(30),
  token VARCHAR(65)
)
  ENGINE = INNODB
  CHARSET =UTF8;

# --- !Downs

DROP TABLE IF EXISTS users;