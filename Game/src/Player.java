
import java.awt.Image;
import java.awt.Rectangle;

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
    public String identifier;
    public int x = 0, y = 0, f = 0; 
    protected int width = 88;
    protected int height= 127;
	ImageIcon walkL;
    ImageIcon walkR;
    ImageIcon walkU;
    ImageIcon walkD;
    ImageIcon stopped;
    ImageIcon fight;
    int pontos = 100;


	public void setup(){
        setText("12");
        walkR = new ImageIcon(
                new ImageIcon(getClass()
                        .getResource("c_d.gif"))
                        .getImage()
                        .getScaledInstance(width, height, Image.SCALE_DEFAULT));
        walkL = new ImageIcon(
                new ImageIcon(getClass()
                        .getResource("c_e.gif"))
                        .getImage()
                        .getScaledInstance(width, height, Image.SCALE_DEFAULT));
        fight = new ImageIcon(
                new ImageIcon(getClass()
                        .getResource("c_f.png"))
                        .getImage()
                        .getScaledInstance(width, height, Image.SCALE_DEFAULT));
        stopped = new ImageIcon(
                new ImageIcon(getClass()
                        .getResource("p_d.gif"))
                        .getImage()
                        .getScaledInstance(width, height, Image.SCALE_DEFAULT));
        setBounds(x, y, 90, 127);
        setIcon(walkR);
        setIcon(walkL);
    }
   
	public Rectangle getBounds() {
	    return new Rectangle(x, y, width, height);
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
	
    public int getPontos() {
		return pontos;
	}

	public void setPontos(int pontos) {
		this.pontos--;
	}
}
