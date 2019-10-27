import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.awt.Container;
import java.awt.Rectangle;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class ConectServer extends JFrame implements Runnable {
    Boolean keyRight = false, keyLeft = false, keyUp = false, keyDown = false, keyFight = false;
    Player player;
    String myHash;
    Container container;
    String host;
    int porta;
    Thread threadPlayer;
    Socket socket;
    BufferedReader reader;
    PrintWriter writer;

    public ConectServer(String host, int porta) {
        this.host = host;
        this.porta = porta;
    }

    public void setup() {
        try {
            socket = new Socket(host, porta);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void receiveMessages() {
        try {
            String message;
            boolean colision = false;

            while ((message = reader.readLine()) != null) {
                String hash = extractHashFromMessage(message);

                Boolean hashExists = false;
                for (Player player : GamePanel.players) {
                    if (player.identifier.contains(hash)) {
                        hashExists = true;
                        if (message.contains("M:F")) {
                            colision = isColision(player, message);
                            
                            if(colision)
                            {
                                player.incrementScore();
                            }
                        }
                    }
                }

                if (!hashExists) {
                    createPlayer(message, hash);
                }

                if (message.contains("T:MP")) {
                    setKetPressed(message);
                }

                if (message.contains("T:MR")) {
                    setKeyReleased(message);
                }

                for (Player player : GamePanel.players) {
                    if (player.identifier.contains(hash)) {
                        this.player = player;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createPlayer(String message, String identifier) {
        Player newPlayer = new Player();
        newPlayer.setup();

        newPlayer.x = Integer.parseInt(extractPositionXFromMessage(message));
        newPlayer.y = Integer.parseInt(extractPositionYFromMessage(message));

        newPlayer.identifier = identifier;
        GamePanel.players.add(newPlayer);

        createLabelScore();

        repaint();

        container.add(newPlayer);
        System.out.println("Adicionei o player " + newPlayer.identifier + " na lista");
    }

    private void setKetPressed(String message) {
        if(message.contains("M:L"))
        {
            keyLeft = true;
        }
        if(message.contains("M:R"))
        {
            keyRight = true;
        }
        if(message.contains("M:U"))
        {
            keyUp = true;
        }
        if(message.contains("M:D"))
        {
            keyDown = true;
        }
        if(message.contains("M:F"))
        {
            keyFight = true;
        }
    }

    private void setKeyReleased(String message) {
        if(message.contains("M:L"))
        {
            keyLeft = false;
        }
        if(message.contains("M:R"))
        {
            keyRight = false;
        }
        if(message.contains("M:U"))
        {
            keyUp = false;
        }
        if(message.contains("M:D"))
        {
            keyDown = false;
        }
        if(message.contains("M:F"))
        {
            keyFight = false;
        }
    }

    public void sendMessage(String message){
        writer.println("Hash:" + myHash + "-" + message);
        writer.flush();
    }

    public void start(){
        threadPlayer = new Thread(this);
        threadPlayer.start();
    }

    private String extractHashFromMessage (String message){
        return extractInfoByKeyFromMessage(message, "Hash:");
    }

    private String extractPositionXFromMessage(String message){
        return extractInfoByKeyFromMessage(message, "X:");
    }

    private String extractPositionYFromMessage(String message){
        return extractInfoByKeyFromMessage(message, "Y:");
    }
    
    private  boolean isColision(Player playeratual, String message) {
    	Rectangle attackingPlayer = playeratual.getBounds();
        boolean colision = false;
        
    	for (Player player : GamePanel.players) {
    	    Rectangle defenderPlayer = player.getBounds();

    	    if (defenderPlayer.intersects(attackingPlayer) && !player.identifier.contains(playeratual.identifier)) {
    	       colision = true;
    	    }
    	}
    	
    	return colision;
    }
    
    private String extractInfoByKeyFromMessage(String message, String key){
        String hash = "";

        for (String part : message.split("-")) {
            if(part.contains(key)){
                hash = part.replace(key, "");
            }   
        };

        return hash;
    }
    
    private void createLabelScore(){
        Integer size = GamePanel.scoresComponents.size();
        Integer distance = 20;

        JLabel labelScore = new JLabel();
        labelScore.setText("100");
        labelScore.setBounds(0, distance * size, 100, 100);
        
        GamePanel.scoresComponents.put(player.identifier, labelScore);

        container.add(labelScore);
        
    }

    @Override
    public void run() {
        setup();
        receiveMessages();
    }
}