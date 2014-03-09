package src;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.text.ParseException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.MouseInputListener;
import javax.swing.text.MaskFormatter;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;


public class Main extends JFrame {

	private JPanel contentPane;
	private String shape;
	private String nop;
	private int xs;
	private int ys;
	private int opn;
	private MyShape focused ;
	private ArrayList<MyShape> d;
	private boolean select;
	private final JColorChooser chooser;
	private Color filler;
	private parameters old;
	private URLClassLoader sys;
	public static  URLClassLoader plug;
	private URLClassLoader main;
	private URL[] plugs;
	private int pluginNum;

	

	/**
	 * Launch the application.
	 */
	
	class PaintPanel extends JPanel implements MouseInputListener{
		
		
		
		public PaintPanel(){
			setBackground(Color.WHITE);
			d = new ArrayList<MyShape>();
			setFocusable(true);
			addMouseListener(this);
			addMouseMotionListener(this);
			setPreferredSize(new Dimension(20000,20000));
			select = false;
			setLayout(null);
			filler = Color.BLACK;
			opn=-1;
			
		}
		
		@Override
		public void paintComponent(Graphics g) {
			// TODO Auto-generated method stub
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D)(g); 
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
			MyShape f ;
			for(int i = 0;i<d.size();i++)
			{
				if(!engine.isDeleted.get(i))
				{		
					f = d.get(i);
					f.draw(g2);
				}
					
			}
		}
		
		
		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			if(focused!=null)
				focused.setfocused(false);
				focused = null;
			if(select)
				for(int i = d.size()-1;i>=0;i--)
			{
				MyShape f = d.get(i);
				if(f.checkIntersect(e.getX(), e.getY()))
				{
					focused = f;
					focused.setfocused(true);
					break;
				}
			}
			repaint();
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			xs = e.getX();
			ys = e.getY();
			if(!select)
			{
				if(focused!= null)
					focused.setfocused(false);
				
				Class c = null;
				try {
					c = Class.forName("src."+shape, true, main);
				} catch (ClassNotFoundException e2) {
					// TODO Auto-generated catch block
//					e2.printStackTrace();
				}
				try {
					if(c!=null)
					{
						focused = (MyShape)c.newInstance();
						focused.setParameters(nop+xs+" "+ys);
						focused.changeColor(filler);
						opn = 0;
						d.add(focused);
						engine.isDeleted.add(false);
						engine.objects.add(focused);
					}
					else {
						if(focused!=null)
							focused.setfocused(false);
						focused = null;
					}
					} catch (InstantiationException e1) {
					// TODO Auto-generated catch block
//					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					// TODO Auto-generated catch block
//					e1.printStackTrace();
				}
			}else
			{
				if(focused!=null){
				 old = focused.getParameters();
				
				}
			}
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			xs = e.getX();
			ys = e.getY();
			switch (opn) {
				case 0:
					engine.add(focused);
					break;
				case 1: 
					engine.delete(d.indexOf(focused)) ;
					break;
				case 2:
					engine.other(d.indexOf(focused), opn, old);
					break;
			}			
			opn =-1;
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub
			if(focused!=null)	
				 if(focused.checkpoints(xs, ys))
				 {
					focused.reSize(e.getX(), e.getY(),focused.getwhichpoint(xs, ys));
					if(select)
					opn = 2;
					repaint();
					xs = e.getX();
					ys = e.getY();
				}
				
