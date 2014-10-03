import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Pattern; 
import java.util.regex.Matcher;

// scrapes for data 
public class pageScraper {
	private String userName = null;
	private String Name= null;
	private URL personPageURL = null;
	
	public pageScraper(String userName) {
		setUserName(userName);
	}
	
	private void updateVariables() { // called after a change to userName so that everything can be set for the new userName
		BufferedReader reader = null;
		String currLine = null;
		String tag = null;
		
		// first generate the new URL
		try {
			//System.out.println("URL will be http://www.ecs.soton.ac.uk/people/" + this.getUserName());
			setPersonPageURL(new URL("http://www.ecs.soton.ac.uk/people/" + this.getUserName()));
		} catch (MalformedURLException e) { // a bad has happened so die with a message
			System.out.println("ERROR: Malformed url in updateVariables() in pageScraper");
			e.printStackTrace();
			System.exit(1);
		}
		
		// now fetch the page
		try {
			reader = new BufferedReader(new InputStreamReader(this.getPersonPageURL().openStream()));
		} catch (IOException e) { // a bad happened so die with a message
			System.out.println("ERROR: Failed to fetch the person page in updateVariables() in pageScraper");
			e.printStackTrace();
			System.exit(1);
		}
				
		// parse the web page. We are looking for <title>ECS - The Name</title>
		// for simplicity and laziness I am going to use a regular expression, however if getting lots of data it would be better to use an XML parser
		Pattern tagPattern = Pattern.compile("<title>[\\w\\s]+");
		Matcher tagMatcher;
		
		try {
			while ((currLine = reader.readLine()) != null) {
				tagMatcher = tagPattern.matcher(currLine);
				
				if (currLine.contains("<title>ECS - ECS Intranet Login</title>")) { // we have hit the login page
					System.out.println("ERROR: Sorry this application does not yet support dealing with the login page");
					System.exit(0);
				}
				
				if (currLine.contains("ECS People")) { // the person does not exist
					System.out.println("ERROR: that person does not exist");
					System.exit(1);
				}
				
				if (tagMatcher.find()) {
					System.out.println("match");
					tag = currLine.substring(tagMatcher.start(), tagMatcher.end());
					break;
				}
			}
		} catch (IOException e) {
			System.out.println("A bad happened when reading through the downloading page");
			e.printStackTrace();
			System.exit(1);
		}
		
		// finally get the name out of the tag
		this.setName(tag.substring(tag.indexOf(">") + 1));
	}
	
// getters and setters
	public String getUserName() {
		return userName;
	}
	
	private void setUserName(String userName) {
		this.userName = userName;
		
		// and now update everything else to match
		this.updateVariables();
	}
	
	public String getName() {
		return Name;
	}
	
	private void setName(String name) {
		Name = name;
	}
	
	private URL getPersonPageURL() {
		return this.personPageURL;
	}
	
	private void setPersonPageURL(URL x) {
		this.personPageURL = x;
	}
}
