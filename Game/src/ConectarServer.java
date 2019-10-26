import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.awt.Container;
import javax.swing.JFrame;

public class ConectarServer extends JFrame implements Runnable{
    Boolean keyRight = false, keyLeft = false, keyUp = false, keyDown = false,  keyFight = false;
    Player player;
    Integer speed = 4;
    Container container;
    String host;
    int porta;
    Thread threadPlayer;
    Socket socket;
    BufferedReader in;
    PrintWriter out;
    String myHash;
    
    public ConectarServer(String host, int porta){
        this.host = host;
        this.porta = porta;
    }
    
    public void configurarCliente(){
        try {
            socket = new Socket(host, porta);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    public void receberMensagens(){
        try {
            String msg;
            //System.out.println(msg = in.toString());
            
            while((msg = in.readLine()) != null){
                String hash = extractHashFromMessage(msg);

                //Verifica se o player já existe
                Boolean hashExists = false;
                for (Player player : GamePanel.players) {
                    if(player.Nome.contains(hash)){
                        hashExists = true;
                    }
                }

                //Adicionar o novo player ao contexto, se necessário
                if(!hashExists){
                    
                    Player newPlayer = new Player();
                    newPlayer.setup();

                    newPlayer.x = Integer.parseInt(extractPositionXFromMessage(msg));
                    newPlayer.y = Integer.parseInt(extractPositionYFromMessage(msg));

                    newPlayer.Nome = hash;
                    GamePanel.players.add(newPlayer);
                    repaint();

                    container.add(newPlayer);
                    System.out.println("Adicionei o player " + newPlayer.Nome + " na lista");
                }

                if(msg.contains("T:MP"))
                {                    
                    if(msg.contains("M:L"))
                    {
                        keyLeft = true;
                    }
                    if(msg.contains("M:R"))
                    {
                        keyRight = true;
                    }
                    if(msg.contains("M:U"))
                    {
                        keyUp = true;
                    }
                    if(msg.contains("M:D"))
                    {
                        keyDown = true;
                    }
                    if(msg.contains("M:F"))
                    {
                        keyFight = true;
                    }
                }

                if(msg.contains("T:MR"))
                {                   
                    if(msg.contains("M:L"))
                    {
                        keyLeft = false;
                    }
                    if(msg.contains("M:R"))
                    {
                        keyRight = false;
                    }
                    if(msg.contains("M:U"))
                    {
                        keyUp = false;
                    }
                    if(msg.contains("M:D"))
                    {
                        keyDown = false;
                    }
                    if(msg.contains("M:F"))
                    {
                        keyFight = false;
                    }
                }
            
                for (Player player : GamePanel.players) {
                    if(player.Nome.contains(hash)){
                        this.player = player;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void enviarMensagem(String msg){
        out.println("Hash:" + myHash + "-" + msg);
        out.flush();
    }

    public void iniciar(){
        threadPlayer = new Thread(this);
        threadPlayer.start();
    }

    private String extractHashFromMessage (String message){
        return extractFromMessage(message, "Hash:");

    }

    private String extractPositionXFromMessage(String message){
        return extractFromMessage(message, "X:");
    }

    private String extractPositionYFromMessage(String message){
        return extractFromMessage(message, "Y:");
    }

    private String extractFromMessage(String message, String key){
        String hash = "";

        for (String part : message.split("-")) {
            if(part.contains(key)){
                hash = part.replace(key, "");
            }   
        };

        return hash;
    }
    
    @Override
    public void run() {
        configurarCliente();
        receberMensagens();
    }
}