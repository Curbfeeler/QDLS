package com;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.TextComponent;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.event.InputMethodListener;
import java.awt.event.InputMethodEvent;

import javax.swing.SpinnerNumberModel;


public class Main {

	private DrawableFrame frame;
	Properties prop = new Properties();
	LEDGrid ledGrid = new LEDGrid();
	BufferedImage img;
	JLabel imgLed1 = new JLabel("");
	JLabel imgLed2 = new JLabel("");
	DrawableJLabel labelPlayfield = new DrawableJLabel("");
	DrawableJLabel labelPlayfieldOverlay = new DrawableJLabel("");
	boolean ledState = true;
	JButton btnRun = new JButton("Play");
	final int LED_COUNT=80;
	int MAX_FRAMES=64;
	int myDelay1=28;
	int myDelay2=625;
	String strLampshowName = "myLampshowName";
	JLabel ledArray[] = new JLabel[LED_COUNT+1];
	JCheckBox chckbxVisable = new JCheckBox("Playfield Visible");
	JSpinner spinnerFrame = new JSpinner();
    JSpinner spinner_X = new JSpinner();
    JSpinner spinner_Y = new JSpinner();
    JSpinner spinner_W = new JSpinner();
    JSpinner spinner_H = new JSpinner();
    JSpinner spinner_X1 = new JSpinner();
    JSpinner spinner_Y1 = new JSpinner();
    JButton btnSnapshot = new JButton("Snapshot");
    JComboBox comboBoxCannedFx = new JComboBox();
    JComboBox comboBoxStep = new JComboBox();
    JSpinner spinnerChangeX = new JSpinner();
    JSpinner spinnerChangeY = new JSpinner();
    JSpinner spinnerChangeX1 = new JSpinner();
    JSpinner spinnerChangeY1 = new JSpinner();
    JSpinner spinnerChangeW = new JSpinner();
    JSpinner spinnerChangeH = new JSpinner();


	JSlider mySlider = new JSlider();
	char[][] ledStates = new char[LED_COUNT+1][];
	char[] frameCopy = new char[LED_COUNT+1];
	char[] newLEDStates = new char[LED_COUNT+1];
	private TimerTask task;
	private TimerTask task1;

	private final JButton btnClearAll = new JButton("Clear All");
	private final JCheckBox chckbxShowPriorFrames = new JCheckBox("Show Prior Frame");
	int mouseX;
	int mouseY;
	private final JButton btnClearCur = new JButton("Clear Current");
	Line2D myLine;
	JLabel lblTestNewLabel = new JLabel();
	private final JButton buttonLine = new JButton("");
	private final JButton buttonRectangle = new JButton("");
	private final JButton buttonEllipse = new JButton("");
	private final JButton buttonSelect = new JButton("");
	private byte optionTool;
	int i=0;
	public static final byte ON = 1;
	public static final byte OFF = 0;
	private Point2D sweepAnchor = new Point2D.Double(999, 0);
	private Point2D sweepStart = new Point2D.Double(999, 0);
	private Point2D sweepEnd = new Point2D.Double(999, 0);
	private int frameNum=0;
	JButton btnCopyPreviousFrame = new JButton("Copy");
	private final JCheckBox chckbxStickyShape = new JCheckBox("Sticky Shape");
	private int rectangleStartXMem = 0;
	private int rectangleStartYMem = 0;
	private int rectangleEndXMem = 0;
	private int rectangleEndYMem = 0;
	private final JLabel lblDelay = new JLabel("Speed");
	final JFileChooser fc = new JFileChooser();
	private final JButton btnChooseFile = new JButton("Choose File");
	private final JPanel panel_1 = new JPanel();
	private final JPanel panelToolKit = new JPanel();
	JButton btnExportFiles = new JButton("Export Files");
	JButton btnPaste = new JButton("Paste");
	
    private int iShapeThickness=7;
	JSpinner borderThickness = new JSpinner();
    private final JCheckBox chckbxIsSolid = new JCheckBox("Is Solid"); 
    JButton buttonLedSelect = new JButton("");
	
