// Copyright 2017 Google Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.codeu.codingchallenge;

import java.io.IOException;
import java.util.Stack;

final class MyJSONParser implements JSONParser {
	
	// Input string "in" converted to stack
	private Stack<Object> inputStringStack = new Stack<Object>();
	
  @Override
  public JSON parse(String in) throws IOException {
	
	// new MyJSON object
	MyJSON returnJSON = new MyJSON();
	  
	// boolean used to check if character is within quotes
	boolean withinQuotes = false;
	
	// boolean used to check escaped characters
	boolean escapeChar = false;
	
	// temporary string used to hold extracted string values
	String tempString = new String();
	
	// go through input string character by character
	for (int i = 0; i < in.length(); i++) {
		if (withinQuotes == false) {
			// switch for text outside of quotes
			switch(in.charAt(i)) {
				case '"':
					withinQuotes = true;
					break;
				case '{':
					inputStringStack.push(String.valueOf('{'));
					break;
				// evaluate up to first '{' found in stack if '}' is found in string
				case '}':
					evaluate();
					break;
				case ':':
					inputStringStack.push(String.valueOf(':'));
					break;
				case ',':
					inputStringStack.push(String.valueOf(','));
					break;
				// the number of whitespace characters outside of quotes doesn't matter
				case ' ':
					break;
				// there shouldn't be any other characters outside of quotes
				default:
					  throw new IOException("Invalid string!");
			}
		}
		else {
			if(escapeChar == false) {
				// switch for text inside of quotes (no escaped characters)
				switch(in.charAt(i)) {
					// if '"', push completed tempString into stack
					case '\"':
						withinQuotes = false;
						inputStringStack.push(tempString);
						tempString = new String();
						break;
					// if '\', treat next character as escaped character
					case '\\':
						tempString += in.charAt(i);
						escapeChar = true;
						break;
					// otherwise add the character to tempString
					default:
						if (withinQuotes == true) {
							tempString += in.charAt(i);
							break;
						}
				}
			}
			else {
				// switch for text inside of quotes (escaped characters)
				switch(in.charAt(i)) {
					// if character can be escaped, add it to tempString
					case '\"':
					case '\\':
					case 't':
					case 'n':
						tempString += in.charAt(i);
						escapeChar = false;
						break;
					// if character can't be escaped, throw exception
					default:
						throw new IOException("Invalid escape sequence!");
				}
			}
		}
	}
	// resulting MyJSON should be the last object in the stack
	if (inputStringStack.size() == 1) {
		returnJSON = (MyJSON)inputStringStack.pop();
		return returnJSON;
	}
	else {
		throw new IOException("Stack is not empty!");
	}
  }
  
  private void evaluate() throws IOException {
	  
	  // new myJSON object that will be added to stack
	  MyJSON newJSON = new MyJSON();
	  
	  // temporary JSON object used for string-object pairs
	  MyJSON tempJSON = new MyJSON();
	  
	  // temporary value string used for string-string pairs
	  String newValue = new String();
	  
	  // temporary key string
	  String newKey = new String();
	  
	  // boolean check if value is object
	  boolean isObject = false;
	  
	  // boolean to check if object is completed
	  boolean objectComplete = false;
	  
	  // until '{' is found in the stack...
	  while (objectComplete == false) {
		  if(inputStringStack.peek() instanceof String) {
			  // if the top of the stack is an opening bracket, push complete object to stack
			  if(inputStringStack.peek().equals(String.valueOf('{'))) {
				  objectComplete = true;
				  inputStringStack.pop();
				  inputStringStack.push(newJSON);
			  }
			  // if the top of the stack is a comma, continue adding key-value pairs
			  else if(inputStringStack.peek().equals(String.valueOf(','))) {
				  inputStringStack.pop();
				  newValue = (String)inputStringStack.pop();
			  }
			  // if the top of the stack is a colon, something is out of place
			  else if (inputStringStack.peek().equals(String.valueOf(':'))) {
					throw new IOException("Colon out of place!");
			  }
			  // if the top of the stack is a regular string, create string-string pair
			  else {
				  newValue = (String)inputStringStack.pop();
			  }
		  }
		  // if the top of the stack is an object, create string-object pair
		  else {		  
			  isObject = true;
			  tempJSON = (MyJSON)inputStringStack.pop();
		  }
		  // the next item should be a colon
		  if(inputStringStack.peek().equals(String.valueOf(':'))) {
			  inputStringStack.pop();
			  newKey = (String)inputStringStack.pop();
		  }
		  else if(inputStringStack.peek().equals(String.valueOf('{'))) {
				throw new IOException("Key-Value pair missing colon!");
		  }
		  else if(inputStringStack.peek().equals(String.valueOf(','))) {
				throw new IOException("Key-Value pair missing colon!");
		  }
		  // if the value is a JSON object, setObject
		  if(isObject == true) {
			  newJSON.setObject(newKey, tempJSON);
		  }
		  // otherwise, setString
		  else {
			  newJSON.setString(newKey, newValue);
		  }
	  }
  }
  
}
