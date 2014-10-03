/*
 NameScraper - a utility to look up the name of a member of the University
 Of Southampton ECS Department using their email id. 
 Copyright (C) 2014 Tom Eccles
 
 This file is part of NameScraper.
 
 NameScraper is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.
 
 NameScraper is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.
 
 You should have received a copy of the GNU General Public License
 along with NameScraper.  If not, see <http://www.gnu.org/licenses/>.
*/

// for getting user input
import java.util.Scanner;

// for input validation
import java.util.regex.Pattern; 
import java.util.regex.Matcher;


public class Main {
	private static Scanner stdin = new Scanner(System.in);
	
	// entry point
	public static void main(String[] args) {
		System.out.println("Returning " + inputUserName());

	}
	
	// get and validate the input
	private static String inputUserName() { // it is probably not correct OO programming to put this here... TODO: don't do this 
		String input;
		Pattern emailPattern = Pattern.compile("^[A-Za-z]+([0-9]+[a-zA-Z][0-9]+)?@(ecs.)?(soton|southampton).ac.uk$"); // people can't have non-Latin characters, right?
		Pattern userNamePattern = Pattern.compile("^[A-Za-z]+([0-9]+[a-zA-Z][0-9]+)?$");
		Matcher emailMatcher;
		Matcher userNameMatcher;
		
		while (true) { // loop forever until correct input
			System.out.print("Please enter either an email address or a username to query...");
			input = stdin.nextLine();
			
			emailMatcher = emailPattern.matcher(input);
			userNameMatcher = userNamePattern.matcher(input);
			
			if (emailMatcher.find()) { // we were given a valid email
				return input.substring(0, input.indexOf("@")); // this has to work or the regex wouldn't have matched
			} else if (userNameMatcher.find()) { // we were given a valid username
				return input;
			} else { // we don't like the input
				System.out.println("Sorry I did not understand your input. Try again."); // this will loop around
			}
			
		}
	}
}
