DROP TABLE public.USER CASCADE;
DROP TABLE public.PERMISSION_GROUP CASCADE;
DROP TABLE public.USER_GROUP CASCADE;
DROP TABLE public.JOB_AD CASCADE;
DROP TABLE public.company_user CASCADE;
DROP TABLE public.student_user CASCADE;
DROP TABLE public.company_profile CASCADE;
DROP TABLE public.student_profile CASCADE;
DROP TABLE public.apply CASCADE;

CREATE TABLE public.USER (
    user_id SERIAL NOT NULL,
	email varchar NOT NULL,
	password varchar NOT NULL,
	username varchar NOT null,
	CONSTRAINT user_user_id_pk PRIMARY KEY(user_id),
	CONSTRAINT user_email_qk UNIQUE(email)
);

CREATE TABLE public.PERMISSION_GROUP(
	group_name varchar NOT NULL,
	level int NOT NULL DEFAULT -1,
	CONSTRAINT permission_group_pk PRIMARY KEY(group_name)
);

CREATE TABLE public.USER_GROUP(
	user_id int NOT NULL,
	group_name varchar DEFAULT 'GroupName' NOT NULL,
	CONSTRAINT user_group_pk PRIMARY KEY(user_id, group_name),
	CONSTRAINT user_group_user_id_fk FOREIGN KEY (user_id) REFERENCES public.USER(user_id),
	CONSTRAINT user_group_group_name_fk FOREIGN KEY (group_name) REFERENCES public.PERMISSION_GROUP(group_name)
);

CREATE TABLE public.company_user (
	company_user_id SERIAL NOT NULL,
	user_id int not null,
	name varchar not null,
	phone varchar,
	CONSTRAINT company_user_pk PRIMARY key(company_user_id),
	CONSTRAINT company_user_fk FOREIGN key(user_id) REFERENCES public.user(user_id)
);

CREATE TABLE public.student_user (
	student_user_id SERIAL NOT NULL,
	user_id int not null,
	date_of_birth date not null,
	first_name varchar not null,
	last_name varchar not null,
	phone varchar,
	CONSTRAINT student_user_pk PRIMARY key(student_user_id),
	CONSTRAINT student_user_fk FOREIGN key(user_id) REFERENCES public.user(user_id)
);


CREATE TABLE public.company_profile (
	company_profile_id SERIAL NOT NULL,
	company_user_id int NOT NULL,
	description varchar NULL,
	address varchar NOT NULL,
	image int, -- index: Auswahl von 5 Bildern aus einer Liste.
	CONSTRAINT company_profile_pk PRIMARY KEY (company_profile_id),
	CONSTRAINT company_profile_fk FOREIGN KEY (company_user_id) REFERENCES public.company_user(company_user_id)
);

CREATE TABLE public.student_profile (
	student_profile_id SERIAL NOT NULL,
	student_user_id int NOT NULL,
	description varchar NULL,
	address varchar NOT NULL,
	image int, -- index: Auswahl von 5 Bildern aus einer Liste.
	CONSTRAINT student_profile_pk PRIMARY KEY (student_profile_id),
	CONSTRAINT student_profile_fk FOREIGN KEY (student_user_id) REFERENCES public.student_user(student_user_id)
);

CREATE TABLE public.JOB_AD(
	job_ad_id SERIAL NOT NULL,
	company_user_id int NOT null,
	title varchar NOT NULL,
	description varchar,
	deadline date,
	job_typ varchar NOT NULL,
	location varchar NOT NULL,
	remote boolean NOT null,
	CONSTRAINT job_ad_id_pk PRIMARY KEY (job_ad_id),
	CONSTRAINT company_job_ad_fk FOREIGN key (company_user_id) REFERENCES public.company_user(company_user_id)
);

CREATE TABLE public.apply(
	apply_id SERIAL NOT NULL,
	student_user_id int NOT NULL,
	job_ad_id int NOT NULL,
	applyed date NOT NULL,
	CONSTRAINT apply_id_pk PRIMARY KEY (apply_id),
	CONSTRAINT apply_id_job_ad_id_uk UNIQUE (student_user_id, job_ad_id),
	CONSTRAINT apply_student_user_id_fk FOREIGN KEY (student_user_id) REFERENCES public.student_user(student_user_id),
	CONSTRAINT apply_job_ad_id_fk FOREIGN KEY (job_ad_id) REFERENCES public.JOB_AD(job_ad_id)
)