				else if(focused.checkIntersect(xs, ys))
				{
					focused.move(e.getX(), e.getY(), xs, ys);
					if(select)
					opn = 2;
					repaint();
					xs = e.getX();
					ys = e.getY();
				}
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
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
		setTitle("UpaintO");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 1366, 730);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(51, 51, 51));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		pluginNum = 0;
		plugs = new URL[100];
		sys = (URLClassLoader) ClassLoader.getSystemClassLoader();
		plug = new URLClassLoader(plugs);
		main = sys;
		
		final JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setForeground(Color.LIGHT_GRAY);
		layeredPane.setBackground(Color.LIGHT_GRAY);
		layeredPane.setBounds(0, 0, 1350, 692);
		contentPane.add(layeredPane);
		layeredPane.setLayout(null);
		
		PaintPanel panel = new PaintPanel();
		panel.setBounds(81, 25, 1266, 488);
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED));
		panel.setAutoscrolls(true);	
		layeredPane.add(panel);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		menuBar.setForeground(new Color(0, 0, 0));
		menuBar.setBackground(SystemColor.activeCaptionBorder);
		menuBar.setBounds(0, 0, 1350, 26);
		layeredPane.add(menuBar);
		
		JMenu mnNewFile = new JMenu("        File...");
		mnNewFile.setHorizontalAlignment(SwingConstants.CENTER);
		mnNewFile.setPreferredSize(new Dimension(80, 22));
		mnNewFile.setHorizontalTextPosition(SwingConstants.CENTER);
		mnNewFile.setForeground(Color.BLACK);
		menuBar.add(mnNewFile);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("New File");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				d.clear();
				engine.action.clear();
				engine.redo.clear();
				engine.objects.clear();
				engine.isDeleted.clear();
				if(focused != null){
					focused.setfocused(false);
					focused = null;
				}
				repaint();
			}
		});
		mnNewFile.add(mntmNewMenuItem);
		
		JMenu mnNewMenu = new JMenu("      Load");
		mnNewMenu.setForeground(new Color(0, 0, 0));
		mnNewMenu.setHorizontalTextPosition(SwingConstants.LEADING);
		mnNewMenu.setPreferredSize(new Dimension(80, 22));
		mnNewMenu.setBorder(null);
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmLoadJason = new JMenuItem("Load JSON");
		mntmLoadJason.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
					FileDialog fd = new FileDialog(new Shell(), SWT.OPEN);
					fd.setFilterNames(new String[]{"JSON Files"});
					fd.setFilterExtensions(new String[]{"*.json"});
					String name = fd.open();
					if (name != null){
						try {
							d.clear();
							engine.action.clear();
							engine.redo.clear();
							engine.objects.clear();
							engine.isDeleted.clear();
							
							JSON_READER.load(new File(name));
							
							for(int i=0;i<engine.objects.size();i++)
							{
								engine.objects.get(i).setfocused(false);
								d.add(engine.objects.get(i));
							}
							select = false;
							if(focused != null){
								focused.setfocused(false);
								focused = null;
							}
							repaint();
							
							
							
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (InstantiationException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
			}
		});
		mntmLoadJason.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		mntmLoadJason.setBorderPainted(true);
		mnNewMenu.add(mntmLoadJason);
		
		JMenuItem mntmLoadXml = new JMenuItem("Load XML");
		mntmLoadXml.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FileDialog fd = new FileDialog(new Shell(), SWT.OPEN);
				fd.setFilterNames(new String[]{"XML Files"});
				fd.setFilterExtensions(new String[]{"*.xml"});
				String name = fd.open();
				if (name != null)
				{
					d.clear();
					engine.action.clear();
					engine.redo.clear();
					engine.objects.clear();
					engine.isDeleted.clear();
					ReadXMLFile.read(name);
				
					for(int i=0;i<engine.objects.size();i++)
					{
						engine.objects.get(i).setfocused(false);
						d.add(engine.objects.get(i));
					}
					select = false;					
					if(focused != null){
						focused.setfocused(false);
						focused = null;
					}
					repaint();
				}
			}
		});
		mntmLoadXml.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		mntmLoadXml.setBorderPainted(true);
		mnNewMenu.add(mntmLoadXml);
		
		JMenu mnSave = new JMenu("      Save");
		mnSave.setForeground(new Color(0, 0, 0));
		mnSave.setBorder(null);
		mnSave.setPreferredSize(new Dimension(80, 22));
		mnSave.setHorizontalTextPosition(SwingConstants.LEADING);
		menuBar.add(mnSave);
		
		JMenuItem mntmAsJson = new JMenuItem("As JSON");
		mntmAsJson.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FileDialog fd = new FileDialog(new Shell(), SWT.SAVE);
				fd.setFilterNames(new String[]{"JSON Files"});
				fd.setFilterExtensions(new String[]{"*.json"});
				String name = fd.open();
				
				if(name!=null)
				{
					try {
						JSONSave.save(name);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				
			}
		});
		mntmAsJson.setBorderPainted(true);
		mntmAsJson.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		mnSave.add(mntmAsJson);
		
		JMenuItem mntmAsXml = new JMenuItem("As XML");
		mntmAsXml.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FileDialog fd = new FileDialog(new Shell(), SWT.SAVE);
				fd.setFilterNames(new String[]{"XML Files"});
				fd.setFilterExtensions(new String[]{"*.xml"});
				String name = fd.open();
				
				if(name!=null){
					try {
						Saves.SavesXML(name);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		mntmAsXml.setBorderPainted(true);
		mntmAsXml.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		mnSave.add(mntmAsXml);
		
		
		chooser = new JColorChooser();
		chooser.setBounds(911, 518, 439, 174);
		chooser.getSelectionModel().addChangeListener( new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				filler = chooser.getColor();
				
			}
		});
		
		chooser.setPreviewPanel(new JPanel());
		
		layeredPane.add(chooser);
		
		JButton btnNewButton = new JButton("");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				shape = "MySquare";
				nop = "4 ";
			}
		});
		btnNewButton.setIcon(new ImageIcon(Main.class.getResource("/src/Icons/square2.png")));
		btnNewButton.setBackground(Color.DARK_GRAY);
		btnNewButton.setBorder(new BevelBorder(BevelBorder.RAISED));
		btnNewButton.setBounds(0, 25, 79, 69);
		layeredPane.add(btnNewButton);
		
		JButton button = new JButton("");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				shape = "MyCircle";
				nop = "4 ";
			}
		});
		button.setIcon(new ImageIcon(Main.class.getResource("/src/Icons/Circle2.png")));
		button.setBackground(Color.DARK_GRAY);
		button.setBorder(new BevelBorder(BevelBorder.RAISED));
		button.setBounds(0, 94, 79, 69);
		layeredPane.add(button);
		
		JButton button_1 = new JButton("");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				shape = "MyEllipse";
				nop = "4 ";
			}
		});
		button_1.setIcon(new ImageIcon(Main.class.getResource("/src/Icons/ellipse.png")));
		button_1.setBackground(Color.DARK_GRAY);
		button_1.setBorder(new BevelBorder(BevelBorder.RAISED));
		button_1.setBounds(0, 162, 79, 69);
		layeredPane.add(button_1);
		
		JButton button_2 = new JButton("");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				shape = "MyRectangle";
				nop = "4 ";
			}
		});
		button_2.setIcon(new ImageIcon(Main.class.getResource("/src/Icons/rect.png")));
		button_2.setBackground(Color.DARK_GRAY);
		button_2.setBorder(new BevelBorder(BevelBorder.RAISED));
		button_2.setBounds(0, 230, 79, 69);
		layeredPane.add(button_2);
		
		JButton button_3 = new JButton("");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				shape = "MyLine";
				nop = "";
			}
		});
		button_3.setIcon(new ImageIcon(Main.class.getResource("/src/Icons/Line.png")));
		button_3.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		button_3.setBackground(Color.DARK_GRAY);
		button_3.setBounds(0, 300, 79, 69);
		layeredPane.add(button_3);
		
		MaskFormatter nums = null;
		try {
			 nums = new MaskFormatter("#");
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		final JFormattedTextField fText;
		fText = new JFormattedTextField(nums);
		fText.setFont(new Font("Tahoma", Font.BOLD, 14));
		fText.setHorizontalAlignment(SwingConstants.CENTER);
		fText.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		fText.setBounds(0, 438, 79, 20);
		layeredPane.add(fText);
		
		JButton button_4 = new JButton("");
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				shape = "MyPolygon";
				String numsOfPoints = "";
				numsOfPoints = fText.getText();
				try{
					int pnts = Integer.parseInt(numsOfPoints);
					if(pnts<4){
						nop = "7 ";
					}else{
						nop = numsOfPoints+" ";
					}
				}catch (Exception e) {
					// TODO: handle exception
					nop = "7 ";
				}
			}
		});
		button_4.setIcon(new ImageIcon(Main.class.getResource("/src/Icons/polygon.png")));
		button_4.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		button_4.setBackground(Color.DARK_GRAY);
		button_4.setBounds(0, 369, 79, 69);
		layeredPane.add(button_4);
		
		final JButton adder = new JButton("");
		adder.setIcon(new ImageIcon(Main.class.getResource("/src/Icons/plus.png")));
		
		adder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FileDialog fd = new FileDialog(new Shell(), SWT.OPEN);
				fd.setFilterNames(new String[]{"JAR Files"});
				fd.setFilterExtensions(new String[]{"*.jar"});
				final String name = fd.open();
				String classname="";
				if (name != null){
					try {
						URL beso = new URL("file:///"+name.replace('\\', '/'));
						plugs[pluginNum] = beso;
						plug = new URLClassLoader(plugs);
						classname =name;
						classname=classname.substring(classname.lastIndexOf('\\') + 1, classname.indexOf('.'));
						boolean found=false;
						for(int i=0;i<engine.imports.size();i++){
							if(classname.equals(engine.imports.get(i)))
							{

								found=true;
								return;
							}
								}		
					if(!found){
						engine.imports.add(classname);
						pluginNum++;
		
					}
					}catch (MalformedURLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					JButton pluginBtn = new JButton(classname);
					pluginBtn.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent arg0) {
							// TODO Auto-generated method stub
							String jarName = name.substring(name.lastIndexOf('\\')+1, name.indexOf('.'));
							try {
								Class c = Class.forName("src."+jarName, true, plug);
								
								shape = jarName;
								try {
									try {
										nop = ""+c.getMethod("NumOfPoints", null).invoke(c.newInstance());
										main = plug;
									} catch (IllegalArgumentException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} catch (IllegalAccessException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} catch (InvocationTargetException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} catch (InstantiationException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								} catch (SecurityException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (NoSuchMethodException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								
							} catch (ClassNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
						}
					});
					pluginBtn.setBackground(Color.DARK_GRAY);
					pluginBtn.setForeground(Color.WHITE);
					pluginBtn.setBorder(new BevelBorder(BevelBorder.RAISED));
					pluginBtn.setFont(new Font("Tahoma", Font.BOLD, 14));
					int y = adder.getY();
					pluginBtn.setBounds(0, y, 79, 69);
					layeredPane.add(pluginBtn);
					adder.setBounds(0, y+68, 79, 26);
				}
			}
		});
		adder.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		adder.setBackground(Color.DARK_GRAY);
		adder.setBounds(0, 462, 79, 26);
		layeredPane.add(adder);
		
		JButton btnNewButton_2 = new JButton("");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(focused != null){
					old = focused.getParameters();
					focused.changeColor(filler);
					engine.other(d.indexOf(focused), 4, old);
					repaint();
				}
			}
		});
		btnNewButton_2.setIcon(new ImageIcon(Main.class.getResource("/src/Icons/fil2l.png")));
		btnNewButton_2.setBackground(Color.DARK_GRAY);
		btnNewButton_2.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnNewButton_2.setBounds(830, 548, 71, 61);
		layeredPane.add(btnNewButton_2);
		
		JButton button_5 = new JButton("");
		button_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(focused!=null)
				{
					focused.setfocused(false);
					int n= d.indexOf(focused);
					engine.delete(n);
					focused=null;
					repaint();
				}
			}
		});
		button_5.setIcon(new ImageIcon(Main.class.getResource("/src/Icons/DeleteRed2.png")));
		button_5.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		button_5.setBackground(Color.DARK_GRAY);
		button_5.setBounds(830, 620, 71, 61);
		layeredPane.add(button_5);
		
		JButton btnRedo = new JButton("");
		btnRedo.setIcon(new ImageIcon(Main.class.getResource("/src/Icons/Redo-icon.png")));
		btnRedo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				UNDO_REDO.redo();
				repaint();
			}
		});
		btnRedo.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnRedo.setBackground(Color.DARK_GRAY);
		btnRedo.setBounds(749, 548, 71, 61);
		layeredPane.add(btnRedo);
		
		JButton btnUndo = new JButton("");
		btnUndo.setIcon(new ImageIcon(Main.class.getResource("/src/Icons/Undo-icon.png")));
		btnUndo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				UNDO_REDO.undo();
				if(focused!=null){
				focused.setfocused(false);
				focused = null;
				}
				repaint();
			}
		});
		btnUndo.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnUndo.setBackground(Color.DARK_GRAY);
		btnUndo.setBounds(749, 620, 71, 61);
		layeredPane.add(btnUndo);
		
		JButton zoomIn = new JButton("");
		zoomIn.setIcon(new ImageIcon(Main.class.getResource("/src/Icons/Zoom-In-icon.png")));
		zoomIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Zoom.zoomDegree++;
				if(Zoom.zoomDegree>3)
				{
					Zoom.zoomDegree = 3;
				}
				repaint();
			}
		});
		zoomIn.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		zoomIn.setBackground(Color.DARK_GRAY);
		zoomIn.setBounds(668, 548, 71, 61);
		layeredPane.add(zoomIn);
		
		JButton zoomOut = new JButton("");
		zoomOut.setIcon(new ImageIcon(Main.class.getResource("/src/Icons/Zoom-Out-icon.png")));
		zoomOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Zoom.zoomDegree--;
				if(Zoom.zoomDegree<0)
				{
					Zoom.zoomDegree = 0;
				}
				repaint();
			}
		});
		zoomOut.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		zoomOut.setBackground(Color.DARK_GRAY);
		zoomOut.setBounds(668, 620, 71, 61);
		layeredPane.add(zoomOut);
		
		JButton button_6 = new JButton("");
		button_6.setIcon(new ImageIcon(Main.class.getResource("/src/Icons/arrow_left.png")));
		button_6.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		button_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Zoom.screenX = Zoom.screenX-50;
				repaint();
			}
		});
		button_6.setBackground(Color.DARK_GRAY);
		button_6.setBounds(346, 562, 71, 61);
		layeredPane.add(button_6);
		
		JButton button_7 = new JButton("");
		button_7.setIcon(new ImageIcon(Main.class.getResource("/src/Icons/arrow_right.png")));
		button_7.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		button_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Zoom.screenX = Zoom.screenX+50;
				repaint();
			}
		});
		button_7.setBackground(Color.DARK_GRAY);
		button_7.setBounds(505, 562, 71, 61);
		layeredPane.add(button_7);
		
		JButton button_8 = new JButton("");
		button_8.setFocusable(false);
		button_8.setIcon(new ImageIcon(Main.class.getResource("/src/Icons/arrow_up.png")));
		button_8.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		button_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Zoom.screenY = Zoom.screenY-50;
				repaint();
			}
		});
		button_8.setBackground(Color.DARK_GRAY);
		button_8.setBounds(427, 534, 71, 61);
		layeredPane.add(button_8);
		
		JButton button_9 = new JButton("");
		button_9.setFocusable(false);
		button_9.setIcon(new ImageIcon(Main.class.getResource("/src/Icons/arrow_down.png")));
		button_9.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		button_9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Zoom.screenY = Zoom.screenY+50;
				repaint();
			}
		});
		button_9.setBackground(Color.DARK_GRAY);
		button_9.setBounds(427, 606, 71, 61);
		layeredPane.add(button_9);
		
		JButton btnNewButton_1 = new JButton("Draw / Manipulate");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				select = !select;
				if(focused != null){
					focused.setfocused(false);
					focused = null;
					repaint();
				}
			}
		});
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnNewButton_1.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnNewButton_1.setBackground(Color.DARK_GRAY);
		btnNewButton_1.setForeground(Color.WHITE);
		btnNewButton_1.setBounds(78, 518, 142, 45);
		layeredPane.add(btnNewButton_1);
		
	}
}
