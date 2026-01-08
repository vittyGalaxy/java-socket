package dummy;

import dummy.tcp.ServerTCP;

public class MainServer {
    public static void main(String[] args){
        ServerTCP serverVitto = new ServerTCP(20000, "VittoServer");
        serverVitto.start();
        serverVitto.communicate();
    }
    
}
