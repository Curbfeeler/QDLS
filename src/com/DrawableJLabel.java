package com;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JLabel;

public class DrawableJLabel extends JLabel{

	private static final long serialVersionUID = 1L;
	
	int x=0;
	int y=0;
	int x1=0;
	int y1=0;
	int rectWidth=0;
	int rectHeight=0;
	int option=0;
	
	Line2D myLine = new Line2D.Double(x1, y1, x, y);
	Rectangle2D rect = new Rectangle(x1, y1, x, y);
	Ellipse2D ellipse = new Ellipse2D.Double(x1, y1, x, y);
	Point2D point = new Point2D.Double(x, y);
	Graphics2D g2d;
	
	public static final byte TOOL_LINE = 1;
	public static final byte TOOL_RECTANGLE = 2;
	public static final byte TOOL_ELLIPSE = 3;
	public static final byte TOOL_SELECT = 4;
	public static final byte TOOL_LED_SELECT = 5;
	
	public DrawableJLabel(String string) {
		super(string);
	}
	
	public void clearCurrent(){
		paintComponent(this.getGraphics());
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g2d = (Graphics2D) g;
		g2d.setColor(Color.GREEN);
		g2d.setStroke(new BasicStroke(2));
		switch (option) {
		case TOOL_LINE:
				myLine = new Line2D.Double(x1, y1, x, y);
				g2d.draw(myLine);
				break;
		case TOOL_RECTANGLE:
				rect.setRect(x1, y1, rectWidth, rectHeight);
				g2d.draw(rect);
				break;
		case TOOL_ELLIPSE:
				ellipse.setFrame(x1, y1, rectWidth, rectHeight);
				g2d.draw(ellipse);	
				break;
		case TOOL_SELECT:
				point.setLocation(x, y);
				break;				
		default:
				break;
		}

	}
	


}
