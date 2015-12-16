package com;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

public class LEDGrid {
	
	private BufferedImage img;
	
	public void setLED(BufferedImage img, LED led){
		createImage(img, led.getX(), led.getY(), led.getColor());
	}
	
	public void setBackground(){
		Graphics2D gfx = this.img.createGraphics();
		gfx.setBackground(new Color(200, 200, 200));
		gfx.clearRect(0, 0, img.getHeight(), img.getWidth());
	}
	
	public BufferedImage createImage(BufferedImage img, int x, int y, Color color){
		
		Graphics2D gfx = this.img.createGraphics();
		gfx.setBackground(new Color(200, 200, 200));
//		gfx.clearRect(0, 0, img.getHeight(), img.getWidth());
		
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,	RenderingHints.VALUE_ANTIALIAS_ON);
		gfx.setRenderingHints(rh);

		int pixelSize = 11;
		
		gfx.setColor(color);
		
		gfx.fillOval(x, y, pixelSize, pixelSize);
		
		return this.img;
	}
	
	public BufferedImage createImage(BufferedImage img, byte data[], int x, int y, Color colorPalette[], boolean transparency){
		
		
		Graphics2D gfx = img.createGraphics();
		gfx.setBackground(new Color(200, 200, 200));
		gfx.clearRect(0, 0, x, y);
		
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,	RenderingHints.VALUE_ANTIALIAS_ON);
		gfx.setRenderingHints(rh);

		int pixelSize = 11;
		
		gfx.setColor(new Color(55, 55, 55));
		int a = 0, b = 0, c = 0;
		
		while(a <= 63){
			gfx.fillOval(b, c, pixelSize, pixelSize);
			b+=12;
			if(b==96){
				b=0;
				c+=12;
			}
			a++;
		}
		
		return img;
	
//			int a=0;
//			//Parse the rest of the data
//			while(a <= data.length - 14){
//
//				singleByte = (byte) data[a++];
//				whatIsIt = (double)(singleByte & 0xFF);
//				
//				if(whatIsIt==0){
//					break;
//				}
//				
//				if(whatIsIt>=0x81){
//					pixelCount = 255 - (int)whatIsIt;
//					
//					if(transparency)
//						gfx.setColor(new Color(0,255,0));
//					else
//						gfx.setColor(colorPalette[0]);
//					
//					for(int i=0;i<=pixelCount;i++){
//						
//						if (largeSize == 1) {
//							gfx.fillOval(row, column, pixelSize, pixelSize);
//							column += 7;
//							if (column == y) {
//								column = 0;
//								row += 7;
//							}
//						} else {
//							gfx.drawRect(row++, column, pixelSize, pixelSize);
//							if (row == y) {
//								row = 0;
//								column++;
//							}
//						}
//					}
//					
//				}else{
//					pixelCount = (int)whatIsIt;
//					
//					for(int i=1;i<=pixelCount;++i){
//						singleByte = (byte) data[a++];
//						whatIsIt = (double)(singleByte & 0xFF);
//						
//						if(whatIsIt>=16){
//							gfx.setColor(new Color(0,255,0));
//							//gfx.setColor(colorPalette[ColorPalette.GREEN]);
//						}else{
//							gfx.setColor(colorPalette[(int)whatIsIt]);
//						}
//						
////						gfx.drawRect(row, column++, 0, 0);
////						
////						if(column==y){
////							column=0;
////							row++;
////						}
//						
//						if (largeSize == 1) {
//							gfx.fillOval(row, column, pixelSize, pixelSize);
//							column += 7;
//							if (column == y) {
//								column = 0;
//								row += 7;
//							}
//						} else {
//							gfx.drawRect(row++, column, pixelSize, pixelSize);
//							if (row == y) {
//								row = 0;
//								column++;
//							}
//						}
//					}
//			
//				}
//				
//			}
//			
////			System.out.println("Total pixels: "+totalPixels);
//			
//
//		return img;

	}

	public BufferedImage getImg() {
		return img;
	}

	public void setImg(BufferedImage img) {
		this.img = img;
	}
}
