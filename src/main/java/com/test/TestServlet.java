package com.test;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

@WebServlet(name = "TestServlet", urlPatterns = {"/TestServlet"})
public class TestServlet extends HttpServlet {

    @Resource(lookup = "java:jboss/datasources/MySQLDS")
    private DataSource dataSource;

    @EJB
    DbInitializer dbInitializer;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        System.out.println("Received request from " + request.getRemoteAddr());
        
        if(!dbInitializer.isInitialized()){
            System.out.println("================= INITIALIZING DB =================");
            dbInitializer.initializeDb();
        }

        PrintWriter out = response.getWriter();
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT ename, job FROM emp ORDER BY ename");
            ResultSet resultSet = preparedStatement.executeQuery();

            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>DB Servlet</title>");
            out.println("</head>");
            out.println("<body>");

            while (resultSet.next()) {
                out.println("<p>" + resultSet.getString(1) + " - " + resultSet.getString(2) + "</p><br/>");
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();

            out.println("</body>");
            out.println("</html>");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            out.close();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

}
