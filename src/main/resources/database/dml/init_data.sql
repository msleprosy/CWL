INSERT INTO users (firstname, lastname, email, password, user_type)
VALUES ('admin', 'admin', 'admin@epam.com', 'admin', 'ADMINISTRATOR'),
       ('Sergei', 'Sergeev', 'serg@mail.ru', '234b', 'SIMPLE_USER'),
       ('Olga', 'Olgina', 'olg_olg@gmail.com', '456c', 'SIMPLE_USER'),
       ('Pavel', 'Pavlov', 'pav_pav@yandex.ru', '567d', 'SIMPLE_USER'),
       ('Ivan', 'Ivanov', 'ivan@gmail.com', '123a', 'SIMPLE_USER');
       
INSERT INTO groups (name, description, creator_id)
VALUES ('Bioinformatics Fans', 'We love bioinformatics!', 5),
       ('Innovators', 'Our goal is to create something great', 4),
       ('Common', 'For everyone', 1);

INSERT INTO user_group (user_id, group_id)
VALUES (1, 3),
       (2, 3),
       (3, 3),
       (4, 3),
       (5, 3),
       (5, 1),
       (2, 1),
       (4, 2);
       
INSERT INTO snippets (name, owner_id, creation_date, modification_date, content, tag, group_id)
VALUES ('cwl1', 1, '2018-06-23', '2019-03-03', 'cwl-content', 'tag1',  1),
       ('cwl2', 2, '2019-06-23', '2019-06-23', 'cw2-content', 'tag2', 3),
       ('cwl3', 2, '2018-12-13', '2019-05-13', 'cw3-content', 'tag3', 1),
       ('cwl4', 3, '2018-06-23', '2019-01-15', 'cw4-content', 'tag4', 3),
       ('cwl5', 4, '2018-01-23', '2019-03-03', 'cw5-content', 'tag2', 2),
       ('cwl6', 5, '2018-06-23', '2019-03-03', 'cw6-content', 'tag1', 3);