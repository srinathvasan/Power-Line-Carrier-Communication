package dbconnectionutil;

import com.google.gson.Gson;
import java.io.*;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnectionutil {

    Properties prop = new Properties();
    Connection conn;
    PreparedStatement pstmt;
    ResultSet result;
    ResultSet rs;

    public DBConnectionutil() {

        try {
            //FileReader reader = new FileReader("C:\\Users\\user\\Documents\\NetBeansProjects\\DBConnectionutil\\src\\dbconnectionutil\\dbconnectionutil.properties");
            //prop.load(reader);
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

        } catch (Exception e) {
            System.out.println("Exception" + e);
        }

    }

    public int getMeterRecId(String metername) //TO GET THE METER RECORDID
    {
        int meterrecid = 1;

        try //TO GET THE METER RECORDID
        {
            String getMeterIdFromMeterName = prop.getProperty("getMeterIdFromMeterNamequery"); //select meterrecid from meter where metername = ?;
            pstmt = conn.prepareStatement(getMeterIdFromMeterName);
            pstmt.setString(1, metername);
            ResultSet meterIdRS = pstmt.executeQuery();
            if (meterIdRS != null) {
                if (meterIdRS.next()) {
                    String meterIdStr = meterIdRS.getString("meterrecid");
                    meterrecid = Integer.parseInt(meterIdStr);
                    System.out.println("meterrecid:" + meterrecid);
                } else {
                    meterrecid = -1;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnectionutil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return meterrecid;

    }

    public boolean addMeter(HashMap hm) //TO ADD A NEW ROW IN METER TABLE
    {
        boolean retVal = false;
        if (!retVal) {
            String meternameIdStr = (String) hm.get("metername");
            int meterRecId = getMeterRecId(meternameIdStr);
            if (meterRecId == -1) {
                try {
                    int hubIdStr = Integer.parseInt((String) hm.get("hubid"));
                    int nodeIdStr = Integer.parseInt((String) hm.get("nodeid"));
                    int metertypeIdStr = Integer.parseInt((String) hm.get("metertypeid"));
                    int phasetypeIdStr = Integer.parseInt((String) hm.get("phasetypeid"));
                    int currenttypeIdStr = Integer.parseInt((String) hm.get("currenttypeid"));
                    int meterparentIdStr = Integer.parseInt((String) hm.get("meterparentid"));
                    Float meterLatitudeStr = Float.parseFloat((String) hm.get("meterlatitude"));
                    Float meterLongitudeStr = Float.parseFloat((String) hm.get("meterlongitude"));
                    String meterLocationStr = (String) hm.get("meterlocation");
                    String meterLandmarkStr = (String) hm.get("meterlandmark");

                    String addMeterquery = prop.getProperty("addMeterquery");
                    // Integer meterrecid = Integer.parseInt(meterRecIdStr);
                    pstmt = conn.prepareStatement(addMeterquery);
                    pstmt.setString(1, meternameIdStr);
                    pstmt.setInt(2, hubIdStr);
                    pstmt.setInt(3, nodeIdStr);
                    pstmt.setInt(4, metertypeIdStr);
                    pstmt.setInt(5, phasetypeIdStr);
                    pstmt.setInt(6, currenttypeIdStr);
                    pstmt.setInt(7, meterparentIdStr);
                    pstmt.setFloat(8, meterLatitudeStr);
                    pstmt.setFloat(9, meterLatitudeStr);
                    pstmt.setString(10, meterLocationStr);
                    pstmt.setString(11, meterLandmarkStr);
                    int numOfRecs = pstmt.executeUpdate();
                    System.out.println("Successully inserted " + numOfRecs + " for meter name: " + hm.get("metername"));
                    retVal = true;
                } catch (SQLException ex) {
                    Logger.getLogger(DBConnectionutil.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                System.out.println("Meter already present");
                retVal = false;
            }
        }
        return retVal;
    }

    public boolean deleteMeter(HashMap hm) { //TO DELETE A SPECIFIED ROW FROM METER TABLE
        boolean retVal = false;
        if (!retVal) {
            String meternameIdStr = (String) hm.get("metername");
            int meterRecId = getMeterRecId(meternameIdStr);
            if (meterRecId != -1) {
                try {
                    String deleteMeterquery = prop.getProperty("deleteMeterquery");
                    pstmt = conn.prepareStatement(deleteMeterquery);
                    pstmt.setInt(1, meterRecId);
                    int numOfRecs = pstmt.executeUpdate();
                    System.out.println("Successully deleted " + numOfRecs + " for meter name " + hm.get("metername"));
                    retVal = true;
                } catch (SQLException ex) {
                    Logger.getLogger(DBConnectionutil.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                System.out.println("No such meter for deletion:");
                retVal = false;
            }
        }
        return retVal;
    }

    public boolean editMeter(HashMap hm) {  //TO EDIT A SPECIFIED ROW IN METER TABLE
        boolean retVal = false;
        if (!retVal) {
            String meternameIdStr = (String) hm.get("metername");
            int meterRecId = getMeterRecId(meternameIdStr);
            if (meterRecId != -1) {
                try {
                    int hubIdStr = Integer.parseInt((String) hm.get("hubid"));
                    int nodeIdStr = Integer.parseInt((String) hm.get("nodeid"));
                    int metertypeIdStr = Integer.parseInt((String) hm.get("metertypeid"));
                    int phasetypeIdStr = Integer.parseInt((String) hm.get("phasetypeid"));
                    int currenttypeIdStr = Integer.parseInt((String) hm.get("currenttypeid"));
                    int meterparentIdStr = Integer.parseInt((String) hm.get("meterparentid"));
                    Float meterLatitudeStr = Float.parseFloat((String) hm.get("meterlatitude"));
                    Float meterLongitudeStr = Float.parseFloat((String) hm.get("meterlongitude"));
                    String meterLocationStr = (String) hm.get("meterlocation");
                    String meterLandmarkStr = (String) hm.get("meterlandmark");

                    String editMeterquery = prop.getProperty("editMeterquery");
                    pstmt = conn.prepareStatement(editMeterquery);
                    pstmt.setString(1, meternameIdStr);
                    pstmt.setInt(2, hubIdStr);
                    pstmt.setInt(3, nodeIdStr);
                    pstmt.setInt(4, metertypeIdStr);
                    pstmt.setInt(5, phasetypeIdStr);
                    pstmt.setInt(6, currenttypeIdStr);
                    pstmt.setInt(7, meterparentIdStr);
                    pstmt.setFloat(8, meterLatitudeStr);
                    pstmt.setFloat(9, meterLongitudeStr);
                    pstmt.setString(10, meterLocationStr);
                    pstmt.setString(11, meterLandmarkStr);
                    pstmt.setInt(12, meterRecId);
                    int numOfRecs = pstmt.executeUpdate();
                    System.out.println("Successully updated " + numOfRecs + " for meter name " + hm.get("metername"));
                    retVal = true;
                } catch (SQLException ex) {
                    Logger.getLogger(DBConnectionutil.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                System.out.println("Meter for updation doesnt exist");
                retVal = false;
            }
        }
        return retVal;
    }

    public String listMeter(HashMap hm) {  //TO DISPLAY A ROW IN METER TABLE
        boolean retVal = false;
        List<HashMap<String, String>> meters = new ArrayList<HashMap<String, String>>();
        HashMap hmmeter = new HashMap();

        if (!retVal) {
            String meternameIdStr = (String) hm.get("metername");
            int meterRecId = getMeterRecId(meternameIdStr);
            if (meterRecId != -1) {
                try {
                    String listMeterquery = prop.getProperty("listMeterquery");
                    pstmt = conn.prepareStatement(listMeterquery);
                    pstmt.setInt(1, meterRecId);
                    result = pstmt.executeQuery();
                    if (result.next()) {
                        int hubIdStr = result.getInt("hubid");
                        int nodeIdStr = result.getInt("nodeid");
                        int metertypeIdStr = result.getInt("metertypeid");
                        int phasetypeIdStr = result.getInt("phasetypeid");
                        int currenttypeIdStr = result.getInt("currenttypeid");
                        int meterparentIdStr = result.getInt("meterparentid");
                        Float meterLatitudeStr = result.getFloat("meterlatitude");
                        Float meterLongitudeStr = result.getFloat("meterlongitude");
                        String meterLocation = result.getString("meterlocation");
                        String meterLandmarkStr = result.getString("meterlandmark");

                        hmmeter.put("metername", meternameIdStr + "");
                        hmmeter.put("hubid", hubIdStr + "");
                        hmmeter.put("nodeid", nodeIdStr + "");
                        hmmeter.put("metertypeid", metertypeIdStr + "");
                        hmmeter.put("phasetypeid", phasetypeIdStr + "");
                        hmmeter.put("currenttypeid", currenttypeIdStr + "");
                        hmmeter.put("meterparentid", meterparentIdStr + "");
                        hmmeter.put("meterlatitude", meterLatitudeStr + "");
                        hmmeter.put("meterlongitude", meterLongitudeStr + "");
                        hmmeter.put("meterlocation", meterLocation + "");
                        hmmeter.put("meterlandmark", meterLandmarkStr + "");
                        meters.add(hmmeter);

                        System.out.println("metername is: " + meternameIdStr);
                        System.out.println("hubid is: " + hubIdStr);
                        System.out.println("nodeid is: " + nodeIdStr);
                        System.out.println("metertypeid is: " + metertypeIdStr);
                        System.out.println("phasetypeid is: " + phasetypeIdStr);
                        System.out.println("currenttypeid is: " + currenttypeIdStr);
                        System.out.println("meterparentid is: " + meterparentIdStr);
                        System.out.println("meterlatitude is: " + meterLatitudeStr);
                        System.out.println("meterlongitude is: " + meterLongitudeStr);
                        System.out.println("meterlocation is: " + meterLocation);
                        System.out.println("meterlandmark is: " + meterLandmarkStr);

                        System.out.println("Rows Listed successfully");

                        retVal = true;
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(DBConnectionutil.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            System.out.println("Meter doesnt exist");
            retVal = false;
        }
        Gson gson = new Gson();
        String json = gson.toJson(meters);
// List<String> target2 = gson.fromJson(json, listType);
        return json;

    }

    public int getUserId(String username) {
        int userid = 1;

        try {
            String getUserIdFromUserName = prop.getProperty("getUserIdFromUserNamequery"); //select userid from meters where metername = ?;
            pstmt = conn.prepareStatement(getUserIdFromUserName);
            pstmt.setString(1, username);
            ResultSet userIdRS = pstmt.executeQuery();
            //from userIdRS check iof there is user with the given username.
            if (userIdRS != null) {
                if (userIdRS.next()) {
                    String meterIdStr = userIdRS.getString("userid");
                    userid = Integer.parseInt(meterIdStr);
                    System.out.println("userid:" + userid);
                } else {
                    userid = -1;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnectionutil.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("User id is" + userid);
        return userid;
    }

    public boolean addUser(HashMap hm) {
        System.out.println("Hashmap value " + hm);
        boolean retVal = false;
        if (!retVal) {
            String usernameIdStr = (String) hm.get("username");
            try {
                int userId = getUserId(usernameIdStr);
                System.out.println("UserId: " + userId);
                if (userId == -1) {
                    String genderTypeIdStr = (String) hm.get("gendertypeid");
                    String useraddressIdStr = (String) hm.get("useraddress");
                    String emailIdStr = (String) hm.get("emailid");
                    Long userageStr = Long.parseLong((String) hm.get("userage"));
                    Long phnoStr = Long.parseLong((String) hm.get("phno"));
                    String addUserquery = prop.getProperty("addUserquery");
                    pstmt = conn.prepareStatement(addUserquery);
                    pstmt.setString(1, usernameIdStr);
                    pstmt.setString(2, useraddressIdStr);
                    pstmt.setLong(3, userageStr);
                    pstmt.setString(4, genderTypeIdStr);
                    pstmt.setLong(5, phnoStr);
                    pstmt.setString(6, emailIdStr);
                    int numOfRecs = pstmt.executeUpdate();
                    System.out.println("Successully inserted " + numOfRecs + " for user name " + hm.get("username"));
                    retVal = true;
                } else {
                    System.out.println("User for insertion already exists");
                    retVal = false;
                }
            } catch (SQLException ex) {
                System.out.println("Exception: " + ex);
            }
        } else {
            System.out.println("User for insertion already exists:null");
        }
        return retVal;
    }

    public boolean deleteUser(HashMap hm) {
        boolean retVal = false;
        if (!retVal) {
            String usernameIdStr = (String) hm.get("username");
            int userId = getUserId(usernameIdStr);
            if (userId != -1) {
                try {
                    String deleteUserquery = prop.getProperty("deleteUserquery");
                    pstmt = conn.prepareStatement(deleteUserquery);
                    pstmt.setInt(1, userId);
                    int numOfRecs = pstmt.executeUpdate();
                    System.out.println("Successully deleted " + numOfRecs + " for user name " + hm.get("username"));
                    retVal = true;
                } catch (SQLException ex) {
                    Logger.getLogger(DBConnectionutil.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            System.out.println("No such user for deletion:null");
        }

        return retVal;
    }

    public boolean editUser(HashMap hm) {
        boolean retVal = false;
        if (!retVal) {
            String usernameIdStr = (String) hm.get("username");
            int userId = getUserId(usernameIdStr);
            if (userId != -1) {
                try {
                    String useraddressIdStr = (String) hm.get("useraddress");
                    String emailIdStr = (String) hm.get("emailid");
                    int userageStr = Integer.parseInt((String) hm.get("userage"));
                    int genderTypeIdStr = Integer.parseInt((String) hm.get("gendertypeid"));
                    int phnoStr = Integer.parseInt((String) hm.get("phno"));
                    String editUserquery = prop.getProperty("editUserquery");
                    pstmt = conn.prepareStatement(editUserquery);
                    pstmt.setString(1, usernameIdStr);
                    pstmt.setString(2, useraddressIdStr);
                    pstmt.setInt(3, userageStr);
                    pstmt.setInt(4, genderTypeIdStr);
                    pstmt.setInt(5, phnoStr);
                    pstmt.setString(6, emailIdStr);
                    pstmt.setInt(7, userId);
                    int numOfRecs = pstmt.executeUpdate();
                    System.out.println("Successully updated " + numOfRecs + " for user name " + hm.get("username"));
                    retVal = true;
                } catch (SQLException ex) {
                    Logger.getLogger(DBConnectionutil.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            System.out.println("User for updation already exists:null");
        }
        return retVal;
    }

    public String listUser(HashMap hm) {
        String retVal = "";
        List<HashMap<String, String>> users = new ArrayList<HashMap<String, String>>();
        HashMap hmuser = new HashMap();

        if (retVal.isEmpty()) {
            String usernameIdStr = (String) hm.get("username");
            int userId = getUserId(usernameIdStr);
            if (userId != -1) {
                try {
                    String listUserquery = prop.getProperty("listUserquery");
                    pstmt = conn.prepareStatement(listUserquery);
                    pstmt.setInt(1, userId);
                    result = pstmt.executeQuery();
                    if (result.next()) {
                        String userAddressStr = result.getString("useraddress");
                        int userAge = result.getInt("userage");
                        int genderTypeId = result.getInt("gendertypeid");
                        int phNo = result.getInt("phno");
                        String emailIdStr = result.getString("emailid");

                        hmuser.put("username", usernameIdStr + "");
                        hmuser.put("useraddress", userAddressStr + "");
                        hmuser.put("userage", userAge + "");
                        hmuser.put("gendertypeid", genderTypeId + "");
                        hmuser.put("phno", phNo + "");
                        hmuser.put("emailid", emailIdStr + "");
                        users.add(hmuser);

                        System.out.println("username is: " + usernameIdStr);
                        System.out.println("useraddress is: " + userAddressStr);
                        System.out.println("userage is: " + userAge);
                        System.out.println("gendertypeid is: " + genderTypeId);
                        System.out.println("phn number is: " + phNo);
                        System.out.println("emailid is: " + emailIdStr);

                        System.out.println("Rows Listed successfully");
                        retVal = emailIdStr;
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(DBConnectionutil.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        return retVal;
    }

    public int getHubId(String hubname) {
        int hubid = 1;
        try {
            String getHubIdFromHubName = prop.getProperty("getHubIdFromHubNamequery"); //select hubid from hubdetails where hubame = ?;
            pstmt = conn.prepareStatement(getHubIdFromHubName);
            pstmt.setString(1, (String) hubname);
            ResultSet hubIdRS = pstmt.executeQuery();
            //from hubIdRS check iof there is hub with the given hubname.
            if (hubIdRS != null) {
                if (hubIdRS.next()) {
                    String hubIdStr = hubIdRS.getString("hubid");
                    hubid = Integer.parseInt(hubIdStr);
                    System.out.println("hubid:" + hubid);
                } else {
                    hubid = -1;
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(DBConnectionutil.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("hub id is" + hubid);

        return hubid;
    }

    public boolean addHub(HashMap hm) {
        boolean retVal = false;
//        List<HashMap<String, String>> addhub = new ArrayList<HashMap<String, String>>();
//        HashMap hmaddhub = new HashMap();
        if (!retVal) {
            System.out.println("hashmap of hub " + hm);
            String hubnameStr = (String) hm.get("hubname");
            int hubId = getHubId(hubnameStr);
            if (hubId == -1) {
                try {
                    String hubIpStr = (String) hm.get("hubIP");
                    String hubdescriptionStr = (String) hm.get("hubdescription");
                    String hubLocationtionStr = (String) hm.get("hublocation");
                    String hubLandmarkStr = (String) hm.get("hublandmark");
                    int userIdStr = 1;
                    System.out.println("hubtypeid :" + hm.get("hubtypeid"));
                    int hubTypeIdStr = Integer.parseInt((String) hm.get("hubtypeid"));
                    float hubLatitudeStr = Float.parseFloat((String) hm.get("hublatitude"));
                    float hubLongitudeStr = Float.parseFloat((String) hm.get("hublongitude"));
                    String addHubquery = prop.getProperty("addHubquery");
                    pstmt = conn.prepareStatement(addHubquery);
                    pstmt.setString(1, hubnameStr);
                    pstmt.setInt(2, hubTypeIdStr);
                    pstmt.setString(3, hubIpStr);
                    pstmt.setString(4, hubdescriptionStr);
                    pstmt.setInt(5, userIdStr);
                    pstmt.setFloat(6, hubLatitudeStr);
                    pstmt.setFloat(7, hubLongitudeStr);
                    pstmt.setString(8, hubLocationtionStr);
                    pstmt.setString(9, hubLandmarkStr);
                    int numOfRecs = pstmt.executeUpdate();
                    System.out.println("Successully inserted " + numOfRecs + " for hub name " + hm.get("hubname"));
                    retVal = true;
                } catch (SQLException ex) {
                    Logger.getLogger(DBConnectionutil.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                System.out.println("Hub already exists");
                retVal = false;
            }
        } else {
            System.out.println("Hub already exists:null");
        }
//        Gson gson = new Gson();
//        String json = gson.toJson(addhub);
//        String output=json.toString();
        return retVal;
    }

    public boolean deleteHub(HashMap hm) {
        boolean retVal = false;
        if (!retVal) {
            String hubnameStr = (String) hm.get("hubname");
            int hubId = getHubId(hubnameStr);
            if (hubId != -1) {
                try {
                    String deleteHubquery = prop.getProperty("deleteHubquery");
                    pstmt = conn.prepareStatement(deleteHubquery);
                    pstmt.setInt(1, hubId);
                    int numOfRecs = pstmt.executeUpdate();
                    System.out.println("Successully deleted " + numOfRecs + " for hub name " + hm.get("hubname"));
                    retVal = true;
                } catch (SQLException ex) {
                    Logger.getLogger(DBConnectionutil.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            System.out.println("Hub doesnt exists:null");
        }
        return retVal;
    }

    public boolean editHub(HashMap hm) {
        boolean retVal = false;
        if (!retVal) {
            String hubnameStr = (String) hm.get("hubname");
            int hubId = getHubId(hubnameStr);
            if (hubId != -1) {
                try {
                    String hubIpStr = (String) hm.get("hubIP");
                    String hubdescriptionStr = (String) hm.get("hubdescription");
                    String hubLocationtionStr = (String) hm.get("hublocation");
                    String hubLandmarkStr = (String) hm.get("hublandmark");
                    int hubTypeIdStr = Integer.parseInt((String) hm.get("hubtypeid"));
                    int userIdStr = Integer.parseInt((String) hm.get("userid"));
                    Float hubLatitudeStr = Float.parseFloat((String) hm.get("hublatitude"));
                    Float hubLongitudeStr = Float.parseFloat((String) hm.get("hublongitude"));
                    String editHubquery = prop.getProperty("editHubquery");
                    pstmt = conn.prepareStatement(editHubquery);
                    pstmt.setString(1, hubnameStr);
                    pstmt.setInt(2, hubTypeIdStr);
                    pstmt.setString(3, hubIpStr);
                    pstmt.setString(4, hubdescriptionStr);
                    pstmt.setInt(5, userIdStr);
                    pstmt.setFloat(6, hubLatitudeStr);
                    pstmt.setFloat(7, hubLongitudeStr);
                    pstmt.setString(8, hubLocationtionStr);
                    pstmt.setString(9, hubLandmarkStr);
                    pstmt.setInt(10, hubId);
                    int numOfRecs = pstmt.executeUpdate();
                    System.out.println("Successully updated " + numOfRecs + " for hub name " + hm.get("hubname"));
                    retVal = true;
                } catch (SQLException ex) {
                    Logger.getLogger(DBConnectionutil.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            System.out.println("Hub doesnt exists:null");
        }
        return retVal;
    }

    public boolean listHub(HashMap hm) {
        boolean retVal = false;
        if (!retVal) {
            String hubnameStr = (String) hm.get("hubname");
            int hubId = getHubId(hubnameStr);
            if (hubId != -1) {
                try {
                    String listHubquery = prop.getProperty("listHubquery");
                    pstmt = conn.prepareStatement(listHubquery);
                    pstmt.setInt(1, hubId);
                    result = pstmt.executeQuery();
                    if (result.next()) {
                        String hubNameStr = result.getString("hubname");
                        int hubTypeId = result.getInt("hubtypeid");
                        String hubIPStr = result.getString("hubIP");
                        int userId = result.getInt("userid");
                        String hubDescription = result.getString("hubdescription");
                        Float hubLatitude = result.getFloat("hublatitude");
                        Float hubLongitude = result.getFloat("hublongitude");
                        String hubLocation = result.getString("hublocation");
                        String hubLandmark = result.getString("hublandmark");

                        List<HashMap<String, String>> hubdetails = new ArrayList<HashMap<String, String>>();
                        HashMap hmhub = new HashMap();
                        hmhub.put("hubname", hubNameStr + "");
                        hmhub.put("hubtypeid", hubTypeId + "");
                        hmhub.put("userid", userId + "");
                        hmhub.put("hubIP", hubIPStr + "");
                        hmhub.put("hubdescription", hubDescription + "");
                        hmhub.put("hublatitude", hubLatitude + "");
                        hmhub.put("hublongitude", hubLongitude + "");
                        hmhub.put("hublocation", hubLocation + "");
                        hmhub.put("hublandmark", hubLandmark + "");
                        hubdetails.add(hmhub);

                        System.out.println("hubname is: " + hubNameStr);
                        System.out.println("hubtypeid is: " + hubTypeId);
                        System.out.println("userid is: " + userId);
                        System.out.println("hubIP is: " + hubIPStr);
                        System.out.println("hubdescription is: " + hubDescription);
                        System.out.println("hublatitude is: " + hubLatitude);
                        System.out.println("hublongitude is: " + hubLongitude);
                        System.out.println("hublocation is: " + hubLocation);
                        System.out.println("hublandmark is: " + hubLandmark);

                        System.out.println("Rows Listed successfully");
                        retVal = true;
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(DBConnectionutil.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return retVal;
    }

    public int getMeterReadingRecId(int nodeid) {
        int meterReadingId = 1;
        try {
            String getMeterReadingIdFromMeterId = prop.getProperty("getMeterReadingIdFromMeterIdquery");
            pstmt = conn.prepareStatement(getMeterReadingIdFromMeterId);
            pstmt.setInt(1, nodeid);
            ResultSet meterIdRS = pstmt.executeQuery();
            if (meterIdRS != null) {
                if (meterIdRS.next()) {
                    meterReadingId = meterIdRS.getInt("meterreadingrecid");
                    System.out.println("meterreadingrecid:" + meterReadingId);
                } else {
                    meterReadingId = -1;
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(DBConnectionutil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return meterReadingId;
    }

    public boolean addMeterReading(HashMap hm) {
        boolean retVal = false;
        if (!retVal) {
            int nodeID = Integer.parseInt((String) hm.get("nodeid"));
            // int meterRecId = getMeterRecId(meternameIdStr);
            if (nodeID != -1) {
                int meterreadingrecId = getMeterReadingRecId(nodeID);
                if (meterreadingrecId != -1) {
                    try {
                        float voltageFlt = Float.parseFloat((String) hm.get("voltage"));
                        float currentFlt = Float.parseFloat((String) hm.get("current"));
                        float powerFlt = Float.parseFloat((String) hm.get("power"));
                        Calendar cal = Calendar.getInstance();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String currentDate = sdf.format(cal.getTime());
                        Timestamp ts = java.sql.Timestamp.valueOf(currentDate);
                        String addMeterqueryStr = prop.getProperty("addMeterReadingquery");
                        pstmt = conn.prepareStatement(addMeterqueryStr);
                        pstmt.setInt(1, nodeID);
                        pstmt.setFloat(2, voltageFlt);
                        pstmt.setFloat(3, currentFlt);
                        pstmt.setFloat(4, powerFlt);
                        pstmt.setTimestamp(5, java.sql.Timestamp.valueOf(currentDate));
                        System.out.println("Statement to insert = " +pstmt);
                        int numOfRecs = pstmt.executeUpdate();
                        System.out.println("Successully inserted " + numOfRecs + " for nodeid " + "nodeID");
                        retVal = true;
                    } catch (SQLException ex) {
                        System.out.println("Exception" + ex);
                    }
                } else {
                    System.out.println("Meter not inserted");
                    retVal = false;
                }
            } else {
                System.out.println("Meter not inserted:null");
                retVal = false;
            }
        }
        return retVal;
    }

    public boolean listMeterReading(HashMap hm) {
        boolean retVal = false;
        if (!retVal) {
            String meternameIdStr = (String) hm.get("metername");
            int meterRecId = getMeterRecId(meternameIdStr);
            if (meterRecId != -1) {
                int meterreadingrecId = getMeterReadingRecId(meterRecId);
                if (meterreadingrecId != -1) {
                    try {
                        String listMeterreadingquery = prop.getProperty("listMeterreadingquery");
                        pstmt = conn.prepareStatement(listMeterreadingquery);
                        pstmt.setInt(1, meterreadingrecId);
                        result = pstmt.executeQuery();
                        if (result.next()) {
                            Float voltageFlt = result.getFloat("voltage");
                            Float currentFlt = result.getFloat("current");
                            Float powerFlt = result.getFloat("power");
                            Calendar cal = Calendar.getInstance();
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            String currentDate = sdf.format(cal.getTime());
                            Timestamp ts = java.sql.Timestamp.valueOf(currentDate);

                            List<HashMap<String, String>> meterreading = new ArrayList<HashMap<String, String>>();
                            HashMap hmmr = new HashMap();
                            hmmr.put("voltage", voltageFlt + "");
                            hmmr.put("current", currentFlt + "");
                            hmmr.put("power", powerFlt + "");
                            hmmr.put("datetime", ts + "");
                            meterreading.add(hmmr);

                            System.out.println("voltage is: " + voltageFlt);
                            System.out.println("current is: " + currentFlt);
                            System.out.println("power is: " + powerFlt);
                            System.out.println("datetime is: " + ts);

                            System.out.println("Rows Listed successfully");
                            retVal = true;
                        } else {
                            System.out.println("Meter not inserted");
                            retVal = false;
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(DBConnectionutil.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    System.out.println("Meter not inserted");
                    retVal = false;
                }
            }
        }
        return retVal;
    }

    public static void main(String[] args) {

        DBConnectionutil dbcu = new DBConnectionutil();

    }

}
