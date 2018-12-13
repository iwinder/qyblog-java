/* Generate Environment
hibernate.format_sql: true
hibernate.id.new_generator_mappings: false
hibernate.physical_naming_strategy: org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy
hibernate.dialect: class org.hibernate.dialect.MySQL5InnoDBDialect
hibernate.implicit_naming_strategy: org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy
*/

    alter table sys_category 
        drop 
        foreign key FK18qsxukvf40pdippprno5eq5b;

    alter table sys_privilege 
        drop 
        foreign key FKl0lymq83eg69a9awoqn9sp3pi;

    alter table sys_privilege 
        drop 
        foreign key FKhidmju68461bmv7st3pfkrhcu;

    alter table sys_privilege 
        drop 
        foreign key FK1nk6vdhuhcniyse9wik3pp5l7;

    alter table sys_privilege 
        drop 
        foreign key FK7h567kbm8i2uteel1pwveyamj;

    alter table sys_role_privilege 
        drop 
        foreign key FKvf9lt66q9oyjludcg83nfinq;

    alter table sys_role_privilege 
        drop 
        foreign key FKr3sw06rqbrufgrkfq2c8dxhvb;

    drop table if exists sys_category;

    drop table if exists sys_ldap_setting;

    drop table if exists sys_privilege;

    drop table if exists sys_role;

    drop table if exists sys_role_privilege;

    drop table if exists sys_user;

    create table sys_category (
        id bigint not null auto_increment,
        created_date datetime,
        last_modified_date datetime,
        description varchar(255),
        display_order bigint,
        id_path varchar(1000),
        key_word varchar(255),
        title varchar(255),
        title_path varchar(1000),
        title_path2 varchar(1000),
        type varchar(255),
        created_by bigint,
        last_modified_by bigint,
        parent_id bigint,
        primary key (id)
    ) ENGINE=InnoDB;

    create table sys_ldap_setting (
        id bigint not null auto_increment,
        base_dn varchar(255),
        email varchar(255),
        first_name varchar(255),
        host varchar(255),
        is_ldaps bit default 0,
        last_name varchar(255),
        login_name varchar(255),
        name varchar(255),
        password varchar(255),
        port integer,
        salt varchar(255),
        username varchar(255),
        primary key (id)
    ) ENGINE=InnoDB;

    create table sys_privilege (
        id bigint not null auto_increment,
        created_date datetime,
        last_modified_date datetime,
        description varchar(255),
        identifier varchar(255),
        is_available bit,
        is_hide bit,
        name varchar(255),
        type varchar(255),
        url varchar(255),
        created_by bigint,
        last_modified_by bigint,
        privilege_id bigint,
        role_id bigint,
        user_id bigint,
        parent_id bigint,
        primary key (id)
    ) ENGINE=InnoDB;

    create table sys_role (
        id bigint not null auto_increment,
        created_date datetime,
        last_modified_date datetime,
        display_order bigint,
        name varchar(255),
        remark varchar(255),
        created_by bigint,
        last_modified_by bigint,
        primary key (id)
    ) ENGINE=InnoDB;

    create table sys_role_privilege (
        role_id bigint not null,
        privilege_id bigint not null,
        primary key (role_id, privilege_id)
    ) ENGINE=InnoDB;

    create table sys_user (
        id bigint not null auto_increment,
        created_date datetime,
        last_modified_date datetime,
        avatar varchar(255),
        email varchar(255),
        is_deleted bit default 0,
        is_disable bit default 0,
        nickname varchar(255),
        password varchar(255),
        salt varchar(255),
        username varchar(255),
        created_by bigint,
        last_modified_by bigint,
        primary key (id)
    ) ENGINE=InnoDB;

    alter table sys_category 
        add constraint FK18qsxukvf40pdippprno5eq5b 
        foreign key (parent_id) 
        references sys_category (id);

    alter table sys_privilege 
        add constraint FKl0lymq83eg69a9awoqn9sp3pi 
        foreign key (privilege_id) 
        references sys_privilege (id);

    alter table sys_privilege 
        add constraint FKhidmju68461bmv7st3pfkrhcu 
        foreign key (role_id) 
        references sys_role (id);

    alter table sys_privilege 
        add constraint FK1nk6vdhuhcniyse9wik3pp5l7 
        foreign key (user_id) 
        references sys_user (id);

    alter table sys_privilege 
        add constraint FK7h567kbm8i2uteel1pwveyamj 
        foreign key (parent_id) 
        references sys_privilege (id);

    alter table sys_role_privilege 
        add constraint FKvf9lt66q9oyjludcg83nfinq 
        foreign key (privilege_id) 
        references sys_privilege (id);

    alter table sys_role_privilege 
        add constraint FKr3sw06rqbrufgrkfq2c8dxhvb 
        foreign key (role_id) 
        references sys_role (id);
