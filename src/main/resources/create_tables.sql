CREATE TABLE Users (
	userId bigserial NOT NULL PRIMARY KEY,
	firstName varchar(50) NOT NULL,
	lastName varchar(50) NOT NULL,
	email varchar(254) NOT NULL,
	password varchar(128) NOT NULL,
	isBanned boolean DEFAULT FALSE,
	userType varchar(25) NOT NULL
);

CREATE TABLE Groups (
	groupId bigserial NOT NULL PRIMARY KEY,
	name varchar(100) NOT NULL,
	description varchar(256),
	creatorId bigint NOT NULL,
	FOREIGN KEY(creatorID) REFERENCES Users(userId)
);

CREATE TABLE User_Group (
	userId bigint NOT NULL REFERENCES Users(userId),
	groupId bigint NOT NULL REFERENCES Groups(groupId),
	PRIMARY KEY(userId, groupId)
);

CREATE TABLE Snippets (
	snippetId bigserial NOT NULL PRIMARY KEY,
	name varchar(100) NOT NULL,
	ownerId bigint NOT NULL REFERENCES Users(userId),
	dateOfCreation date,
	dateOfModification date,
	content text,
	tag varchar(256),
	groupId bigint NOT NULL REFERENCES Groups(groupId)
);

