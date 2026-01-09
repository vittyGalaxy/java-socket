package dummy;

import dummy.bomb.ClientTcpBomb;

public class MainClient {
    public static void main(String[] args){
        ClientTcpBomb client = new ClientTcpBomb("Client", "127.0.0.1", 20000);
        client.start();
        client.communicate();
    }
}
