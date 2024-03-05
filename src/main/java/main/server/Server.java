package main.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Server {
    private static final int PORT = 12345;
    private static final Map<SocketChannel, String> clients = new HashMap<>();
    private static Selector selector;
    private static final GameManagement gameManagement = new GameManagement();

    public static void main(String[] args) {
        try {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(PORT));
            serverSocketChannel.configureBlocking(false);

            selector = Selector.open();
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            System.out.println("Server is running on port " + PORT);

            while (true) {
                selector.select();

                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    iterator.remove();

                    if (key.isAcceptable()) {
                        handleAccept(key);
                    } else if (key.isReadable()) {
                        handleRead(key);
                    }
                    else if (key.isWritable()) {
                        handleWrite(key);
                    }

                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void handleWrite(SelectionKey key) throws IOException {
        SocketChannel sc = (SocketChannel) key.channel();
        String clientId = clients.get(sc);

        String s = gameManagement.message(clientId);

        ByteBuffer buffer = ByteBuffer.wrap(s.getBytes());
        sc.write(buffer);

        key.interestOps(SelectionKey.OP_READ);
    }

    private static void handleAccept(SelectionKey key) throws IOException {
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
        SocketChannel clientChannel = serverSocketChannel.accept();
        clientChannel.configureBlocking(false);
        clientChannel.register(selector, SelectionKey.OP_WRITE); //tu zmieniam

        String clientId = "Player" + clients.size();
        clients.put(clientChannel, clientId);

        gameManagement.newPlayer(clientId);

        System.out.println("New connection from: " + clientId);
    }

    private static void handleRead(SelectionKey key) throws IOException {
        SocketChannel clientChannel = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        try {
            int bytesRead = clientChannel.read(buffer);

            if (bytesRead == -1) {
                String clientId = clients.get(clientChannel);
                System.out.println("Connection closed by: " + clientId);
                clients.remove(clientChannel);
                key.cancel();
                clientChannel.close();
            } else {
                buffer.flip();
                byte[] bytes = new byte[buffer.remaining()];
                buffer.get(bytes);
                String message = new String(bytes);

                for(SocketChannel c : clients.keySet()) {
                    c.write(buffer);
                }

                if ("exit".equalsIgnoreCase(message.trim())) {
                    handleClientExit(key);
                } else {
                    System.out.println("Received from " + clients.get(clientChannel) + ": " + message);
                    String clientId = clients.get(clientChannel);
                    gameManagement.response(clientId, message);
                }

                // Tutaj czekam kiedy klient będzie gotowy na czytanie

                key.interestOps(SelectionKey.OP_WRITE);
            }
        } catch (IOException e) {
            handleClientDisconnect(key);
        }
    }

    private static void handleClientDisconnect(SelectionKey key) {
        SocketChannel clientChannel = (SocketChannel) key.channel();
        String clientId = clients.get(clientChannel);

        System.out.println("Client disconnected unexpectedly: " + clientId);

        clients.remove(clientChannel);
        key.cancel();

        try {
            clientChannel.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void handleClientExit(SelectionKey key) {
        SocketChannel clientChannel = (SocketChannel) key.channel();
        String clientId = clients.get(clientChannel);

        System.out.println("Client exited: " + clientId);
        clients.remove(clientChannel);
        key.cancel();

        try {
            clientChannel.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());

        }
    }
}