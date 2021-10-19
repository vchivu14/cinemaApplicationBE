-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema cinemaV5
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema cinemaV5
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `cinemaV5` DEFAULT CHARACTER SET utf8 ;
USE `cinemaV5` ;

-- -----------------------------------------------------
-- Table `cinemaV5`.`Categories`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cinemaV5`.`Categories` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `Name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cinemaV5`.`Movies`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cinemaV5`.`Movies` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `Title` VARCHAR(45) NOT NULL,
  `Rating` FLOAT NOT NULL,
  `Min_Age` SMALLINT NOT NULL,
  `Description` VARCHAR(255) NOT NULL,
  `Image` BLOB NULL,
  `Trailer` VARCHAR(180) NULL,
  `Category_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Movies_Category1_idx` (`Category_id` ASC),
  CONSTRAINT `fk_Movies_Category1`
    FOREIGN KEY (`Category_id`)
    REFERENCES `cinemaV5`.`Categories` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cinemaV5`.`Actors`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cinemaV5`.`Actors` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `First_Name` VARCHAR(45) NOT NULL,
  `Last_Name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cinemaV5`.`Movie_Actors`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cinemaV5`.`Movie_Actors` (
  `Movies_id` INT NOT NULL,
  `Actors_id` INT NOT NULL,
  PRIMARY KEY (`Movies_id`, `Actors_id`),
  INDEX `fk_Movies_has_Actors_Actors1_idx` (`Actors_id` ASC),
  INDEX `fk_Movies_has_Actors_Movies_idx` (`Movies_id` ASC),
  CONSTRAINT `fk_Movies_has_Actors_Movies`
    FOREIGN KEY (`Movies_id`)
    REFERENCES `cinemaV5`.`Movies` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Movies_has_Actors_Actors1`
    FOREIGN KEY (`Actors_id`)
    REFERENCES `cinemaV5`.`Actors` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cinemaV5`.`Theaters`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cinemaV5`.`Theaters` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `Name` VARCHAR(45) NOT NULL,
  `Street` VARCHAR(45) NOT NULL,
  `City` VARCHAR(45) NOT NULL,
  `Zipcode` SMALLINT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `Name_UNIQUE` (`Name` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cinemaV5`.`Halls`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cinemaV5`.`Halls` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `Tag` VARCHAR(45) NOT NULL,
  `Theaters_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Halls_Theaters1_idx` (`Theaters_id` ASC),
  CONSTRAINT `fk_Halls_Theaters1`
    FOREIGN KEY (`Theaters_id`)
    REFERENCES `cinemaV5`.`Theaters` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cinemaV5`.`Movies_Playing`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cinemaV5`.`Movies_Playing` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `Date_Starts` DATE NOT NULL,
  `Date_Ends` DATE NOT NULL,
  `Movies_id` INT NOT NULL,
  `Theaters_id` INT NOT NULL,
  INDEX `fk_Movies_Playing_Theaters1_idx` (`Theaters_id` ASC),
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_Movies_in_Theater_Movies1`
    FOREIGN KEY (`Movies_id`)
    REFERENCES `cinemaV5`.`Movies` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Movies_Playing_Theaters1`
    FOREIGN KEY (`Theaters_id`)
    REFERENCES `cinemaV5`.`Theaters` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cinemaV5`.`Shows`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cinemaV5`.`Shows` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `Date` DATE NOT NULL,
  `Time` TIME NOT NULL,
  `Halls_id` INT NOT NULL,
  `Movies_Playing_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Shows_Halls1_idx` (`Halls_id` ASC),
  INDEX `fk_Shows_Movies_Playing1_idx` (`Movies_Playing_id` ASC),
  CONSTRAINT `fk_Shows_Halls1`
    FOREIGN KEY (`Halls_id`)
    REFERENCES `cinemaV5`.`Halls` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Shows_Movies_Playing1`
    FOREIGN KEY (`Movies_Playing_id`)
    REFERENCES `cinemaV5`.`Movies_Playing` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cinemaV5`.`Seats`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cinemaV5`.`Seats` (
  `Number` VARCHAR(45) NOT NULL,
  `row` SMALLINT NOT NULL,
  `column` SMALLINT NOT NULL,
  `Halls_id` INT NOT NULL,
  INDEX `fk_Seats_Halls1_idx` (`Halls_id` ASC),
  PRIMARY KEY (`Number`),
  CONSTRAINT `fk_Seats_Halls1`
    FOREIGN KEY (`Halls_id`)
    REFERENCES `cinemaV5`.`Halls` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cinemaV5`.`Tickets`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cinemaV5`.`Tickets` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `Price` DOUBLE NOT NULL,
  `Shows_id` BIGINT NOT NULL,
  `Seats_Number` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Tickets_Shows1_idx` (`Shows_id` ASC),
  INDEX `fk_Tickets_Seats1_idx` (`Seats_Number` ASC),
  CONSTRAINT `fk_Tickets_Shows1`
    FOREIGN KEY (`Shows_id`)
    REFERENCES `cinemaV5`.`Shows` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Tickets_Seats1`
    FOREIGN KEY (`Seats_Number`)
    REFERENCES `cinemaV5`.`Seats` (`Number`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
