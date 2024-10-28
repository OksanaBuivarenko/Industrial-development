insert into locations (id, slug, name)
values (1, 'spb', 'Saint Petersburg');
insert into locations (id, slug, name)
values (2, 'msk', 'Moscow');

SELECT setval ('locations_id_seq', (SELECT MAX(id) FROM locations));

insert into events (id, name, dates, price, locations_id)
values (1, 'Festival ONE', '2024-10-26', '500', '2');
insert into events (id, name, dates, price, locations_id)
values (2, 'Festival TWO', '2024-11-20', '1500', '1');
insert into events (id, name, dates, price, locations_id)
values (3, 'Festival THREE', '2025-12-26', '700', '2');

SELECT setval ('events_id_seq', (SELECT MAX(id) FROM events));