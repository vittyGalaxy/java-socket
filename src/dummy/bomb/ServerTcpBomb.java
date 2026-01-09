package dummy.bomb;

import java.net.ServerSocket;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Random;

public class ServerTcpBomb {
    ServerSocket    server;   
    Socket          client;
    int             port;
    String          name;
    BufferedReader  inputFromClient;
    DataOutputStream outputToClient;

    public ServerTcpBomb(String name, int port){
        setName(name);
        setPort(port);
    }

    // getter
    public String getName(){
        return this.name;
    }

    public int getPort(){
        return this.port;
    }

    // setter
    public void setName(String name){
        this.name = name;
    }
    
    public void setPort(int port){
        this.port = port;
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
        Random rand = new Random();
        int bomb = rand.nextInt(100);
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
                bomb--;
                if (bomb == 0){
                    System.out.println("boom");
                    break;
                }
                String outBomb = String.valueOf(bomb);
                outputToClient.writeBytes(outBomb);
            }
        } catch (IOException ex) {
            System.out.println("Error.");
        } finally {
            try { client.close(); } catch (IOException ignored) {}
            try { server.close(); } catch (IOException ignored) {}
        }
    }
}
