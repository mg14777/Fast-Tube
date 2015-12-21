import java.util.*;
public class NodeCoordinate {

	String nodeName;
	String line;
	int x = 1;
	int y = 1;
	NodeCoordinate(String line,String nodeName,int x,int y) {
		this.nodeName = nodeName;
		this.line = line;
		this.x = x;
		this.y = y;
	}
}
