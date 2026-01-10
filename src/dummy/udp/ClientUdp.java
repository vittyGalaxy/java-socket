package dummy.udp;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class ClientUdp {
    String serverAddress;
    int port;
    DatagramSocket clientSocket;
    DatagramPacket sendPacket, receivePacket;
    Scanner keyboard;
    String toShip, receive;
    byte bufferIN[], bufferOUT[];
    InetAddress ipServerAddress;

    public ClientUdp(int port, String serverAddress){
        setPort(port);
        setServerAddress(serverAddress);
    }

    // getter
    public int getPort(){
        return this.port;
    }
    
    public String getServerAddress(){
        return this.serverAddress;
    }

    // setter
    public void setPort(int port){
        this.port = port;
    }

    public void setServerAddress(String serverAddress){
        this.serverAddress = serverAddress;
    }

    // start
    public void start(){
        try {
            clientSocket = new DatagramSocket();
            ipServerAddress = InetAddress.getByName(serverAddress);
            System.out.println("Connected.");
            keyboard = new Scanner(System.in);
        } catch (IOException ex) {
            System.out.println("Error...");
            System.exit(1);
        }
    }

    public void communicate(){
        try {
            while(true){
                System.out.println("[CLIENT] message: ");
                toShip = keyboard.nextLine();
                bufferOUT = toShip.getBytes();
                sendPacket = new DatagramPacket(bufferOUT, bufferOUT.length, ipServerAddress, port);
                clientSocket.send(sendPacket);
                bufferIN = new byte[1024];
                receivePacket = new DatagramPacket(bufferIN, bufferIN.length);
                clientSocket.receive(receivePacket);
                receive = new String(receivePacket.getData());
                receive = receive.substring(0, receivePacket.getLength());
                System.out.println("[CLIENT] Server: " + receive);
                if ("bye".equalsIgnoreCase(receive)){
                    break;
                }
            }
        } catch (IOException ex) {
            System.out.println("Error.");
            clientSocket.close();
            System.exit(1);
        }finally{
            clientSocket.close(); 
        }
    }
}
