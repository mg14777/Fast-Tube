import java.util.*;
import java.util.Timer;

import javax.swing.*;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.io.*;
import java.lang.*;

public class MapView extends JLabel{

	private static int speed = 5;
	Timer timer;	
	public void timer(Edge edge,Graphics2D g2d) {
    timer.scheduleAtFixedRate(new TimerTask() {
    	 public void run() {
    		 System.out.println("Hello");
    		 NodeCoordinate node1;
    			NodeCoordinate node2;
    			int x1,y1,x2,y2;
    			int smallx,smally,largex,largey;
    			//for(Edge edge:app.path) {
    				node1 = app.model.getCoords(edge.node1,edge.line);
    				node2 = app.model.getCoords(edge.node2,edge.line);
    				if(node1 != null) {
    					x1 = node1.x;
    					y1 = node1.y;
    				}
    				else {
    					x1 = 0;
    					y1 = 0;
    				}
    				if(node2 != null) {
    					x2 = node2.x;
    					y2 = node2.y;
    				}
    				else {
    					x2=0;
    					y2=0;
    				}
    				smallx = x1;
    				smally = y1;
    				largex = x2;
    				largey = y2;
    				int swap = 0;
    				if(x2 < x1) {
    					smallx = x2;
    					largex = x1;
    				}
    				if(y2 < y1) {
    					smally = y2;
    					largey = y1;
    				}
    				if(!(x1 == 0 || x2 == 0)) {
    					if(Math.abs(x1 - x2) <=3)
    						g2d.drawLine(x1,y1,x1,y2);
    					else if(Math.abs(y1 - y2) <=3)
    						g2d.drawLine(x1,y1,x2,y1);	
    					else {
    						Point joint = null;
    						for(Point p:app.model.joints)
    							if(p.x >= smallx && p.x <= largex && p.y >= smally && p.y <= largey) {
    								joint = p;
    								
    								g2d.drawLine(x1,y1,joint.x,joint.y);		
    								g2d.drawLine(joint.x,joint.y,x2,y2);
    								break;
    							}
    						if(joint == null)
    							g2d.drawLine(x1,y1,x2,y2);
    						
    					}
    					/*
    					try {
    					    Thread.sleep(5000);                 //1000 milliseconds is one second.
    					} catch(InterruptedException ex) {
    					    Thread.currentThread().interrupt();
    					}
    					*/
    					app.view.revalidate();
    					app.view.repaint();
    					//*/
    				}
    			//}
    	 }
    },1000,1000);
	}
	ImageIcon icon;
	FastTubeApplication app;
	MapView(FastTubeApplication app) {
		this.app = app;
		icon = new ImageIcon("C:/Users/lenovo/Desktop/tubemap-2012-12.png");
		setIcon(icon);
	}
	public void addMapClickedListener(MouseAdapter listenLoadGame) {
		this.addMouseListener(listenLoadGame);
	}
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		//if(app.flag == 1)
			draw(g);
	}
	public void drawCircle(Graphics g,int x,int y, int r) {
    	g.fillOval(x-r, y-r, 2*r, 2*r);
    	
	}
	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setStroke(new BasicStroke(5));
		g2d.setPaint(Color.blue);
		g2d.setColor(Color.GREEN);
		g2d.setRenderingHint(
		    RenderingHints.KEY_ANTIALIASING,
		    RenderingHints.VALUE_ANTIALIAS_ON);

			// You can also enable antialiasing for text:

		g2d.setRenderingHint(
		    RenderingHints.KEY_TEXT_ANTIALIASING,
		    RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		NodeCoordinate node1;
		NodeCoordinate node2;
		int x1,y1,x2,y2;
		int smallx,smally,largex,largey;
		/*
		g2d.setColor(Color.GREEN);
		for(Edge edge:app.model.graph.getEdges()) {
			node1 = app.model.getCoords(edge.node1,edge.line);
			node2 = app.model.getCoords(edge.node2,edge.line);
			if(node1 == null || node2 == null)
				continue;
			g2d.drawLine(node1.x,node1.y,node2.x,node2.y);
		}
		g2d.setColor(new Color(238,0,238));
		for(NodeCoordinate nodex:app.model.coords) {
			g2d.setColor(new Color(176,23,31));
			drawCircle(g2d,nodex.x,nodex.y,8);
			g2d.setColor(new Color(255,255,255));
			drawCircle(g2d,nodex.x,nodex.y,4);
			
		}
			
			//*/
		//drawCircle(g2d,node2.x,node2.y,8);
		
		/*
		node1 = app.model.getCoords(app.path.get(0).node1,app.path.get(0).line);
		node2 = app.model.getCoords(app.path.get(app.path.size()-1).node2,app.path.get(app.path.size()-1).line);
		*/
		for(Edge edge:app.path) {
			node1 = app.model.getCoords(edge.node1,edge.line);
			node2 = app.model.getCoords(edge.node2,edge.line);
			if(node1 == null || node2 == null)
				continue;
			
			if(node1 != null) {
				x1 = node1.x;
				y1 = node1.y;
			}
			else {
				x1 = 0;
				y1 = 0;
			}
			if(node2 != null) {
				x2 = node2.x;
				y2 = node2.y;
			}
			else {
				x2=0;
				y2=0;
			}
			smallx = x1;
			smally = y1;
			largex = x2;
			largey = y2;
			
			if(x2 < x1) {
				smallx = x2;
				largex = x1;
			}
			if(y2 < y1) {
				smally = y2;
				largey = y1;
			}
			if(!(x1 == 0 || x2 == 0)) {
				g2d.setColor(new Color(255,193,37));
				if(Math.abs(x1 - x2) <=3)
					g2d.drawLine(x1,y1,x1,y2);
				else if(Math.abs(y1 - y2) <=3)
					g2d.drawLine(x1,y1,x2,y1);	
				else {
					Point joint = null;
					for(Point p:app.model.joints)
						if(p.x >= smallx && p.x <= largex && p.y >= smally && p.y <= largey) {
							joint = p;
							
							g2d.drawLine(x1,y1,joint.x,joint.y);		
							g2d.drawLine(joint.x,joint.y,x2,y2);
							break;
						}
					if(joint == null)
						g2d.drawLine(x1,y1,x2,y2);
					
				}
				/*
				try {
				    Thread.sleep(5000);                 //1000 milliseconds is one second.
				} catch(InterruptedException ex) {
				    Thread.currentThread().interrupt();
				}
				revalidate();
				repaint();
				//*/
			}
			g2d.setColor(new Color(176,23,31));
			drawCircle(g2d,node1.x,node1.y,8);
			g2d.setColor(new Color(255,255,255));
			drawCircle(g2d,node1.x,node1.y,4);
			g2d.setColor(new Color(176,23,31));
			drawCircle(g2d,node2.x,node2.y,8);
			g2d.setColor(new Color(255,255,255));
			drawCircle(g2d,node2.x,node2.y,4);
		}
		

	}
	
    
    	public class timeIt implements ActionListener {
        public void actionPerformed ( ActionEvent e )
        {
        	/*
            if ( diff < 20 )
            {
                diff++;
            }
            else
            {
                diff = 0;
            }
            */
            repaint ();
           
        }
    } 
    
	
}
