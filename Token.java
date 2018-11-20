import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Token {
	
	private String token;
	private Map <Document, List<Integer>> positionalIndex = new HashMap<>();
	
	public Token(String str) {
		//TODO
		this.token = str;
		

	}
	
	/**
	 * Get the positions of the Token for the document passed in.
	 * If the Token has no positions in the document, meaning there is no Map from the doc to the list, return null.
	 * 
	 * @param doc
	 * @return list of integers or null
	 */
	public List<Integer> getPositions(Document doc){
		List<Integer>listOfPos = null;
		
		if(positionalIndex.containsKey(doc)) {
			System.out.println("test");
			listOfPos = new ArrayList<>();
			listOfPos = positionalIndex.get(doc);
		}
		
		return listOfPos;
		
	}

	/**
	 * Sets the position of the Token in the document passed in.
	 * If doc already occurs in the postionalIndex, it means the token has already appeared in the document.
	 * If doc doesn't exist in positionalIndex, it means this is the first time the token appeared in the document.
	 * How can you check for this?
	 * After checking, what should be done for either case to make sure the list of integers is correct?
	 * 
	 * @param doc
	 * @param p
	 */
	public void setPositions(Document doc, Integer p) {
		
		//TODO - setPostions
		List<Integer>listOfPos = new ArrayList <>();
		if(positionalIndex.containsKey(doc) && p >= 0) {
			listOfPos = positionalIndex.get(doc);
			listOfPos.add(p);
			System.out.print("test");
			
		}
		else if((!positionalIndex.containsKey(doc) && p >= 0)) {
			listOfPos.add(p);
			positionalIndex.put(doc, listOfPos);
		
		}

	}

	@Override
	public String toString() {
		
		//TODO - toString
		//toString only returns the String instance variable.
		
		return this.token;
		
	}
	
	@Override
	public boolean equals(Object o) {
		
		//TODO - equals method
		//equals only checks if the passed in Object is a Token and the String variables match.
		
		if(o == null) {
			return false;
		}
		
		if(this == o) {
			return true;
		}
		if( o instanceof Token) {
			Token temp = (Token)o;
			return this.token.equals(temp.token);
		}
		
		return false;
	}

}
