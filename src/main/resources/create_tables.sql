CREATE TABLE users (
	user_id bigserial NOT NULL PRIMARY KEY,
	firstname varchar(50) NOT NULL,
	lastname varchar(50) NOT NULL,
	email varchar(254) NOT NULL,
	password varchar(128) NOT NULL,
	banned boolean DEFAULT FALSE,
	user_type varchar(25) NOT NULL
);

CREATE TABLE groups (
	group_id bigserial NOT NULL PRIMARY KEY,
	name varchar(100) NOT NULL,
	description varchar(256),
	creator_id bigint NOT NULL,
	FOREIGN KEY(creator_id) REFERENCES Users(user_id)
);

CREATE TABLE user_group (
	user_id bigint NOT NULL REFERENCES Users(user_id),
	group_id bigint NOT NULL REFERENCES Groups(group_id),
	PRIMARY KEY(user_id, group_id)
);

CREATE TABLE snippets (
	snippet_id bigserial NOT NULL PRIMARY KEY,
	name varchar(100) NOT NULL,
	owner_id bigint NOT NULL REFERENCES Users(user_id),
	creation_date date,
	modification_date date,
	content text DEFAULT '',
	tag varchar(256),
	group_id bigint NOT NULL REFERENCES Groups(group_id)
);

