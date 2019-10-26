
public class ExecServer {

    public static void main(String[] args) {
        // TODO code application logic here
    	MultiPlayerGameServer mucs = new MultiPlayerGameServer();
        mucs.configureServer();
        mucs.waitPlayer();
    }

}