	private int testInt = 0;
	private final JLabel lblNewLabel = new JLabel("X");
	private final JLabel lblNewLabel_1 = new JLabel("W");
	private final JLabel lblNewLabel_2 = new JLabel("Y");
	private final JLabel lblNewLabel_3 = new JLabel("H");
	private final JButton btnPerformFx = new JButton("Perform Fx");
	private final JLabel lblChangex = new JLabel("ChangeX1");
	private final JLabel lblChangey = new JLabel("ChangeY1");

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Main() {

		initialize();
		
		chckbxVisable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				if(labelPlayfield.isVisible()){
					labelPlayfield.setVisible(false);
				}else{
					labelPlayfield.setVisible(true);
				}
			}
		});
		
		chckbxVisable.setBounds(164, 505, 117, 23);
		frame.getContentPane().add(chckbxVisable);
		btnClearAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				clearAllLEDs();
			}
		});
		
		btnClearAll.setBounds(287, 495, 126, 23);
		
		frame.getContentPane().add(btnClearAll);
		
		ImageIcon imgPlayfield = new ImageIcon(getClass().getResource("/playfield/playfield.jpg"));
		labelPlayfield.setIcon(imgPlayfield);
		
		btnClearCur.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				clearCurrentFrame();
			}
		});
		
		btnClearCur.setBounds(287, 461, 126, 23);
		
		frame.getContentPane().add(btnClearCur);
	
		labelPlayfieldOverlay.setFocusable(true);
		
				labelPlayfieldOverlay.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent me) {
						
						labelPlayfieldOverlay.option=0;
						labelPlayfieldOverlay.x=0;
						labelPlayfieldOverlay.y=0;
						
//						if(rdbtnSweep.isSelected() && sweepAnchor.getX()!=999 && sweepEnd.getX()!=999){
//							
//							labelPlayfield.option = DrawableJLabel.TOOL_LINE;
//							labelPlayfield.x1 = (int) sweepAnchor.getX();
//							labelPlayfield.y1 = (int) sweepAnchor.getY();
//							labelPlayfield.x = 9;
//							labelPlayfield.y = Integer.valueOf(textFieldSweepStart.getText());
//						}
						
						if(chckbxStickyShape.isSelected()){
							
							if(rectangleEndXMem==0){
								rectangleEndXMem = me.getX();
								rectangleEndYMem = me.getY();
//								System.out.println("mouseReleased:"+rectangleStartXMem+" , "+rectangleEndXMem+" , "+rectangleStartYMem+" , "+rectangleEndYMem);

							}
							
						}else{
							//This fixed the clear all and having it show up again when reselecting.
							labelPlayfieldOverlay.rect.setRect(0,0,0,0);
							labelPlayfieldOverlay.ellipse.setFrame(0, 0, 0, 0);
							labelPlayfieldOverlay.point.setLocation(0, 0);
							labelPlayfieldOverlay.myLine.setLine(0, 0, 0, 0);
							labelPlayfieldOverlay.repaint();
						}
						
//						System.out.println("mouseReleased:"+" "+labelPlayfieldOverlay.option);
//						System.out.println("mouseReleased:"+rectangleStartXMem+" , "+rectangleStartYMem+" , "+rectangleEndXMem+" , "+rectangleEndYMem);

						//Fix for Slowness creating stickyshape
						if(chckbxStickyShape.isSelected()){
							spinner_H.setValue(labelPlayfieldOverlay.rectHeight);
							spinner_W.setValue(labelPlayfieldOverlay.rectWidth);
							spinner_X.setValue(rectangleStartXMem);
							spinner_Y.setValue(rectangleStartYMem);
							spinner_X1.setValue(me.getX());
							spinner_Y1.setValue(me.getY());
						}
						updateLEDSOnMouseUp();
						//Dan Try Fix
						//clearnewLEDStates();
					}
					
					@Override
					public void mousePressed(MouseEvent me) {
						
						mouseX = me.getX();
						mouseY = me.getY();
			
//						System.out.println("mousePressed: "+mouseX+" , "+mouseY);
											
						if(optionTool==DrawableJLabel.TOOL_SELECT){
//							System.out.println(optionTool);
							int currentState;
							int ledHit;
							int frameNum = (Integer) spinnerFrame.getValue();
							labelPlayfieldOverlay.option = DrawableJLabel.TOOL_SELECT;
							labelPlayfieldOverlay.x = mouseX;
							labelPlayfieldOverlay.y = mouseY;
							labelPlayfieldOverlay.paintComponent(labelPlayfieldOverlay.getGraphics());
							
							for (int i = 0; i <= LED_COUNT-1; i++) {
								Rectangle rectangle = ledArray[i].getBounds();
								rectangle.setBounds(ledArray[i].getBounds().x , ledArray[i].getBounds().y , ledArray[i].getBounds().width, ledArray[i].getBounds().height);
								
//								System.out.println(labelPlayfieldOverlay.point.getX());
								
									currentState = OFF;
									ledHit = ON;
									
									if (rectangle.contains(labelPlayfieldOverlay.point)) {
										if (ledStates[i][frameNum] == '-') {
											ledHit = ON;
										}else if(ledStates[i][frameNum] == '.' && SwingUtilities.isRightMouseButton(me)){
											ledStates[i][frameNum] = '-';
										}
										setLEDTempState(i, currentState, ledHit, SwingUtilities.isRightMouseButton(me) );
									}
								
								}

						}
						//Dan Try Fix
						//clearnewLEDStates();
						
					}
				});
				
				labelPlayfieldOverlay.addMouseMotionListener(new MouseMotionAdapter() {
					
					@Override
					public void mouseDragged(MouseEvent me) {
						
						rectangleEndXMem = 0;
						rectangleEndYMem = 0;
						labelPlayfieldOverlay.x = me.getX();
						labelPlayfieldOverlay.y = me.getY();
						labelPlayfieldOverlay.x1 = mouseX;
						labelPlayfieldOverlay.y1 = mouseY;
						

						
						rectangleStartXMem = mouseX;
						rectangleStartYMem = mouseY;
						
						labelPlayfieldOverlay.rectWidth = me.getX()-mouseX;
						labelPlayfieldOverlay.rectHeight = me.getY()-mouseY;

						
						labelPlayfieldOverlay.repaint();

						int frameNum = (Integer) spinnerFrame.getValue();

						labelPlayfieldOverlay.option = optionTool;
						int currentState;
						int ledHit;
						
						for (int i = 0; i <= LED_COUNT-1; i++) {
							Rectangle rectangle = ledArray[i].getBounds();
							rectangle.setBounds(ledArray[i].getBounds().x , ledArray[i].getBounds().y , ledArray[i].getBounds().width, ledArray[i].getBounds().height);
							
							currentState = OFF;
							ledHit = OFF;
							
							if (ledStates[i][frameNum] == '.') {
								currentState = ON;
							}
							
							if(optionTool==DrawableJLabel.TOOL_LINE){
								
								if (rectangle.intersectsLine(labelPlayfieldOverlay.myLine)) {
									ledHit = ON;
								}
								
								setLEDTempState(i, currentState, ledHit, SwingUtilities.isRightMouseButton(me) );

							}else if(optionTool==DrawableJLabel.TOOL_RECTANGLE){
																
								if (labelPlayfieldOverlay.rect.intersects(rectangle)&&chckbxIsSolid.isSelected()) {
									ledHit = ON;
								}else{   
	                                Rectangle2D innerRect = (Rectangle2D) labelPlayfield.rect.clone();     
	                                iShapeThickness = (Integer) borderThickness.getValue();     
	                                innerRect.setRect(labelPlayfield.rect.getX() + iShapeThickness, labelPlayfield.rect.getY()+iShapeThickness, labelPlayfield.rect.getWidth() - (iShapeThickness*2), labelPlayfield.rect.getHeight() - (iShapeThickness*2));     
	  
	                                Rectangle2D outerRect = (Rectangle2D) labelPlayfield.rect.clone();     
	                                outerRect.setRect(labelPlayfield.rect.getX() -1, labelPlayfield.rect.getY()-1, labelPlayfield.rect.getWidth() + 2, labelPlayfield.rect.getHeight() + 2);     
	                                                                                                    
                                if (outerRect.intersects(rectangle) && !innerRect.contains(rectangle)) {     
                                    ledHit = ON;     
                                }     
                            } 

								
								if(!chckbxStickyShape.isSelected()){
									setLEDTempState(i, currentState, ledHit, SwingUtilities.isRightMouseButton(me) );
								}
								

							}else if(labelPlayfieldOverlay.option == DrawableJLabel.TOOL_ELLIPSE){
								if (labelPlayfieldOverlay.ellipse.intersects(rectangle)&&chckbxIsSolid.isSelected()) {
									ledHit = ON;
								}
								else{   
	                                Ellipse2D innerEllipse = (Ellipse2D) labelPlayfieldOverlay.ellipse.clone();     
	                                iShapeThickness = (Integer) borderThickness.getValue();     
	                                innerEllipse.setFrame(labelPlayfieldOverlay.ellipse.getX() + iShapeThickness, labelPlayfieldOverlay.ellipse.getY()+iShapeThickness, labelPlayfieldOverlay.ellipse.getWidth() - (iShapeThickness*2), labelPlayfieldOverlay.ellipse.getHeight() - (iShapeThickness*2));     
	  
	                                Ellipse2D outerEllipse = (Ellipse2D) labelPlayfield.ellipse.clone();     
	                                outerEllipse.setFrame(labelPlayfieldOverlay.ellipse.getX() -1, labelPlayfieldOverlay.ellipse.getY()-1, labelPlayfieldOverlay.ellipse.getWidth() + 2, labelPlayfieldOverlay.ellipse.getHeight() + 2);     
	                                                                                                    
	                            if (outerEllipse.intersects(rectangle) && !innerEllipse.contains(rectangle)) {     
	                                ledHit = ON;     
	                            }     
	                        } 
								
								if(!chckbxStickyShape.isSelected()){
									setLEDTempState(i, currentState, ledHit, SwingUtilities.isRightMouseButton(me) );
								}
								
							}else if(optionTool==DrawableJLabel.TOOL_SELECT){
								currentState = OFF;
								ledHit = ON;
								
								if (rectangle.contains(labelPlayfieldOverlay.point)) {
									if (ledStates[i][frameNum] == '-') {
										ledHit = ON;
									}else if(ledStates[i][frameNum] == '.' && SwingUtilities.isRightMouseButton(me)){
										ledStates[i][frameNum] = '-';
									}
									setLEDTempState(i, currentState, ledHit, SwingUtilities.isRightMouseButton(me) );
								}
							}
	
						}
					}
					
					
				@Override
				public void mouseMoved(MouseEvent me) {
						
				int currentState;
				int ledHit;

					if (chckbxStickyShape.isSelected()) {
					
					int frameNum = (Integer) spinnerFrame.getValue();
					
					labelPlayfieldOverlay.option = DrawableJLabel.TOOL_RECTANGLE;
					
					if(optionTool == DrawableJLabel.TOOL_LINE){
						labelPlayfieldOverlay.option = DrawableJLabel.TOOL_LINE;
					}else if(optionTool == DrawableJLabel.TOOL_ELLIPSE){
						labelPlayfieldOverlay.option = DrawableJLabel.TOOL_ELLIPSE;
					}
					
					for (int i = 0; i <= LED_COUNT - 1; i++) {
						
						if(optionTool == DrawableJLabel.TOOL_RECTANGLE||optionTool == DrawableJLabel.TOOL_ELLIPSE){
							labelPlayfieldOverlay.rectWidth = rectangleEndXMem	- rectangleStartXMem;
							labelPlayfieldOverlay.rectHeight = rectangleEndYMem - rectangleStartYMem;

							labelPlayfieldOverlay.x = me.getX();
							labelPlayfieldOverlay.y = me.getY();

							labelPlayfieldOverlay.x1 = me.getX() - labelPlayfieldOverlay.rectWidth / 2;
							labelPlayfieldOverlay.y1 = me.getY() - labelPlayfieldOverlay.rectHeight / 2;
							spinner_X.setValue(labelPlayfieldOverlay.x1);
							spinner_Y.setValue(labelPlayfieldOverlay.y1);
							spinner_X1.setValue(labelPlayfieldOverlay.x);
							spinner_Y1.setValue(labelPlayfieldOverlay.y);

						}else if(optionTool == DrawableJLabel.TOOL_LINE){
							labelPlayfieldOverlay.x1 = me.getX() - (rectangleEndXMem - rectangleStartXMem)/2;
							labelPlayfieldOverlay.y1 = me.getY() - (rectangleEndYMem - rectangleStartYMem)/2;
							labelPlayfieldOverlay.x = me.getX() + (rectangleEndXMem - rectangleStartXMem)/2;
							labelPlayfieldOverlay.y = me.getY() + (rectangleEndYMem - rectangleStartYMem)/2;
						}
						
//						System.out.println(rectangleStartXMem+" , "+rectangleEndXMem + "   "+
//										   rectangleStartYMem + " , " + rectangleEndYMem + "   "+ 
//										   labelPlayfield.x1 + " , " + labelPlayfield.y1 + "   "+
//										   labelPlayfield.x + " , " + labelPlayfield.y);
						
						ledArray[i].transferFocusDownCycle();
						Rectangle rectangle = ledArray[i].getBounds();
						rectangle.setBounds(ledArray[i].getBounds().x, ledArray[i].getBounds().y, ledArray[i].getBounds().width,
								ledArray[i].getBounds().height);

						currentState = OFF;
						ledHit = OFF;

						if (ledStates[i][frameNum] == '.') {
							currentState = ON;
						}

						if(labelPlayfieldOverlay.option == DrawableJLabel.TOOL_RECTANGLE){
							if (labelPlayfieldOverlay.rect.intersects(rectangle)&&chckbxIsSolid.isSelected()) {
								ledHit = ON;
							}else{   
                                Rectangle2D innerRect = (Rectangle2D) labelPlayfieldOverlay.rect.clone();     
                                iShapeThickness = (Integer) borderThickness.getValue();     
                                innerRect.setRect(labelPlayfieldOverlay.rect.getX() + iShapeThickness, labelPlayfieldOverlay.rect.getY()+iShapeThickness, labelPlayfieldOverlay.rect.getWidth() - (iShapeThickness*2), labelPlayfieldOverlay.rect.getHeight() - (iShapeThickness*2));     
  
                                Rectangle2D outerRect = (Rectangle2D) labelPlayfield.rect.clone();     
                                outerRect.setRect(labelPlayfieldOverlay.rect.getX() , labelPlayfieldOverlay.rect.getY(), labelPlayfieldOverlay.rect.getWidth() , labelPlayfieldOverlay.rect.getHeight() );     
                                                                                                    
                                if (outerRect.intersects(rectangle) && !innerRect.contains(rectangle)) {     
                                    ledHit = ON;     
                                }     
                            } 
						}else if(labelPlayfieldOverlay.option == DrawableJLabel.TOOL_ELLIPSE){
							if (labelPlayfieldOverlay.ellipse.intersects(rectangle)&&chckbxIsSolid.isSelected()) {
								ledHit = ON;
							}
							else{   
                                Ellipse2D innerEllipse = (Ellipse2D) labelPlayfieldOverlay.ellipse.clone();     
                                iShapeThickness = (Integer) borderThickness.getValue();     
                                innerEllipse.setFrame(labelPlayfieldOverlay.ellipse.getX() + iShapeThickness, labelPlayfieldOverlay.ellipse.getY()+iShapeThickness, labelPlayfieldOverlay.ellipse.getWidth() - (iShapeThickness*2), labelPlayfieldOverlay.ellipse.getHeight() - (iShapeThickness*2));     
  
                                Ellipse2D outerEllipse = (Ellipse2D) labelPlayfield.ellipse.clone();     
                                outerEllipse.setFrame(labelPlayfieldOverlay.ellipse.getX() -1, labelPlayfieldOverlay.ellipse.getY()-1, labelPlayfieldOverlay.ellipse.getWidth() + 2, labelPlayfieldOverlay.ellipse.getHeight() + 2);     
                                                                                                    
                            if (outerEllipse.intersects(rectangle) && !innerEllipse.contains(rectangle)) {     
                                ledHit = ON;     
                            }     
                        } 
							
							if(!chckbxStickyShape.isSelected()){
								setLEDTempState(i, currentState, ledHit, SwingUtilities.isRightMouseButton(me) );
							}
							
							
							
						}else if(labelPlayfieldOverlay.option == DrawableJLabel.TOOL_LINE){
							if (rectangle.intersectsLine(labelPlayfieldOverlay.myLine)) {
								ledHit = ON;
							}
						}
						
						setLEDTempState(i, currentState, ledHit, SwingUtilities.isRightMouseButton(me));

						labelPlayfieldOverlay.repaint();
					}
				}
			}
				});
				
				labelPlayfield.setBounds(0, 0, 240, 498);
				frame.getContentPane().add(labelPlayfield);
				
				JPanel panelFrames = new JPanel();
				panelFrames.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Frame", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));
				((javax.swing.border.TitledBorder) panelFrames.getBorder()).setTitleFont(new Font("Arial", Font.PLAIN, 11));
				panelFrames.setBounds(9, 543, 265, 119);
				frame.getContentPane().add(panelFrames);
				panelFrames.setLayout(null);
				mySlider.setBounds(54, 16, 200, 23);
				panelFrames.add(mySlider);
				
				mySlider.addChangeListener(new ChangeListener() {
					public void stateChanged(ChangeEvent arg0) {
						spinnerFrame.setValue(mySlider.getValue());
						//updateLEDState();
						
					}
				});
				mySlider.setValue(0);
				mySlider.setMinorTickSpacing(1);
				mySlider.setMajorTickSpacing(8);
				mySlider.setMaximum(63);
				spinnerFrame.setBounds(7, 19, 43, 20);
				panelFrames.add(spinnerFrame);
				btnCopyPreviousFrame.setBounds(10, 47, 74, 23);
				panelFrames.add(btnCopyPreviousFrame);
				
				
				btnCopyPreviousFrame.setFont(new Font("Tahoma", Font.PLAIN, 11));
				btnPaste.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						pasteFrame();
					}
				});
				
				btnPaste.setBounds(94, 47, 74, 23);
				panelFrames.add(btnPaste);
				chckbxShowPriorFrames.setBounds(7, 77, 134, 23);
				panelFrames.add(chckbxShowPriorFrames);
				
				
				
				JComboBox comboBoxSpeed = new JComboBox();
				comboBoxSpeed.setToolTipText("Lampshow Speed");
				comboBoxSpeed.setBounds(178, 50, 58, 20);
				panelFrames.add(comboBoxSpeed);
				comboBoxSpeed.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						JComboBox cb2 = (JComboBox)arg0.getSource();
						if ((String)cb2.getSelectedItem() == "fast"){
							myDelay1 = 14;
							myDelay2 = 625;
						}
						else if ((String)cb2.getSelectedItem() == "med"){
							myDelay1 = 28;
							myDelay2 = 625;
						}
						else if ((String)cb2.getSelectedItem() == "slow"){
							myDelay1 = 56;
							myDelay2 = 625;
						}
						else{
							myDelay1 = 72;
							myDelay2 = 625;
						}
							
					}
				});
				comboBoxSpeed.setModel(new DefaultComboBoxModel(new String[] {"fast", "med", "slow", "turtle"}));
				comboBoxSpeed.setSelectedIndex(1);
				comboBoxSpeed.setMaximumRowCount(4);
				comboBoxSpeed.setEditable(true);
				
				JComboBox comboBoxFrames = new JComboBox();
				comboBoxFrames.setToolTipText("Max Frames");
				comboBoxFrames.setBounds(178, 78, 58, 20);
				panelFrames.add(comboBoxFrames);
				comboBoxFrames.setEditable(true);
				comboBoxFrames.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						JComboBox cb1 = (JComboBox)arg0.getSource();
						MAX_FRAMES = Integer.valueOf((String)cb1.getSelectedItem());
						mySlider.setMaximum(MAX_FRAMES-1);
					}
				});
				comboBoxFrames.setModel(new DefaultComboBoxModel(new String[] {"16", "32", "64"}));
				comboBoxFrames.setSelectedIndex(2);
				comboBoxFrames.setMaximumRowCount(3);
				chckbxShowPriorFrames.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(chckbxShowPriorFrames.isSelected()){
							showPriorFrame();
						}else{
							updateLEDState();
						}
							
					}
				});
				btnCopyPreviousFrame.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						copyFrame();
					}
				});
				
				spinnerFrame.addChangeListener(new ChangeListener() {
					public void stateChanged(ChangeEvent arg0) {
						updateLEDState();
						clearnewLEDStates();
					}
				});
				lblDelay.setFont(new Font("Tahoma", Font.PLAIN, 11));
				lblDelay.setBounds(154, 582, 58, 17);
				
				frame.getContentPane().add(lblDelay);
				
				
				btnExportFiles.setFont(new Font("Tahoma", Font.PLAIN, 11));
				btnExportFiles.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						saveCurrentState();
					}
				});
				btnExportFiles.setBounds(287, 561, 126, 23);
				frame.getContentPane().add(btnExportFiles);
				btnChooseFile.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						
						//Handle open button action.
					    if (arg0.getSource() == btnChooseFile)
					    {
					        int returnVal = fc.showOpenDialog(null);

					        if (returnVal == JFileChooser.APPROVE_OPTION) {
					            File selectedFile = fc.getSelectedFile();
//					            System.out.println(selectedFile.getParent());
//					            System.out.println(selectedFile.getName());
					            
					    		try {
					    			prop.load(new FileInputStream(selectedFile.getParent().replace("\\", "\\\\") +"\\" +selectedFile.getName()));
					    		} catch (FileNotFoundException e) {
//					    			System.out.println("No map file found, creating new one with defaults.");
					    			createNewLEDMapFile();
					    		} catch (IOException e) {
					    			e.printStackTrace();
					    		}				            

					    		initSavedState();

					            
//					            FileInputStream fis = 
//					                    new FileInputStream(selectedFile);
//					                 InputStreamReader in = 
//					                    new InputStreamReader(fis, Charset.forName("UTF-8")); 
//					                 char[] buffer = new char[1024];
//					                 int n = in.read(buffer);
//					                 String text = new String(buffer, 0, n);
//					                 
//					                 myPane.setText(text);
//					                 in.close();
					            
					            //This is where a real application would open the file.
					            //log.append("Opening: " + file.getName() + "." + newline);
					        } else {
					            //log.append("Open command cancelled by user." + newline);
					        }
					   }	
						
						
					}
				});
				btnChooseFile.setBounds(287, 527, 126, 23);
				
				frame.getContentPane().add(btnChooseFile);
				panel_1.setBorder(new TitledBorder(null, "Lampshow Name", TitledBorder.LEADING, TitledBorder.TOP, null, null));
				panel_1.setBounds(267, 388, 209, 56);
				
				frame.getContentPane().add(panel_1);
				TextField textFieldLampshowName = new TextField();
				panel_1.add(textFieldLampshowName);
				textFieldLampshowName.setText("myLampshowName");
				panelToolKit.setBorder(new TitledBorder(null, "Toolkit", TitledBorder.LEADING, TitledBorder.TOP, null, null));
				panelToolKit.setBounds(280, 7, 134, 231);
				
				frame.getContentPane().add(panelToolKit);
				panelToolKit.setLayout(null);
				chckbxStickyShape.setBounds(24, 26, 87, 23);
				panelToolKit.add(chckbxStickyShape);
				chckbxStickyShape.setEnabled(false);
				buttonLine.setBounds(25, 59, 33, 33);
				panelToolKit.add(buttonLine);
				
				buttonLine.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						setButtonState(DrawableJLabel.TOOL_LINE);
						optionTool=DrawableJLabel.TOOL_LINE;
					}
				});
				buttonLine.setToolTipText("Draw Line");
				buttonRectangle.setBounds(25, 98, 33, 33);
				panelToolKit.add(buttonRectangle);
				buttonRectangle.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setButtonState(DrawableJLabel.TOOL_RECTANGLE);
						optionTool=DrawableJLabel.TOOL_RECTANGLE;
					}
				});
				buttonRectangle.setToolTipText("Draw Rectangle");
				buttonEllipse.setBounds(25, 137, 33, 33);
				panelToolKit.add(buttonEllipse);
				buttonEllipse.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setButtonState(DrawableJLabel.TOOL_ELLIPSE);
						optionTool=DrawableJLabel.TOOL_ELLIPSE;
					}
				});
				buttonEllipse.setToolTipText("Draw Ellipse");
				buttonSelect.setBounds(25, 176, 33, 33);
				panelToolKit.add(buttonSelect);
				buttonSelect.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setButtonState(DrawableJLabel.TOOL_SELECT);
						optionTool=DrawableJLabel.TOOL_SELECT;
						spinner_H.setValue(0);
						spinner_H.setEnabled(false);
						spinner_W.setValue(0);
						spinner_W.setEnabled(false);

						
					}
				});
				buttonSelect.setToolTipText("Select Single Lamp");
				
				
				buttonLedSelect.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setButtonState(DrawableJLabel.TOOL_LED_SELECT);
						optionTool=DrawableJLabel.TOOL_LED_SELECT;
					}
				});
				buttonLedSelect.setToolTipText("Move Lamps");
				buttonLedSelect.setBounds(78, 176, 33, 33);
				panelToolKit.add(buttonLedSelect);
				
				final JLabel lblHollowShapeWidth = new JLabel("Outline Width");
				lblHollowShapeWidth.setEnabled(false);
				lblHollowShapeWidth.setBounds(340, 245, 88, 23);
				frame.getContentPane().add(lblHollowShapeWidth);
				borderThickness.setEnabled(false);
				

				borderThickness.addInputMethodListener(new InputMethodListener() {
					public void caretPositionChanged(InputMethodEvent arg0) {
					}
					public void inputMethodTextChanged(InputMethodEvent arg0) {
						 iShapeThickness = (Integer) borderThickness.getValue();
					}
				});
				borderThickness.setBounds(290, 246, 29, 20);
				frame.getContentPane().add(borderThickness);
                chckbxIsSolid.setBounds(287, 270, 97, 23);
                frame.getContentPane().add(chckbxIsSolid);
				
                chckbxIsSolid.setSelected(true);
                
                spinner_X.addChangeListener(new ChangeListener() {
                	public void stateChanged(ChangeEvent arg0) {
						labelPlayfieldOverlay.x1 = (Integer) spinner_X.getValue();
						spinner_X1.setValue(labelPlayfieldOverlay.x);
						EffectsCheckCollision();
						labelPlayfieldOverlay.repaint();
                	}
                });
                spinner_X.setBounds(443, 215, 44, 20);
                frame.getContentPane().add(spinner_X);
                
                spinner_Y.addChangeListener(new ChangeListener() {
                	public void stateChanged(ChangeEvent e) {
						labelPlayfieldOverlay.y1 = (Integer) spinner_Y.getValue();
						spinner_Y1.setValue(labelPlayfieldOverlay.y);
						EffectsCheckCollision();
						labelPlayfieldOverlay.repaint();
                	}
                });
                spinner_Y.setBounds(514, 215, 44, 20);
                frame.getContentPane().add(spinner_Y);
                
                spinner_X1.addChangeListener(new ChangeListener() {
                	public void stateChanged(ChangeEvent arg0) {
						labelPlayfieldOverlay.x = (Integer) spinner_X1.getValue();
						spinner_X.setValue(labelPlayfieldOverlay.x1);
						EffectsCheckCollision();
						labelPlayfieldOverlay.repaint();
                	}
                });
                spinner_X1.setBounds(443, 240, 44, 20);
                frame.getContentPane().add(spinner_X1);

                spinner_Y1.addChangeListener(new ChangeListener() {
                	public void stateChanged(ChangeEvent arg0) {
						labelPlayfieldOverlay.y = (Integer) spinner_Y1.getValue();
						spinner_Y.setValue(labelPlayfieldOverlay.y1);
						EffectsCheckCollision();
						labelPlayfieldOverlay.repaint();

                	}
                });
                spinner_Y1.setBounds(514, 242, 44, 20);
                frame.getContentPane().add(spinner_Y1);
                
                spinner_W.addChangeListener(new ChangeListener() {
                	public void stateChanged(ChangeEvent e) {
                		labelPlayfieldOverlay.rectWidth = (Integer) spinner_W.getValue();
                		EffectsCheckCollision();
                		labelPlayfieldOverlay.repaint();

                	}
                });
                spinner_W.setBounds(443, 267, 44, 20);
                frame.getContentPane().add(spinner_W);

                
                spinner_H.addChangeListener(new ChangeListener() {
                	public void stateChanged(ChangeEvent e) {
                		labelPlayfieldOverlay.rectHeight = (Integer) spinner_H.getValue();
                		EffectsCheckCollision();
                		labelPlayfieldOverlay.repaint();
                		
                	}
                });
                spinner_H.setBounds(515, 267, 44, 20);
                frame.getContentPane().add(spinner_H);

                btnSnapshot.setFont(new Font("Tahoma", Font.PLAIN, 11));
                btnSnapshot.addActionListener(new ActionListener() {
                	public void actionPerformed(ActionEvent arg0) {
                		updateLEDSOnMouseUp();
                	}
                });
                btnSnapshot.setBounds(497, 325, 108, 23);
                frame.getContentPane().add(btnSnapshot);
                lblNewLabel.setBounds(424, 218, 21, 14);
                
                frame.getContentPane().add(lblNewLabel);
                lblNewLabel_1.setBounds(424, 269, 21, 14);
                
                frame.getContentPane().add(lblNewLabel_1);
                lblNewLabel_2.setBounds(497, 218, 21, 14);
                
                frame.getContentPane().add(lblNewLabel_2);
                lblNewLabel_3.setBounds(497, 270, 21, 14);
                
                frame.getContentPane().add(lblNewLabel_3);
                
                btnPerformFx.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						task1 = new TimerTask() {
							public void run() {
								performEffect();
								task1.cancel();
							}
						};
						
						Timer timer = new Timer();
						timer.schedule(task1, 0, 1);//15625
						}
						
                });
                btnPerformFx.setBounds(497, 298, 108, 23);
                
                frame.getContentPane().add(btnPerformFx);
                
                comboBoxStep.setToolTipText("recording speed");
                comboBoxStep.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3"}));
                comboBoxStep.setSelectedIndex(0);
                comboBoxStep.setBounds(530, 155, 72, 20);
                frame.getContentPane().add(comboBoxStep);
                
                JLabel lblNewLabel_4 = new JLabel("ChangeX");
                lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 11));
                lblNewLabel_4.setBounds(440, 17, 63, 14);
                frame.getContentPane().add(lblNewLabel_4);
                
                JLabel lblNewLabel_5 = new JLabel("ChangeY");
                lblNewLabel_5.setFont(new Font("Tahoma", Font.PLAIN, 11));
                lblNewLabel_5.setBounds(440, 40, 63, 14);
                frame.getContentPane().add(lblNewLabel_5);
                
                JLabel lblNewLabel_6 = new JLabel("ChangeW");
                lblNewLabel_6.setFont(new Font("Tahoma", Font.PLAIN, 11));
                lblNewLabel_6.setBounds(440, 112, 63, 14);
                frame.getContentPane().add(lblNewLabel_6);
                
                JLabel lblNewLabel_7 = new JLabel("ChangeH");
                lblNewLabel_7.setFont(new Font("Tahoma", Font.PLAIN, 11));
                lblNewLabel_7.setBounds(440, 136, 63, 14);
                frame.getContentPane().add(lblNewLabel_7);
                
                JLabel lblStep = new JLabel("Step");
                lblStep.setFont(new Font("Tahoma", Font.PLAIN, 11));
                lblStep.setBounds(443, 158, 63, 14);
                frame.getContentPane().add(lblStep);
                
                spinnerChangeX.setModel(new SpinnerNumberModel(-5, -400, 400, 1));
                spinnerChangeX.setBounds(510, 14, 58, 20);
                frame.getContentPane().add(spinnerChangeX);
                
                spinnerChangeY.setModel(new SpinnerNumberModel(-5, -400, 400, 1));
                spinnerChangeY.setBounds(510, 37, 58, 20);
                frame.getContentPane().add(spinnerChangeY);
 
                spinnerChangeX1.setModel(new SpinnerNumberModel(-5, -400, 400, 1));
                spinnerChangeX1.setBounds(510, 62, 58, 20);
                frame.getContentPane().add(spinnerChangeX1);

                spinnerChangeY1.setModel(new SpinnerNumberModel(-5, -400, 400, 1));
                spinnerChangeY1.setBounds(510, 86, 58, 20);
                frame.getContentPane().add(spinnerChangeY1);
                
                
                spinnerChangeW.setModel(new SpinnerNumberModel(10, -400, 400, 1));
                spinnerChangeW.setBounds(510, 109, 58, 20);
                frame.getContentPane().add(spinnerChangeW);
                
                spinnerChangeH.setModel(new SpinnerNumberModel(10, -400, 400, 1));
                spinnerChangeH.setBounds(510, 133, 58, 20);
                frame.getContentPane().add(spinnerChangeH);
                
                comboBoxCannedFx.setMaximumRowCount(12);
                comboBoxCannedFx.setFont(new Font("Tahoma", Font.PLAIN, 11));
                comboBoxCannedFx.addActionListener(new ActionListener() {
                	public void actionPerformed(ActionEvent arg0) {
                		JComboBox cbTemp = (JComboBox)arg0.getSource();
						if ((String)cbTemp.getSelectedItem() == "5by5-BiggerFromCenter"){
							spinner_H.setValue(5);
							spinner_W.setValue(5);
							spinner_X.setValue(108);
							spinner_Y.setValue(250);
						    spinnerChangeW.setValue(10*Integer.valueOf((String)comboBoxStep.getSelectedItem()));
						    spinnerChangeH.setValue(10*Integer.valueOf((String)comboBoxStep.getSelectedItem()));
							spinnerChangeX.setValue(-5*Integer.valueOf((String)comboBoxStep.getSelectedItem()));
						    spinnerChangeY.setValue(-5*Integer.valueOf((String)comboBoxStep.getSelectedItem()));
						}
						else if ((String)cbTemp.getSelectedItem() == "20by20-LeftCornerShooter"){
							spinner_H.setValue(20);
							spinner_W.setValue(20);
							spinner_X.setValue(34);
							spinner_Y.setValue(403);
						    spinnerChangeW.setValue(0*Integer.valueOf((String)comboBoxStep.getSelectedItem()));
						    spinnerChangeH.setValue(0*Integer.valueOf((String)comboBoxStep.getSelectedItem()));
							spinnerChangeX.setValue(5*Integer.valueOf((String)comboBoxStep.getSelectedItem()));
						    spinnerChangeY.setValue(-8*Integer.valueOf((String)comboBoxStep.getSelectedItem()));
						}
						else if ((String)cbTemp.getSelectedItem() == "20by20-RightCornerShooter"){
							spinner_H.setValue(20);
							spinner_W.setValue(20);
							spinner_X.setValue(170);
							spinner_Y.setValue(403);
						    spinnerChangeW.setValue(0*Integer.valueOf((String)comboBoxStep.getSelectedItem()));
						    spinnerChangeH.setValue(0*Integer.valueOf((String)comboBoxStep.getSelectedItem()));
							spinnerChangeX.setValue(-5*Integer.valueOf((String)comboBoxStep.getSelectedItem()));
						    spinnerChangeY.setValue(-8*Integer.valueOf((String)comboBoxStep.getSelectedItem()));
						}
						else if ((String)cbTemp.getSelectedItem() == "20by20-OutholeShooter"){
							spinner_H.setValue(20);
							spinner_W.setValue(20);
							spinner_X.setValue(100);
							spinner_Y.setValue(458);
						    spinnerChangeW.setValue(0*Integer.valueOf((String)comboBoxStep.getSelectedItem()));
						    spinnerChangeH.setValue(0*Integer.valueOf((String)comboBoxStep.getSelectedItem()));
							spinnerChangeX.setValue(0*Integer.valueOf((String)comboBoxStep.getSelectedItem()));
						    spinnerChangeY.setValue(-10*Integer.valueOf((String)comboBoxStep.getSelectedItem()));
						}
						else if ((String)cbTemp.getSelectedItem() == "20by20-LeftCornerSweep"){
							spinner_H.setValue(20);
							spinner_W.setValue(20);
							spinner_X.setValue(34);
							spinner_Y.setValue(403);
						    spinnerChangeW.setValue(10*Integer.valueOf((String)comboBoxStep.getSelectedItem()));
						    spinnerChangeH.setValue(10*Integer.valueOf((String)comboBoxStep.getSelectedItem()));
							spinnerChangeX.setValue(-5*Integer.valueOf((String)comboBoxStep.getSelectedItem()));
						    spinnerChangeY.setValue(-5*Integer.valueOf((String)comboBoxStep.getSelectedItem()));
						}
						else if ((String)cbTemp.getSelectedItem() == "5by500-LeftRightSweep"){
							spinner_H.setValue(500);
							spinner_W.setValue(5);
							spinner_X.setValue(-10);
							spinner_Y.setValue(-10);
						    spinnerChangeW.setValue(10*Integer.valueOf((String)comboBoxStep.getSelectedItem()));
						    spinnerChangeH.setValue(10*Integer.valueOf((String)comboBoxStep.getSelectedItem()));
							spinnerChangeX.setValue(-5*Integer.valueOf((String)comboBoxStep.getSelectedItem()));
						    spinnerChangeY.setValue(-5*Integer.valueOf((String)comboBoxStep.getSelectedItem()));
						}
						else if ((String)cbTemp.getSelectedItem() == "DanLineTest1"){
							spinner_H.setValue(124);
							spinner_W.setValue(0);
							spinner_X.setValue(-400);
							spinner_Y.setValue(0);
							spinner_X1.setValue(123);
							spinner_Y1.setValue(501);
							spinnerChangeW.setValue(0*Integer.valueOf((String)comboBoxStep.getSelectedItem()));
						    spinnerChangeH.setValue(0*Integer.valueOf((String)comboBoxStep.getSelectedItem()));
							spinnerChangeX.setValue(0*Integer.valueOf((String)comboBoxStep.getSelectedItem()));
						    spinnerChangeY.setValue(-200*Integer.valueOf((String)comboBoxStep.getSelectedItem()));
							spinnerChangeX1.setValue(0*Integer.valueOf((String)comboBoxStep.getSelectedItem()));
						    spinnerChangeY1.setValue(0*Integer.valueOf((String)comboBoxStep.getSelectedItem()));
						}
					
						else if ((String)cbTemp.getSelectedItem() == "DanLineTest2"){
							spinner_H.setValue(100);
							spinner_W.setValue(100);
							spinner_X.setValue(506);
							spinner_Y.setValue(42);
							spinner_X1.setValue(110);
							spinner_Y1.setValue(472);
							spinnerChangeW.setValue(0*Integer.valueOf((String)comboBoxStep.getSelectedItem()));
						    spinnerChangeH.setValue(0*Integer.valueOf((String)comboBoxStep.getSelectedItem()));
							spinnerChangeX.setValue(-12*Integer.valueOf((String)comboBoxStep.getSelectedItem()));
						    spinnerChangeY.setValue(0*Integer.valueOf((String)comboBoxStep.getSelectedItem()));
							spinnerChangeX1.setValue(0*Integer.valueOf((String)comboBoxStep.getSelectedItem()));
						    spinnerChangeY1.setValue(0*Integer.valueOf((String)comboBoxStep.getSelectedItem()));
						}
						else if ((String)cbTemp.getSelectedItem() == "DanLineTest3"){
							spinner_H.setValue(100);
							spinner_W.setValue(100);
							spinner_X.setValue(110);
							spinner_Y.setValue(42);
							spinner_X1.setValue(110);
							spinner_Y1.setValue(472);
							spinnerChangeW.setValue(0*Integer.valueOf((String)comboBoxStep.getSelectedItem()));
						    spinnerChangeH.setValue(0*Integer.valueOf((String)comboBoxStep.getSelectedItem()));
							spinnerChangeX.setValue(-10*Integer.valueOf((String)comboBoxStep.getSelectedItem()));
						    spinnerChangeY.setValue(0*Integer.valueOf((String)comboBoxStep.getSelectedItem()));
							spinnerChangeX1.setValue(0*Integer.valueOf((String)comboBoxStep.getSelectedItem()));
						    spinnerChangeY1.setValue(0*Integer.valueOf((String)comboBoxStep.getSelectedItem()));
						}
						else if ((String)cbTemp.getSelectedItem() == "BuffyLineTest"){
							spinner_H.setValue(100);
							spinner_W.setValue(100);
							spinner_X.setValue(612);
							spinner_Y.setValue(42);
							spinner_X1.setValue(127);
							spinner_Y1.setValue(472);
							spinnerChangeW.setValue(0*Integer.valueOf((String)comboBoxStep.getSelectedItem()));
						    spinnerChangeH.setValue(0*Integer.valueOf((String)comboBoxStep.getSelectedItem()));
							spinnerChangeX.setValue(-14*Integer.valueOf((String)comboBoxStep.getSelectedItem()));
						    spinnerChangeY.setValue(0*Integer.valueOf((String)comboBoxStep.getSelectedItem()));
							spinnerChangeX1.setValue(0*Integer.valueOf((String)comboBoxStep.getSelectedItem()));
						    spinnerChangeY1.setValue(0*Integer.valueOf((String)comboBoxStep.getSelectedItem()));
						}						
						else if ((String)cbTemp.getSelectedItem() == "buffy-BiggerFromCenter"){
							spinner_H.setValue(95);
							spinner_W.setValue(95);
							spinner_X.setValue(75);
							spinner_Y.setValue(236);
						    spinnerChangeW.setValue(8*Integer.valueOf((String)comboBoxStep.getSelectedItem()));
						    spinnerChangeH.setValue(8*Integer.valueOf((String)comboBoxStep.getSelectedItem()));
							spinnerChangeX.setValue(-4*Integer.valueOf((String)comboBoxStep.getSelectedItem()));
						    spinnerChangeY.setValue(-4*Integer.valueOf((String)comboBoxStep.getSelectedItem()));
						}
                	}
                	
                });
                comboBoxCannedFx.setModel(new DefaultComboBoxModel(new String[] {"Pick Fx", "5by5-BiggerFromCenter", "20by20-LeftCornerShooter", "20by20-OutholeShooter", "20by20-RightCornerShooter", "20by20-LeftCornerSweep", "5by500-LeftRightSweep", "DanLineTest1", "DanLineTest2", "DanLineTest3", "BuffyLineTest", "buffy-BiggerFromCenter"}));
                comboBoxCannedFx.setBounds(424, 181, 178, 23);
                frame.getContentPane().add(comboBoxCannedFx);
                
                JLabel lblNewLabel_8 = new JLabel("X1");
                lblNewLabel_8.setFont(new Font("Tahoma", Font.PLAIN, 11));
                lblNewLabel_8.setBounds(424, 243, 46, 14);
                frame.getContentPane().add(lblNewLabel_8);
                
                JLabel lblNewLabel_9 = new JLabel("Y1");
                lblNewLabel_9.setFont(new Font("Tahoma", Font.PLAIN, 11));
                lblNewLabel_9.setBounds(497, 244, 46, 14);
                frame.getContentPane().add(lblNewLabel_9);

                lblChangex.setBounds(440, 65, 63, 14);
                
                frame.getContentPane().add(lblChangex);
                lblChangey.setBounds(440, 88, 63, 14);
                
                frame.getContentPane().add(lblChangey);
                
                
                
                
                chckbxIsSolid.addChangeListener(new ChangeListener() {
                	public void stateChanged(ChangeEvent arg0) {
                		if (!chckbxIsSolid.isSelected()){
                			lblHollowShapeWidth.setEnabled(true);
                			borderThickness.setEnabled(true);
                		}else{
                			lblHollowShapeWidth.setEnabled(false);
                			borderThickness.setEnabled(false);
                		}
                		
                	}
                });
				

				

				
				chckbxStickyShape.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						makeSpinners(true);
					}
				});
				textFieldLampshowName.addTextListener(new TextListener() {
					public void textValueChanged(TextEvent arg0) {
						TextComponent TCPROCLampshow = (TextComponent)arg0.getSource();
						strLampshowName = TCPROCLampshow.getText();
					}
				});
		
		setButtonState(optionTool);
	}
	
	public void performEffect(){
		
		spinner_H.setValue(spinner_H.getValue());
		spinner_W.setValue(spinner_W.getValue());
		spinner_X.setValue(spinner_X.getValue());
		spinner_Y.setValue(spinner_Y.getValue());
		spinner_X1.setValue(spinner_X1.getValue());
		spinner_Y1.setValue(spinner_Y1.getValue());

		EffectsCheckCollision();
		for( int i=0; i < MAX_FRAMES - 1; i++){
			if (i % (Integer) Integer.valueOf((String)comboBoxStep.getSelectedItem()) == 0){
				spinner_H.setValue((Integer) spinner_H.getValue() +((Integer) spinnerChangeH.getValue() ));
				spinner_W.setValue((Integer) spinner_W.getValue() +((Integer) spinnerChangeW.getValue() ));
				spinner_X.setValue((Integer) spinner_X.getValue() +((Integer) spinnerChangeX.getValue() ));
				spinner_Y.setValue((Integer) spinner_Y.getValue() +((Integer) spinnerChangeY.getValue() ));
				spinner_X1.setValue((Integer) spinner_X1.getValue() +((Integer) spinnerChangeX1.getValue() ));
				spinner_Y1.setValue((Integer) spinner_Y1.getValue() +((Integer) spinnerChangeY1.getValue() ));

			}
			spinnerFrame.setValue((Integer)spinnerFrame.getValue() + 1);
			try {
				Thread.sleep(myDelay1, myDelay2);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			EffectsCheckCollision();
			updateLEDSOnFx();
		}
	}
	
	protected void EffectsCheckCollision() {
		// TODO Auto-generated method stub
		int currentState;
		int ledHit;
		clearnewLEDStates();
		for (int i = 0; i <= LED_COUNT - 1; i++) {
			
			if(optionTool == DrawableJLabel.TOOL_RECTANGLE||optionTool == DrawableJLabel.TOOL_ELLIPSE){
				labelPlayfieldOverlay.rectWidth = (Integer) spinner_W.getValue();
				labelPlayfieldOverlay.rectHeight = (Integer) spinner_H.getValue();
				labelPlayfieldOverlay.x1 = (Integer) spinner_X.getValue();
				labelPlayfieldOverlay.y1 = (Integer) spinner_Y.getValue();
				labelPlayfieldOverlay.x = (Integer) spinner_X1.getValue();
				labelPlayfieldOverlay.y = (Integer) spinner_Y1.getValue();
			}
			
//			System.out.println(rectangleStartXMem+" , "+rectangleEndXMem + "   "+
//							   rectangleStartYMem + " , " + rectangleEndYMem + "   "+ 
//							   labelPlayfield.x1 + " , " + labelPlayfield.y1 + "   "+
//							   labelPlayfield.x + " , " + labelPlayfield.y);
			
			ledArray[i].transferFocusDownCycle();
			Rectangle rectangle = ledArray[i].getBounds();
			rectangle.setBounds(ledArray[i].getBounds().x, ledArray[i].getBounds().y, ledArray[i].getBounds().width,
					ledArray[i].getBounds().height);

			currentState = OFF;
			ledHit = OFF;

			if (ledStates[i][frameNum] == '.') {
				currentState = ON;
			}

			if(labelPlayfieldOverlay.option == DrawableJLabel.TOOL_RECTANGLE){
				if (labelPlayfieldOverlay.rect.intersects(rectangle)&&chckbxIsSolid.isSelected()) {
					ledHit = ON;
				}else{   
                    Rectangle2D innerRect = (Rectangle2D) labelPlayfieldOverlay.rect.clone();     
                    iShapeThickness = (Integer) borderThickness.getValue();     
                    innerRect.setRect(labelPlayfieldOverlay.rect.getX() + iShapeThickness, labelPlayfieldOverlay.rect.getY()+iShapeThickness, labelPlayfieldOverlay.rect.getWidth() - (iShapeThickness*2), labelPlayfieldOverlay.rect.getHeight() - (iShapeThickness*2));     

                    Rectangle2D outerRect = (Rectangle2D) labelPlayfield.rect.clone();     
                    outerRect.setRect(labelPlayfieldOverlay.rect.getX() , labelPlayfieldOverlay.rect.getY(), labelPlayfieldOverlay.rect.getWidth() , labelPlayfieldOverlay.rect.getHeight() );     
                                                                                        
                    if (outerRect.intersects(rectangle) && !innerRect.contains(rectangle)) {     
                        ledHit = ON;     
                    }     
                } 
			}else if(labelPlayfieldOverlay.option == DrawableJLabel.TOOL_ELLIPSE){
				if (labelPlayfieldOverlay.ellipse.intersects(rectangle)&&chckbxIsSolid.isSelected()) {
					ledHit = ON;
				}
				else{   
                    Ellipse2D innerEllipse = (Ellipse2D) labelPlayfieldOverlay.ellipse.clone();     
                    iShapeThickness = (Integer) borderThickness.getValue();     
                    innerEllipse.setFrame(labelPlayfieldOverlay.ellipse.getX() + iShapeThickness, labelPlayfieldOverlay.ellipse.getY()+iShapeThickness, labelPlayfieldOverlay.ellipse.getWidth() - (iShapeThickness*2), labelPlayfieldOverlay.ellipse.getHeight() - (iShapeThickness*2));     

                    Ellipse2D outerEllipse = (Ellipse2D) labelPlayfield.ellipse.clone();     
                    outerEllipse.setFrame(labelPlayfieldOverlay.ellipse.getX() -1, labelPlayfieldOverlay.ellipse.getY()-1, labelPlayfieldOverlay.ellipse.getWidth() + 2, labelPlayfieldOverlay.ellipse.getHeight() + 2);     
                                                                                        
                if (outerEllipse.intersects(rectangle) && !innerEllipse.contains(rectangle)) {     
                    ledHit = ON;     
                }     
            } 
				
				if(!chckbxStickyShape.isSelected()){
					setLEDTempState(i, currentState, ledHit, false );
				}
				
				
				
			}else if(labelPlayfieldOverlay.option == DrawableJLabel.TOOL_LINE){
				if (rectangle.intersectsLine(labelPlayfieldOverlay.myLine)) {
					ledHit = ON;
				}
			}
			
			setLEDTempState(i, currentState, ledHit, false);

			labelPlayfieldOverlay.repaint();
		}
	}

	public void setButtonState(byte optionTool){
		
		
		buttonLine.setIcon(new ImageIcon(getClass().getResource("/button/lineTool.png")));
		buttonEllipse.setIcon(new ImageIcon(getClass().getResource("/button/ellipseTool.png")));
		buttonRectangle.setIcon(new ImageIcon(getClass().getResource("/button/rectangleTool.png")));
		buttonSelect.setIcon(new ImageIcon(getClass().getResource("/button/selectTool.png")));
		buttonLedSelect.setIcon(new ImageIcon(getClass().getResource("/button/selectToolLed.png")));
		labelPlayfieldOverlay.setVisible(true);
		
		switch (optionTool) {
		case DrawableJLabel.TOOL_LINE:
			chckbxStickyShape.setEnabled(true);
			buttonLine.setIcon(new ImageIcon(getClass().getResource("/button/lineToolSelected.png")));
			//makeSpinners(true);
			break;
		case DrawableJLabel.TOOL_RECTANGLE:
			chckbxStickyShape.setEnabled(true);
			buttonRectangle.setIcon(new ImageIcon(getClass().getResource("/button/rectangleToolSelected.png")));
			//makeSpinners(true);
			break;
		case DrawableJLabel.TOOL_ELLIPSE:
			chckbxStickyShape.setEnabled(true);
			buttonEllipse.setIcon(new ImageIcon(getClass().getResource("/button/ellipseToolSelected.png")));
			//makeSpinners(true);
			break;
		case DrawableJLabel.TOOL_SELECT:
			chckbxStickyShape.setEnabled(false);
			chckbxStickyShape.setSelected(false);
			buttonSelect.setIcon(new ImageIcon(getClass().getResource("/button/selectToolSelected.png")));
			makeSpinners(false);
			break;
		case DrawableJLabel.TOOL_LED_SELECT:
			labelPlayfieldOverlay.setVisible(false);
			chckbxStickyShape.setEnabled(false);
			chckbxStickyShape.setSelected(false);
			makeSpinners(false);
			buttonLedSelect.setIcon(new ImageIcon(getClass().getResource("/button/selectToolLedSelected.png")));
			break;	
		default:
			buttonSelect.setIcon(new ImageIcon(getClass().getResource("/button/selectTool.png")));
			break;
		}
		
	}
	
	private void makeSpinners(boolean b) {
		// TODO Auto-generated method stub
		
		if (!b)
		{
			spinner_H.setValue(0);
			spinner_W.setValue(0);
			spinner_X.setValue(0);
			spinner_Y.setValue(0);
			spinner_X1.setValue(0);
			spinner_Y1.setValue(0);	
		}
		
		btnSnapshot.setEnabled(b);
		btnPerformFx.setEnabled(b);
		comboBoxStep.setEnabled(b);
		comboBoxCannedFx.setEnabled(b);
		spinner_H.setEnabled(b);
		spinner_W.setEnabled(b);
		spinner_X.setEnabled(b);
		spinner_Y.setEnabled(b);
		spinner_X1.setEnabled(b);
		spinner_Y1.setEnabled(b);
		spinnerChangeH.setEnabled(b);
		spinnerChangeW.setEnabled(b);
		spinnerChangeX.setEnabled(b);
		spinnerChangeX1.setEnabled(b);
		spinnerChangeY.setEnabled(b);
		spinnerChangeY1.setEnabled(b);
		
		
		
	}

	public void copyFrame(){
		
		int frameNum = (Integer) spinnerFrame.getValue();
		
		btnCopyPreviousFrame.setText("Copy "+frameNum);

		if (frameNum >= 0) {
			for (int i = 0; i <= LED_COUNT - 1; i++) {
				frameCopy[i] = ledStates[i][frameNum];
			}

		}
		updateLEDState();
		
	}
	
	public void pasteFrame(){
		
		int frameNum = (Integer) spinnerFrame.getValue();
		
		if(frameNum>=0){
		for (int i = 0; i <= LED_COUNT-1; i++) {
				ledStates[i][frameNum] = frameCopy[i];
			
		}
		
		updateLEDState();
		
		}	
	}
	
	public void copyPreviousFrame(){
		
		int frameNum = (Integer) spinnerFrame.getValue();
		
		if(frameNum>=1){
			
			for (int i = 0; i <= LED_COUNT-1; i++) {
					ledStates[i][frameNum] = ledStates[i][frameNum-1];
				
			}
			
			updateLEDState();
		
		}
				
	}
	
	public void setLEDTempState(int i, int currentState, int ledHit, boolean rightMouseButtonPressed){
		
		int frameNum = (Integer) spinnerFrame.getValue();

		BufferedImage bufferedImage = new BufferedImage(10, 10,	BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = bufferedImage.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,	RenderingHints.VALUE_ANTIALIAS_ON);
		
		if (currentState == OFF && ledHit == ON && !rightMouseButtonPressed) {	
			g.setColor(Color.RED);		
			newLEDStates[i] = '.';
		}else if (currentState == ON && ledHit == ON && rightMouseButtonPressed) {
			if((frameNum-1>=0) && chckbxShowPriorFrames.isSelected()&& ledStates[i][frameNum-1]=='.'){
				g.setColor(Color.MAGENTA);
			}else{
				g.setColor(Color.BLUE);
			}
//			System.out.println("Scenario1-" +Integer.valueOf(i).toString());
			newLEDStates[i] = '-';
		}else{
			if(currentState==ON ){
				g.setColor(Color.RED);
				newLEDStates[i] = '.';
			}else{
				if((frameNum-1>=0) && chckbxShowPriorFrames.isSelected()&& ledStates[i][frameNum-1]=='.'){
					g.setColor(Color.MAGENTA);
					
				}else{
					g.setColor(Color.BLUE);
					
				}
//				System.out.println("Scenario1-" +Integer.valueOf(i).toString());
				
				newLEDStates[i] = '-';
			}
		}

		g.fillOval(0, 0, 10, 10);
		ImageIcon imgOff = new ImageIcon(bufferedImage);
		ledArray[i].setIcon(imgOff);
	}
	
	public void clearnewLEDStates(){
		Arrays.fill(newLEDStates, '-');
//		System.out.println(Arrays.toString(newLEDStates));
	}
	
	
	public void updateLEDSOnFx(){
		int frameNum = (Integer) spinnerFrame.getValue();
		//mySlider.setValue(frameNum);

		for (int i = 0; i <= LED_COUNT-1; i++) {
			if (newLEDStates[i] == '.') {
				ledStates[i][frameNum] = '.';
			}/*else if(newLEDStates[i] == '-' && (ledStates[i][frameNum]=='.')) {
				ledStates[i][frameNum] = '-';
			}*/
		}
	
	}

	
	
	public void updateLEDSOnMouseUp(){
		int frameNum = (Integer) spinnerFrame.getValue();
		//mySlider.setValue(frameNum);

		for (int i = 0; i <= LED_COUNT-1; i++) {
			if (newLEDStates[i] == '.') {
				ledStates[i][frameNum] = '.';
			}else if(newLEDStates[i] == '-' && (ledStates[i][frameNum]=='.')) {
				ledStates[i][frameNum] = '-';
			}
		}
	
	}
	
	
	
	public void generateEffect(){

		BufferedImage bufferedImage = new BufferedImage(11, 11,	BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = bufferedImage.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,	RenderingHints.VALUE_ANTIALIAS_ON);
		
		for (int a = 0 ; a <= 3; a++){
			
			if(labelPlayfield.y>=8&&labelPlayfield.x<=9){
				labelPlayfield.y-=5;
			}else if(labelPlayfield.y<=8&&labelPlayfield.x<=200){
				labelPlayfield.x+=3;
			}else if(labelPlayfield.y<=sweepEnd.getY()&&labelPlayfield.x>=sweepEnd.getX()){
				labelPlayfield.y+=5;
			}else if(labelPlayfield.y>=sweepEnd.getY()){
				sweepAnchor.setLocation(999, 0);
				sweepStart.setLocation(999, 0);
				sweepEnd.setLocation(999, 0);
			}
					
		for (int i = 0; i <= MAX_FRAMES; i++) {
			Rectangle rectangle = ledArray[i].getBounds();
			rectangle.setBounds(ledArray[i].getBounds().x - 9, ledArray[i].getBounds().y - 8, ledArray[i].getBounds().width, ledArray[i].getBounds().height);

				if (rectangle.intersectsLine(labelPlayfield.myLine)) {
					if (ledStates[i][frameNum] == '-') {
						g.setColor(Color.RED);
						ledStates[i][frameNum] = '.';
					}
				} else {
					if (ledStates[i][frameNum] == '.') {	
						ledStates[i][frameNum] = '-';
					}
						g.setColor(Color.BLUE);
				}
				
				g.fillOval(0, 0, 10, 10);
				ImageIcon imgOff = new ImageIcon(bufferedImage);
				ledArray[i].setIcon(imgOff);
				labelPlayfield.repaint();
				updateLEDState();
		}
		
		spinnerFrame.setValue(frameNum);
		mySlider.setValue(frameNum);

		
		labelPlayfield.repaint();
	
		}
		
		if(frameNum==MAX_FRAMES-1){
			frameNum=0;
		}else{
			frameNum++;
		}
	}
	
	public void toolUsed(int i, boolean me){
		int frameNum = (Integer) spinnerFrame.getValue();
		//mySlider.setValue(frameNum);

		if (me) {
			if (ledStates[i][frameNum] == '-') {

				ledStates[i][frameNum] = '.';

			}
		} else {
			ledStates[i][frameNum] = '-';
		}
		updateLEDState();
	
	}
	
	private void showPriorFrame(){

	    int frameNum = (Integer) spinnerFrame.getValue();
		//mySlider.setValue(frameNum);

   
	    for(int i=0;i<=LED_COUNT-1;i++){
		    BufferedImage bufferedImage = new BufferedImage(11,11,BufferedImage.TYPE_INT_ARGB);
		    Graphics2D g = bufferedImage.createGraphics();
		    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		    
			if((frameNum-1>=0)&&ledStates[i][frameNum-1]=='.'&&ledStates[i][frameNum]!='.'){
				g.setColor(Color.MAGENTA);
			}else if(ledStates[i][frameNum]=='.'){
				g.setColor(Color.RED);
			}else{
				g.setColor(Color.BLUE);
			}
		    
			g.fillOval(0, 0, 10, 10);
			g.drawImage(ledGrid.getImg(), 100, 0, null);
			ImageIcon imgOff = new ImageIcon(bufferedImage);
			ledArray[i].setIcon(imgOff);
	    }
	}
	
	private void updateLEDState(){
				
	    int frameNum = (Integer) spinnerFrame.getValue();
		mySlider.setValue(frameNum);
		
	    
	    if(frameNum<0){
	    	frameNum = MAX_FRAMES-1;
	    	spinnerFrame.setValue(MAX_FRAMES-1);
			mySlider.setValue(MAX_FRAMES-1);

	    }else if(frameNum>=MAX_FRAMES){
	    	frameNum = 0;
	    	spinnerFrame.setValue(0);
	    	mySlider.setValue(0);
	    }
	    
	    
	    
	    for(int i=0;i<=LED_COUNT-1;i++){
		    BufferedImage bufferedImage = new BufferedImage(11,11,BufferedImage.TYPE_INT_ARGB);
		    Graphics2D g = bufferedImage.createGraphics();
		    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		    
			if(ledStates[i][frameNum]=='.'){
				g.setColor(Color.RED);
			}else{
				g.setColor(Color.BLUE);
			}
		    
			g.fillOval(0, 0, 10, 10);
			ImageIcon imgOff = new ImageIcon(bufferedImage);
			ledArray[i].setIcon(imgOff);
	    }
	    
	    if(chckbxShowPriorFrames.isSelected()){
	    	showPriorFrame();
	    }
		
	}
	
	private void clearAllLEDs(){
		for(int i=0;i<=LED_COUNT-1;i++){
			for(int j=0;j<=MAX_FRAMES-1;j++){
				ledStates[i][j]='-';
			}
		}
		clearnewLEDStates();
		updateLEDState();
	}
	
	private void clearCurrentFrame(){
		for (int i = 0; i <= MAX_FRAMES-1; i++) {
			int frameNum = (Integer) spinnerFrame.getValue();
			ledStates[i][frameNum] = '-';
		}
		updateLEDState();
	}
	
	private void turnLEDOn(JLabel jLabel){
		changeLEDState(jLabel, true);
	}
	
	private void turnLEDOff(JLabel jLabel){
		changeLEDState(jLabel, false);
	}
	
	private void changeLEDState(JLabel jLabel, boolean ledState){
	    BufferedImage bufferedImage = new BufferedImage(11,11,BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g = bufferedImage.createGraphics();
	    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	    int ledNum=Integer.parseInt(jLabel.getToolTipText());
	    int frameNum = (Integer) spinnerFrame.getValue();
	    //mySlider.setValue(frameNum);
	    
	    if(ledState){
	    	g.setColor(Color.RED);
	    	ledStates[ledNum][frameNum]='.';
	    }else{
	    	g.setColor(Color.BLUE);
	    	ledStates[ledNum][frameNum]='-';
	    }
	    
		g.fillOval(0, 0, 10, 10);
		
		ImageIcon imgOff = new ImageIcon(bufferedImage);
		
		jLabel.setIcon(imgOff);
		
	}
	
	private void buildLEDArray(){
				
		for(int i=0;i<=LED_COUNT;i++){
			ledArray[i]=new JLabel();
			ledArray[i].setToolTipText(Integer.toString(i));
			ledArray[i].setBounds(0, 0, 10, 10);
			frame.getContentPane().add(ledArray[i]);
		}
		
	}
	
	private void initSavedState(){
		
		if(prop.isEmpty()){
			loadMapFile();
		}
		
		for (int i = 0; i <= LED_COUNT-1; i++) {
			String integers = prop.getProperty("led_"+Integer.toString(i));
			String[] xy = integers.split("\\s");
			ledStates[i] = xy[2].toCharArray();

			ledArray[i].setLocation(Integer.parseInt(xy[0]),Integer.parseInt(xy[1]));
			BufferedImage bufferedImage = new BufferedImage(10, 10,	BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = bufferedImage.createGraphics();
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,	RenderingHints.VALUE_ANTIALIAS_ON);
			
			if(ledStates[i][0]=='.'){
				g.setColor(Color.RED);
			}else{
				g.setColor(Color.BLUE);
			}
			
			g.fillOval(0, 0, 10, 10);
			ImageIcon imgOff = new ImageIcon(bufferedImage);
			ledArray[i].setIcon(imgOff);

		}

	}
	
	private static String reverseIt(String source) {
	    int i, len = source.length();
	    StringBuilder dest = new StringBuilder(len);

	    for (i = (len - 1); i >= 0; i--){
	        dest.append(source.charAt(i));
	    }

	    return dest.toString();
	}

	private static String inverseIt(String source) {

		String strTemp1 = new String();

		strTemp1 = source.replace("-", "~");
		strTemp1 = strTemp1.replace(".", " ");
		strTemp1 = strTemp1.replace("~", ".");

	    return strTemp1;
	}	

	private void saveCurrentState(){
		saveCurrentState("");
	}

	
	private void saveCurrentState(String strTimeStamp){
		
		try {
			BufferedReader br = new BufferedReader(new FileReader("c:\\temp\\blanktempate" +String.valueOf(MAX_FRAMES) +".lampshow"));
			StringBuilder sb = new StringBuilder();
			
			PrintWriter out = new PrintWriter("c:\\temp\\ledmap.txt");

			PrintWriter outbak = new PrintWriter("c:\\temp\\" +strTimeStamp +strLampshowName +"ledmap.txt");
			PrintWriter outinverse = new PrintWriter("c:\\temp\\" +strTimeStamp +strLampshowName +"ledmap_inverse.txt");
			PrintWriter outreverse = new PrintWriter("c:\\temp\\" +strTimeStamp +strLampshowName +"ledmap_reverse.txt");
			PrintWriter outreverseinverse = new PrintWriter("c:\\temp\\" +strTimeStamp +strLampshowName +"ledmap_reverse_inverse.txt");

			PrintWriter procout = new PrintWriter("c:\\temp\\" +strTimeStamp +strLampshowName +".lampshow");
			PrintWriter procoutinverse = new PrintWriter("c:\\temp\\" +strTimeStamp +strLampshowName +"_inverse.lampshow");
			PrintWriter procoutreverse = new PrintWriter("c:\\temp\\" +strTimeStamp +strLampshowName +"_reverse.lampshow");
			PrintWriter procoutreverseinverse = new PrintWriter("c:\\temp\\" +strTimeStamp +strLampshowName +"_reverse_inverse.lampshow");

			String strLine = "";
			//PROC HEADER STUFF...
			for(int i=0;i<=6;i++){
				strLine = br.readLine();
				sb.append(strLine);
				procout.println(strLine);
				procoutinverse.println(strLine);
				procoutreverse.println(strLine);
				procoutreverseinverse.println(strLine);
			}

			for(int i=0;i<=LED_COUNT-1;i++){
				
				String strTemp = "";
				strTemp = br.readLine();

				strLine = strTemp +String.valueOf(ledStates[i]).substring(0, MAX_FRAMES);
				sb.append(strLine);
				procout.println(strLine.replace("-", " "));

				strLine = strTemp +inverseIt(String.valueOf(ledStates[i]).substring(0, MAX_FRAMES));
				sb.append(strLine);
				procoutinverse.println(strLine.replace("-", " "));

				strLine = strTemp +reverseIt(String.valueOf(ledStates[i]).substring(0, MAX_FRAMES));
				sb.append(strLine);
				procoutreverse.println(strLine.replace("-", " "));

				strLine = strTemp +inverseIt(reverseIt(String.valueOf(ledStates[i]).substring(0, MAX_FRAMES)));
				sb.append(strLine);
				procoutreverseinverse.println(strLine.replace("-", " "));
				
				out.println(((i < 10)?" led_":"led_") +Integer.toString(i)+" = " +((ledArray[i].getX() < 100)?"0":"")  +Integer.toString(ledArray[i].getX()) +" " +((ledArray[i].getY() < 100)?"0":"") +Integer.toString(ledArray[i].getY())+" "+String.valueOf(ledStates[i]));
				outbak.println(((i < 10)?" led_":"led_") +Integer.toString(i)+" = " +((ledArray[i].getX() < 100)?"0":"")  +Integer.toString(ledArray[i].getX()) +" " +((ledArray[i].getY() < 100)?"0":"") +Integer.toString(ledArray[i].getY())+" "+String.valueOf(ledStates[i]));
				outinverse.println(((i < 10)?" led_":"led_") +Integer.toString(i)+" = " +((ledArray[i].getX() < 100)?"0":"")  +Integer.toString(ledArray[i].getX()) +" " +((ledArray[i].getY() < 100)?"0":"") +Integer.toString(ledArray[i].getY())+" "+inverseIt(String.valueOf(ledStates[i])));
				outreverse.println(((i < 10)?" led_":"led_") +Integer.toString(i)+" = " +((ledArray[i].getX() < 100)?"0":"")  +Integer.toString(ledArray[i].getX()) +" " +((ledArray[i].getY() < 100)?"0":"") +Integer.toString(ledArray[i].getY())+" "+reverseIt(String.valueOf(ledStates[i])));
				outreverseinverse.println(((i < 10)?" led_":"led_") +Integer.toString(i)+" = " +((ledArray[i].getX() < 100)?"0":"")  +Integer.toString(ledArray[i].getX()) +" " +((ledArray[i].getY() < 100)?"0":"") +Integer.toString(ledArray[i].getY())+" "+inverseIt(reverseIt(String.valueOf(ledStates[i]))));
			}

			procout.flush();
			procout.close();
			procoutreverse.flush();
			procoutreverse.close();
			procoutinverse.flush();
			procoutinverse.close();
			procoutreverseinverse.flush();
			procoutreverseinverse.close();
			
			out.flush();
			out.close();
			outbak.flush();
			outbak.close();
			outinverse.flush();
			outinverse.close();
			outreverse.flush();
			outreverse.close();
			outreverseinverse.flush();
			outreverseinverse.close();
			br.close();
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void createNewLEDMapFile(){
		
		try {
			PrintWriter out = new PrintWriter("c:\\temp\\ledmap.txt");
			int x=255, y=158;
			
			for(int i=0;i<=LED_COUNT;i++){
				out.print("led_"+Integer.toString(i)+" = "+Integer.toString(x)+" "+Integer.toString(y));
				out.println(" ----------------------------------------------------------------");
				x+=11;
				if(x>275){
					x=255;
					y+=11;
				}
			}
			
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private void initialize() {
		
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		} catch (UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}
		
		frame = new DrawableFrame();

		frame.getContentPane().addMouseWheelListener(new MouseWheelListener() {
			public void mouseWheelMoved(MouseWheelEvent mw) {
				if(mw.getWheelRotation()==-1){
					spinnerFrame.setValue((Integer)spinnerFrame.getValue() + 1);
				}else if(mw.getWheelRotation()==1){
					spinnerFrame.setValue((Integer)spinnerFrame.getValue() - 1);
				}
				
			}
		});
		
		frame.getContentPane().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent me) {
				
			}
			@Override
			public void mousePressed(MouseEvent e) {
			}
		});
		frame.getContentPane().addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent me) {
			}
		});
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {
				//get the time stamp
				String timestamp = new Timestamp(System.currentTimeMillis()).toString();
				//remove the seconds part
				timestamp = timestamp.replaceAll("-","");
				timestamp = timestamp.replaceAll(":","");
				timestamp = timestamp.replaceAll(" ","");
				timestamp = timestamp.replaceAll("\\.","");
				
				saveCurrentState(timestamp);
			}
		});
		
		
		
		frame.setBounds(200, 200, 657, 742);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("QDLS");
		frame.setVisible(true);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);
		
		labelPlayfieldOverlay.addMouseWheelListener(new MouseWheelListener() {
			public void mouseWheelMoved(MouseWheelEvent mw) {
				if(mw.getWheelRotation()==-1){
					spinnerFrame.setValue((Integer)spinnerFrame.getValue() + 1);
				}else if(mw.getWheelRotation()==1){
					spinnerFrame.setValue((Integer)spinnerFrame.getValue() - 1);
				}
				
			}
		});
		
		
		frame.getContentPane().add(labelPlayfieldOverlay);
