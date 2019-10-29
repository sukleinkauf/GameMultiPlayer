import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.awt.Container;
import java.awt.Rectangle;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class ConectServer extends JFrame implements Runnable {
    private static final long serialVersionUID = 1L;
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

            while ((message = reader.readLine()) != null) {
                String hash = extractHashFromMessage(message);

                Boolean hashExists = false;
                for (Player player : GamePanel.players) {
                    if (player.identifier.contains(hash)) {
                        // Se o player já existe, seta como true
                        // Caso contrário, irá criar o player e adicionar na tela
                        hashExists = true;

                        if (message.contains(Constants.TYPE_PRESSED)) {
                            setKeyPressed(message, player);
                            player.x = extractPositionXFromMessage(message);
                            player.y = extractPositionYFromMessage(message);
                        }

                        if (message.contains(Constants.TYPE_RELEASED)) {
                            setKeyReleased(message, player);
                        }

                        if (message.contains(Constants.MOVEMENT_FIGHT) && isColision(player, message)) {
                            player.incrementScore();
                        }
                    }
                }

                if (!hashExists) {
                    createPlayer(message, hash);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createPlayer(String message, String identifier) {
        Player newPlayer = new Player("Outro Player");
        newPlayer.setup();

        newPlayer.x = extractPositionXFromMessage(message);
        newPlayer.y = extractPositionYFromMessage(message);

        newPlayer.identifier = identifier;
        GamePanel.players.add(newPlayer);
        createLabelScore(newPlayer);

        repaint();

        container.add(newPlayer);
    }

    private void setKeyPressed(String message, Player player) {
        if (message.contains(Constants.MOVEMENT_LEFT)) {
            player.keyLeft = true;
        }
        if (message.contains(Constants.MOVEMENT_RIGHT)) {
            player.keyRight = true;
        }
        if (message.contains(Constants.MOVEMENT_UP)) {
            player.keyUp = true;
        }
        if (message.contains(Constants.MOVEMENT_DOWN)) {
            player.keyDown = true;
        }
        if (message.contains(Constants.MOVEMENT_FIGHT)) {
            player.keyFight = true;
        }
    }

    private void setKeyReleased(String message, Player player) {
        if (message.contains(Constants.MOVEMENT_LEFT)) {
            player.keyLeft = false;
        }
        if (message.contains(Constants.MOVEMENT_RIGHT)) {
            player.keyRight = false;
        }
        if (message.contains(Constants.MOVEMENT_UP)) {
            player.keyUp = false;
        }
        if (message.contains(Constants.MOVEMENT_DOWN)) {
            player.keyDown = false;
        }
        if (message.contains(Constants.MOVEMENT_FIGHT)) {
            player.keyFight = false;
        }
    }

    public void sendMessage(String message) {
        writer.println("Hash:" + myHash + "-" + message);
        writer.flush();
    }

    public void start() {
        threadPlayer = new Thread(this);
        threadPlayer.start();
    }

    private String extractHashFromMessage(String message) {
        return extractInfoByKeyFromMessage(message, "Hash:");
    }

    private int extractPositionXFromMessage(String message) {
        return Integer.parseInt(extractInfoByKeyFromMessage(message, "X:"));
    }

    private int extractPositionYFromMessage(String message) {
        return Integer.parseInt(extractInfoByKeyFromMessage(message, "Y:"));
    }

    private String extractInfoByKeyFromMessage(String message, String key) {
        String hash = "";

        for (String part : message.split("-")) {
            if (part.contains(key)) {
                hash = part.replace(key, "");
            }
        }
        ;

        return hash;
    }

    private boolean isColision(Player playeratual, String message) {
        Rectangle attackingPlayer = playeratual.getBounds();
        boolean colision = false;

        for (Player player : GamePanel.players) {
            Rectangle defenderPlayer = player.getBounds();

            if (defenderPlayer.intersects(attackingPlayer) && !player.identifier.contains(playeratual.identifier)) {
                colision = true;
                break;
            }
        }

        return colision;
    }

    private void createLabelScore(Player newPlayer) {
        Integer size = GamePanel.scoresComponents.size();
        Integer distance = 40;

        JLabel labelScore = new JLabel();
        labelScore.setText(newPlayer.getName() + ": " + newPlayer.getScore());
        labelScore.setBounds(0, distance * size, 100, 100);

        GamePanel.scoresComponents.put(newPlayer.identifier, labelScore);

        container.add(labelScore);

    }

    @Override
    public void run() {
        setup();
        receiveMessages();
    }
}