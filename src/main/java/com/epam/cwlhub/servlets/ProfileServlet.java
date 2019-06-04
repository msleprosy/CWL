package com.epam.cwlhub.servlets;

import com.epam.cwlhub.constants.Endpoints;
import com.epam.cwlhub.entities.user.UserEntity;
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
    }

}

