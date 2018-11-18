
public class Players implements Comparable<Object> {
	String name;
	int score , scorein; 
	
	public Players(String name,int score)  {
		this.name = name;
		this.score = score;
	}
	public Players(String name2) {
		this.name = name2;
	}
	public String getName() {	
		return name;
	 }
	public int getScore() {	
	      return score;
	}
	public int getScoreIn() {	
	      return scorein;
	}
	public String toString() {
        return name + "	"+score;
    } 
  public void updScore(int h) {
	  if (h > this.score) {
		  int temp;
		  temp = this.score;
		  this.score = h;
		  h = temp;	
	       }
	   }
@Override
public int compareTo(Object o) {
	// TODO Auto-generated method stub
	return 0;
}
}
