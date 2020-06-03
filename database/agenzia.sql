# lulrulez
# Script for database agenzia

DROP database if exists agenzia;
create database if not exists agenzia default character set utf8;
show create database agenzia;
use agenzia;

drop table if exists payment,
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

show engine InnoDB status;

create table user (
  userid int unsigned not null auto_increment,
  username varchar(255) not null,
  firstname varchar(255) not null,
  surname varchar(255) not null,
  password varchar(512) not null,
  email varchar(255) not null,
  nationality varchar(255) default null,
  date_birth date not null,
  sex char not null,
  cellular bigint not null,
  street varchar(255) not null,
  number varchar(255) not null,
  city varchar(255) not null,
  province varchar(255) not null,
  cap int not null,
  avatar longtext default null,
  profession varchar(255) not null,
  cf varchar(16) not null,
  role varchar(255) not null,
  primary key (userid)
) engine=InnoDB character set utf8 collate utf8_general_ci;

show create table user;

create table category (
  id int unsigned not null auto_increment,
  name varchar (255) not null,
  description varchar(255) not null,
  primary key (id)
) engine=InnoDB character set utf8 collate utf8_general_ci;

show create table category;

create table card (
  number bigint not null,
  holder varchar(255) not null,
  cvv int not null,
  expiration date not null,
  userid int unsigned not null,
  primary key (number),
  index (userid),
  constraint user_fk_card foreign key (userid) references user(userid) on delete cascade on update cascade
) engine=InnoDB character set utf8 collate utf8_general_ci;

show create table `card`;

create table `order` (
  number int unsigned not null auto_increment,
  userid int  unsigned not null,
  date date not null,
  total double not null,
  created timestamp not null default current_timestamp,
  updated timestamp not null default current_timestamp on update current_timestamp,
  primary key (number),
  index (userid),
  constraint user_fk_order foreign key (userid) references user(userid) on delete cascade on update cascade
) engine=InnoDB character set utf8 collate utf8_general_ci;

show create table `order`;

create table travel (
  id int unsigned not null AUTO_INCREMENT,
  category_id int unsigned not null,
  price double not null,
  name varchar(255) not null,
  discount double default null,
  start_date date not null,
  means bigint not null,
  description text not null,
  start_place varchar(255) not null,
  start_hour time not null,
  duration varchar(255) not null,
  seats_available int not null,
  seats_total int not null,
  destination varchar(255) not null,
  deleted tinyint default 0,
  hide tinyint default 0,
  primary key (id),
  index (category_id),
  constraint category_fk_travel foreign key (category_id) references category(id) on delete cascade on update cascade
) engine=InnoDB character set utf8 collate utf8_general_ci;

show create table travel;

create table cart (
  userid int unsigned not null,
  total double default null,
  primary key (userid),
  constraint user_fk_cart foreign key (userid) references user(userid) on delete cascade on update cascade
) engine=InnoDB character set utf8 collate utf8_general_ci;

show create table cart;

create table have (
  userid int unsigned not null,
  travel_code int unsigned not null,
  quantity int default null,
  primary key (userid, travel_code),
  index (userid),
  index (travel_code),
  constraint user_fk_have foreign key (userid) references cart(userid) on delete cascade on update cascade,
  constraint travel_fk_have foreign key (travel_code) references travel(id) on delete cascade on update cascade
) engine=InnoDB character set utf8 collate utf8_general_ci;

show create table have;

create table payment (
  number int unsigned not null,
  date date not null,
  amount double not null,
  `with` varchar(255) not null,
  primary key (number),
  constraint order_fk_payment foreign key (number) references `order`(number) on delete cascade on update cascade
) engine=InnoDB character set utf8 collate utf8_general_ci;

show create table payment;

create table details (
  quantity int not null,
  order_number int unsigned not null,
  travel_code int unsigned not null,
  primary key (order_number, travel_code),
  index (order_number),
  index (travel_code),
  constraint order_fk_details foreign key (order_number) references `order`(number) on delete cascade on update cascade,
  constraint travel_fk_details foreign key (travel_code) references travel(id) on delete cascade on update cascade
) engine=InnoDB character set utf8 collate utf8_general_ci;

show create table details;

create table review (
  id int unsigned not null auto_increment,
  travel_code int unsigned not null,
  userid int unsigned not null,
  star int not null,
  date date not null,
  description text not null,
  approved tinyint not null default 0,
  primary key (id),
  index (userid),
  index (travel_code),
  constraint user_fk_review foreign key (userid) references user(userid) on delete cascade on update cascade,
  constraint travel_fk_review foreign key (travel_code) references travel(id) on delete cascade on update cascade
) engine=InnoDB character set utf8 collate utf8_general_ci;

show create table review;

create table images (
  id int unsigned not null auto_increment,
  travel_code int unsigned not null,
  category_id int unsigned not NULL,
  review_id int unsigned not null,
  name varchar(255) not null,
  path longtext not null,
  primary key (id),
  index (travel_code),
  index (category_id),
  index (review_id),
  constraint travel_fk_images foreign key (travel_code) references travel(id) on delete cascade on update cascade,
  constraint category_fk_images foreign key (category_id) references category(id) on delete cascade on update cascade,
  constraint review_fk_images foreign key (review_id) references review(id) on delete cascade on update cascade
) engine=InnoDB character set utf8 collate utf8_general_ci;

show create table images;

