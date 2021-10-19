-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema cinema
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema cinema
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `cinema` DEFAULT CHARACTER SET utf8 ;
USE `cinema` ;

-- -----------------------------------------------------
-- Table `cinema`.`Categories`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cinema`.`Categories` (
  `id` INT NOT NULL,
  `Name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cinema`.`Movies`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cinema`.`Movies` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `Title` VARCHAR(45) NOT NULL,
  `Rating` FLOAT NOT NULL,
  `Min_Age` SMALLINT NOT NULL,
  `Description` VARCHAR(255) NOT NULL,
  `Category_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Movies_Category1_idx` (`Category_id` ASC),
  CONSTRAINT `fk_Movies_Category1`
    FOREIGN KEY (`Category_id`)
    REFERENCES `cinema`.`Categories` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cinema`.`Actors`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cinema`.`Actors` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `First_Name` VARCHAR(45) NOT NULL,
  `Last_Name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cinema`.`Movie_Actors`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cinema`.`Movie_Actors` (
  `Movies_id` INT NOT NULL,
  `Actors_id` INT NOT NULL,
  PRIMARY KEY (`Movies_id`, `Actors_id`),
  INDEX `fk_Movies_has_Actors_Actors1_idx` (`Actors_id` ASC),
  INDEX `fk_Movies_has_Actors_Movies_idx` (`Movies_id` ASC),
  CONSTRAINT `fk_Movies_has_Actors_Movies`
    FOREIGN KEY (`Movies_id`)
    REFERENCES `cinema`.`Movies` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Movies_has_Actors_Actors1`
    FOREIGN KEY (`Actors_id`)
    REFERENCES `cinema`.`Actors` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cinema`.`Shows`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cinema`.`Shows` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `Date` DATE NOT NULL,
  `Time` TIME NOT NULL,
  `Movies_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Shows_Movies1_idx` (`Movies_id` ASC),
  CONSTRAINT `fk_Shows_Movies1`
    FOREIGN KEY (`Movies_id`)
    REFERENCES `cinema`.`Movies` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cinema`.`Theaters`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cinema`.`Theaters` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `Street` VARCHAR(45) NOT NULL,
  `City` VARCHAR(45) NOT NULL,
  `Zipcode` SMALLINT NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cinema`.`Halls`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cinema`.`Halls` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `Theaters_id` INT NOT NULL,
  `Tag` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Halls_Theaters1_idx` (`Theaters_id` ASC),
  CONSTRAINT `fk_Halls_Theaters1`
    FOREIGN KEY (`Theaters_id`)
    REFERENCES `cinema`.`Theaters` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cinema`.`Hall_Shows`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cinema`.`Hall_Shows` (
  `Hall_id` INT NOT NULL,
  `Shows_id` INT NOT NULL,
  PRIMARY KEY (`Hall_id`, `Shows_id`),
  INDEX `fk_Hall_has_Shows_Shows1_idx` (`Shows_id` ASC),
  INDEX `fk_Hall_has_Shows_Hall1_idx` (`Hall_id` ASC),
  CONSTRAINT `fk_Hall_has_Shows_Hall1`
    FOREIGN KEY (`Hall_id`)
    REFERENCES `cinema`.`Halls` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Hall_has_Shows_Shows1`
    FOREIGN KEY (`Shows_id`)
    REFERENCES `cinema`.`Shows` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