//		layeredPane.add(labelPlayfieldOverlay);
		labelPlayfieldOverlay.setBounds(0, 0, 240, 498);

		btnRun.setBounds(287, 595, 126, 23);
		frame.getContentPane().add(btnRun);
		loadMapFile();
		
		buildLEDArray();
		initSavedState();
		chckbxVisable.setSelected(true);
		
		btnRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(task!=null){
					task.cancel();
					task=null;
					btnRun.setText("Play");
				}else{
					btnRun.setText("Stop");
				task = new TimerTask() {
					public void run() {
						
						int nextFrame = (Integer) spinnerFrame.getValue();
						
						if(nextFrame>MAX_FRAMES-2){
							nextFrame=0;
						}
						spinnerFrame.setValue(nextFrame+1);
						mySlider.setValue(nextFrame+1);
						updateLEDState();
						
						try {
							Thread.sleep(myDelay1, myDelay2);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					
					}
				};
				
				Timer timer = new Timer();
				timer.schedule(task, 0, 1);//15625
				}
			}
		});
		
		for(int i=0;i<=LED_COUNT;i++){
			addLED(i);
		}
		

	}
	
	
	private void loadMapFile(){
		try {
			prop.load(new FileInputStream("c:\\temp\\ledmap.txt"));
			
		} catch (FileNotFoundException e) {
			createNewLEDMapFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void addLED(final int i){
		ledArray[i].addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent me) {
				
				int x = me.getPoint().x	+ (ledArray[i].getX() - ledArray[i].getWidth() / 2);
				int y = me.getPoint().y	+ (ledArray[i].getY() - ledArray[i].getHeight() / 2);
				ledArray[i].setLocation(x, y);

			}
		});
		
		ledArray[i].addMouseListener(new MouseAdapter() {
			
			public void mouseClicked(MouseEvent arg0) {

				int frameNum = (Integer) spinnerFrame.getValue();
				
				if(ledStates[i][frameNum]=='-'){
					turnLEDOn(ledArray[i]);
					ledState=false;
				}else{
					turnLEDOff(ledArray[i]);
					ledState=true;
				}
			}
			
		});
	}
}
