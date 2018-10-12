/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udppacket;

import java.net.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Properties;
import dbconnectionutil.DBConnectionutil;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class UDPParse {
    
    int version;
    int sequenceNumber;
    int hubID;
    int nodeID; // 1 = voltage, 2 = current, 3 = Power, 9 = control
    int length;
    int value;
    int count = 0;
    int packetType; // meter reading =0
    float powerValue;
    float voltage;
    float current;
    String[] s;
    
    public static List<String> arr = null;
    
    private InputStream inputStream;
    
    private OutputStream outputStream;
    
    DBConnectionutil dbcu = new DBConnectionutil();

//InputStream is = DBConnectionutil.class.getResourceAsStream("/resources/dbconnectionutil.properties");  
    HashMap hm = new HashMap();
    Properties prop = new Properties();
    Connection conn;
    PreparedStatement pstmt;
    
    public UDPParse(int version, int sequenceNumber, int hubID, int nodeID, int length, int value, int packetType, float voltage, float current, float powerValue) {
        this.version = version;
        this.sequenceNumber = sequenceNumber;
        this.hubID = hubID;
        this.nodeID = nodeID;
        this.length = length;
        this.value = value;
        this.packetType = packetType;
        this.powerValue = powerValue;//ver, seq, hub, node, len, val, packet, power, volt, cur
        this.voltage = voltage;
        this.current = current;
    }
    
    public UDPParse() {
        
    }

    //GETTER SETTER METHOD
    public int getVersion() {
        return version;
    }
    
    public int getSequenceNumber() {
        return sequenceNumber;
    }
    
    public int getHubID() {
        return hubID;
    }
    
    public int getNodeID() {
        return nodeID;
    }
    
    public int getLength() {
        return length;
    }
    
    public int getValue() {
        return value;
    }
    
    public int getPacketType() {
        return packetType;
    }
    
    public float getPowerValue() {
        return powerValue;
    }
    
    public float getVoltage() {
        return voltage;
    }
    
    public float getCurrent() {
        return current;
    }
    
    public void setVersion(int version) {
        this.version = version;
    }
    
    public void setSequenceNumber(int sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }
    
    public void setHubID(int hubID) {
        this.hubID = hubID;
    }
    
    public void setLength(int length) {
        this.length = length;
    }
    
    public void setValue(int value) {
        this.value = value;
    }
    
    public void setPacketType(int packetType) {
        this.packetType = packetType;
    }
    
    public void setPowerValue(float powerValue) {
        this.powerValue = powerValue;
    }
    
    public void setVoltage(float voltage) {
        this.voltage = voltage;
    }
    
    public void setCurrent(float current) {
        this.current = current;
    }
    
    public void setNodeID(int nodeID) {
        this.nodeID = nodeID;
    }
    
    @Override
    public String toString() {
        return "UDPParse{" + "s=" + Arrays.toString(s) + ", version=" + version + ", sequenceNumber=" + sequenceNumber + ", hubID=" + hubID + ", nodeID=" + nodeID + ", length=" + length + ", value=" + value + ", packetType=" + packetType + ", powerValue=" + powerValue + ", voltage=" + voltage + ", current=" + current + ", count=" + count + '}';
    }
    
    public void display() {
        
        System.out.println("ver=" + version + ", seq=" + sequenceNumber + ", hubid= " + hubID + ", node= " + nodeID + " Status=ON " + ", packet_type=" + packetType + ", read_type= 0" + ", length=" + length + ", Voltage=" + voltage + ", readType= 1 " + ", length= " + length + ", current= " + current + ", readType= 2 " + ", length " + length + ", powervalue " + powerValue);
        
    }
    
    public List<String> inputData(Demo d) {
        List<String> linearray = new ArrayList();
        List<String> arr = new ArrayList();
        List<Integer> listArray = new ArrayList();
        byte received = -1;
        do {
            try {
                int val = 0;
                
                InputStream inputReader = inputStream;
                BufferedReader br = new BufferedReader(new InputStreamReader(inputReader));
                String text = br.readLine();//read the data from the device
                linearray = readEachLine(text, d);//call the function to seperate each set of data
                arr = parseData(text);//parsing done on the read  data

                listArray = seperateDataObject(arr);//PARSE THE VALUES IN THE LIST TO REQ TYPES

                byte[] buffer = text.getBytes();
                outputStream.write(buffer);
            } catch (Exception e) {
                // System.err.println("Error reading USB:" + e.getMessage());
            }
            
        } while (received != -1);
        
        return arr;
    }
    
    public static List readEachLine(String text, Demo d) {//SEPERATE THE SERIAL DATA OBTAINED INTO EACH LINE OF DATA 
        ArrayList<String> objectlist = new ArrayList<>();
        
        String str = "Version";
        String[] s = new String[text.length()];
        String[] dataInEachLine = text.replaceAll(str, "\n" + str).split("\n");//parse based on version keyword
        UDPParse udp[] = new UDPParse[1024];
        int ver = 0, seq = 1, hub = 1, node = 1, len = 2, val = 0, packet = 0, power = 0, volt = 0, cur = 0;
        for (int i = 0; i < dataInEachLine.length; i++) {
            objectlist.add("\n" + dataInEachLine[i]);
            d.incrementCounter();
            s[i] = objectlist.get(i);
        }
        return objectlist;
    }
    
    public static List<String> parseData(String text) {//PARSING DATA BASED ON COMMA AND EQUAL TO AND ADDING EACH TO A SEPERATE LIST
        String str1 = "Version";
        String[] strArray = text.split(",");
        String[] strArray2;
        List<String> itemList = new ArrayList<String>();
        for (int k = 0; k < strArray.length; k++) {
            String[] strArray1 = strArray[k].split("=");
            itemList.add(strArray1[1]);
        }
        return itemList;
    }
    Demo d;
    
    public List seperateDataObject(List<String> arr)//GET THE INDIVIDUAL DATA FROM THE LIST AND PARSE TO REQ TYPES
    {
//        UDPParse udp[] = new UDPParse[1024];
        int packetType = 0, len = 2;
        float power = 0.0f, current = 0.0f, voltage = 0.0f;
        try {
            String version1 = arr.get(0).trim();
            
            int ver = Integer.parseInt(version1);
            
            String sequenceNumber1 = arr.get(1).trim();
            int seq = Integer.parseInt(sequenceNumber1);
            
            String hubID1 = arr.get(2).trim();
            int hub = Integer.parseInt(hubID1);
            
            String nodeID1 = arr.get(3).trim();
            int node = Integer.parseInt(nodeID1);
            setNodeID(node);
            
            String packetType1 = arr.get(5).trim();
            packetType = Integer.parseInt(packetType1);
            
            String length1 = arr.get(7).trim();
            len = Integer.parseInt(length1);//status =on

            String voltage1 = arr.get(8).trim();
            voltage1 = voltage1.replaceAll("V", "");
            voltage = Float.parseFloat(voltage1);
            setVoltage(voltage);
            String current1 = arr.get(11).trim();
            current1 = current1.replaceAll("A", "");
            current = Float.parseFloat(current1);
            setCurrent(current);
            String power1 = arr.get(14).trim();
            power1 = power1.replaceAll("W", "");
            power1 = power1.replaceAll("P", "");
            power1 = power1.replaceAll("\\.", "");
            power = Float.parseFloat(power1);
            setPowerValue(power);
//            for (int k = 0; k < 1; k++) {//CREATE OBJECT  FOR EACH LINE OF DATA
//                udp[k] = new UDPParse(ver, seq, hub, node, len, 0, packetType, voltage, current, power);//int version, int sequenceNumber, int hubID, int nodeID, int length, int value, int packetType, float powerValue, float voltage, float current
//                udp[k].display();

            HashMap hm = new HashMap();
//                HashMap hm1 = new HashMap();
            //hm1 = 
            createHashMapForPersisting(hm);
            System.out.println("HM1 value is " + hm);
            System.out.println("Object value" + dbcu);

            //dbcu.addMeterReading(hm1);
//            }   //System.out.println("meteradded");
        } catch (NullPointerException e) {
            e.printStackTrace();
            System.out.println("Exception: " + e);
        }
        return arr;
    }
    
    public void createHashMapForPersisting(HashMap hm) {
        
        hm.put("nodeid", getNodeID() + "");
        hm.put("hubid", getHubID() + "");
        hm.put("voltage", getVoltage() + "");
        hm.put("current", getCurrent() + "");
        hm.put("power", getPowerValue() + "");
        
        dbcu.addMeterReading(hm);
        System.out.println("Meter added");
        //return hm;
    }
    
    public boolean addMeterReading(HashMap hm) {
        boolean retVal = false;
        if (!retVal) {
            int nodeid = Integer.parseInt((String) hm.get("nodeid"));
            // int meterRecId = getMeterRecId(meternameIdStr);
            if (nodeid != -1) {
                int meterreadingrecId = getMeterReadingRecId(nodeid);
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
                        pstmt.setInt(1, nodeid);
                        pstmt.setFloat(2, voltageFlt);
                        pstmt.setFloat(3, currentFlt);
                        pstmt.setFloat(4, powerFlt);
                        pstmt.setTimestamp(5, java.sql.Timestamp.valueOf(currentDate));
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
    
    public int getMeterReadingRecId(int nodeId) {
        int meterReadingId = 1;
        try {
            String getMeterReadingIdFromMeterId = prop.getProperty("getMeterReadingIdFromMeterIdquery");
            pstmt = conn.prepareStatement(getMeterReadingIdFromMeterId);
            pstmt.setInt(1, nodeId);
            
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
    
    protected void write(byte[] buffer) {//WRITING DATA
        try {
            
            System.out.println("writing data");
            outputStream.write(buffer);
            
        } catch (IOException e) {
            System.err.println("Cannot write:" + e.getMessage());
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

//    while(true)
//    {    
//        UDPParse udpp =  new UDPParse(1,1,1,1,1,1,1,2.2f,3.4f,2.1f);
//        
//       HashMap hm = new HashMap ();
//       udpp.createHashMapForPersisting(udpp, hm);
//       udpp.display();
//    }
        /* Demo dem = new Demo(50);
       
      List<String> myStrings = new ArrayList<String> ();
      udpp.inputData(dem); */
 /* UDPParse udp[] = new UDPParse[1024];
        
       for (int j = 0; j < 10; j++) {
           udp[j] = new UDPParse(1,1,1,1,1,0,1,2.2f,3.3f,4.5f);//int version, int sequenceNumber, int hubID, int nodeID, int length, int value, int packetType, float powerValue, float voltage, float current
               
                
                HashMap hm1 = new HashMap();
                //hm1 = 
                      udp[j].createHashMapForPersisting(udp[j], hm);
                       udp[j].display();

               
           
       }*/
        try {
            String host = "10.9.39.23";
            int port = 2390;
            
            byte[] message = "Java Source and Support".getBytes();
            byte[] receiveData = new byte[1024];

            // Get the internet address of the specified host
            InetAddress address = InetAddress.getByName(host);

            // Initialize a datagram packet with data and address
            DatagramPacket packet = new DatagramPacket(message, message.length, address, port);
            UDPParse udpp = new UDPParse();
            try ( // Create a datagram socket, send the packet through it, close it.
                    DatagramSocket dsocket = new DatagramSocket()) {
                dsocket.send(packet);
                //System.out.println("packet sent");
                List listArray;//PARSE THE VALUES IN THE LIST TO REQ TYPES
               
                while (true) {
                     try{
                    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                    dsocket.receive(receivePacket);
                    
                    String modifiedSentence = new String(receivePacket.getData());
                    
                    System.out.println("FROM SERVER:" + modifiedSentence);
                    List<String> parseData = parseData(modifiedSentence);
                    
                    listArray = udpp.seperateDataObject(parseData);
//                    udpp.createHashMapForPersisting(udpp, new HashMap());

                }catch (Exception e){
                    System.err.println(e);
                }
            }
            }
        } catch (Exception e) {
            System.err.println(e);
        }
        
    }
    
}

class Demo {
    
    int count = 0;
    
    Demo(int counter) {
        this.count = count;
//incrementCounter();
    }
    
    public void incrementCounter() {
        count++;
    }
    
    public void decrementCounter() {
        count--;
    }
    
    public int returnCounter() {
        return count;
    }
}
