-- Create syntax for TABLE 'category'
CREATE TABLE `category` (
                            `category_id` tinyint(3) unsigned NOT NULL AUTO_INCREMENT,
                            `name` varchar(25) NOT NULL,
                            `last_update` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                            PRIMARY KEY (`category_id`)
) ;

-- Create syntax for TABLE 'film'
CREATE TABLE `film` (
                        `film_id` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
                        `title` varchar(128) NOT NULL,
                        `description` text,
                        `category_id` smallint(5) unsigned NOT NULL,
                        `release_year` year(4) DEFAULT NULL,
                        `language_id` tinyint(3) unsigned NOT NULL,
                        `original_language_id` tinyint(3) unsigned DEFAULT NULL,
                        `rental_duration` tinyint(3) unsigned NOT NULL DEFAULT '3',
                        `rental_rate` decimal(4,2) NOT NULL DEFAULT '4.99',
                        `length` smallint(5) unsigned DEFAULT NULL,
                        `replacement_cost` decimal(5,2) NOT NULL DEFAULT '19.99',
                        `rating` enum('G','PG','PG-13','R','NC-17') DEFAULT 'G',
                        `special_features`varchar(128) DEFAULT NULL,
                        `last_update` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                        PRIMARY KEY (`film_id`)
                       -- KEY `idx_title` (`title`),
                       -- KEY `idx_fk_language_id` (`language_id`),
                       -- KEY `idx_fk_original_language_id` (`original_language_id`),
                       -- CONSTRAINT `fk_film_language` FOREIGN KEY (`language_id`) REFERENCES `language` (`language_id`) ON UPDATE CASCADE,
                       --  CONSTRAINT `fk_film_language_original` FOREIGN KEY (`original_language_id`) REFERENCES `language` (`language_id`) ON UPDATE CASCADE
) ;

-- Create syntax for TABLE 'actor'
CREATE TABLE `actor` (
                         `actor_id` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
                         `first_name` varchar(45) NOT NULL,
                         `last_name` varchar(45) NOT NULL,
                         `last_update` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                         PRIMARY KEY (`actor_id`)
    -- KEY `idx_actor_last_name` (`last_name`)
) ;
-- Create syntax for TABLE 'film_actor'
CREATE TABLE `film_actor` (
                              `actor_id` smallint(5) unsigned NOT NULL,
                              `film_id` smallint(5) unsigned NOT NULL,
                              `last_update` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                              PRIMARY KEY (`actor_id`,`film_id`),
                             --  KEY `idx_fk_film_id` (`film_id`),
                              CONSTRAINT `fk_film_actor_actor` FOREIGN KEY (`actor_id`) REFERENCES `actor` (`actor_id`) ON UPDATE CASCADE,
                              CONSTRAINT `fk_film_actor_film` FOREIGN KEY (`film_id`) REFERENCES `film` (`film_id`) ON UPDATE CASCADE
) ;
-- Create syntax for TABLE 'staff'
CREATE TABLE `staff` (
                         `staff_id` tinyint(3) unsigned NOT NULL AUTO_INCREMENT,
                         `first_name` varchar(45) NOT NULL,
                         `last_name` varchar(45) NOT NULL,
                         `address_id` smallint(5) unsigned NOT NULL,
                         `picture` blob,
                         `email` varchar(50) DEFAULT NULL,
                         `store_id` tinyint(3) unsigned NOT NULL,
                         `active` tinyint(1) NOT NULL DEFAULT '1',
                         `username` varchar(16) NOT NULL,
                         `password` varchar(40) DEFAULT NULL,
                         `last_update` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                         PRIMARY KEY (`staff_id`)
                         -- KEY `idx_fk_store_id` (`store_id`),
                         -- KEY `idx_fk_address_id` (`address_id`),
                         -- CONSTRAINT `fk_staff_address` FOREIGN KEY (`address_id`) REFERENCES `address` (`address_id`) ON UPDATE CASCADE,
                         -- CONSTRAINT `fk_staff_store` FOREIGN KEY (`store_id`) REFERENCES `store` (`store_id`) ON UPDATE CASCADE
) ;
-- Create syntax for TABLE 'rental'
CREATE TABLE `rental` (
                          `rental_id` int(11) NOT NULL AUTO_INCREMENT,
                          `rental_date` datetime NOT NULL,
                          `customer_id` smallint(5) unsigned NOT NULL,
                          `return_date` datetime DEFAULT NULL,
                          `staff_id` tinyint(3) unsigned NOT NULL,
                          `film_id` int(11) DEFAULT NULL,
                          `last_update` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                          PRIMARY KEY (`rental_id`)
                          -- KEY `idx_fk_customer_id` (`customer_id`),
                          -- KEY `idx_fk_staff_id` (`staff_id`),
                          -- CONSTRAINT `fk_rental_customer` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`) ON UPDATE CASCADE,
                          -- CONSTRAINT `fk_rental_staff` FOREIGN KEY (`staff_id`) REFERENCES `staff` (`staff_id`) ON UPDATE CASCADE
) ;

-- Create syntax for TABLE 'customer'
CREATE TABLE `customer` (
                            `customer_id` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
                            `store_id` tinyint(3) unsigned NOT NULL,
                            `first_name` varchar(45) NOT NULL,
                            `last_name` varchar(45) NOT NULL,
                            `email` varchar(50) DEFAULT NULL,
                            `address_id` smallint(5) unsigned NOT NULL,
                            `active` tinyint(1) NOT NULL DEFAULT '1',
                            `create_date` datetime NOT NULL,
                            `last_update` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                            PRIMARY KEY (`customer_id`)
                            -- KEY `idx_fk_store_id` (`store_id`),
                            -- KEY `idx_fk_address_id` (`address_id`),
                            -- KEY `idx_last_name` (`last_name`),
                            -- CONSTRAINT `fk_customer_address` FOREIGN KEY (`address_id`) REFERENCES `address` (`address_id`) ON UPDATE CASCADE,
                            -- CONSTRAINT `fk_customer_store` FOREIGN KEY (`store_id`) REFERENCES `store` (`store_id`) ON UPDATE CASCADE
) ;