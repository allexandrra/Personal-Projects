
public class cellComparator implements Comparable<Character>{

	public cellComparator() {}
	
	public int compareTo(Character ch) {
		
		if(ch.equals('^')) return 0;
		if(ch.equals('>')) return 1;
		if(ch.equals('V')) return 2;
		if(ch.equals('<')) return 3;
		return -1;
	}
}
