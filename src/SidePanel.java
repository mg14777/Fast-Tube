import java.awt.Font;
import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionListener;

public class SidePanel extends JPanel{
	JPanel labelPanel = new JPanel();
	JPanel boxPanel = new JPanel();
	JPanel timePanel = new JPanel();
	JLabel origin;
	JLabel destination;
	JLabel time;
	JLabel colon;
	JComboBox<String> hours;
	JComboBox<String> mins;
	JComboBox<String> stations1;
	JComboBox<String> stations2;
	JButton calculate;
	
	SidePanel(ArrayList<String> stationList) {
		calculate = new JButton("Plan Journey");
		origin = new JLabel("Origin");
		origin.setFont(new Font("Times New Roman",Font.PLAIN,20));
		//origin.setBorder(BorderFactory.createEmptyBorder(40,10, 40, 10));
		
		destination = new JLabel("Destination");
		destination.setFont(new Font("Times New Roman",Font.PLAIN,20));
		//destination.setBorder(BorderFactory.createEmptyBorder(40,10, 80, 10));
		
		stations1 = new JComboBox<String>();
		
		stations2 = new JComboBox<String>();
		hours = new JComboBox<String>();
		mins = new JComboBox<String>();
		for(String station:stationList) {
			stations1.addItem(station);
			stations2.addItem(station);
		}
		int i = 0;
		time = new JLabel("Time");
		colon = new JLabel(":");
		time.setFont(new Font("Times New Roman",Font.PLAIN,20));
		colon.setFont(new Font("Times New Roman",Font.PLAIN,20));
		for(i= 0;i < 24;i++) {
			if(i<9)
				hours.addItem("0"+Integer.toString(i));
			else
				hours.addItem(Integer.toString(i));
		}
		for(i= 0;i < 60;i++) {
			if(i<9)
				mins.addItem("0"+Integer.toString(i));
			else
				mins.addItem(Integer.toString(i));
		}
			
		
		labelPanel.add(origin);
		labelPanel.add(stations1);
		boxPanel.add(destination);
		boxPanel.add(stations2);
		boxPanel.setBorder(BorderFactory.createEmptyBorder(0,40, 0, 40));
		timePanel.add(time);
		timePanel.add(hours);
		timePanel.add(colon);
		timePanel.add(mins);
		timePanel.setBorder(BorderFactory.createEmptyBorder(0,0, 0, 40));
		labelPanel.setBackground(new Color(30,144,225));
		boxPanel.setBackground(new Color(30,144,225));
		timePanel.setBackground(new Color(30,144,225));
		Box hbox = Box.createHorizontalBox();
		hbox.setOpaque(true);
		hbox.setBackground(new Color(30,144,225));
		hbox.add(labelPanel);
		hbox.add(boxPanel);
		hbox.add(timePanel);
		hbox.add(calculate);
		add(hbox);
		setBackground(new Color(30,144,225));
		
	}
	public void addCalculateListener(ActionListener listenCalculateButton) {
		calculate.addActionListener(listenCalculateButton);
	}
}
