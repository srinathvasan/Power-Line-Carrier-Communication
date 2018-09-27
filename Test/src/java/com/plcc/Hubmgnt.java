/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.plcc;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dbconnectionutil.DBConnectionutil;

/**
 *
 * @author user
 */
@WebServlet(name = "Hubmgnt", urlPatterns = {"/Hubmgnt"})
public class Hubmgnt extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    DBConnectionutil dbhub = new DBConnectionutil();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       // processRequest(request, response);

        System.out.println("inside doPost method");
        String hubname = request.getParameter("hubname");
        String hubtypeid = request.getParameter("hubtypeid");
        String hubIP = request.getParameter("hubIP");
        String hubdescription = request.getParameter("hubdescription");
        String userid = request.getParameter("userid");

        String hublatitude = request.getParameter("hublatitude");
        String hublongitude = request.getParameter("hublongitude");
        String hublocation = request.getParameter("hublocation");
        String hublandmark = request.getParameter("hublandmark");

        if (hubname != null && !hubname.isEmpty()) {
            //System.out.println("Hub ID:" +hubid);
            System.out.println("Hub Name :" + hubname);
            System.out.println("Hub Type ID :" + hubtypeid);
            System.out.println("HUb description" + hubdescription);
            System.out.println("User ID" + userid);

            System.out.println("Hub IP :" + hubIP);
            System.out.println("Hub Latitude :" + hublatitude);
            System.out.println("Hub Longitude :" + hublongitude);
            System.out.println("Hub Location :" + hublocation);
            System.out.println("Hub Landamrk :" + hublandmark);

            HashMap hmhub = new HashMap();
            // hmhub.put("hubid", hubid);
            hmhub.put("hubname", hubname);
            hmhub.put("hubtypeid", hubtypeid);
            hmhub.put("hubIP", hubIP);
            hmhub.put("hubdescription", hubdescription);
            hmhub.put("userid", userid);

            hmhub.put("hublatitude", hublatitude);
            hmhub.put("hublongitude", hublongitude);
            hmhub.put("hublocation", hublocation);
            hmhub.put("hublandmark", hublandmark);

            dbhub.addHub(hmhub);

        } else {
        }

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        //out.println("hubid: " + hubid);
        out.println("hubname: " + hubname);
        out.println("hubtypeid: " + hubtypeid);
        out.println("hubdescription" + hubdescription);
        out.println("userid" + userid);
        out.println("hubIP" + hubIP);
        out.println("hublatitude" + hublatitude);
        out.println("hublongitude" + hublongitude);

        out.println("hublocation" + hublocation);
        out.println("hublandmark" + hublandmark);

        // do some processing here...
        // get response writer
        PrintWriter writer = response.getWriter();

        // build HTML code
        String htmlRespone = "<html>";
        // htmlRespone += "<h2>Your hubid is: " + hubid + "<br/>";
        htmlRespone += "Your hubname is: " + hubname + "<br/>";
        htmlRespone += "Your hubtypeid is: " + hubtypeid + "<br/>";
        htmlRespone += "Your hubIP is: " + hubIP + "<br/>";
        htmlRespone += "Your hub description is: " + hubdescription + "<br/>";
        htmlRespone += "Your userid is: " + userid + "<br/>";

        htmlRespone += "Your hublatitude number is: " + hublatitude + "<br/>";
        htmlRespone += "Your hublongitude number is: " + hublongitude + "<br/>";
        htmlRespone += "Your hublocation is: " + hublocation + "<br/></h2>";
        htmlRespone += "Your hublandmark is: " + hublandmark + "<br/>";
        htmlRespone += "</html>";

        // return response
        writer.println(htmlRespone);

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
