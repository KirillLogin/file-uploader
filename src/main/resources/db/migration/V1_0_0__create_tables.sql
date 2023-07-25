create table files
(
    id           bigserial,
    url          varchar not null,
    size_bytes   bigint  not null,
    content_type varchar not null,
    content      bytea   not null
);

create table summary
(
    files_count      bigint not null,
    files_size_bytes bigint not null
);

insert into summary values (0, 0);

create unique index summary_one_row_only_uidx ON summary ((true));