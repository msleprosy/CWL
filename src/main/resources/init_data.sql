INSERT INTO Users (firstName, lastName, email, password, userType) 
VALUES ('Ivan', 'Ivanov', 'ivan_ivan@gmail.com', '12kflsd', 'Administrator'),
       ('Sergei', 'Sergeev', 'serg_serg@mail.ru', '1sa2wdfgsd', 'SIMPLE_USER'),
       ('Olga', 'Olgina', 'olg_olg@gmail.com', 'gsds456', 'SIMPLE_USER'),
       ('Pavel', 'Pavlov', 'pav_pav@yandex.ru', '2w2mknnd', 'Administrator'),
       ('Admin', 'Admin', 'admin@epam.com', 'sdfjsks17291kmkl', 'Administrator');
       
INSERT INTO Groups (name, creatorId)
VALUES ('Bioinformatics Fans', 1),
       ('Innovators', 4),
       ('Common', 5);

INSERT INTO User_Group (userId, groupId)
VALUES (1, 3),
       (2, 3),
       (3, 3),
       (4, 3),
       (5, 3),
       (1, 1),
       (2, 1),
       (4, 2);
       
INSERT INTO Snippets (name, ownerId, groupId)
VALUES ('cwl1', 1, 1),
       ('cwl2', 2, 3),
       ('cwl3', 2, 1),
       ('cwl4', 3, 3),
       ('cwl5', 4, 2),
       ('cwl6', 5, 3);