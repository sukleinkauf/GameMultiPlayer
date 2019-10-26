
import java.awt.Image;
import javax.swing.ImageIcon;
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
public class Player extends JLabel{
    public String Nome;
    public int x = 0, y = 0, f = 0;   
	ImageIcon walkL;
    ImageIcon walkR;
    ImageIcon walkU;
    ImageIcon walkD;
    ImageIcon stopped;
    ImageIcon fight;

    public void setup(){
        setText("12");
        walkR = new ImageIcon(
                new ImageIcon(getClass()
                        .getResource("c_d.gif"))
                        .getImage()
                        .getScaledInstance(88, 127, Image.SCALE_DEFAULT));
        walkL = new ImageIcon(
                new ImageIcon(getClass()
                        .getResource("c_e.gif"))
                        .getImage()
                        .getScaledInstance(88, 127, Image.SCALE_DEFAULT));
        fight = new ImageIcon(
                new ImageIcon(getClass()
                        .getResource("c_f.png"))
                        .getImage()
                        .getScaledInstance(88, 127, Image.SCALE_DEFAULT));
        stopped = new ImageIcon(
                new ImageIcon(getClass()
                        .getResource("p_d.gif"))
                        .getImage()
                        .getScaledInstance(88, 127, Image.SCALE_DEFAULT));
        setBounds(x, y, 90, 127);
        setIcon(walkR);
        setIcon(walkL);
    }
    
    public void move(){
        setBounds(x, y, 90, 127);
    }
    
    public void setIconRight(){
        setIcon(walkR);
    }
    
    public void setIconLeft(){
        setIcon(walkL);
    }
    
    public void setIconStopped(){
        setIcon(stopped);
    }
    
    public void setIconFight(){
        setIcon(fight);
    }
    
    public int getF() {
		return f;
	}

	public void setF(int f) {
		this.f = f;
	}
}
