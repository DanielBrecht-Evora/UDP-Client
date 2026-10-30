import java.net.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws Exception {
        // Broadcast (255.255.255.255) wird im Cluster blockiert -> Unicast an alle Peers.
        // Ziel-Hostname optional als Argument, Standard ist der "peers"-Service.
        String host = args.length > 0 ? args[0] : "peers";
        DatagramSocket socket = new DatagramSocket();
        Scanner in = new Scanner(System.in);
        System.out.println("Client gestartet, sende an: " + host);
        while (in.hasNextLine()) {
            byte[] data = in.nextLine().getBytes();
            // Bei jeder Nachricht neu aufloesen, da sich die Pods aendern koennen.
            // Der Cluster ist dual-stack: "peers" liefert pro Pod eine IPv4- und eine IPv6-Adresse.
            // Nur IPv4 nehmen, sonst kommt jede Nachricht doppelt beim Server an.
            for (InetAddress addr : InetAddress.getAllByName(host)) {
                if (addr instanceof Inet4Address) {
                    socket.send(new DatagramPacket(data, data.length, addr, 4999));
                }
            }
        }
    }
}
