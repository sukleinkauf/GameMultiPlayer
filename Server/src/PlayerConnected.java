import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class PlayerConnected implements Runnable {
    int x, y, speed = 4;
    String Hash;
    Socket socket;
    Thread threadReceiveMessages;
    BufferedReader input;
    PrintWriter output;

    public PlayerConnected(Socket socket) {
        configureClient(socket);
    }

    public void configureClient(Socket socket) {
        this.socket = socket;
        try {
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream());
            threadReceiveMessages = new Thread(this);
            threadReceiveMessages.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void receiveMessages() {
        try {
            String message;
            while ((message = input.readLine()) != null) {
                CalculeMove(message);

                for (PlayerConnected cliente : MultiPlayerGameServer.players) {
                    System.out.println(message + "-X:" + this.x + "-Y:" + this.y);
                    cliente.sendMessage(message + "-X:" + this.x + "-Y:" + this.y);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void CalculeMove(String message) {
        if (!message.contains(Constants.TYPE_PRESSED)) {
            return;
        }

        if (message.contains(Constants.MOVEMENT_RIGHT)) {
            this.x += speed;
        }

        if (message.contains(Constants.MOVEMENT_LEFT)) {
            this.x -= speed;
        }

        if (message.contains(Constants.MOVEMENT_UP)) {
            this.y -= speed;
        }

        if (message.contains(Constants.MOVEMENT_DOWN)) {
            this.y += speed;
        }
    }

    public void sendMessage(String message) {
        output.println(message);
        output.flush();
    }

    @Override
    public void run() {
        receiveMessages();
    }

}
