import java.util.*;
import java.io.*;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.Border;
public class FastTubeApplication {
	int flag = 0;
	ApplicationModel model;
	ApplicationViewer view;
	int option = 1;
	JPanel panel = new JPanel();
	ArrayList<Edge> path = new ArrayList<Edge>();
	FastTubeApplication() throws IOException{
		
	}
	class CalculateListener implements ActionListener {
		public void actionPerformed(ActionEvent actionEvent) {
			panel.removeAll();
			path.clear();
			float totalTime = 0;
			float totalDistance = 0;
			option = getOption(Integer.parseInt(view.sidePanel.hours.getSelectedItem().toString()));
			System.out.println("Option: "+option);
			path = model.pathCalculation(view.sidePanel.stations1.getSelectedItem().toString(), view.sidePanel.stations2.getSelectedItem().toString(),option);
			Collections.reverse(path);
			if(path.size()!= 0) {
				flag = 1;
				view.revalidate();
				view.repaint();
				Border border = BorderFactory.createLineBorder(Color.BLUE, 5);
				ImageIcon arrow = new ImageIcon("output.gif");
				String line = path.get(0).line;;
				System.out.println(path.size());
				JLabel originLine = new JLabel();
				originLine.setText(line);
				originLine.setAlignmentX(Component.CENTER_ALIGNMENT);
				originLine.setForeground(setLineColor(line));
				originLine.setFont(new Font("Candara",Font.BOLD,18));
				JLabel origin = new JLabel();
				origin.setFont(new Font("Candara",Font.PLAIN,20));
				origin.setText(path.get(0).node1);
				origin.setBorder(border);
				origin.setAlignmentX(Component.CENTER_ALIGNMENT);
				JLabel transit1 = new JLabel();
				transit1.setIcon(arrow);
				transit1.setAlignmentX(Component.CENTER_ALIGNMENT);
				panel.add(originLine);
				panel.add(origin);
				panel.add(transit1);
				
				
				for(Edge edge:path) {
					System.out.println(edge.node1);
					totalTime += edge.param;
					totalDistance += edge.distance;
					if(!edge.line.equals(line)) {
						line = edge.line;
						JLabel lineLab = new JLabel(line);
						lineLab.setAlignmentX(Component.CENTER_ALIGNMENT);
						lineLab.setFont(new Font("Candara",Font.BOLD,18));
						System.out.println(setLineColor(line).toString() +" "+ line+"1");
						lineLab.setForeground(setLineColor(line));
						JLabel label = new JLabel();
						label.setFont(new Font("Candara",Font.PLAIN,20));
						label.setText(edge.node1);
						label.setBorder(border);
						label.setAlignmentX(Component.CENTER_ALIGNMENT);
						JLabel transit = new JLabel();
						transit.setIcon(arrow);
						transit.setAlignmentX(Component.CENTER_ALIGNMENT);
						panel.add(lineLab);
						panel.add(label);
						panel.add(transit);
						
					}
				}
				JLabel destinationLine = new JLabel(line);
				destinationLine.setAlignmentX(Component.CENTER_ALIGNMENT);
				destinationLine.setForeground(setLineColor(line));
				destinationLine.setFont(new Font("Candara",Font.BOLD,18));
				JLabel destination = new JLabel();
				destination.setFont(new Font("Candara",Font.PLAIN,20));
				destination.setText(view.sidePanel.stations2.getSelectedItem().toString());
				destination.setBorder(border);
				destination.setAlignmentX(Component.CENTER_ALIGNMENT);
				panel.add(destinationLine);
				panel.add(destination);
				JLabel TotalDistance = new JLabel("DISTANCE:  "+Float.toString(totalDistance)+" km");
				TotalDistance.setBorder(BorderFactory.createEmptyBorder(60,0, 0, 0));
				TotalDistance.setFont(new Font("Californian FB",Font.BOLD,20));
				TotalDistance.setAlignmentX(Component.CENTER_ALIGNMENT);
				JLabel TotalTime = new JLabel("TIME:  "+Float.toString(totalTime)+" mins");
				TotalTime.setBorder(BorderFactory.createEmptyBorder(20,0, 0, 0));
				TotalTime.setFont(new Font("Californian FB",Font.BOLD,20));
				TotalTime.setAlignmentX(Component.CENTER_ALIGNMENT);
				panel.add(TotalDistance);
				panel.add(TotalTime);
				
			}
			panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
			panel.setBackground(new Color(255,140,0));
			panel.setBorder(BorderFactory.createEmptyBorder(40,40, 40,40));
			
			JScrollPane pane = new JScrollPane(panel);
			view.mainBox.add(pane);
			view.revalidate();
			view.repaint();
		}
		public Color setLineColor(String line) {
			if(line.equals("Central "))
				return Color.RED;
			else if(line.equals("Northern "))
				return Color.BLACK;
			else if(line.equals("Piccadilly "))
				return Color.BLUE;
			else if(line.equals("Victoria"))
				return Color.CYAN;
			else if(line.equals("Bakerloo "))
				return new Color(139,69,19);
			else if(line.equals("Circle "))
				return Color.YELLOW;
			else if(line.equals("District"))
				return new Color(0,139,0);
			else if(line.equals("Metropolitan"))
				return new Color(135,38,87);
			else if(line.equals("H & C"))
				return Color.MAGENTA;
			else if(line.equals("Jubilee "))
				return Color.DARK_GRAY;
			else
				return Color.BLACK;
			
		}
		public int getOption(int time) {
			if(time >= 7 && time <= 10)
				return 2;
			else if(time >=10 && time <= 16)
				return 3;
			else
				return 1;
		}
	}
	class MapClick extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			int xCoord = e.getX();
			int yCoord = e.getY();
			System.out.println(Integer.toString(xCoord)+"\t"+Integer.toString(yCoord));
		}
	}
	public static void main(String[] args) throws IOException{
		
		FastTubeApplication app = new FastTubeApplication();
		app.model = new ApplicationModel();
		app.model.readGraph("stations.txt","data.txt");
		app.view = new ApplicationViewer(app.model.listOfStations(),app);
		app.view.sidePanel.addCalculateListener(app.new CalculateListener());
		app.view.map.addMouseListener(app.new MapClick());
	}
	
}