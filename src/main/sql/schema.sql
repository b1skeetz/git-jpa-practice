create table people(
    id serial8 primary key,
    city_id int8 not null,
    name varchar(20) not null,
    age int4 not null,
    address varchar (30) not null,
    foreign key (city_id) references cities (id)
);

drop table people;
drop table cities;

create table cities(
    id serial8 primary key,
    name varchar(30) not null,
    country_name varchar(30) not null
);

insert into cities (name, country_name) values ('Астана', 'Казахстан'),
                                               ('Алматы', 'Казахстан'),
                                               ('Москва', 'Россия'),
                                               ('Берлин', 'Германия'),
                                               ('Прага', 'Чехия');

insert into people (city_id, name, age, address)
values (1, 'Азамат', 25, 'ул.Победы 19'),
       (2, 'Рауан', 30, 'ул.Манаса 96'),
       (3, 'Кирилл', 54, 'пр.Ленина 40'),
       (4, 'Ганс', 17, 'Fugger Strasse 89'),
       (5, 'Йозеф', 25, 'Pivovarská 305')