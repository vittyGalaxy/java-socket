package dummy.calculator;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.util.Scanner;

public class ClientTcpCalculator {
    String              name;
    String              ip;
    int                 port;
    Socket              socket;
    BufferedReader      inputfromServer;
    DataOutputStream    outputToServer;
    Scanner             keyboard;

    public ClientTcpCalculator(String name, String ip, int port) {
        setName(name);
        setIp(ip);
        setPort(port);
    }

    // getter
    public String getName(){
        return this.name;
    }

    public String getIp(){
        return this.ip;
    }

    public int getPort(){
        return this.port;
    }

    // Setter
    public void setName(String name){
        this.name = name;
    }

    public void setIp(String ip){
        this.ip = ip;
    }

    public void setPort(int port){
        this.port = port;
    }

    //start
    public void start(){
        System.out.println("[CLIENT] Connection Attempt");
        try {
            socket = new Socket(ip, port);
            if(socket.isConnected()){
                System.out.println("Connected.");
            }else{
                System.out.println("Not Connected");
            }
            inputfromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            outputToServer = new DataOutputStream(socket.getOutputStream());
            keyboard = new Scanner(System.in);
        } catch(IOException ex){
            System.out.println("Error.");
            System.exit(1);
        }
    }

    public void communicate(){
        try {
            while (true) {
                System.out.println("enter n1: ");
                String message = keyboard.nextLine();
                outputToServer.writeBytes(message + "\n");

                System.out.println("enter op: ");
                message = keyboard.nextLine();
                outputToServer.writeBytes(message + "\n");

                System.out.println("enter n2: ");
                message = keyboard.nextLine();
                outputToServer.writeBytes(message + "\n");

                String messageFromServer = inputfromServer.readLine();
                System.out.println("[CLIENT] Result: " + messageFromServer);

                if ("bye".equalsIgnoreCase(messageFromServer.trim())){
                    System.out.println("[CLIENT] Server Closed");
                    break;
                }
            }
        } catch(IOException ex){
            System.out.println("Error commuinication.");
        }finally{
            try { socket.close(); } catch(IOException ignored) {}
        }
    }
}