-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema webscraper
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `webscraper` ;

-- -----------------------------------------------------
-- Schema webscraper
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `webscraper` DEFAULT CHARACTER SET utf8 ;
SHOW WARNINGS;
USE `webscraper` ;

-- -----------------------------------------------------
-- Table `webscraper`.`account`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `webscraper`.`account` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `webscraper`.`account` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `display_name` VARCHAR(45) NOT NULL,
  `user` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  UNIQUE INDEX `display-name_UNIQUE` (`display_name` ASC) VISIBLE,
  UNIQUE INDEX `user_UNIQUE` (`user` ASC) VISIBLE)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `webscraper`.`host`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `webscraper`.`host` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `webscraper`.`host` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `webscraper`.`feed`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `webscraper`.`feed` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `webscraper`.`feed` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `host_id` INT UNSIGNED NOT NULL,
  `path` VARCHAR(255) NOT NULL,
  `type` VARCHAR(45) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `path_UNIQUE` (`path` ASC) VISIBLE,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `fk_feed_host1_idx` (`host_id` ASC) VISIBLE,
  CONSTRAINT `fk_feed_host1`
    FOREIGN KEY (`host_id`)
    REFERENCES `webscraper`.`host` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `webscraper`.`image`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `webscraper`.`image` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `webscraper`.`image` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `feed_id` INT UNSIGNED NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  `path` VARCHAR(255) NOT NULL,
  `date` DATE NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_image_feed_idx` (`feed_id` ASC) VISIBLE,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  UNIQUE INDEX `path_UNIQUE` (`path` ASC) VISIBLE,
  CONSTRAINT `fk_image_feed`
    FOREIGN KEY (`feed_id`)
    REFERENCES `webscraper`.`feed` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;
SET SQL_MODE = '';
DROP USER IF EXISTS cst8288@localhost;
SET SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';
SHOW WARNINGS;
CREATE USER 'cst8288'@'localhost' IDENTIFIED BY '8288';

GRANT ALL ON *.* TO 'cst8288'@'localhost' WITH GRANT OPTION;
SHOW WARNINGS;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `webscraper`.`account`
-- -----------------------------------------------------
START TRANSACTION;
USE `webscraper`;
INSERT INTO `webscraper`.`account` (`id`, `display_name`, `user`, `password`) VALUES (1, 'administrator', 'admin', 'admin');
INSERT INTO `webscraper`.`account` (`id`, `display_name`, `user`, `password`) VALUES (2, 'Shawn', 'cst8288', '8288');

COMMIT;


-- -----------------------------------------------------
-- Data for table `webscraper`.`host`
-- -----------------------------------------------------
START TRANSACTION;
USE `webscraper`;
INSERT INTO `webscraper`.`host` (`id`, `name`) VALUES (1, 'Reddit');
INSERT INTO `webscraper`.`host` (`id`, `name`) VALUES (2, '4Chan');

COMMIT;


-- -----------------------------------------------------
-- Data for table `webscraper`.`feed`
-- -----------------------------------------------------
START TRANSACTION;
USE `webscraper`;
INSERT INTO `webscraper`.`feed` (`id`, `host_id`, `path`, `type`, `name`) VALUES (1, 1, 'https://www.reddit.com/r/Art.json', 'json', 'Art');
INSERT INTO `webscraper`.`feed` (`id`, `host_id`, `path`, `type`, `name`) VALUES (2, 1, 'https://www.reddit.com/r/EarthPorn.json', 'json', 'EarthPorn');
INSERT INTO `webscraper`.`feed` (`id`, `host_id`, `path`, `type`, `name`) VALUES (3, 1, 'https://www.reddit.com/r/wallpapers.json', 'json', 'Wallpapers');
INSERT INTO `webscraper`.`feed` (`id`, `host_id`, `path`, `type`, `name`) VALUES (4, 1, 'https://www.reddit.com/r/wallpaper.json', 'json', 'Wallpaper');

COMMIT;

