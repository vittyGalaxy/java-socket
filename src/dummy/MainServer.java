package dummy;

import dummy.udp.ServerUdp;;

public class MainServer {
    public static void main(String[] args){
        ServerUdp server = new ServerUdp(7849);
        server.start();
        server.communicate();
    }
}
