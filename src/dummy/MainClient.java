package dummy;

import dummy.tcp.ClientTCP;

public class MainClient {
    public static void main(String[] args){
        ClientTCP client = new ClientTCP("VittoClient", "127.0.0.1", 20000);
        client.start();
        client.communicate();
    }
}
