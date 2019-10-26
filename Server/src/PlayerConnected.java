import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class PlayerConnected implements Runnable {
    Socket socket;
    Thread threadReceiveMessages;
    BufferedReader input;
    PrintWriter output;

    public PlayerConnected(Socket socket){
        configureClient(socket);
    }
    
    public void configureClient(Socket socket){
        this.socket = socket;
        try {
            input = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream());
            threadReceiveMessages = new Thread(this);
            threadReceiveMessages.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    public void receiveMessages(){
        try {
            String message;
            while((message = input.readLine()) != null){
                System.out.println(message);
                for(PlayerConnected cliente:MultiPlayerGameServer.players){
                    cliente.sendMessage(message);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void sendMessage(String message){
        output.println(message);
        output.flush();
    }
    
    @Override
    public void run() {
        receiveMessages();
    }
    
    
}
