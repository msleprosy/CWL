package com.epam.cwlhub.servlets;

import com.epam.cwlhub.entities.snippet.Snippet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;

public class ServletUtils {

    public static Snippet createSnippetObjectFromRequest(HttpServletRequest request)
            throws ServletException, IOException {

        String fileName = request.getParameter("fileName");
        String tags = request.getParameter("tags");

        InputStream inputStream = null;
        Part filePart = request.getPart("cwl");
        if (filePart != null) {
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

        return snippet;
    }
}
