-- # noinspection sqlnodatasourceinspectionforfile
insert into usr_user (usr_id, usr_ds_username, usr_ds_password, usr_ds_firstname, usr_ds_lastname, usr_ds_email, usr_st_activated)
values ('62134742-a36e-4d9a-a076-e7c2a098f915', 'admin', '$2a$08$ldnhpz7euksi6ao14twuau08mzhwrl4kyzggu5xfigalo/vxd5doi',
        'admin', 'admin', 'admin@admin.com', 1);
insert into usr_user (usr_id, usr_ds_username, usr_ds_password, usr_ds_firstname, usr_ds_lastname, usr_ds_email, usr_st_activated)

values ('bc6ed14c-75d0-46f7-b414-ee7772c93271', 'user', '$2a$08$ukvvwpulis18s19s5pzfn.yhpzt3oaqhzndwqbcw9pft6uftkxkdc',
        'user', 'user', 'enabled@user.com', 1);
insert into usr_user (usr_id, usr_ds_username, usr_ds_password, usr_ds_firstname, usr_ds_lastname, usr_ds_email, usr_st_activated)

values ('1fd6358c-8275-4d73-a6ad-058a73192411', 'disabled',
        '$2a$08$ukvvwpulis18s19s5pzfn.yhpzt3oaqhzndwqbcw9pft6uftkxkdc',
        'user', 'user', 'disabled@user.com', 0);

insert into usr_user (usr_id, usr_ds_username, usr_ds_password, usr_ds_firstname, usr_ds_lastname, usr_ds_email, usr_st_activated)
values ('621asç42-s36e-4d9a-a076-e7c2a098f915', 'douglas', '$2a$08$ldnhpz7euksi6ao14twuau08mzhwrl4kyzggu5xfigalo/vxd5doi',
        'admin', 'admin', 'admin@admin.com', 1);

insert into aut_authority
values ('62134732-a36e-497a-a076-e7c2a098f915', 'role_user');
insert into aut_authority
values ('62134742-a36e-4d9a-1876-eaftr098f915', 'role_admin');

insert into usa_user_authority (usr_id, aut_id)
values ('62134742-a36e-4d9a-a076-e7c2a098f915', '62134732-a36e-497a-a076-e7c2a098f915');
insert into usa_user_authority (usr_id, aut_id)
values ('62134742-a36e-4d9a-a076-e7c2a098f915', '62134742-a36e-4d9a-1876-eaftr098f915');
insert into usa_user_authority (usr_id, aut_id)
values ('bc6ed14c-75d0-46f7-b414-ee7772c93271', '62134732-a36e-497a-a076-e7c2a098f915');
insert into usa_user_authority (usr_id, aut_id)
values ('1fd6358c-8275-4d73-a6ad-058a73192411', '62134732-a36e-497a-a076-e7c2a098f915');
