create table "users"
(
    telegram_id         numeric     not null primary key,
    created_at          timestamp   not null default now(),
    updated_at          timestamp   not null default now(),
    telegram_user_name  varchar(50),
    telegram_first_name varchar(50),
    admin               boolean     not null default false,
    state               varchar(50) not null
);

create table teachers
(
    id               numeric   not null primary key,
    created_at       timestamp not null default now(),
    updated_at       timestamp not null default now(),
    user_telegram_id numeric   not null references "users" (telegram_id),
    photo_id         varchar,
    fio              varchar,
    info             varchar,
    chocolate        varchar,
    contacts         varchar
);

create table questions
(
    id         numeric   not null primary key,
    created_at timestamp not null default now(),
    updated_at timestamp not null default now(),
    author_id  numeric   not null references "users" (telegram_id),
    text       varchar,
    answer     varchar,
    selected   boolean   not null default false,
    teacher_id numeric references "teachers" (id)
);

create index idx_users_username on users (telegram_user_name);

INSERT INTO public.users (telegram_id, created_at, updated_at, telegram_user_name, telegram_first_name, admin, state)
VALUES (761245887, DEFAULT, DEFAULT, 'nekit_vp', 'Nekit', true, 'START');


