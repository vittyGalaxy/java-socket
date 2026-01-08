package dummy.tcp;

import java.net.ServerSocket;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.net.Socket;

public class ServerTCP {
    ServerSocket    server;   
    Socket          client;
    int             port;
    String          name;
    BufferedReader  inputFromClient;
    DataOutputStream outputToClient;

    public ServerTCP(int port, String name){
        setPort(port);
        setName(name);
    }

    // getter
    public int getPort(){
        return this.port;
    }

    public String getName(){
        return this.getName();
    }
    
    // setter
    public void setPort(int port){
        this.port = port;
    }

    public void setName(String name){
        this.name = name;
    }

    // start
    public void start(){
        try{
            System.out.println("Connection...");
            server = new ServerSocket(this.port);
            System.out.println("[SERVER] Ip: " + server.getInetAddress());
            System.out.println("[SERVER] Port: " + server.getLocalPort());

            client = server.accept();
            inputFromClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
            outputToClient = new DataOutputStream(client.getOutputStream());
            System.out.println("[SERVER] Client Accept!");
            System.out.println("[SERVER] Client Port: " + client.getPort());
            System.out.println("[SERVER] Client Ip: " + server.getInetAddress());
        } catch(IOException ex) {
            System.out.println("Not Connected.");
            System.exit(1);
        }
    }

    public void communicate() {
        try {
            while(true) {
                String messageFromClient = inputFromClient.readLine();
                System.out.println("Message Received");

                if ("quit".equalsIgnoreCase(messageFromClient.trim())) {
                    String byeMessage = "BYE";
                    outputToClient.writeBytes(byeMessage + "\n");
                    break;
                }

                String result = "Received ok";
                outputToClient.writeBytes(result + "\n");
                System.out.println("[SERVER] Send: " + result);
            }
        } catch (IOException ex) {
            System.out.println("Error.");
        } finally {
            try { client.close(); } catch (IOException ignored) {}
            try { server.close(); } catch (IOException ignored) {}
        }
    }

}
