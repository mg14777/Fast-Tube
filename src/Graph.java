import java.util.ArrayList;
import java.util.List;
public class Graph {

	private List<Node> nodes;
	private List<Edge> edges;
	
	Graph() {
		nodes = new ArrayList<Node>();
		edges = new ArrayList<Edge>();
	}
	public void addNode(Node node) {
		nodes.add(node);
	}
	public void addEdge(Edge edge) {
		edges.add(edge);
	}
	public List<Node> getNodes() {
		return nodes;
	}
	public List<Edge> getEdges() {
		return edges;
	}
	
}
