/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.plcc;

import com.google.gson.Gson;
import dbconnectionutil.DBConnectionutil;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.simple.JSONObject;
import udppacket.UDPParse;
import gnu.io.*;

/**
 *
 * @author user
 */
public class Meterreading extends HttpServlet {

    Properties prop = new Properties();
    Connection conn;
    PreparedStatement pstmt;
    ResultSet result;
    ResultSet rs;
    UDPParse UdpCom = new UDPParse();
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    DBConnectionutil db = new DBConnectionutil();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet AddUser</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Testservlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
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
        //  processRequest(request, response);
        response.setContentType("application/json");
      
        
       
        

        JSONObject obj = new JSONObject();
        JSONObject obj1 = new JSONObject();
        Date dd = new Date();
        int i = 0;
        obj.put("message", "Meter Reading referesh successfully");
        obj.put("statusCode", new Integer(0));
        obj.put("status", "success");
        String DateVal = "\"" + dd + "\"";
        obj.put("servertime", "" + DateVal + "");
        JSONArray ja = new JSONArray();
        System.out.println("inside doPost method for Selected Test");
        InputStream is = DBConnectionutil.class.getResourceAsStream("/resources/dbconnectionutil.properties");
        prop.load(is);
        String db_url = prop.getProperty("db_url");
        String user = prop.getProperty("user");
        String password = prop.getProperty("password");
        System.out.println("testing db");
        System.out.println("DB_URL:" + db_url);
        System.out.println("USER:" + user);
        System.out.println("PASSWORD:" + password);

        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Meterreading.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(Meterreading.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Meterreading.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Connecting to database...");

        try {
            conn = DriverManager.getConnection(db_url, user, password);
        } catch (SQLException ex) {
            Logger.getLogger(Meterreading.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Creating statement...");

        String listUserquery = prop.getProperty("selectMeterreading");
        try {
            pstmt = conn.prepareStatement(listUserquery);
        } catch (SQLException ex) {
            Logger.getLogger(Meterreading.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            result = pstmt.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(Meterreading.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            while (result.next()) {
                int meterreadingrecid = result.getInt("meterreadingrecid");
                int nodeid = result.getInt("nodeid");
                float voltage = result.getFloat("voltage");
                float current = result.getFloat("current");
                float power = result.getFloat("power");
                Date datetime = result.getTimestamp("datetime");

                obj1.put("meterreadingrecid", meterreadingrecid);
                obj1.put("nodeid", nodeid);
                obj1.put("voltage", voltage);
                obj1.put("current", current);
                obj1.put("power", power);
                obj1.put("datetime", datetime);
                ja.put(obj1);
                i++;

            }
        } catch (SQLException ex) {
            Logger.getLogger(Meterreading.class.getName()).log(Level.SEVERE, null, ex);
        }
        obj.put("total", i);
        obj.put("rows", ja);
        PrintWriter out = response.getWriter();

        System.out.println("The Final Result : " + obj);

        out.print(obj);
        out.flush();

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

    public void selectUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("The Request : " + request.getParameter("name"));

    }

}
