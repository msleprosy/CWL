package com.epam.cwlhub.servlets;

import com.epam.cwlhub.entities.snippet.Snippet;
import com.epam.cwlhub.services.SnippetService;
import com.epam.cwlhub.services.impl.SnippetServiceImpl;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/SnippetUploadServlet")
@MultipartConfig(maxFileSize = 16177215)
public class SnippetUploadServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        SnippetServiceImpl snippetService = SnippetServiceImpl.getInstance();
        Snippet snippet = snippetService.createSnippetObjectFromRequest(request);
        snippetService.insert(snippet);
        String message = "Done!";
        request.setAttribute("Message", message);
        getServletContext().getRequestDispatcher("/Message.jsp").forward(request, response);
        //getServletContext().getRequestDispatcher("/Message.jsp").forward(request, response);
    }
}