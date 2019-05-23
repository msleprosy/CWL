# Project description

First Web Application
Project Planned duration: ~ 3 weeks

General project requirements:
• Layered and MVC architecture patterns
• Data should be stored in DB
• Internationalization of an interface (English, Russian)
• Logging of exceptions • Follow all naming conventions and keep project well-structured
• Avoid structured programming style
• Try to use different GoF Patterns (optional)
• Pagination of long lists (optional)
• Unit tests
• Avoid page stubs
• Documentation
• VCS (Git recommended)
• Agile

Functional requirements:
• Authentication
• Registration of new users
• View of information (task related)
• Removing of information (task related)
• Addition and modifying of information (task related)
• Different privileges for users and administrators
• Pretty front-end (optional)

You should use some older technologies.
The reason is that usually using of only new technologies doesn’t give deep understating of what are you exactly doing.
For example, you should NOT use Spring ‘magic tools’ like Boot, Data etc., which configures almost everything for you.

Technologies: JDBC, Servlets/JSP, Tomcat

System CWL hub
Обязательное: пользователь должен иметь возможность зарегистрироваться, выкладывать в общий
(или приватный для какой-то группы) доступ свои cwl файлы и изменять их в дальнейшем.
Также должна быть возможность просмотра файлов других пользователей.

Дополнительное 1: Возможность к каждому файлу присвоить несколько тегов с дальнейшей возможностью поиска по ним.

Дополнительное 2: Возможность визуализировать cwl файлы.

Требования к функциональности:
1. Вход и выход
2. Регистрация
3. Просмотр информации (просмотр всех открытых cwl файлов)
4. Удаление информации (удаление выложенных cwl файлов)
5. Добавление и модификация