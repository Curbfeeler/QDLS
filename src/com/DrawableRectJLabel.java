package com;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class DrawableRectJLabel extends JLabel{

	int x=0;
	int y=0;
	int x1=0;
	int y1=0;
	
	Line2D myLine;
	Rectangle rect;
	Graphics2D g2d;
	
	public DrawableRectJLabel(String string) {
		super(string);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g2d = (Graphics2D) g;
		g2d.setColor(Color.GREEN);
		g2d.setStroke(new BasicStroke(2));
		rect = new Rectangle(x1, y1, x, y);
		g2d.draw(rect);
		
	}

}
