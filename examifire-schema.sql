SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

DROP SCHEMA IF EXISTS `examifire`;
CREATE SCHEMA `examifire`;
USE `examifire`;

--
-- Administration User for the schema `examifire`
--

DROP USER IF EXISTS 'examifire'@'localhost';
CREATE USER 'examifire'@'localhost' IDENTIFIED BY 'examifire' PASSWORD EXPIRE NEVER;
GRANT ALL ON `examifire`.* TO 'examifire'@'localhost';
GRANT SELECT, INSERT ON `examifire`.* TO 'examifire'@'localhost';

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;