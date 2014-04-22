import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.PopupMenu;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;



public class main {
	static int numofPoints=0;
	static List<Point> bezierPoints=new ArrayList<>();
	static List<Point> polygonPoints=new ArrayList<>();
	static myJPanel pane;
	static int poligon_vertex =0;
	static Point pointPressed =null;
	static Point pointRelease =null;

	static int lastDrag_x=0;
	static int lastDrag_y=0;

	static int shape = 1;		// 1=Line   2=Circle   3=Rectangle
	static Color color = Color.black;

	public static void main(String[] args) {
        final int width = 600;
        final int height = 600;

        
        
        /* java window - the container managed the frame */
        JFrame frame = new JFrame("Direct draw demo");       
        
        /* specific frame */
        pane = new myJPanel(width, height);
        
        /******************************************************************
         * Administrate window adjustment
         */
        /* Add the pane to the frame manager */
        frame.add(pane);
        // to show at least the panel data
        frame.pack();
        /* Resizability */
        frame.setResizable(true);     
        /* close all app in close button */
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        /**MENU's******************************************************/
        /* set menu for window */
      	//Create the menu bar.
        JMenuBar menuBar = new JMenuBar();
        
     // Menu
        //JMenu menu = new JMenu("Menu");
        JMenuItem menu_clearScreen = new JMenuItem("Clear Screen");
        menu_clearScreen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				pane.fillCanvas(Color.white);
			}
		});
        //Build the first menu.
        //menu.add(menu_clearScrean);
        //menu.setMnemonic(KeyEvent.VK_M);
        //************************************************
        
        // Objects Menu
        JMenu objectsMenu = new JMenu ("Objects");
        objectsMenu.setMnemonic(KeyEvent.VK_O);
        JMenuItem objectItem_line = new JMenuItem("Line");
        objectItem_line.setMnemonic(KeyEvent.VK_F1);
        objectItem_line.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				shape=1;
			}
		});
        JMenuItem objectItem_circle = new JMenuItem("Circle");
        objectItem_circle.setMnemonic(KeyEvent.VK_F2);
        objectItem_circle.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				shape=2;
			}
		});
        JMenuItem objectItem_poligon = new JMenuItem("Poligon");
        objectItem_poligon.setMnemonic(KeyEvent.VK_F3);
        objectItem_poligon.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				shape=3;

				//custom title, custom icon
				String result = JOptionPane.showInputDialog("Enter poligon vertex:");
				poligon_vertex= Integer.parseInt(result);
			}
		});

        JMenuItem objectItem_bezier = new JMenuItem("Bezier Curve");
        objectItem_bezier.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				shape=4;
			}
		});
        objectsMenu.add(objectItem_line);
        objectsMenu.add(objectItem_circle);
        objectsMenu.add(objectItem_poligon);
        objectsMenu.add(objectItem_bezier);
       

       
      //************************************************
        
        // Color Menu
        JMenu colorMenu = new JMenu("Color");
        JMenuItem colorMenu_black = new JMenuItem("");
        colorMenu_black.setPreferredSize(new Dimension(40, 20));
        colorMenu_black.setBackground(Color.BLACK);
        colorMenu_black.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				color = Color.BLACK;
			}
		});
        JMenuItem colorMenu_green = new JMenuItem("");
        colorMenu_green.setPreferredSize(new Dimension(40, 20));
        colorMenu_green.setBackground(Color.GREEN);
        colorMenu_green.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				color = Color.GREEN;
			}
		});
        JMenuItem colorMenu_blue = new JMenuItem("");
        colorMenu_blue.setPreferredSize(new Dimension(40, 20));
        colorMenu_blue.setBackground(Color.BLUE);
        colorMenu_blue.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				color = Color.BLUE;
			}
		});
        JMenuItem colorMenu_white = new JMenuItem("");
        colorMenu_white.setPreferredSize(new Dimension(40, 20));
        colorMenu_white.setBackground(Color.WHITE);
        colorMenu_white.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				color = Color.WHITE;
			}
		});
        JMenuItem colorMenu_yellow = new JMenuItem("");
        colorMenu_yellow.setPreferredSize(new Dimension(40, 20));
        colorMenu_yellow.setBackground(Color.YELLOW);
        colorMenu_yellow.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				color = Color.YELLOW;
			}
		});
        JMenuItem colorMenu_red = new JMenuItem("");
        colorMenu_red.setPreferredSize(new Dimension(40, 20));
        colorMenu_red.setBackground(Color.RED);
        colorMenu_red.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				color = Color.RED;
			}
		});
        //Build the first menu.
        colorMenu.add(colorMenu_black);
        colorMenu.add(colorMenu_red);
        colorMenu.add(colorMenu_green);
        colorMenu.add(colorMenu_blue);
        colorMenu.add(colorMenu_white);
        colorMenu.add(colorMenu_yellow);
        //*********************************************
        
        menuBar.add(objectsMenu);
        menuBar.add(colorMenu);
        menuBar.add(menu_clearScreen);

        pane.setLayout(new BorderLayout());
        frame.add(menuBar, BorderLayout.NORTH);
        /**End menu's*******************************************************/
        
        /* visibility */
        frame.setVisible(true);
        
        

        
        
        /* print Mouse location */
