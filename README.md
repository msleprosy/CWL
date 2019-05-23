# Project description

System CWL hub: requirements.
Project Planned duration: ~ 3 weeks

General project requirements:
• Layered and MVC architecture patterns
• Data should be stored in DB
• Internationalization of an interface (English, Russian)
• Logging of exceptions
• Follow all naming conventions and keep project well-structured
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

Project-specific requirements:

Mandatory: the user must be able to register, upload in public (or private for some group) access his .cwl files and change them later.
It should also be possible to view files of other users.
Additional 1: Ability to assign several tags to each file with further possibility to search by them.
Additional 2: Ability to visualize .cwl files.

Requirements for functionality:
1. Log in and log out
2. Registration
3. View of information (view all public .cwl files)
4. Removal of information (delete uploaded files)
5. Adding and changing

Some older technologies should be used.
The reason is that usually using of only new technologies doesn't give deep understating of what are you exactly doing.
For example, you should NOT use Spring ‘magic tools’ like Boot, Data etc., which configures almost everything for you.

Technologies: JDBC, Servlets/JSP, Tomcat