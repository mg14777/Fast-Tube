
public class Edge {
	String line;
	String direction;
	String node1;
	String node2;
	float distance;
	float unImped_Run;
	float AMPeak;
	float InterPeak;
	float param = 0;
	Edge() {
		
	}
	Edge(String line,String direction,String node1,String node2,float distance,float unImped_Run,float AMPeak,float InterPeak) {
		this.line = line;
		this.direction = direction;
		this.node1 = node1;
		this.node2 = node2;
		this.distance = distance;
		this.unImped_Run = unImped_Run;
		this.AMPeak = AMPeak;
		this.InterPeak = InterPeak;
	}
	public void initializeParam(float val) {
		this.param = val;
	}
	
}
