
CREATE TABLE `developers`.`customer`
(
  `id`       INT         NOT NULL AUTO_INCREMENT,
  `name`     VARCHAR(45) NOT NULL,
  `region`   VARCHAR(45) NOT NULL,
  `projects` VARCHAR(45) NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;

CREATE TABLE `developers`.`projects`
(
  `id`         INT         NOT NULL AUTO_INCREMENT,
  `name`       VARCHAR(45) NOT NULL,
  `company`    VARCHAR(45) NOT NULL,
  `customers`  VARCHAR(45) NULL,
  `developers` VARCHAR(45) NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;

CREATE TABLE `developers`.`skills`
(
  `id`         INT         NOT NULL AUTO_INCREMENT,
  `technology` VARCHAR(45) NOT NULL,
  `seniority`  VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;

CREATE TABLE `developers`.`companies`
(
  `id`                INT         NOT NULL AUTO_INCREMENT,
  `name`              VARCHAR(45) NOT NULL,
  `count-of-employee` VARCHAR(45) NOT NULL,
  `projects`          VARCHAR(45) NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;

CREATE TABLE `developers`.`developer`
(
  `id`        INT         NOT NULL AUTO_INCREMENT,
  `age`       INT         NOT NULL,
  `firstName` VARCHAR(45) NOT NULL,
  `lastName`  VARCHAR(45) NOT NULL,
  `sex`       VARCHAR(45) NOT NULL,
  `salary`    INT         NULL,
  `skills`    VARCHAR(45) NULL,
  `companies` VARCHAR(45) NULL,
  `projects`  VARCHAR(45) NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `developers-companies`
    FOREIGN KEY (`id`)
      REFERENCES `developers`.`companies` (`id`)
      ON DELETE RESTRICT
      ON UPDATE CASCADE,
  CONSTRAINT `developers-projets`
    FOREIGN KEY (`id`)
      REFERENCES `developers`.`projects` (`id`)
      ON DELETE RESTRICT
      ON UPDATE CASCADE,
  CONSTRAINT `developers-skills`
    FOREIGN KEY (`id`)
      REFERENCES `developers`.`skills` (`id`)
      ON DELETE RESTRICT
      ON UPDATE CASCADE
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;

ALTER TABLE `developers`.`projects`
  CHANGE COLUMN `name` `name` VARCHAR(45) NOT NULL ;
ALTER TABLE `developers`.`projects`
  ADD CONSTRAINT `companies-projects`
    FOREIGN KEY (`id`)
      REFERENCES `developers`.`companies` (`id`)
      ON DELETE RESTRICT
      ON UPDATE CASCADE;
