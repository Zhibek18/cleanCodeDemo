create table if not exists posts (id serial primary key not null, posted_at date not null DEFAULT CURRENT_DATE, text varchar(255), user_id int8 not null)
