package com.epam.cwlhub.servlets.snippet;

import com.epam.cwlhub.entities.snippet.Snippet;
import com.epam.cwlhub.entities.user.UserEntity;
import com.epam.cwlhub.services.impl.SnippetServiceImpl;
import com.epam.cwlhub.services.impl.UserServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.Map;
import java.util.stream.Collectors;

import static com.epam.cwlhub.constants.Endpoints.*;
import static com.epam.cwlhub.listeners.CWLAppServletContextListener.USER_SESSION_DATA;

@WebServlet(name="SnippetUploadServlet", urlPatterns = SNIPPET_UPLOAD_URL)
@MultipartConfig(maxFileSize = 16177215)
public class SnippetUploadServlet extends HttpServlet {
    private final SnippetServiceImpl snippetService = SnippetServiceImpl.getInstance();
    String pattern = "^[a-zA-Z0-9_-]+( [a-zA-Z0-9_-]+)*$";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(SNIPPET_UPLOAD).forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String fileName = request.getParameter("fileName").trim();
        String fileTags = request.getParameter("tags").trim();
        String file = request.getPart("cwl").getSubmittedFileName();


        if (fileName.equals("") || fileTags.equals("") || request.getPart("cwl").getSize() == 0){
            request.setAttribute("errorMessage", "Empty fields are not allowed!");
            RequestDispatcher dispatcher = request.getRequestDispatcher(UPLOAD_SNIPPET);
            dispatcher.forward(request, response);
        } else if ((!fileName.matches(pattern) || fileName.length() > 30)) {
            request.setAttribute("errorMessage", "Invalid file name! It may only contain letters, numbers, \"_\" and  \"-\" and must be less than 30 symbols.");
            RequestDispatcher dispatcher = request.getRequestDispatcher(UPLOAD_SNIPPET);
            dispatcher.forward(request, response);
        } else if ((!fileTags.matches(pattern) || fileName.length() > 150)) {
            request.setAttribute("errorMessage", "Invalid tags! This field may only contain letters, numbers, \"_\" and  \"-\" and must be less than 150 symbols.");
            RequestDispatcher dispatcher = request.getRequestDispatcher(UPLOAD_SNIPPET);
            dispatcher.forward(request, response);
        } else if (!request.getPart("cwl").getContentType().equals("application/octet-stream") || !file.substring(file.length() - 4).equals(".cwl")){
            request.setAttribute("errorMessage", "Wrong file type!");
            RequestDispatcher dispatcher = request.getRequestDispatcher(UPLOAD_SNIPPET);
            dispatcher.forward(request, response);
        } else {
            Boolean state = createSnippetObjectFromRequest(request);
            if (state) {
                response.sendRedirect(GROUP_URL + "?id=" + request.getParameter("group_id"));
            } else {
                request.setAttribute("errorMessage", "This file already exists in this group!");
                RequestDispatcher dispatcher = request.getRequestDispatcher(UPLOAD_SNIPPET);
                dispatcher.forward(request, response);
            }
        }
    }

    public boolean createSnippetObjectFromRequest(HttpServletRequest request) throws ServletException, IOException {
        Long id = ((Map<String, Long>) request.getServletContext().getAttribute(USER_SESSION_DATA))
                .get(request.getSession().getId());
        UserEntity user = UserServiceImpl.getInstance().findById(id);
        if (user != null && request.getParameterMap().containsKey("group_id")) {
            String fileName = request.getParameter("fileName");
            String tags = request.getParameter("tags");
            InputStream inputStream = null;
            Part filePart = request.getPart("cwl");
            if (filePart != null) {
                inputStream = filePart.getInputStream();
            }
            Snippet file = snippetService.findByFileNameInGroup(fileName, Long.parseLong(request.getParameter("group_id")));
            if (file != null) {
                return false;
            }

            String content = new BufferedReader(new InputStreamReader(inputStream))
                    .lines().collect(Collectors.joining("\n"));
            Snippet snippet = new Snippet();
            snippet.setName(fileName);
            snippet.setOwnerId(user.getId());
            snippet.setGroupId(Long.parseLong(request.getParameter("group_id")));
            snippet.setContent(content);
            snippet.setCreationDate(LocalDate.now());
            snippet.setModificationDate(LocalDate.now());
            snippet.setTag(tags);
            snippetService.insert(snippet);
            return true;
        }
        return false;
    }
}



