CREATE TABLE `Router` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`name` varchar(255) NOT NULL,
	PRIMARY KEY (`id`)
);
CREATE TABLE `Point` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`name` varchar(255) NOT NULL,
	`locale` INT NOT NULL,
	PRIMARY KEY (`id`)
);
CREATE TABLE `Bus` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`name` varchar(144) NOT NULL,
	`router` INT NOT NULL,
	`point` INT NOT NULL,
	PRIMARY KEY (`id`)
);
CREATE TABLE `Coordinates` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`router` INT NOT NULL,
	`isPoint` BOOLEAN NOT NULL,
	`latitude` varchar(255) NOT NULL,
	`longitude` varchar(255) NOT NULL,
	PRIMARY KEY (`id`)
);
ALTER TABLE `Point` ADD CONSTRAINT `Point_fk1` FOREIGN KEY (`locale`) REFERENCES `Coordinates`(`id`);
ALTER TABLE `Bus` ADD CONSTRAINT `Bus_fk0` FOREIGN KEY (`router`) REFERENCES `Router`(`id`);
ALTER TABLE `Bus` ADD CONSTRAINT `Bus_fk1` FOREIGN KEY (`point`) REFERENCES `Point`(`id`);
ALTER TABLE `Coordinates` ADD CONSTRAINT `Coordinates_fk0` FOREIGN KEY (`router`) REFERENCES `Router`(`id`);


