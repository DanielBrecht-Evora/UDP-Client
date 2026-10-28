import java.net.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket();
        socket.setBroadcast(true);
        InetAddress broadcast = InetAddress.getByName("255.255.255.255");
        Scanner in = new Scanner(System.in);
        while (in.hasNextLine()) {
            byte[] data = in.nextLine().getBytes();
            socket.send(new DatagramPacket(data, data.length, broadcast, 4999));
        }
    }
}
