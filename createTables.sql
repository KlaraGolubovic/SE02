DROP TABLE public.USER CASCADE CONSTRAINT;
DROP TABLE public.PERMISSION_GROUP CASCADE CONSTRAINT;
DROP TABLE public.USER_GROUP CASCADE CONSTRAINT;
DROP TABLE public.PROFILE CASCADE CONSTRAINT;
DROP TABLE public.JOB_AD CASCADE CONSTRAINT;
CREATE TABLE public.USER (
    user_id int NOT NULL,
    date_of_birth date NOT NULL,
	email varchar NOT NULL,
	first_name varchar NOT NULL,
	last_name varchar NOT NULL,
	occupation varchar,
	password varchar NOT NULL,
	phone varchar,
	username varchar NOT null,
	CONSTRAINT user_user_id_pk PRIMARY KEY(user_id),
	unique(email)
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

CREATE TABLE public.profile (
	profile_id int NOT NULL,
	user_id int NOT NULL,
	describtion varchar NULL,
	adress varchar NOT NULL,
	docs bytea NULL,
	CONSTRAINT profile_pk PRIMARY KEY (profile_id),
	CONSTRAINT profile_fk FOREIGN KEY (user_id) REFERENCES public."user"(user_id)
);

CREATE TABLE public.JOB_AD(
	job_ad_id int NOT NULL,
	profile_id int NOT null,
	title varchar NOT NULL,
	descrbtion varchar,
	deadline date,
	job_typ varchar NOT NULL,
	location varchar NOT NULL,
	remote boolean NOT null,
	CONSTRAINT job_ad_id_pk PRIMARY KEY (job_ad_id),
	CONSTRAINT job_ad_fk FOREIGN key (profile_id) REFERENCES public.PROFILE(profile_id)
);

commit;