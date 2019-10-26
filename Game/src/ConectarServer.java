import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JTextArea;

public class ConectarServer implements Runnable{

    String host;
    int porta;
    Thread threadPlayer;
    Socket socket;
    BufferedReader in;
    PrintWriter out;
    
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
            while((msg = in.readLine()) != null){
                System.out.println(msg);
                //TODO implementar os movimentos recebidos do adversário
                // txtSaida.append(msg);
                // txtSaida.append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void enviarMensagem(String msg){
        out.println(msg);
        out.flush();
        System.out.println(msg);
    }

    public void iniciar(){
        threadPlayer = new Thread(this);
        threadPlayer.start();
    }

    @Override
    public void run() {
        configurarCliente();
        receberMensagens();
    }
}