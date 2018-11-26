import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Indexer {

	/**TODO
	 * Create the four instance variables needed for this class
	 * var1)	reversedIndex 	-is a Map that maps a Token to a List of Documents
	 * var2)	allTokens 		-is a Map that maps a String to a Token
	 * 								-use this to determine no duplicate Tokens are created
	 * var3)	allDocs			-is a Map that maps a string to a Document
	 * 								-use this to determine no duplicate Documents are created
	 * var4)	allDocsSorted	-A List that holds all Documents that are sorted by DocID
	 * 								-If done correctly, Documents added to this variable be inserted in sorted order
	 * var5)	assignID		-A int to assign docIDs
	 * 								-Should increment with each new document created
	 * 								-First document added will always get ID of 1
	 */
	Map <Token, List<Document>> reversedIndex;
	Map <String, Token> allTokens;
	Map <String,Document> allDocs;
	List<Document> allDocsSorted;
	int assignID;

	public Indexer() {
		//TODO
		//Declare all instance variables
		//What should each variable be initialized to?
		reversedIndex = new HashMap<>();
		allDocs = new HashMap <>();
		allDocsSorted = new ArrayList<>();
		allTokens = new HashMap <>();
	
		
		this.assignID = 1;
	}
	
	
	/**
	 * A method used to add a String document to the Reversed Index.
	 * You must	1) Parse out the document name.  This will always appear at the front of a
	 * 			   document and be surrounded by "<" ">" symbols
	 * 			2) Use the Map allDocs to make sure the document name(a String) currently being checked has not been added already.
	 * 			   If it has been seen, do nothing
	 * 			   If it hasn't been added, update allDocs and proceed to the next steps.
	 * 			3) Parse out the file contents.  The contents of the file will always appear after the document name.
	 * 			4) Break the file contents into individual tokens.
	 * 			5) Loop over all tokens contained in file contents.
	 * 				Think of how you can use removePunctuation, checkToken, and checkToken_Document methods.
	 * 				Calling these methods in a certain order and using their return types can give you a Token and
	 * 					a List of Documents to update the reversedIndex.
	 * 				Don't forget to update the positionalIndex too!  When should this happen?
	 * 
	 * @param docString
	 */
	public void indexDocument(String docString) {
		String name = getName(docString);
		System.out.println(name.length());
		

		if(allDocs.containsKey(name)) {

			return;

		}
		
		Document createDoc = new Document(this.assignID,name);
		
		assignID++;
		
		allDocs.put(name, createDoc);
 
		allDocsSorted.add(createDoc);

		String docContents = docString.substring(name.length()+2);
		System.out.println(docContents);

		List<String> seperateTokens = new ArrayList<String>(Arrays.asList(docContents.split(" ")));

		int index = 1;
		for(String token: seperateTokens) {
			
		
			String formattedString = removePunctuation(token).trim();
			
			Token createToken = checkToken(formattedString);
			
			createToken.setPositions(createDoc, index);
			
			checkToken_Document(createToken, createDoc);
		
			index +=1;
				
			
			// update posi index by char posi here. Set positions checks to make sure passed position it not -1 
//			while(positionIndex!= -1) {
//				positionIndex = docString.toLowerCase().indexOf(formattedString, index);
//				createToken.setPositions(createDoc, positionIndex);
//			
//				index = positionIndex + token.length();
//			
//		}
		

		}
	}

	
	// helper method to get name
	private String getName(String docString) {
		
		String name = null;
		
		Pattern nameWrap = Pattern.compile("<(.*?)>");
		
		Matcher m = nameWrap.matcher(docString);
		while (m.find()) {
		    name = m.group(1);
		    return name;
		}
		return name;
	}

	
	/**
	 * Remove all punctuation and convert String to lower case.
	 * Only a single term, or word, should be passed to this method at a time.
	 * Punctuation to remove: "," "." "!" "?"
	 * 
	 * @param str
	 * @return a formatted String
	 */
	
	protected String removePunctuation(String str) {
		
		//TODO - removePunctuation
		
		return str.replaceAll("[\\,\\.\\!\\?]", "").toLowerCase().trim();
		
	}
	
	/**
	 * Use allTokens to see if the String passed in currently is a token.
	 * If it is there, what should it return?
	 * If it isn't, what set of operations should happen?
	 * This method should never return null, meaning a Token object is always returned.
	 * 
	 * @param str
	 * @return Token
	 */
	protected Token checkToken(String str) {
		
		Token temp = new Token(str);
		if(allTokens.containsKey(str)) {
			temp = allTokens.get(str);
			return temp;
		}
		allTokens.put(str, temp);
		return temp;
		

	}
	
	/**
	 * Use reversedIndex to see if the Token is part of the reversedIndex.
	 * Remember, the reversedIndex maps a Token to a List of Documents.
	 * If the token is there, what should the Token be mapped to?
	 * If the Token isn't there, is the Token mapped to anything?  What should happen if it isn't mapped to anything?
	 * If the List of Documents mapped to by the Token does not contain the Document passed in, add it to the list.
	 * We need to make sure no duplicate docs are added to the List of Documents.
	 * This method should never return null, it should always return a List of Documents pointed to by the Token passed in.
	 * 
	 * @param token
	 * @param doc
	 * @return a List of Documents with the passed in Document possibly added to the List.
	 */
	protected List<Document> checkToken_Document(Token token, Document doc){

		List <Document> addDoc = new ArrayList<>();
		if(reversedIndex.get(token) == null) {
			addDoc.add(doc);
			reversedIndex.put(token,addDoc);
	
		}
		else if(!reversedIndex.get(token).contains(doc) ) {
			addDoc = reversedIndex.get(token);
			addDoc.add(doc);
			reversedIndex.put(token, addDoc);
		
			
		}
		
		return reversedIndex.get(token);
	

		
	}
	
	/**
	 * Use the reversedIndex to answer the query and print out the list of documents that contain the term.
	 * If the query is not in the reversedIndex, Simply print out the query was not found.
	 * If the query is there:
	 * 		1)	Print out the token name and the list of documents containing the query.
	 * 			You may use the built in toString method for Lists to print them out.
	 * 		2)	For each document the query appears in ,print out each docID followed by the query location(s) for each document.
	 * See the driver output in the homework description for example output.
	 * 
	 * @param query
	 */
	public void singleQuery(String query) {
		
		//TODO - singleQuery


		Token tokenHolder = null;

		List<Document> listOfDocs = new ArrayList<>();
		
		
		if(allTokens.get(query) == null) {
			System.out.println(String.format("No results for \"%s\"", query));
			return;
			
		}
		tokenHolder = allTokens.get(query);
		
		listOfDocs = reversedIndex.get(tokenHolder);


		System.out.println(String.format("Documents contatining: \"%s\" : %s", tokenHolder, listOfDocs.toString()));

		for(Document doc: listOfDocs) {
			
			System.out.println(String.format("DocID: %s , DocPositions = %s", doc.toString(),tokenHolder.getPositions(doc).toString()));
				}
			

		}





	

	/**
	 * Graduate students must complete.
	 * Undergraduates may feel free to attempt this if they wish.
	 * Write the code to perform a two word query.
	 * This should print out a result if both words in the query occur next to each other.
	 * 
	 * @param query
	 */
	public void twoWordQuery(String[] query) {
		
		String formattedToken1 = removePunctuation(query[0]);
		String formattedToken2 = removePunctuation(query[1]);

		if(!(allTokens.containsKey(formattedToken1) && allTokens.containsKey(formattedToken2))) {
			System.out.println(String.format("No results were found for %s %s", query[0], query[1]));
			return;
		}

		Token token1 = allTokens.get(formattedToken1);
		Token token2 = allTokens.get(formattedToken2);

		List<Document> occurencesToken1 = reversedIndex.get(token1);
		List<Document> occurencesToken2 = reversedIndex.get(token2);

		//Intersection method returns List of shared document between occuranceToken#
		List<Document> sharedDocs = intersection(occurencesToken1, occurencesToken2);

		//init map of docs(docID) and positions --> doc: posPairs

		HashMap <Integer, List<Integer>> docPositions = new HashMap<>();

		//iterate through sharedDocs -> cartesian method returns positions of each occurence 

		for(Document doc: sharedDocs) {
			
			//temp list to store position pairs - empty lists are not appened to docPositions
			List<Integer> positionPairs = check_positionPairs(token1, token2, doc);
			
			if(positionPairs.size() != 0) {
				docPositions.put(doc.getID(), positionPairs);
				
			}
			
		}
			
			
		System.out.println(String.format("Documents containing \"%s %s\" \n %s", query[0], query[1], docPositions.keySet().toString()));
		System.out.println();
		
			//System.out.println(String.format("positions: %s ", ));
			for(Integer docID: docPositions.keySet()) {
				
				for(Integer pos: docPositions.get(docID)) {
					System.out.println(String.format("Two word query found at position %s in document %s",pos, docID));
				}
				
			}
		


		




	}
		
	/**
	 * A simple method that prints out all Documents that have been seen.
	 * Use the list containing allDocsSorted to print them out.
	 * 
	 * 
	 */
	
	
	private List<Document> intersection(List<Document> docs1, List<Document> docs2) {
		
		List <Document> sharedDocs = new ArrayList<>();
		
		for(Document doc1: docs1) {
			if(docs2.contains(doc1)) {
				sharedDocs.add(doc1);
			}
			
				
		}
		return sharedDocs;
		
		
	}
	
	private List<Integer> check_positionPairs(Token token1, Token token2, Document doc){
		
		List<Integer> positionPairs = new ArrayList<>();
		List<Integer> posToken1 = token1.getPositions(doc);
		List<Integer> posToken2 = token2.getPositions(doc);
		
		if(posToken1.size() < 0 || posToken2.size() < 0) {
			
			return positionPairs;
			
		}
		
		for(Integer pos1: posToken1) {
			if(posToken2.contains(pos1 + 1)) {
				//Integer pos2 = posToken2.get(pos1 + 1);
				positionPairs.add(pos1);
			}	
		}
		return positionPairs;
		
		
		
	}
	public void printOutAllDocs() {
		
		//TODO - printOutAllDocs
		
		for(Document doc: this.allDocsSorted) {
			System.out.println("DocID: " + doc.getID() + " DocName: " + doc.getName());
			System.out.println();
			
		}
	
	}
	
}
