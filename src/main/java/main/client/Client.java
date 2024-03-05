package main.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {
        try {
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.connect(new InetSocketAddress("localhost", 12345));

            Scanner scanner = new Scanner(System.in);

            while (true) {

                ByteBuffer bb = ByteBuffer.allocate(1024);
                socketChannel.read(bb);

                bb.flip();
                byte[] bytes = new byte[bb.remaining()];
                bb.get(bytes);
                String response = new String(bytes);

                System.out.println(response);

                System.out.print("Enter a message: ");
                String message = scanner.nextLine();

                ByteBuffer buffer = ByteBuffer.wrap(message.getBytes());
                socketChannel.write(buffer);

                if ("exit".equalsIgnoreCase(message.trim())) {
                    break;
                }
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
