package com.epam.cwlhub.servlets;

import com.epam.cwlhub.constants.Endpoints;
import com.epam.cwlhub.entities.user.UserEntity;
import com.epam.cwlhub.entities.user.UserType;
import com.epam.cwlhub.services.impl.UserServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import static com.epam.cwlhub.listeners.CWLAppServletContextListener.USER_SESSION_DATA;

@WebServlet(urlPatterns = {"/userInfo"})
public class ProfileServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserServiceImpl userService = UserServiceImpl.getInstance();
    private static final String PASSWORD_PARAMETER = "password";
    private static final String LASTNAME_PARAMETER = "lastName";
    private static final String FIRSTNAME_PARAMETER = "firstName";
    private static final String USER = "user";
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Long id = ((Map<String, Long>) request.getServletContext().getAttribute(USER_SESSION_DATA))
                .get(request.getSession().getId());
        Optional<UserEntity> receivedUser = UserServiceImpl.getInstance().findById(id);
        UserEntity loginedUser = receivedUser.get();

        if (loginedUser == null) {
            response.sendRedirect(request.getContextPath() + Endpoints.LOGIN_URL);
            return;
        }
        request.setAttribute("user", loginedUser);

        RequestDispatcher dispatcher //
                = this.getServletContext().getRequestDispatcher(Endpoints.USERINFOVIEW_PAGE);
        dispatcher.forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
        boolean hasError = false;
        String errorString = null;
        Long id = ((Map<String, Long>) request.getServletContext().getAttribute(USER_SESSION_DATA))
                .get(request.getSession().getId());
        Optional<UserEntity> receivedUser = UserServiceImpl.getInstance().findById(id);
        UserEntity updateUser = receivedUser.get();

        if (receivedUser.isPresent()) {
            UserEntity user = userInstatiate(request,updateUser);
            request.setAttribute(USER, user);
            RequestDispatcher dispatcher
                    = this.getServletContext().getRequestDispatcher(Endpoints.USERINFOVIEW_PAGE);
            dispatcher.forward(request, response);
        }
    }
    private UserEntity userInstatiate(HttpServletRequest request, UserEntity user) {
        String firstName = request.getParameter(FIRSTNAME_PARAMETER);
        String lastName = request.getParameter(LASTNAME_PARAMETER);
        String password = request.getParameter(PASSWORD_PARAMETER);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPassword(password);
        userService.update(user);
        return user;
    }


}

