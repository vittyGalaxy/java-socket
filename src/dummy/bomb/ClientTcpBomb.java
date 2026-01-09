package dummy.bomb;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.DataOutputStream;
// import java.util.Scanner;

public class ClientTcpBomb {
    String              name;
    String              ip;
    int                 port;
    Socket              socket;
    BufferedReader      inputfromServer;
    DataOutputStream    outputToServer;

    public ClientTcpBomb(String name, String ip, int port){
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

    // setter
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

            inputfromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            outputToServer = new DataOutputStream(socket.getOutputStream());
            if (socket.isConnected()){
                System.out.println("Connected");
                String initMsg = "Init";
                outputToServer.writeBytes(initMsg + "\n");
            }else{
                System.out.println("Not Connected");
            }
        } catch(IOException ex){
            System.out.println("Error.");
            System.exit(1);
        }
    }

    public void communicate(){
        try {
            while (true) {
                String messageFromServer = inputfromServer.readLine();
                int bomb = Integer.parseInt(messageFromServer);
                bomb--;
                System.out.println(bomb);
                if (bomb == 0){
                    System.out.println("boom");
                    break;
                }else if(bomb == 1){
                    String outbomb = String.valueOf(bomb);
                    outputToServer.writeBytes(outbomb + "\n");
                    break;
                }
                String outbomb = String.valueOf(bomb);
                outputToServer.writeBytes(outbomb + "\n");
            }
        } catch(IOException ex){
            System.out.println("Error commuinication.");
        }finally{
            try { socket.close(); } catch(IOException ignored) {}
        }
    }
}
