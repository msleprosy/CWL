package com.epam.cwlhub.servlets.snippet;

import com.epam.cwlhub.services.impl.SnippetServiceImpl;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import static com.epam.cwlhub.constants.Endpoints.*;

@WebServlet(name="SnippetUploadServlet", urlPatterns = SNIPPET_UPLOAD_URL)
@MultipartConfig(maxFileSize = 16177215)
public class SnippetUploadServlet extends HttpServlet {
    private final SnippetServiceImpl snippetService = SnippetServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(SNIPPET_UPLOAD).forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("fileName").equals("")
                || request.getParameter("tags").equals("")
                || request.getPart("cwl").getSize() == 0){
            request.setAttribute("errorMessage", "Empty fields are not allowed!");
            response.sendRedirect(request.getHeader("referer"));
        } else {
            Boolean state = snippetService.createSnippetObjectFromRequest(request);
            if (state) {
                response.sendRedirect(GROUP_URL + "?id=" + request.getParameter("group_id"));
            } else {
                request.setAttribute("errorMessage", "This file already exists in the database!");
                response.sendRedirect(request.getHeader("referer"));
            }
        }
    }
}



