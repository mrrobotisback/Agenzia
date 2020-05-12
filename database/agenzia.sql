# lulrulez
# Script for database agenzia

DROP DATABASE IF EXISTS agenzia;
CREATE DATABASE IF NOT EXISTS agenzia DEFAULT CHARACTER SET utf8;
SHOW CREATE DATABASE agenzia;
USE agenzia;

DROP TABLE IF EXISTS invoice,
                     `order`,
                     details,
                     cart,
                     `add`,
                     card,
                     review,
                     images,
                     category,
                     travel,
                     user;

-- SHOW ENGINE InnoDB STATUS;

CREATE TABLE user (
  userid int unsigned NOT NULL AUTO_INCREMENT,
  username varchar(255) NOT NULL,
  firstname varchar(255) NOT NULL,
  surname varchar(255) NOT NULL,
  password varchar(512) NOT NULL,
  email varchar(255) NOT NULL,
  nationality varchar(255) default NULL,
  date_birth date NOT NULL,
  sex char NOT NULL,
  cellular bigint NOT NULL,
  street varchar(255) NOT NULL,
  number varchar(255) NOT NULL,
  city varchar(255) NOT NULL,
  province varchar(255) NOT NULL,
  cap int NOT NULL,
  avatar longtext default NULL,
  profession varchar(255) NOT NULL,
  cf varchar(16) NOT NULL,
  role varchar(255) NOT NULL,
  PRIMARY KEY (userid)
) ENGINE=InnoDB CHARACTER SET utf8;

SHOW CREATE TABLE user;

CREATE TABLE category (
  id int unsigned NOT NULL AUTO_INCREMENT,
  name varchar (255) NOT NULL,
  description varchar(255) NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB CHARACTER SET utf8 COLLATE utf8_general_ci;

SHOW CREATE TABLE category;

CREATE TABLE card (
  number bigint NOT NULL,
  holder varchar(255) NOT NULL,
  cvv int NOT NULL,
  expiration date NOT NULL,
  userid int unsigned NOT NULL,
  PRIMARY KEY (number),
  INDEX (userid),
  CONSTRAINT user_fk_card foreign key (userid) REFERENCES user(userid) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB CHARACTER SET utf8 COLLATE utf8_general_ci;

SHOW CREATE TABLE `card`;

CREATE TABLE `order` (
  `number` int unsigned not null AUTO_INCREMENT,
  `travel_code` int unsigned not null,
  `userid` int  unsigned NOT NULL,
  `name` varchar(255) not null,
  `date` date not null,
  `total` double not null,
  `created` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`number`),
  INDEX (`userid`),
  CONSTRAINT `user_fk_order` foreign key (`userid`) REFERENCES `user`(`userid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB CHARACTER SET utf8 COLLATE utf8_general_ci;

SHOW CREATE TABLE `order`;

CREATE TABLE travel (
  id int unsigned NOT NULL AUTO_INCREMENT,
  category_id int unsigned NOT NULL,
  order_id int unsigned NOT NULL,
  price double NOT NULL,
  name varchar(255) NOT NULL,
  discount double default null,
  start_date date NOT NULL,
  means bigint NOT NULL,
  description text NOT NULL,
  start_place varchar(255) NOT NULL,
  start_hour time NOT NULL,
  duration varchar(255) NOT NULL,
  seats_available int NOT NULL,
  seats_total int NOT NULL,
  destination varchar(255) NOT NULL,
  PRIMARY KEY (id),
  INDEX (category_id),
  INDEX (order_id),
  CONSTRAINT category_fk_travel foreign key (category_id) REFERENCES category(id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT order_fk_travel foreign key (order_id) REFERENCES `order`(`number`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB CHARACTER SET utf8 COLLATE utf8_general_ci;

SHOW CREATE TABLE travel;

CREATE TABLE `add` (
  userid int unsigned not null,
  travel_code int unsigned not null,
  PRIMARY KEY (userid, travel_code),
  INDEX (userid),
  INDEX (travel_code),
  CONSTRAINT user_fk_add foreign key (userid) REFERENCES user(userid) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT travel_fk_add foreign key (travel_code) REFERENCES travel(id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB CHARACTER SET utf8 COLLATE utf8_general_ci;

SHOW CREATE TABLE `add`;

CREATE TABLE invoice (
  number int unsigned not null,
  name varchar(255) not null,
  date date not null,
  total double not null,
  firstname varchar(255) NOT NULL,
  lastname varchar(255) NOT NULL,
  phone bigint NOT NULL,
  `street` varchar(255) NOT NULL,
  `nationality` varchar(255) NOT NULL,
  `city` varchar(255) NOT NULL,
  `province` varchar(255) NOT NULL,
  `cf` varchar(16) NOT NULL,
  `cap` int NOT NULL,
  PRIMARY KEY (`number`),
  CONSTRAINT `order_fk_invoice` foreign key (`number`) REFERENCES `order`(`number`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB CHARACTER SET utf8 COLLATE utf8_general_ci;

SHOW CREATE TABLE invoice;

CREATE TABLE details (
  quantity int not null,
  order_number int unsigned not null,
  travel_code int unsigned not null,
  PRIMARY KEY (order_number, travel_code),
  INDEX (order_number),
  INDEX (travel_code),
  CONSTRAINT `order_fk_details` foreign key (order_number) REFERENCES `order`(`number`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `travel_fk_details` foreign key (`travel_code`) REFERENCES `travel`(`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB CHARACTER SET utf8 COLLATE utf8_general_ci;


CREATE TABLE cart (
  userid int unsigned NOT NULL,
  travel_code int unsigned NOT NULL,
  quantity int default NULL,
  name varchar(255) default null,
  price double default null,
  PRIMARY KEY (userid),
  INDEX (travel_code),
  CONSTRAINT user_fk_cart foreign key (userid) REFERENCES user(userid) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT travel_fk_cart foreign key (travel_code) REFERENCES travel(id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB CHARACTER SET utf8 COLLATE utf8_general_ci;

SHOW CREATE TABLE `cart`;

CREATE TABLE review (
  id int unsigned NOT NULL AUTO_INCREMENT,
  travel_code int unsigned not null,
  userid int unsigned NOT NULL,
  star int NOT NULL,
  date date NOT NULL,
  description text NOT NULL,
  approved tinyint NOT NULL DEFAULT 0,
  PRIMARY KEY (id),
  INDEX (userid),
  INDEX (travel_code),
  CONSTRAINT user_fk_review foreign key (userid) REFERENCES user(userid) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT travel_fk_review foreign key (travel_code) REFERENCES travel(id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB CHARACTER SET utf8 COLLATE utf8_general_ci;

SHOW CREATE TABLE review;

CREATE TABLE images (
  id int unsigned NOT NULL AUTO_INCREMENT,
  travel_code int unsigned not null,
  category_id int unsigned not NULL,
  review_id int unsigned not null,
  name varchar(255) NOT NULL,
  path longtext NOT NULL,
  PRIMARY KEY (id),
  INDEX (travel_code),
  INDEX (category_id),
  INDEX (review_id),
  CONSTRAINT travel_fk_images foreign key (travel_code) REFERENCES travel(id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT category_fk_images foreign key (category_id) REFERENCES category(id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT review_fk_images foreign key (review_id) REFERENCES review(id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB CHARACTER SET utf8 COLLATE utf8_general_ci;

SHOW CREATE TABLE images;

-- Add foreign key to order

ALTER TABLE `order`
ADD CONSTRAINT travel_fk_order
FOREIGN KEY (travel_code) REFERENCES travel(id) ON DELETE CASCADE ON UPDATE CASCADE;