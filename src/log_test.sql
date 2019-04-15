CREATE SCHEMA `jdbc_test` DEFAULT CHARACTER SET utf8;


-- # CREATE developers table
CREATE TABLE `jdbc_test`.`developers`
(
  `id`         INT                                                         NOT NULL AUTO_INCREMENT,
  `age`        INT(11)                                                     NULL,
  `first_name` VARCHAR(255) CHARACTER SET 'utf8' COLLATE 'utf8_general_ci' NOT NULL,
  `last_name`  VARCHAR(255) CHARACTER SET 'utf8' COLLATE 'utf8_general_ci' NOT NULL,
  `sex`        VARCHAR(45) CHARACTER SET 'utf8' COLLATE 'utf8_general_ci'  NOT NULL,
  `salary`     DOUBLE                                                      NOT NULL,
  `company_id` INT(11)                                                     NULL,
  `skill_id`   INT(11)                                                     NULL,
  PRIMARY KEY (`id`)
);

-- # CREATE companies table
CREATE TABLE `jdbc_test`.`companies`
(
  `id`                INT                                                         NOT NULL AUTO_INCREMENT,
  `name`              VARCHAR(255) CHARACTER SET 'utf8' COLLATE 'utf8_general_ci' NOT NULL,
  `count_of_employee` DOUBLE                                                      NOT NULL,
  PRIMARY KEY (`id`)
);


-- # CREATE projects table
CREATE TABLE `jdbc_test`.`projects`
(
  `id`          INT                                                         NOT NULL AUTO_INCREMENT,
  `name`        VARCHAR(255) CHARACTER SET 'utf8' COLLATE 'utf8_general_ci' NOT NULL,
  `customer_id` INT(11)                                                     NULL,
  PRIMARY KEY (`id`)
);

-- # CREATE skills table
CREATE TABLE `jdbc_test`.`skills`
(
  `id`         INT                                                         NOT NULL AUTO_INCREMENT,
  `technology` VARCHAR(255) CHARACTER SET 'utf8' COLLATE 'utf8_general_ci'  NOT NULL,
  `seniority`  VARCHAR(255) CHARACTER SET 'utf8' COLLATE 'utf8_general_ci' NOT NULL,
  PRIMARY KEY (`id`)
);

-- # CREATE customers table
CREATE TABLE `jdbc_test`.`customers`
(
  `id`     INT          NOT NULL AUTO_INCREMENT,
  `name`   VARCHAR(255) CHARACTER SET 'utf8' COLLATE 'utf8_general_ci' NOT NULL,
  `region` VARCHAR(255) CHARACTER SET 'utf8' COLLATE 'utf8_general_ci' NULL,
  PRIMARY KEY (`id`)
);


-- # ADD FOREIGN_KEYS developers - skills & company
ALTER TABLE `jdbc_test`.`developers`
  ADD INDEX `company_id_idx` (`company_id` ASC) VISIBLE,
  ADD INDEX `skill_id_idx` (`skill_id` ASC) VISIBLE;
;
ALTER TABLE `jdbc_test`.`developers`
  ADD CONSTRAINT `company_id`
    FOREIGN KEY (`company_id`)
      REFERENCES `jdbc_test`.`companies` (`id`)
      ON DELETE RESTRICT
      ON UPDATE CASCADE,
  ADD CONSTRAINT `skill_id`
    FOREIGN KEY (`skill_id`)
      REFERENCES `jdbc_test`.`skills` (`id`)
      ON DELETE RESTRICT
      ON UPDATE CASCADE;

-- # MANY TO MANY developers-projects
CREATE TABLE `jdbc_test`.`developers_projects`
(
  `developer_id` INT(11) NOT NULL,
  `project_id`   INT(11) NOT NULL,
  INDEX `developer_id_idx` (`developer_id` ASC) VISIBLE,
  INDEX `project_id_idx` (`project_id` ASC) VISIBLE,
  CONSTRAINT `developer_id`
    FOREIGN KEY (`developer_id`)
      REFERENCES `jdbc_test`.`developers` (`id`)
      ON DELETE RESTRICT
      ON UPDATE CASCADE,
  CONSTRAINT `project_id`
    FOREIGN KEY (`project_id`)
      REFERENCES jdbc_test.`projects` (`id`)
      ON DELETE RESTRICT
      ON UPDATE CASCADE
);

-- # MANY TO MANY companies-projects
CREATE TABLE `jdbc_test`.`companies_projects`
(
  `company_id` INT(11) NOT NULL,
  `project_id` INT(11) NOT NULL,
  INDEX `company_id_idx` (`company_id` ASC) VISIBLE,
  INDEX `project_id_idx` (`project_id` ASC) VISIBLE,
  CONSTRAINT `companies_id`
    FOREIGN KEY (`company_id`)
      REFERENCES `jdbc_test`.`companies` (`id`)
      ON DELETE RESTRICT
      ON UPDATE CASCADE,
  CONSTRAINT `projects_id`
    FOREIGN KEY (`project_id`)
      REFERENCES `jdbc_test`.`projects` (`id`)
      ON DELETE RESTRICT
      ON UPDATE CASCADE
);

-- # ONE TO MANY projects-customer
ALTER TABLE `jdbc_test`.`projects`
  ADD INDEX `customers_idx` (`customer_id` ASC) VISIBLE;
;
ALTER TABLE `jdbc_test`.`projects`
  ADD CONSTRAINT `customers`
    FOREIGN KEY (`customer_id`)
      REFERENCES `jdbc_test`.`customers` (`id`)
      ON DELETE RESTRICT
      ON UPDATE CASCADE;




