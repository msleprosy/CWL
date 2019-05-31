package com.epam.cwlhub.servlets;

import com.epam.cwlhub.entities.snippet.Snippet;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet("/SnippetUploadServlet")
@MultipartConfig(maxFileSize = 16177215)    // upload file's size up to 16MB
public class SnippetUploadServlet extends HttpServlet {

    //ServletContext context = getServletContext();
    //Map<String, Long> userSessionData = (Map<String, Long>) context.getAttribute("UserSessionData");

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        String fileName = request.getParameter("fileName");
        String tags = request.getParameter("tags");

        InputStream inputStream = null; // input stream of the upload file
        Part filePart = request.getPart("cwl");
        if (filePart != null) {

            System.out.println(filePart.getName());
            System.out.println(filePart.getSize());
            System.out.println(filePart.getContentType());

            inputStream = filePart.getInputStream();
        }

        java.util.Scanner s = new java.util.Scanner(inputStream).useDelimiter("\\A"); // "beginning of the input boundary"
        String content = s.hasNext() ? s.next() : "";


        Snippet snippet = new Snippet();
        snippet.setName(fileName);
        snippet.setOwnerId(2);
        //snippet.setOwnerId((Long)request.getSession().getAttribute("UserID"));
        snippet.setGroupId(1); // !!!! Change
        snippet.setContent(content);
        snippet.setCreationDate(LocalDate.now());
        snippet.setModificationDate(LocalDate.now());
        snippet.setTag(tags);

        //SnippetServiceImpl.getInstance().insert(snippet);

        String message = "Done!";
        // sets the message in request scope
        request.setAttribute("Message", message);

        // forwards to the message page
        getServletContext().getRequestDispatcher("/Message.jsp").forward(request, response);

    }
}