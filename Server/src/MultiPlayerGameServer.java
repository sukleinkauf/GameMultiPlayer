
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author gabriel
 */
public class MultiPlayerGameServer {
  static List<PlayerConnected> players = new ArrayList<>();
    ServerSocket ss;
    Socket socketNewClient;

    public void configureServer() {
        try {
            ss = new ServerSocket(8000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void waitPlayer() {
        try {
            while (true) {
                socketNewClient = ss.accept();
                PlayerConnected newPlayer = new PlayerConnected(socketNewClient);                
                players.add(newPlayer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
