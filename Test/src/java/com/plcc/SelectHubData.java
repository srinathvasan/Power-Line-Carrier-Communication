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

import org.json.JSONException;
import java.util.Date;
import org.json.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author user
 */
public class SelectHubData extends HttpServlet {

    Properties prop = new Properties();
    Connection conn;
    PreparedStatement pstmt;
    ResultSet result;
    ResultSet rs;
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
            out.println("<title>SelectHubData</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SelectHubData  at " + request.getContextPath() + "</h1>");
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
        try {
            response.setContentType("application/json");
            //  processRequest(request, response);
            JSONObject obj = new JSONObject();
            JSONObject obj1 = new JSONObject();
            Date dd = new Date();
            int i = 0;
            obj.put("message", "Hub referesh  successfully");
            obj.put("statusCode", new Integer(0));
            obj.put("status", "success");
            String DateVal = "\"" + dd + "\"";
            obj.put("servertime", "" + DateVal + "");
            JSONArray ja = new JSONArray();
            System.out.println("inside doPost method for Selected Test from hub");
            InputStream is = DBConnectionutil.class.getResourceAsStream("/resources/dbconnectionutil.properties");
            prop.load(is);
            String db_url = prop.getProperty("db_url");
            String user = prop.getProperty("user");
            String password = prop.getProperty("password");
            System.out.println("testing db");
            System.out.println("DB_URL:" + db_url);
            System.out.println("USER:" + user);
            System.out.println("PASSWORD:" + password);

            Class.forName("com.mysql.jdbc.Driver").newInstance();
            System.out.println("Connecting to database...");

            conn = DriverManager.getConnection(db_url, user, password);
            System.out.println("Creating statement...");

            String listHubquery = prop.getProperty("selectedHubList");
            pstmt = conn.prepareStatement(listHubquery);
            result = pstmt.executeQuery();
            while (result.next()) {
                String hubNameStr = result.getString("hubname");
                int hubTypeId = result.getInt("hubtypeid");
                String hubIPStr = result.getString("hubIP");
                int userId = result.getInt("userid");
                String hubDescription = result.getString("hubdescription");
                Float hubLatitude = result.getFloat("hublatitude");
                Float hubLongitude = result.getFloat("hublongitude");
                String hubLocation = result.getString("hublocation");
                String hubLandmark = result.getString("hublandmark");

                obj1.put("hubname", hubNameStr);
                obj1.put("hubtypeid", hubTypeId);
                obj1.put("hubIP", hubIPStr);
                obj1.put("hubdescription", hubDescription);
                obj1.put("hublatitude", hubLatitude);
                obj1.put("hublongitude", hubLongitude);
                obj1.put("hublocation", hubLocation);
                obj1.put("hublandmark", hubLandmark);
                obj1.put("userid", userId);
                ja.put(obj1);
                i++;

            }
            obj.put("total", i);
            obj.put("rows", ja);
            PrintWriter out = response.getWriter();

            System.out.println("The Final Result : " + obj);

            out.print(obj);
            out.flush();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SelectHubData.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(SelectHubData.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(SelectHubData.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(SelectHubData.class.getName()).log(Level.SEVERE, null, ex);
        }

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
