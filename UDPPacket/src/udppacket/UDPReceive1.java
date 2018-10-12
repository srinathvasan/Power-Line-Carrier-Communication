package udppacket;

import java.io.*;
import java.net.*;


class UDPReceive1
{
    public static void main (String args[]) throws Exception
    {
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
        DatagramSocket clientSocket = new DatagramSocket();
        
        InetAddress IPAddress = InetAddress.getByName("10.9.39.23");
        
        byte[] sendData = new byte[8];
        byte[] receiveData = new byte[8];
        
        String sentence = inFromUser.readLine();
        sendData = sentence.getBytes();
        
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length,IPAddress,2390);
        clientSocket.send(sendPacket);
        
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        clientSocket.receive(receivePacket);
        
        String modifiedSentence = new String (receivePacket.getData());
        
        System.out.println("FROM SERVER:" + modifiedSentence);
        clientSocket.close();
        
        
        
    }
}