//        while (true){
//	        PointerInfo a = MouseInfo.getPointerInfo();
//	        Point b = a.getLocation();
//	        x = (int) b.getX();
//	        y = (int) b.getY();
//	        System.out.print(new Date() +" --- x="+x +", y="+ y +"\n");
//	        
//	         Code for move programmatically the mouse 
//	        Robot r;
//			try {
//				r = new Robot();
//				r.mouseMove(x, y);
//			} catch (AWTException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//        }
        
        /* Mouse Listener */
        frame.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e1) {
				// TODO Auto-generated method stub
				System.out.println("release"+e1.getPoint());
				pointRelease= new Point( e1.getPoint() );
				// Fix point
				pointRelease.x-=8;
				pointRelease.y-=53;

				switch (shape){
				// Draw line
				case 1: 
					if (width- pointRelease.getX() >=0 && height- pointRelease.getY() >=0 )
						pane.drawLine(color, (int)pointPressed.getX(), (int)pointRelease.getX(), (int)pointPressed.getY(), (int)pointRelease.getY());
					else pane.drawLine(color, (int)pointPressed.getX(), (int)lastDrag_x, (int)pointPressed.getY(), (int)lastDrag_y);
					break;
				case 2:
					pane.drawCircle(color, pointPressed.x, pointPressed.y, pointRelease.x, pointRelease.y);
					break;
				case 3:
					polygonPoints.add(new Point(pointPressed.x,pointPressed.y));
					polygonPoints.add(new Point(pointRelease.x,pointRelease.y));
					pane.regularPolygon(color ,polygonPoints, poligon_vertex);
					polygonPoints.clear();
		
					break;

				}

			}
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				System.out.println("pressed"+e.getPoint());
				pointPressed= new Point(e.getPoint() );
				// Fix point
				pointPressed.x-=8;
				pointPressed.y-=53;
			}
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				System.out.println("exited");

			}
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				System.out.println("entered");

			}
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				if ((shape==4) && (numofPoints<4)){ //shape 4 = bezier curve
					pane.putSuperPixel(e.getX(),e.getY(),color);
					numofPoints++;
					bezierPoints.add(new Point(e.getX(),e.getY()));
					if (numofPoints==4)
					{
						numofPoints=0;
						pane.drawBezierCurve(color,bezierPoints);
						bezierPoints=new ArrayList<>();
					}
				}

				System.out.println("clicked");
			}
		});
        
        /* mouse motion */
        frame.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO Auto-generated method stub
				System.out.println("moved");
				System.out.println( "("+e.getPoint().getX()+" , "+e.getPoint().getY()+")");
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub
				System.out.println("dragged");
				lastDrag_x= (int) e.getPoint().getX();
				lastDrag_y= (int) e.getPoint().getY();
			}
		});
        
    }
}