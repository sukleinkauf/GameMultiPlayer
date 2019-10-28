
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JLabel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author gabriel
 */
public class GamePanel extends javax.swing.JFrame implements Runnable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    static List<Player> players = new ArrayList<Player>();
    static Map<String,JLabel> scoresComponents = new HashMap<String,JLabel>();
    Player player;
    ConectServer server;
    Boolean keyRight = false, keyLeft = false, keyUp = false, keyDown = false,  keyFight = false;
    Thread t;
    Integer speed = 4;

    /**
     * Creates new form GamePanel
     */
    public GamePanel() {
        initComponents();
        server = new ConectServer("localhost", 8000);
        server.start();
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                formKeyReleased(evt);
            }
        });
        
        
          
        getContentPane().setLayout(null);
        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_RIGHT:
                server.sendMessage(Constants.TYPE_PRESSED + "-" + Constants.MOVEMENT_RIGHT + "-X:" + player.getX() + "-Y:" + player.getY());
                break;
            case KeyEvent.VK_LEFT:
                server.sendMessage(Constants.TYPE_PRESSED + "-" + Constants.MOVEMENT_LEFT + "-X:" + player.getX() + "-Y:" + player.getY());
                break;
            case KeyEvent.VK_UP:
                server.sendMessage(Constants.TYPE_PRESSED + "-" + Constants.MOVEMENT_UP + "-X:" + player.getX() + "-Y:" + player.getY());
                break;
            case KeyEvent.VK_DOWN:
                server.sendMessage(Constants.TYPE_PRESSED + "-" + Constants.MOVEMENT_DOWN + "-X:" + player.getX() + "-Y:" + player.getY());
                break;
            case KeyEvent.VK_SPACE:
                server.sendMessage(Constants.TYPE_PRESSED + "-" + Constants.MOVEMENT_FIGHT + "-X:" + player.getX() + "-Y:" + player.getY());
            	break;
        }
 
    }//GEN-LAST:event_formKeyPressed

    private void formKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyReleased
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_RIGHT:
                server.sendMessage(Constants.TYPE_RELEASED + "-" + Constants.MOVEMENT_RIGHT + "-X:" + player.getX() + "-Y:" + player.getY());
                break;
            case KeyEvent.VK_LEFT:
                server.sendMessage(Constants.TYPE_RELEASED + "-" + Constants.MOVEMENT_LEFT + "-X:" + player.getX() + "-Y:" + player.getY());
                break;
            case KeyEvent.VK_UP:
                server.sendMessage(Constants.TYPE_RELEASED + "-" + Constants.MOVEMENT_UP + "-X:" + player.getX() + "-Y:" + player.getY());
                break;
            case KeyEvent.VK_DOWN:
                server.sendMessage(Constants.TYPE_RELEASED + "-" + Constants.MOVEMENT_DOWN + "-X:" + player.getX() + "-Y:" + player.getY());
                break;
            case KeyEvent.VK_SPACE:
                server.sendMessage(Constants.TYPE_RELEASED + "-" + Constants.MOVEMENT_FIGHT + "-X:" + player.getX() + "-Y:" + player.getY());
                break;
        }
    }//GEN-LAST:event_formKeyReleased

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        player = new Player("Voc�");
        player.setup();
        player.identifier = Integer.toString(player.hashCode());
        players.add(player);
        player.setText("A");


        JLabel labelText = new JLabel();
        labelText.setText("Placar: ");
        labelText.setBounds(0, 0, 100, 100);

        
        JLabel labelScore = new JLabel();
        labelScore.setText(player.getName() +": "+ player.getScore());
        labelScore.setBounds(0, 20, 100, 100);
        
        scoresComponents.put(player.identifier, labelScore);

        getContentPane().add(labelText);
        getContentPane().add(labelScore);
        getContentPane().add(player);
        repaint();

        t = new Thread(this);
        t.start();

        server.container = getContentPane();
        server.myHash = player.identifier;
    }//GEN-LAST:event_formWindowOpened
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GamePanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GamePanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GamePanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GamePanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                GamePanel g = new GamePanel();
                g.setSize(800, 600);
        		g.setTitle("Mortal Kombat");
        		g.setVisible(true);
            }
        });        
    }

    public void updateGame() {
        if(server == null || server.player == null)
            return;

        server.player.setF(0);

        if (server.keyRight) {
            server.player.setIconRight();
            server.player.x += speed;
        }
        
        if (server.keyLeft) {
            server.player.setIconLeft();
            server.player.x -= speed;
        }
        
        if (server.keyUp) {
            server.player.y -= speed;
        }
        
        if (server.keyDown) {
            server.player.y += speed;
        }

        if(server.keyFight) {
        	server.player.setIconFight();
            server.player.setF(1);
         
            scoresComponents.get(server.player.identifier).setText(server.player.getName()+": "+ server.player.getScore());
        }
        
        if(!(server.keyDown||server.keyUp||server.keyLeft||server.keyRight||server.keyFight)){        	
        	server.player.setIconStopped();
        }

        server.player.move();
    }
    
    @Override
    public void run() {
        while (true) {
            try {
                updateGame();
                Thread.sleep(20);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
