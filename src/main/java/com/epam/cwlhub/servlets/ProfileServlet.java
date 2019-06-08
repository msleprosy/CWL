package com.epam.cwlhub.servlets;

import com.epam.cwlhub.constants.Endpoints;
import com.epam.cwlhub.entities.user.UserEntity;
import com.epam.cwlhub.services.impl.UserServiceImpl;
import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Endpoint;
import java.io.IOException;
import java.util.Map;

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
        Long id = ((Map<String, Long>) request.getServletContext().getAttribute(USER_SESSION_DATA))
                .get(request.getSession().getId());
        UserEntity loginedUser = userService.findById(id);

        if (loginedUser == null) {
            response.sendRedirect(request.getContextPath() + Endpoints.LOGIN_URL);
        }
        request.setAttribute("user", loginedUser);
        RequestDispatcher dispatcher //
                = this.getServletContext().getRequestDispatcher(Endpoints.USERINFOVIEW_PAGE);
        dispatcher.forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //doGet(request, response);
        Long id = ((Map<String, Long>) request.getServletContext().getAttribute(USER_SESSION_DATA)).get(request.getSession().getId());
        UserEntity updatedUser = userService.findById(id);

        if (updatedUser!=null) {
            UserEntity user = userInstatiate(request, updatedUser);
            request.setAttribute(USER, user);
            response.sendRedirect(request.getContextPath()+"/userInfo");
        }
    }


    private UserEntity userInstatiate(HttpServletRequest request, UserEntity user) {
        String firstName = request.getParameter(FIRSTNAME_PARAMETER);
        String lastName = request.getParameter(LASTNAME_PARAMETER);
        String password = request.getParameter(PASSWORD_PARAMETER);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPassword(DigestUtils.md5Hex(password));
        userService.update(user);
        return user;
    }


}

