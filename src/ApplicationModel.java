import java.awt.Point;
import java.io.*;
import java.util.*;
public class ApplicationModel {

	public Graph graph;
	public ArrayList<NodeCoordinate> coords = new ArrayList<NodeCoordinate>();
	public ArrayList<Point> joints = new ArrayList<Point>();
	ApplicationModel() {
		
	}
	public void readGraph(String fileNode,String fileEdge) throws IOException {
		graph = new Graph();
		String line;
		String direction;
		String node1;
		String node2;
		float distance;
		float unImped_Run;
		float AMPeak;
		float InterPeak;
		node1 = node2 = line = direction = "";
		distance = unImped_Run = AMPeak = InterPeak = 0;
		File file1 = new File(fileNode);
		File file2 = new File(fileEdge);
		Scanner in = new Scanner(file1);
		//BufferedReader br = new BufferedReader(new FileReader(fileNode));
		int j=1;
		String sCurrentLine;
		while(in.hasNextLine()) {
			
			//System.out.println(j);
			sCurrentLine = in.nextLine();
			if (sCurrentLine.isEmpty()) {
		        continue;
		      }
			Node node = new Node(sCurrentLine,0,0,1000);
			graph.addNode(node);
			//System.out.println(Integer.toString(node.x));
			j++;
		}
		in = new Scanner(new File("pos.txt"));
		int x1,y1;
		x1 = y1 = 0;
		while(in.hasNextLine()) {
			j=1;
			sCurrentLine = in.nextLine();
			
			if(sCurrentLine.isEmpty()) 
				continue;
			line = sCurrentLine.split("\t")[0];
			node1 = sCurrentLine.split("\t")[1];
			x1 = Integer.parseInt(sCurrentLine.split("\t")[2]);
			y1 = Integer.parseInt(sCurrentLine.split("\t")[3]);
			if(node1.equals("JOINT")) {
				
				joints.add(new Point(x1,y1));
				continue;
			}
			/*
			for(String x:sCurrentLine.split("\t")) {
				//System.out.println(x);
				if(j==1)
					line = x;
				else if(j==2) {
					if(x.equals("JOINT")) {
						
						joints.add(new Point(x1,y1));
						continue;
					}
					//System.out.println(Integer.toString(node.x));
					node1 = x;
				}
				else if(j==3)
					x1 = Integer.parseInt(x);
				else
					y1 = Integer.parseInt(x);
				j++;
			}
			*/
			coords.add(new NodeCoordinate(line,node1,x1,y1));
				
		}
		
		//System.out.println(graph.getNodes().size());
		int i = 1;
		//try {
		in = new Scanner(file2);
		
		
		while(in.hasNextLine()) {
			
			sCurrentLine = in.nextLine();
			//System.out.println(Integer.toString(j)+" "+line);
			i = 1;
			for(String info:sCurrentLine.split("\t")) {
				if(i==1)
					line = info;
				else if(i==2)
					direction = info;
				else if(i==3)
					node1 = info;
				else if(i==4)
					node2 = info;
				else if(i==5)
					distance = Float.parseFloat(info);
				else if(i==6)
					if(info == "")
						unImped_Run = 0;
					else
						unImped_Run = Float.parseFloat(info);
				else if(i==7)
					AMPeak = Float.parseFloat(info);
				else
					InterPeak = Float.parseFloat(info);
				i++;
			}
			
			graph.addEdge(new Edge(line,direction,node1,node2,distance,unImped_Run,AMPeak,InterPeak));	
			
		}
		/*
		}
		catch(Exception ist) { 
			System.out.println("Error!");
		}
		*/
		

	}
	public NodeCoordinate getCoords(String nodeName,String line) {
		for(NodeCoordinate c : coords) {
			if(c.nodeName.equals(nodeName) && line.equals(c.line))
				return c;
		}
		return null;
	}
	public Node getNode(String nodeName) {
		for(Node node: graph.getNodes()) {
			//System.out.println(node.name());
			if(node.name.equals(nodeName)) {
				
				return node;
			}
		}
		System.out.println(nodeName);
		System.out.println("Null node");
		return null;
	}
	public ArrayList<String> listOfStations() {
		ArrayList<String> stations = new ArrayList<String>();
		for(Node node:graph.getNodes())
			stations.add(node.name);
		return stations;
	}
	public ArrayList<Edge> pathCalculation(String origin,String destination,int option) {
		ArrayList<Node> covered = new ArrayList<Node>();
		ArrayList<Node> remaining = new ArrayList<Node>();
		ArrayList<PredPointer> pred = new ArrayList<PredPointer>();
		
		for(Node node:graph.getNodes()) {
			node.distance = 1000;
			remaining.add(node);
		}
		if(option == 1) {
			for(Edge edge:graph.getEdges())
				edge.initializeParam(edge.unImped_Run);
		}
		else if(option == 2) {
			for(Edge edge:graph.getEdges())
				edge.initializeParam(edge.AMPeak);
		}
		else if(option == 3) {
			for(Edge edge:graph.getEdges())
				edge.initializeParam(edge.InterPeak);
		}
		else
			System.out.println("Option Error!");
		
		ArrayList<Edge> path = new ArrayList<Edge>();
		
		int flag = 0;
		String currentNode = new String(origin);
		String nextNode = new String();
		Node neighbour = new Node();
		Node current = new Node();
		Edge relatedEdge = new Edge();
		current = getNode(currentNode);
		current.distance = 0;
		
		float min;
		int i;
		while(flag == 0) {
			min = 1000;
			//System.out.println("LOL");
			i = 1;
			int deadEnd = 1;
			if(current.name().equals("CHESHAM"))
				i = 0;
			for(Node node:remaining) {
				/*
				if(i == 0)
					System.out.println(node.name() + " "+node.distance);
				*/
				if(node.distance < min) {
					min = node.distance;
					current = node;
					deadEnd = 0;
				}
			}
			if(deadEnd == 1) {
				System.out.println("Route not found till "+destination);
				System.out.println("Last stop: "+current.name());
				return null;
			}
				
			for(Edge edge:getEdgesFromName(current.name())) {
				
				if(edge.node1.equals(current.name()))
					neighbour = getNode(edge.node2);
				else
					neighbour = getNode(edge.node1);
				if(!covered.contains(neighbour)) {
					if(edge.param+current.distance < neighbour.distance) {
						neighbour.distance = edge.param+current.distance;
						PredPointer newPred;
						int ind = 0;
						for(PredPointer pointer:pred)
							if(pointer.node.equals(neighbour)) {
								pointer.link = current;
								ind = 1;
							}
						if(ind==0) {
							newPred = new PredPointer(neighbour,current);		
							pred.add(newPred);
						}
					}
				}
				
				
				i++;	
			}
			if(current.name().equals(destination))
					flag = 1;
			covered.add(current);
			
			if(covered.size() == graph.getNodes().size()) {
				System.out.println("No route found");
				return null;
			}
			//System.out.println(remaining.indexOf(current) + " "+current.name());
			remaining.remove(remaining.indexOf(current));
			
		}
		
		Node source = getNode(destination);
		while(!source.name().equals(origin)) {
			//System.out.println(source.name());
			for(PredPointer pointer:pred)
				if(pointer.node.equals(source)) {
					path.add(findEdge(pointer.link.name(),pointer.node.name()));
					source = pointer.link;
					break;
				}
		}
	
		return path;
	}
	
	public Edge findEdge(String node1,String node2) {
		for(Edge edge:graph.getEdges())
			if(edge.node1.equals(node1) && edge.node2.equals(node2))
				return edge;
		return null;
	}
	public List<Edge> getEdgesFromName(String nodeName) {
		ArrayList<Edge> edges = new ArrayList<Edge>(); 
		for(Edge edge: graph.getEdges()) {
			if(nodeName.equals(edge.node1))//||nodeName.equals(edge.node2))
				edges.add(edge);
		}
		return edges;	
	}
	
}
