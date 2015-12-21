
public class Node {
	String name;
	int x;
	int y;
	float distance;
	Node() {
		
	}
	Node(String name,int x,int y,float distance ) {
		this.name = name;
		this.x = x;
		this.y = y;
		this.distance = distance;
	}
	String name() {
		return name;
	}
}
