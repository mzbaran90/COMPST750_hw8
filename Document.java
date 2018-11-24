
public class Document {

	private int docID;
	private String docName;
	
	//constructor
	public Document(int docID, String docName) {
		this.docID= docID;
		this. docName = docName;
		
	}
	public String getName() {
		return this.docName;
		
	}
	
	public int getID() {
		return this.docID;
		
	}
	
	public String toString() {
		return Integer.toString(this.docID);
		
	}
	
	@Override
	public boolean equals(Object O) {
		
		if (O == null){
			return false;
		}
		if(O == this) {
			return true;
		}
		if(O instanceof Document) {
			Document tempDoc = (Document)O;
			return this.docID == tempDoc.docID && this.docName.equals(tempDoc.docName);
			
		}
		return false;
	}
	
}
