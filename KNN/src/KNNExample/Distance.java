package KNNExample;

public class Distance implements Comparable<Distance> {

	public double distance;
	public String answerClass;
	
	public Distance(double distance, String answerClass) {
		this.distance = distance;
		this.answerClass = answerClass;
	}

	@Override
	public int compareTo(Distance o) {
		// TODO Auto-generated method stub
		return new Double(this.distance).compareTo( o.distance);
	}
	
	
	
}
