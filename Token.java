//TODO
//Import all necessary libraries.

public class Token {

	
	public Token(String str) {
		//TODO
		//Declare all instance variables
		//Set the variables.
	}
	
	/**
	 * Get the positions of the Token for the document passed in.
	 * If the Token has no positions in the document, meaning there is no Map from the doc to the list, return null.
	 * 
	 * @param doc
	 * @return list of integers or null
	 */
	public List<Integer> getPositions(Document doc){
		
		//TODO - getPositions
		
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

	}

	@Override
	public String toString() {
		
		//TODO - toString
		//toString only returns the String instance variable.
		
	}
	
	@Override
	public boolean equals(Object o) {
		
		//TODO - equals method
		//equals only checks if the passed in Object is a Token and the String variables match.
		
	}

}
