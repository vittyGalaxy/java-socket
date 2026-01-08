package dummy;

import dummy.calculator.ServerTcpCalculator;

public class MainServer {
    public static void main(String[] args){
        ServerTcpCalculator server = new ServerTcpCalculator("Server", 20000);
        server.start();
        server.communicate();
    }
    
}
