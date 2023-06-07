delete from bookings;
alter table bookings alter column id restart with 1;

delete from comments;
alter table comments alter column id restart with 1;

delete from items;
alter table items alter column id restart with 1;

delete from users;
alter table users alter column id restart with 1;


insert into users (name, email) values ('user1','user1@mail.ru');
insert into users (name, email) values ('user2','user2@mail.ru');
insert into users (name, email) values ('user3','user3@mail.ru');
insert into users (name, email) values ('user4','user4@mail.ru');
insert into users (name, email) values ('user5','user5@mail.ru');

insert into items (name, description, is_available, user_id) values ('item1', 'item1 description', 1, 1);
insert into items (name, description, is_available, user_id) values ('item2', 'item2 description', 1, 1);
insert into items (name, description, is_available, user_id) values ('item3', 'item3 searchTesT description', 1, 2);
insert into items (name, description, is_available, user_id) values ('item4', 'item4 description', 1, 2);
insert into items (name, description, is_available, user_id) values ('item5', 'item5 description', 1, 3);

insert into bookings (start_date, end_date, item_id, booker_id, status)
values('2023-01-01 12:00:00', '2023-01-01 13:00:00', 1, 2, 'APPROVED');

insert into bookings (start_date, end_date, item_id, booker_id, status)
values('2023-01-01 12:00:00', '2023-01-01 13:00:00', 2, 2, 'WAITING');

insert into bookings (start_date, end_date, item_id, booker_id, status)
values('2023-01-01 12:00:00', '2023-01-01 13:00:00', 3, 2, 'WAITING');

