/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udppacket;

import java.net.*;


/**
 *
 * @author Admin
 */
public class UDPPacket {

   
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
       
        
    
        try {
            String host = "10.9.39.23";
            int port = 2390;

            byte[] message = "Java Source and Support".getBytes();
            byte[] receiveData = new byte[1024];

            // Get the internet address of the specified host
            InetAddress address = InetAddress.getByName(host);

            // Initialize a datagram packet with data and address
            DatagramPacket packet = new DatagramPacket(message, message.length, address, port);

            try ( // Create a datagram socket, send the packet through it, close it.
                    DatagramSocket dsocket = new DatagramSocket()) {
                dsocket.send(packet);
                System.out.println("packet sent");
                while (true) {
                    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                    dsocket.receive(receivePacket);

                    String modifiedSentence = new String(receivePacket.getData());

                    System.out.println("FROM SERVER:" + modifiedSentence);
                }
            }
        } catch (Exception e) {
            System.err.println(e);
        }

    }

}
