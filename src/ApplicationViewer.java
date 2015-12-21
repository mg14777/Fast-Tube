import java.util.*;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.io.*;

import javax.swing.*;
public class ApplicationViewer extends JFrame{
	MapView map;
	SidePanel sidePanel;
	FastTubeApplication app;
	Box mainBox = Box.createHorizontalBox();
	ApplicationViewer(ArrayList<String> stations,FastTubeApplication app) {
		this.app = app;
		Box box = Box.createVerticalBox();
		map = new MapView(app);
		JScrollPane pane = new JScrollPane(map);
		sidePanel = new SidePanel(stations); 
		box.add(pane);
		box.add(sidePanel);
		mainBox.add(box);
		mainBox.setOpaque(true);
		mainBox.setBackground(new Color(255,140,0));
		add(mainBox);
		setLocationByPlatform(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(1366,768);
		setVisible(true);	
	}

	
	
}
