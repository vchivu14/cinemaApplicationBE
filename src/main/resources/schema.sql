-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema cinema
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema cinema
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `cinema` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `cinema` ;

-- -----------------------------------------------------
-- Table `cinema`.`Actors`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cinema`.`Actors` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(255) NOT NULL,
  `last_name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `cinema`.`Categories`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cinema`.`Categories` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `cinema`.`Theaters`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cinema`.`Theaters` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `street` VARCHAR(255) NOT NULL,
  `city` VARCHAR(255) NOT NULL,
  `zipcode` SMALLINT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `Name_UNIQUE` (`name` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `cinema`.`Halls`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cinema`.`Halls` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `tag` VARCHAR(255) NOT NULL,
  `Theaters_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Halls_Theaters1_idx` (`Theaters_id` ASC) VISIBLE,
  CONSTRAINT `fk_Halls_Theaters1`
    FOREIGN KEY (`Theaters_id`)
    REFERENCES `cinema`.`Theaters` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `cinema`.`Movies`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cinema`.`Movies` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(255) NOT NULL,
  `rating` FLOAT NULL,
  `min_age` SMALLINT NULL,
  `description` TEXT NULL,
  `image` VARCHAR(255) NULL DEFAULT NULL,
  `trailer` VARCHAR(255) NULL DEFAULT NULL,
  `Category_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Movies_Category1_idx` (`Category_id` ASC) VISIBLE,
  CONSTRAINT `fk_Movies_Category1`
    FOREIGN KEY (`Category_id`)
    REFERENCES `cinema`.`Categories` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `cinema`.`Movie_Actors`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cinema`.`Movie_Actors` (
  `Movies_id` INT NOT NULL,
  `Actors_id` INT NOT NULL,
  PRIMARY KEY (`Movies_id`, `Actors_id`),
  INDEX `fk_Movies_has_Actors_Actors1_idx` (`Actors_id` ASC) VISIBLE,
  INDEX `fk_Movies_has_Actors_Movies_idx` (`Movies_id` ASC) VISIBLE,
  CONSTRAINT `fk_Movies_has_Actors_Actors1`
    FOREIGN KEY (`Actors_id`)
    REFERENCES `cinema`.`Actors` (`id`),
  CONSTRAINT `fk_Movies_has_Actors_Movies`
    FOREIGN KEY (`Movies_id`)
    REFERENCES `cinema`.`Movies` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `cinema`.`Movies_Playing`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cinema`.`Movies_Playing` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `date_starts` DATE NOT NULL,
  `date_ends` DATE NOT NULL,
  `Movies_id` INT NOT NULL,
  `Theaters_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Movies_Playing_Theaters1_idx` (`Theaters_id` ASC) VISIBLE,
  INDEX `fk_Movies_in_Theater_Movies1` (`Movies_id` ASC) VISIBLE,
  CONSTRAINT `fk_Movies_in_Theater_Movies1`
    FOREIGN KEY (`Movies_id`)
    REFERENCES `cinema`.`Movies` (`id`),
  CONSTRAINT `fk_Movies_Playing_Theaters1`
    FOREIGN KEY (`Theaters_id`)
    REFERENCES `cinema`.`Theaters` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `cinema`.`Seats`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cinema`.`Seats` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `seat_row` SMALLINT NOT NULL,
  `seat_column` SMALLINT NOT NULL,
  `Halls_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Seats_Halls1_idx` (`Halls_id` ASC) VISIBLE,
  CONSTRAINT `fk_Seats_Halls1`
    FOREIGN KEY (`Halls_id`)
    REFERENCES `cinema`.`Halls` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `cinema`.`Shows`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cinema`.`Shows` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `date` DATE NOT NULL,
  `time` TIME NOT NULL,
  `Halls_id` INT NOT NULL,
  `Movies_Playing_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Shows_Halls1_idx` (`Halls_id` ASC) VISIBLE,
  INDEX `fk_Shows_Movies_Playing1_idx` (`Movies_Playing_id` ASC) VISIBLE,
  CONSTRAINT `fk_Shows_Halls1`
    FOREIGN KEY (`Halls_id`)
    REFERENCES `cinema`.`Halls` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Shows_Movies_Playing1`
    FOREIGN KEY (`Movies_Playing_id`)
    REFERENCES `cinema`.`Movies_Playing` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `cinema`.`Tickets`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cinema`.`Tickets` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `price` DOUBLE NOT NULL,
  `Shows_id` BIGINT NOT NULL,
  `Seats_id` BIGINT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Tickets_Shows1_idx` (`Shows_id` ASC) VISIBLE,
  INDEX `fk_Tickets_Seats1_idx` (`Seats_id` ASC) VISIBLE,
  CONSTRAINT `fk_Tickets_Shows1`
    FOREIGN KEY (`Shows_id`)
    REFERENCES `cinema`.`Shows` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Tickets_Seats1`
    FOREIGN KEY (`Seats_id`)
    REFERENCES `cinema`.`Seats` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
