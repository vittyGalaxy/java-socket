package dummy;

import dummy.calculator.ClientTcpCalculator;

public class MainClient {
    public static void main(String[] args){
        ClientTcpCalculator client = new ClientTcpCalculator("Client", "127.0.0.1", 20000);
        client.start();
        client.communicate();
    }
}
