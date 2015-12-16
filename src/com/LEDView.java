package com;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class LEDView extends Canvas {

	private static final long serialVersionUID = 1L;
	Image img;

	public void paint(Graphics g) {
		g.drawImage(img, 0, 0, this);
	}

}
