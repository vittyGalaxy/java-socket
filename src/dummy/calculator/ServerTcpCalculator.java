package dummy.calculator;

import java.net.ServerSocket;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.net.Socket;

public class ServerTcpCalculator {
    ServerSocket        server;
    Socket              client;
    int                 port;
    String              name;
    BufferedReader      inputFromClient;
    DataOutputStream    outputToClient;

    public ServerTcpCalculator(String name, int port){
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
        System.out.println("Connection...");
        try {
            server = new ServerSocket(this.port);
            System.out.println("[SERVER] Ip: " + server.getInetAddress());
            System.out.println("[SERVER] Port: " + server.getLocalPort());
            client= server.accept();
            inputFromClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
            outputToClient = new DataOutputStream(client.getOutputStream());
            System.out.println("[SERVER] Client Accept!");
        } catch (IOException ex) {
            System.out.println("Error.");
            System.exit(1);
        }
    }

    public void communicate(){
        try {
            int i = 0;
            double number_one = 0.0;
            String operator = "";
            double number_two = 0.0;
            double result = 0.0;
            String emergency = "Error...";
            String output = "";
            while (true) {
                System.out.println("i: " + i);
                String messageFromCLient = inputFromClient.readLine();
                System.out.println("Message Received");
                
                if ("quit".equalsIgnoreCase(messageFromCLient.trim())) {
                    String byeMessage = "BYE";
                    outputToClient.writeBytes(byeMessage + "\n");
                    break;
                }

                switch (i) {
                    case 0:

                        number_one = Double.parseDouble(messageFromCLient);
                        System.out.println("Assigned " + number_one);
                        i++;
                        break;
                    
                    case 1:
                        operator = messageFromCLient;
                        System.out.println("Assigned " + operator);
                        i++;
                        break;
                    
                    case 2:
                        number_two = Double.parseDouble(messageFromCLient);
                        System.out.println("Assigned " + number_two);
                        result = operation(number_one, number_two, operator);
                        if (Double.isNaN(result)){
                            outputToClient.writeBytes(emergency + "\n");
                            System.out.println("[SERVER] Send: " + emergency);
                        }else{
                            output = String.valueOf(result);
                            outputToClient.writeBytes(output + "\n");
                            System.out.println("[SERVER] Send: " + output);
                        }
                        i = 0;
                        break;
                
                    default:
                        System.out.println("Error during operation.");
                        break;
                }
            }
        } catch (IOException ex) {
            System.out.println("Error.");
        } finally {
            try { client.close(); } catch (IOException ignored) {}
            try { server.close(); } catch (IOException ignored) {}
        }
    }

    public double operation(double num1, double num2, String operator){
        double result = 0.0;
        switch (operator.trim()) {
            case "sum":
                result = num1 + num2;
                break;
            
            case "minus":
                result = num1 - num2;
                break;
            
            case "multply":
                result = num1 * num2;
                break;
            
            case "divide":
                if (num2 == 0){
                    result = Double.NaN;
                }else{
                    result = num1 / num2;
                }
                break;
            default:
                System.out.println("Error.");
                result = Double.NaN;
                break;
        }
        return result;
    }
}
