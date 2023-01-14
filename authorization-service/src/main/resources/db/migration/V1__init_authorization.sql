create table roles (
    id serial primary key,
    name varchar(14)
);

create table users (
    id serial primary key,
    email varchar(255) not null unique,
    password varchar(255) not null
);

create table users_roles(
    users_id bigint not null,
    roles_id bigint not null,
    constraint users_roles_pkey primary key (users_id, roles_id),
    constraint fk_roles foreign key (roles_id)
        references roles (id) match SIMPLE
        on update no action
        on delete no action,
    constraint fk_users foreign key (users_id)
        references users (id) match SIMPLE
        on update no action
        on delete no action
);

insert into roles(id, name)
	values (1, 'ROLE_ADMIN'), (2, 'ROLE_USER');

insert into users(id, email, password)
	values (177, 'admin', '$2a$10$oDEfA0ASTrfUy6xchHTr7OR8bb5W8yaNzm4aJoAcl6xX97BEggFiW');

insert into users_roles(users_id, roles_id)
    values (177, 1);
