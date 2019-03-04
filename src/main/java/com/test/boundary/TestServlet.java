package com.test.boundary;

import com.test.entity.Emp;
import com.test.entity.EmpManager;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "TestServlet", urlPatterns = {"/TestServlet"})
public class TestServlet extends HttpServlet {

    @EJB
    EmpManager empManager;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        System.out.println("Received request from " + request.getRemoteAddr());

        PrintWriter out = response.getWriter();

        List<Emp> emps = empManager.getAllEmps();

        try {

            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>DB Servlet</title>");
            out.println("</head>");
            out.println("<body>");

            int i = 0;

            if (emps != null) {
                while (i < emps.size()) {
                    Emp emp = emps.get(i);
                    out.println("<p>" + emp.getEmpno() + " - " + emp.getEname() + " - " + emp.getSal() + "</p><br/>");
                    i++;
                }
            } else {
                out.println("No employees found!");
            }

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
