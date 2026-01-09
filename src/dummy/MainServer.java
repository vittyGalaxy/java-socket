package dummy;

import dummy.bomb.ServerTcpBomb;

public class MainServer {
    public static void main(String[] args){
        ServerTcpBomb server = new ServerTcpBomb("Server", 20000);
        server.start();
        server.communicate();
    }
    
}
