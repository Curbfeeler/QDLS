package com;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class DrawableFrame extends JFrame{

	int x=0;
	int y=0;
	int x1=0;
	int y1=0;
	
	Line2D myLine=new Line2D.Double(0, 0, 400, 400);;
	
	public void paintComponent(Graphics g) {
		System.out.println(x+"  "+y+"  "+x1+"  "+y1);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.RED);
		myLine = new Line2D.Double(x, y, x1, y1);
		//g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,	RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.draw(myLine);
		//g2d.setColor(Color.RED);
	}

}
