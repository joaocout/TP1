CREATE DATABASE `tp1`;

CREATE TABLE IF NOT EXISTS `tp1`.`Clientes` (
  `RG` VARCHAR(45) NOT NULL,
  `Nome` VARCHAR(45) NULL,
  `CPF` VARCHAR(45) NULL,
  `email` VARCHAR(45) NULL,
  `Telefone` VARCHAR(45) NULL,
  PRIMARY KEY (`RG`))
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `tp1`.`Jogos` (
  `id` INT NULL AUTO_INCREMENT,
  `Titulo` VARCHAR(45) NOT NULL,
  `Preco_base` DOUBLE NULL,
  `Quantidade` INT NULL,
  `Plataforma` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`Titulo`, `Plataforma`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE)
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `tp1`.`Locacoes` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `data_aluguel` VARCHAR(45) NULL,
  `data_devolucao` VARCHAR(45) NULL,
  `hora_aluguel` VARCHAR(45) NULL,
  `hora_devolucao` VARCHAR(45) NULL,
  `preco_final` DOUBLE NULL,
  `finalizada` TINYINT NULL,
  `protocolo` INT NULL,
  `dias` INT NULL,
  `jogo_id` INT NULL,
  `cliente_id` INT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `tp1`.`Plataformas` (
  `NomePlat` VARCHAR(45) NOT NULL,
  `Coeficiente` DOUBLE NULL,
  PRIMARY KEY (`NomePlat`))
ENGINE = InnoDB;
