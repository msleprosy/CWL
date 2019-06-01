TRUNCATE users, groups, user_group, snippets RESTART IDENTITY;

INSERT INTO users (firstname, lastname, email, password, user_type)
VALUES ('admin', 'admin', 'admin@epam.com', 'admin', 'ADMINISTRATOR'),
       ('Sergei', 'Sergeev', 'serg@mail.ru', '234b', 'SIMPLE_USER'),
       ('Olga', 'Olgina', 'olg_olg@gmail.com', '456c', 'SIMPLE_USER'),
       ('Pavel', 'Pavlov', 'pav_pav@yandex.ru', '567d', 'SIMPLE_USER'),
       ('Ivan', 'Ivanov', 'ivan@gmail.com', '123a', 'SIMPLE_USER'),
       ('user1', 'user1', 'user1@user1', '1111', 'SIMPLE_USER'),
       ('user2', 'user2', 'user2@user2', '1111', 'SIMPLE_USER'),
       ('user3', 'user3', 'user3@user3', '1111', 'SIMPLE_USER'),
       ('user4', 'user4', 'user4@user4', '1111', 'SIMPLE_USER'),
       ('user5', 'user5', 'user5@user5', '1111', 'SIMPLE_USER');

INSERT INTO groups (name, description, creator_id)
VALUES ('Common', 'For everyone', 1),
       ('Bioinformatics Fans', 'We love bioinformatics!', 5),
       ('Innovators', 'Our goal is to create something great', 4),
       ('group1', 'group1', 6),
       ('group2', 'group2', 7),
       ('group3', 'group3', 9);

INSERT INTO user_group (user_id, group_id)
VALUES (1, 1),
       (2, 1),
       (3, 1),
       (4, 1),
       (5, 1),
       (6, 1),
       (7, 1),
       (8, 1),
       (9, 1),
       (10, 1),
       (5, 2),
       (2, 2),
       (4, 3),
       (6, 4),
       (7, 5),
       (8, 5),
       (9, 6);

INSERT INTO snippets (name, owner_id, creation_date, modification_date, content, tag, group_id)
VALUES ('cwl1', 1, '2018-06-23', '2019-03-03', 'cwl-content', 'tag1', 2),
       ('cwl2', 2, '2019-06-23', '2019-06-23', 'cw2-content', 'tag2', 1),
       ('cwl3', 2, '2018-12-13', '2019-05-13', 'cw3-content', 'tag3', 2),
       ('cwl4', 3, '2018-06-23', '2019-01-15', 'cw4-content', 'tag4', 1),
       ('cwl5', 4, '2018-01-23', '2019-03-03', 'cw5-content', 'tag2', 3),
       ('cwl6', 5, '2018-06-23', '2019-03-03', 'cw6-content', 'tag1', 1),
       ('file1', 6, '2000-01-01', '2010-02-01', 'file1', 'filetag1', 4),
       ('file2', 7, '2000-02-02', '2010-03-02', 'file2', 'filetag1', 5),
       ('file3', 8, '2000-02-02', '2010-04-02', 'file3', 'filetag1', 5),
       ('file4', 9, '2000-03-03', '2010-05-03', 'file4', 'filetag1', 6);