DROP TABLE USER CASCADE CONSTRAINT;
DROP TABLE PERMISSION_GROUP CASCADE CONSTRAINT;
DROP TABLE USER_GROUP CASCADE CONSTRAINT;
DROP TABLE PROFILE CASCADE CONSTRAINT;
DROP TABLE JOB_AD CASCADE CONSTRAINT;
CREATE TABLE USER (
    user_id int PRIMARY KEY,
    date_of_birth date NOT NULL,
	email varchar UNIQUE KEY,
	first_name varchar NOT NULL,
	last_name varchar NOT NULL,
	occupation varchar,
	password varchar NOT NULL,
	phone varchar,
	username varchar NOT NULL
);

CREATE TABLE PERMISSION_GROUP(
	group_name varchar NOT NULL
);

CREATE TABLE USER_GROUP(
	user_id int FOREIGN KEY REFERENCES USER(user_id),
	group_name varchar FOREIGN KEY REFERENCES PERMISSION_GROUP(group_name)
	PRIMARY KEY(user_id, group_name):
);

CREATE TABLE PROFILE(
	profile_id int PRIMARY KEY,
	user_id int FOREIGN KEY REFERENCES USER(user_id),
	describtion varchar,
	adress varchar NOT NULL,
	docs BLOB
);

CREATE TABLE JOB_AD(
	id int PRIMARY KEY,
	profile_id FOREIGN KEY REFERENCES PROFILE(profile_id);
	title varchar NOT NULL,
	descrbtion varchar,
	deadline date,
	job_typ varchar NOT NULL,
	location varchar NOT NULL,
	remote boolean NOT NULL
);