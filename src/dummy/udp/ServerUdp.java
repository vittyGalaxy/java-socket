package dummy.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Scanner;

public class ServerUdp {
    int port;
    DatagramSocket serverSocket;
    DatagramPacket receivePacket, sendPacket;
    byte bufferIN[];
    byte bufferOUT[];
    String toShip, receive;
    Scanner keyboard;

    public ServerUdp(int port){
        setPort(port);
    }

    // getter
    public int getPort(){
        return this.port;
    }

    // setter
    public void setPort(int port){
        this.port = port;
    }

    public void start() {
        try {
            serverSocket = new DatagramSocket(port);
            System.out.println("Connecting...");
            keyboard = new Scanner(System.in);
        } catch (SocketException ex) {
            System.out.println("Connecting failed.");
            System.exit(1);
        }
    }

    public void communicate(){
        try {
            bufferIN = new byte[1024];
            while(true){
                System.out.println("Waiting data...");
                receivePacket = new DatagramPacket(bufferIN, bufferIN.length);
                serverSocket.receive(receivePacket);
                receive = new String(receivePacket.getData());
                receive = receive.substring(0, receivePacket.getLength());
                System.out.println("Receive Client:" + receive);
                if ("quit".equalsIgnoreCase(receive)){
                    toShip = "bye";
                    bufferOUT = toShip.getBytes();
                    sendPacket = new DatagramPacket(bufferOUT, bufferOUT.length, receivePacket.getAddress(), receivePacket.getPort());
                    serverSocket.send(sendPacket);
                    break;
                }
                toShip = keyboard.nextLine();
                bufferOUT = toShip.getBytes();
                sendPacket = new DatagramPacket(bufferOUT, bufferOUT.length, receivePacket.getAddress(), receivePacket.getPort());
                serverSocket.send(sendPacket);
            }
        } catch (Exception e){
            System.out.println("Error...");
            serverSocket.close();
            System.exit(1);
        } finally {
            serverSocket.close();
        }
    }
}
