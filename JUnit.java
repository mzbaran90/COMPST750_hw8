
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JUnit {

	Indexer r;
	Document d1;
	Document d2;
	Document d3;

	private Object getField(Object instance, String name) throws Exception {
		Class c = instance.getClass();
		Field f = c.getDeclaredField(name);
		f.setAccessible(true);
		return f.get(instance);
	}

	@Before
	public void setUp() {

		d1 = new Document(1, "Test1.txt");
		d2 = new Document(2, "Test2.txt");
		d3 = new Document(3, "Test3.txt");

		r = new Indexer();

		//Simple set of String "documents" to test.
		r.indexDocument("<Test1.txt>Testing if this works. The documents are very short for easier debugging.");
		r.indexDocument("<Test2.txt>Adding for stuff to index, the word works should be added twice.");
		r.indexDocument("<Test3.txt>The final string to add to see if it works! Random punctuati.on i!s added? along with works again.");

	}


	@After
	public void tearDown(){
		r = null;
		d1 = null;
		d2 = null;
		d3 = null;
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testAllTokens() throws Exception {

		//Get allTokens HashMap from ReversedIndex r variable after adding several documents.
		HashMap<String,Token> allTokensCopy = (HashMap<String, Token>) getField(r,"allTokens");
		Token tmpToken;
		Token tmpTokenEquals = new Token("testing");
		
		assertTrue(allTokensCopy.containsKey("testing"));				//Confirm String "testing" exists in allTokens.
		tmpToken = allTokensCopy.get("testing");						//Retrieve token from allTokens.
		assertEquals(tmpToken.toString(),"testing");					//Test toString of Token class.
		assertTrue(tmpToken.equals(tmpTokenEquals));
		
		tmpToken = r.checkToken("testing");								//Test checkToken
		assertTrue(tmpToken.equals(tmpTokenEquals));					//Test equals method of Token class.
		
		assertTrue(allTokensCopy.containsKey("add"));					//Tests repeat similar to above.  Equals not tested more.
		tmpToken = allTokensCopy.get("add");
		assertEquals(tmpToken.toString(),"add");
		
		tmpToken = r.checkToken("add");									//Test checkToken one more time.
		assertEquals(tmpToken.toString(), "add");

		assertTrue(allTokensCopy.containsKey("works"));
		tmpToken = allTokensCopy.get("works");
		assertEquals(tmpToken.toString(),"works");

		assertTrue(allTokensCopy.containsKey("random"));
		tmpToken = allTokensCopy.get("random");
		assertEquals(tmpToken.toString(),"random");

		//Following tests should all fail since none of these terms existed in the documents.
		assertFalse(allTokensCopy.containsKey("nate"));
		assertFalse(allTokensCopy.containsKey("cat"));
		assertFalse(allTokensCopy.containsKey("Random"));
		assertFalse(allTokensCopy.containsKey("index,"));
		assertFalse(allTokensCopy.containsKey("Testing"));
		assertFalse(allTokensCopy.containsKey("added?"));
		assertFalse(allTokensCopy.containsKey("i!s"));

	}

	@SuppressWarnings("unchecked")
	@Test
	public void testAllDocs() throws Exception {

		//Get allDocs HashMap from ReversedIndex r variable after adding several documents.
		HashMap<String,Document> allDocsCopy = (HashMap<String, Document>) getField(r,"allDocs");
		Document tmpDoc;

		assertTrue(allDocsCopy.containsKey("Test1.txt"));
		tmpDoc = allDocsCopy.get("Test1.txt");
		assertTrue(tmpDoc.equals(d1));						//Test equals method of Document class

		assertTrue(allDocsCopy.containsKey("Test2.txt"));
		tmpDoc = allDocsCopy.get("Test2.txt");
		assertTrue(tmpDoc.equals(d2));						//Test equals method of Document class

		assertTrue(allDocsCopy.containsKey("Test3.txt"));
		tmpDoc = allDocsCopy.get("Test3.txt");
		assertTrue(tmpDoc.equals(d3));						//Test equals method of Document class

		assertFalse(tmpDoc.equals(d1));						//Test equals method of Document class
		assertFalse(tmpDoc.equals(d2));						//Test equals method of Document class

	}

	@SuppressWarnings("unchecked")
	@Test
	public void testReversedIndex() throws Exception {

		HashMap<Token,List<Document>> revIndex = (HashMap<Token,List<Document>>) getField(r,"reversedIndex");
		HashMap<String,Token> allT = (HashMap<String, Token>) getField(r,"allTokens");
		Token tmpToken;
		List<Document> tmpList;

		List<Document> docList12  = new ArrayList<>();			//Fill list with documents 1 and 2
		docList12.add(d1);
		docList12.add(d2);

		List<Document> docList23= new ArrayList<>();			//Fill list with documents 2 and 3
		docList23.add(d2);
		docList23.add(d3);

		List<Document> docList123= new ArrayList<>();			//Fill list with documents 1,2, and 3.
		docList123.add(d1);
		docList123.add(d2);
		docList123.add(d3);

		assertTrue(allT.containsKey("for"));					//Confirm String "for" exists.
		tmpToken = allT.get("for");								//Retrieve token from allTokens.
		assertTrue(revIndex.containsKey(tmpToken));				//Confirm token exists in reversed index.
		tmpList = revIndex.get(tmpToken);						//Get the list of docs associated with token.
		assertEquals(docList12,tmpList);						//List should contain documents 1 and 2.

		assertTrue(allT.containsKey("added"));					//Tests repeat for other tokens.
		tmpToken = allT.get("added");
		assertTrue(revIndex.containsKey(tmpToken));
		tmpList = revIndex.get(tmpToken);
		assertEquals(docList23,tmpList);

		assertTrue(allT.containsKey("the"));
		tmpToken = allT.get("the");
		assertTrue(revIndex.containsKey(tmpToken));
		tmpList = revIndex.get(tmpToken);
		assertEquals(docList123,tmpList);

	}

	@SuppressWarnings("unchecked")
	@Test
	public void testTokenPositions() throws Exception {

		HashMap<String,Token> allTokensCopy = (HashMap<String, Token>) getField(r,"allTokens");
		HashMap<String,Document> allDocsCopy = (HashMap<String, Document>) getField(r,"allDocs");
		List<Integer> tmpList = new ArrayList<>();

		Token tmpToken = allTokensCopy.get("documents");				//Get token associated with "documents".
		Document tmpDoc = allDocsCopy.get("Test1.txt");					//Get Document 1.
		List<Integer> tokenPositions = tmpToken.getPositions(tmpDoc);	//Get postions of token in Document 1.
		assertEquals(tokenPositions.size(), 1);							//"documents" occurs once, should only have 1 position.
		tmpList.add(6);													//"documents" should occur at spot 6 in document.
		assertEquals(tmpList,tokenPositions);							//compare tmpList to poistions of "documents"

		tmpDoc = allDocsCopy.get("Test2.txt");							//Change to Document 2.
		tokenPositions = tmpToken.getPositions(tmpDoc);					//the Token "documents" does not occur in Test2.txt
		assertEquals(null,tokenPositions);								//Should be null.

		tmpToken = allTokensCopy.get("works");							//Repeat tests with new Tokens.
		tmpDoc = allDocsCopy.get("Test1.txt");
		tokenPositions = tmpToken.getPositions(tmpDoc);
		tmpList.clear();												//Reset list to compare to.
		tmpList.add(4);
		assertEquals(tokenPositions.size(), 1);
		assertEquals(tmpList,tokenPositions);

		tmpDoc = allDocsCopy.get("Test2.txt");
		tokenPositions = tmpToken.getPositions(tmpDoc);
		tmpList.clear();												//Reset list to compare to.
		tmpList.add(8);
		assertEquals(tokenPositions.size(), 1);
		assertEquals(tmpList,tokenPositions);

		tmpDoc = allDocsCopy.get("Test3.txt");
		tokenPositions = tmpToken.getPositions(tmpDoc);
		tmpList.clear();												//Reset list to compare to.
		tmpList.add(10);
		tmpList.add(17);
		assertEquals(tokenPositions.size(), 2);
		assertEquals(tmpList,tokenPositions);
	}
	
	@Test
	public void removePunctuation() {
		
		Indexer ir = new Indexer();
		String result = ir.removePunctuation("same");
		assertTrue("same".equals(result));
		
		result = ir.removePunctuation("  spaces       ");
		assertTrue("spaces".equals(result));
		
		result = ir.removePunctuation("WoN't");
		assertTrue("won't".equals(result));
		
		result = ir.removePunctuation("com,ma");
		assertTrue("comma".equals(result));
	
		result = ir.removePunctuation("period.");
		assertTrue("period".equals(result));
		
		result = ir.removePunctuation("!exclamation!");
		assertTrue("exclamation".equals(result));
		
		result = ir.removePunctuation("?quest?ion");
		assertTrue("question".equals(result));
		
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void checkToken() throws Exception {
		
		Indexer ir = new Indexer();
		HashMap<String,Token> allTokensCopy = (HashMap<String, Token>) getField(ir,"allTokens");

		assertFalse(allTokensCopy.containsKey("test"));			//"test" should exist in allTokensCopy
		Token token = ir.checkToken("test");					//after calling checkToken, "test" is now a token.
		assertTrue(allTokensCopy.containsKey("test"));			//checkToken should have added "test" to allTokenscopy
		assertEquals(token.toString(),"test");
		
		Token token2 = ir.checkToken("test");					//If calling checkToken again with "test", same token should be returned
		assertTrue(token == token2);
		
	}

}
