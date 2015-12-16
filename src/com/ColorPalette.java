package com;

import java.awt.Color;

public class ColorPalette {
	
	public final static int RED = 0;
	public final static int BLUE = 1;
	public final static int GREEN = 2;
	public final static int GRAY = 3;
	public final static int YELLOW = 4;
	
	private int colorLevel = 0;
	
	
	ColorPalette(){
	}
	

	private Color[] getPalette(int COLOR) {
		
		Color colorPalette[] = new Color[16];

		for (int i = 0; i < 16; i++) {
			if(COLOR==RED)
				colorPalette[i] = new Color(colorLevel, 0, 0);
			else if(COLOR==BLUE)
				colorPalette[i] = new Color(0, colorLevel, 0);
			else if(COLOR==GREEN)
				colorPalette[i] = new Color(0, 0, colorLevel);
			else if(COLOR==GRAY)
				colorPalette[i] = new Color(colorLevel, colorLevel, colorLevel);
			
			colorLevel += 15;
		}
		
		return colorPalette;
	}
	
	
	public Color[] getColor(int COLOR){
		
		Color retColor[] = new Color[16];;
		
		switch (COLOR) {
		case 0:
			retColor = getPalette(RED);
			break;
		case 1:
			retColor = getPalette(BLUE);
			break;
		case 2:
			retColor = getPalette(GREEN);
			break;
		case 3:
			retColor = getPalette(GRAY);
			break;
		case 4:
			retColor = getPalette(YELLOW);
			break;			
		default:
			retColor = getPalette(GRAY);
			break;
		}
			
		return retColor;
		
	}

}
