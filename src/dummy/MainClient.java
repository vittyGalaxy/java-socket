package dummy;

import dummy.udp.ClientUdp;;

public class MainClient {
    public static void main(String[] args){
        ClientUdp client = new ClientUdp(7849, "127.0.0.1");
        client.start();
        client.communicate();
    }
}
