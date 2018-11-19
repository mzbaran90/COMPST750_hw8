import java.util.Scanner;

public class Driver {

	/**
	 * A driver that creates a Reversed Index.
	 * Fills the Reversed Index with Tokens.
	 * Then performs a query that only works with single terms.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		Scanner scr = new Scanner(System.in);
		Indexer rIndex = new Indexer();

		//Add String "documents" to the Indexer
		for(String doc: getWisconsinFacts()) {
			rIndex.indexDocument(doc);
		}

		System.out.println("The documents have been indexed.  If done correctly, all 10 documents should appear below, with DocID and DocName shown correctly.");
		rIndex.printOutAllDocs();
		System.out.println();


		String query = "";
		String[] qArray;
		while(!query.equals("quit")) {

			if(!query.isEmpty()) {
				qArray = query.split(" ");
				if(qArray.length == 1)
					rIndex.singleQuery(query.toLowerCase());
				else if(qArray.length == 2) {
					rIndex.twoWordQuery(qArray);
				} else {
					System.out.println("\nError in query entry.\n");
				}
			} else {
				System.out.println("\nYou did not enter anything.\n");
			}

			System.out.print("Please enter a query to search for or type 'quit': ");
			query = scr.nextLine();

		}

		System.out.println("Goodbye");
		scr.close();

	}


	/**
	 * A simple method to return an array of Strings.
	 * Each String represents a document, where the document name is contained in the "<docName>".
	 * The contents after the docName are the file contents.
	 * I've placed a bunch of facts about Wisconsin to represent the file contents.
	 * 
	 * @return
	 */
	public static String[] getWisconsinFacts() {

		String[] phrases = new String[13];

		//ID = 1
		phrases[0] = "<Milwaukee.txt>Milwaukee is the largest city in Wisconsin. Population is 595,000. It is home of the Brewers, Bucks, and the Fonz.";
		//ID = 2
		phrases[1] = "<DeerHunting.txt>Deering hunting season is a big past time for many residents in Wisconsin in November. There are approximately 800,000 deer that roam wisconsin. If all of the hunter's on opening day of deer season in Wisconsin were grouped together, they would comprise the sixth largest army in the world.";
		//ID = 3
		phrases[2] = "<Madison.txt>Madison is the capital of Wisconsin, but it wasn't always. Belmont was the original. The capitol was established in 1836, when Wisconsin was not yet a state but a territory. You can still visit the Council House and a lodging house for then legislators at this historic site just west of Belmont Mound State Park.";
		//ID = 4
		phrases[3] = "<Brewers.txt>The Milwaukee Brewers are a major league baseball team. Their home stadium is Miller Park which can seat 45,000 cheering fans.";
		//ID = 5
		phrases[4] = "<CitizenKane.txt>The creator of what many consider the greatest movie ever made, Citizen Kane, did not hail from Hollywood. He was an export of Wisconsin. Orson Welles was born in Kenosha and went on to become an accomplished writer, producer and director. His works have appeared on Broadway, in legendary films and in the production of an infamous radio broadcast.";
		//ID = 6
		phrases[5] = "<Brats.txt>Most Wisconsinite’s know that the World’s Largest Brat Fest is located in Madison every Memorial Day weekend. But, not nearly as many know that Sheboygan is also known as the Bratwurst Capitol of the World. Sheboygan is where I am from and currently live. Yes, I commute from there to campus.";
		//ID = 7
		phrases[6] = "<HappyDays.txt>Happy Days was a famous TV show during the 1970's. Two of the stars were Ron Howard and Henry Wrinkler, aka the Fonz.";
		//ID = 8
		phrases[7] = "<Exports.txt>Known for her dairy production, Wisconsin actually leads the nation in exports of cranberries, whey, ginseng root and sweet corn.";
		//ID = 9
		phrases[8] = "<Lakes.txt>Minnesota’s official motto may be the “Land of 10,000 Lakes”, but Wisconsin is not one to brag. The lake count comes in somewhere over 15,000, but the Wisconsin DNR modestly publishes a listing of 16,692 lakes.";
		//ID = 10
		phrases[9] = "<GreenBayPackers.txt>The Green Bay Packers are a professional football team in Green Bay, Wisconsin. Their home stadium is the legendary Labeau Field, where the Ice Bowl was played in 1967. The game-time temperature at Lambeau Field was about -15F (-26C), with an average wind chill around -48F (-44C). It is easy to say the Packers have some devoted fans.";
		//When indexing, these files should be viewed as duplicates and not added to the reversed index.
		phrases[10] = "<Milwaukee.txt>Milwaukee is the largest city in Wisconsin. Population is 595,000. It is home of the Brewers, Bucks, and the Fonz.";
		phrases[11] = "<DeerHunting.txt>Deering hunting season is a big past time for many residents in Wisconsin in November. There are approximately 800,000 deer that roam wisconsin. If all of the hunter's on opening day of deer season in Wisconsin were grouped together, they would comprise the sixth largest army in the world.";
		phrases[12] = "<Madison.txt>Madison is the capital of Wisconsin, but it wasn't always. Belmont was the original. The capitol was established in 1836, when Wisconsin was not yet a state but a territory. You can still visit the Council House and a lodging house for then legislators at this historic site just west of Belmont Mound State Park.";

		return phrases;

		//Websites facts were obtained from.
		//http://bobber.discoverwisconsin.com/10-facts-probably-didnt-know-wisconsin/
		//https://www.wisconsinhistory.org/Records/Article/CS2906
	}

}
