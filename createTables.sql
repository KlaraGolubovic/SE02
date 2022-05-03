DROP TABLE public.USER CASCADE;
DROP TABLE public.PERMISSION_GROUP CASCADE;
DROP TABLE public.USER_GROUP CASCADE;
DROP TABLE public.PROFILE CASCADE;
DROP TABLE public.JOB_AD CASCADE;
drop table public.comp_user CASCADE;
drop table public.stud_user CASCADE;
drop table public.comp_profile CASCADE;
drop table public.stud_profile CASCADE;

CREATE TABLE public.USER (
    user_id int NOT NULL,
	email varchar NOT NULL,
	password varchar NOT NULL,
	username varchar NOT null,
	CONSTRAINT user_user_id_pk PRIMARY KEY(user_id),
	CONSTRAINT user_email_qk UNIQUE(email)
);

CREATE TABLE public.PERMISSION_GROUP(
	group_name varchar NOT NULL,
	level int NOT NULL DEFAULT -1,
	constraint permission_group_pk PRIMARY KEY(group_name)
);

CREATE TABLE public.USER_GROUP(
	user_id int NOT NULL,
	group_name varchar DEFAULT 'GroupName' NOT NULL,
	CONSTRAINT user_group_pk PRIMARY KEY(user_id, group_name),
	CONSTRAINT user_group_user_id_fk FOREIGN KEY (user_id) REFERENCES public.USER(user_id),
	CONSTRAINT user_group_group_name_fk FOREIGN KEY (group_name) REFERENCES public.PERMISSION_GROUP(group_name)
);

CREATE TABLE public.comp_user (
	comp_user_id int NOT NULL,
	user_id int not null,
	name varchar not null,
	phone varchar,
	CONSTRAINT comp_user_pk PRIMARY key(comp_user_id),
	CONSTRAINT comp_user_fk FOREIGN key(user_id) REFERENCES public.user(user_id)
);

CREATE TABLE public.stud_user (
	stud_user_id int NOT NULL,
	user_id int not null,
	date_of_birth date not null,
	first_name varchar not null,
	last_name varchar not null,
	phone varchar,
	CONSTRAINT stud_user_pk PRIMARY key(stud_user_id),
	CONSTRAINT stud_user_fk FOREIGN key(user_id) REFERENCES public.user(user_id)
);


CREATE TABLE public.comp_profile (
	comp_profile_id int NOT NULL,
	comp_user_id int NOT NULL,
	description varchar NULL,
	address varchar NOT NULL,
	docs bytea,
	CONSTRAINT comp_profile_pk PRIMARY KEY (comp_profile_id),
	CONSTRAINT comp_profile_fk FOREIGN KEY (comp_user_id) REFERENCES public.comp_user(comp_user_id)
);

CREATE TABLE public.stud_profile (
	stud_profile_id int NOT NULL,
	stud_user_id int NOT NULL,
	description varchar NULL,
	address varchar NOT NULL,
	docs bytea,
	CONSTRAINT stud_profile_pk PRIMARY KEY (stud_profile_id),
	CONSTRAINT stud_profile_fk FOREIGN KEY (stud_user_id) REFERENCES public.stud_user(stud_user_id)
);

CREATE TABLE public.JOB_AD(
	job_ad_id int NOT NULL,
	comp_profile_id int NOT null,
	title varchar NOT NULL,
	description varchar,
	deadline date,
	job_typ varchar NOT NULL,
	location varchar NOT NULL,
	remote boolean NOT null,
	CONSTRAINT job_ad_id_pk PRIMARY KEY (job_ad_id),
	CONSTRAINT comp_job_ad_fk FOREIGN key (comp_profile_id) REFERENCES public.comp_profile(comp_profile_id)
);