
public class ExecServer {

    public static void main(String[] args) {
        MultiPlayerGameServer mucs = new MultiPlayerGameServer();
        mucs.configureServer();
        mucs.waitPlayer();
    }
}
