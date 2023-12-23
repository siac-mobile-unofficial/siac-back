CREATE TABLE `router` (
	`id` int NOT NULL AUTO_INCREMENT,
	`name` varchar(255) NOT NULL,
    `direction` ENUM('START','BACK'),
	PRIMARY KEY (`id`)
);

CREATE TABLE `point` (
	`id` int NOT NULL AUTO_INCREMENT,
	`name` varchar(255) NOT NULL,
	`locale` int NOT NULL,
	PRIMARY KEY (`id`)
);

CREATE TABLE `bus` (
	`id` int NOT NULL AUTO_INCREMENT,
	`name` varchar(144) NOT NULL,
	`router` int NOT NULL,
	`point` int NOT NULL,
	PRIMARY KEY (`id`)
);

CREATE TABLE `coordinates` (
	`id` int NOT NULL AUTO_INCREMENT,
	`router` int NOT NULL,
	`ispoint` BOOLEAN NOT NULL,
	`latitude` double NOT NULL,
	`longitude` double NOT NULL,
	PRIMARY KEY (`id`)
);

ALTER TABLE `point` ADD CONSTRAINT `point_fk0` FOREIGN KEY (`locale`) REFERENCES `coordinates`(`id`);

ALTER TABLE `bus` ADD CONSTRAINT `bus_fk0` FOREIGN KEY (`router`) REFERENCES `router`(`id`);

ALTER TABLE `bus` ADD CONSTRAINT `bus_fk1` FOREIGN KEY (`point`) REFERENCES `point`(`id`);

ALTER TABLE `coordinates` ADD CONSTRAINT `coordinates_fk0` FOREIGN KEY (`router`) REFERENCES `router`(`id`);





