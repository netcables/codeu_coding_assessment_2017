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
	
	// JSON object to be returned
	MyJSON returnJSON = new MyJSON();
	  
	// boolean used to check if within quotes
	boolean withinQuotes = false;
	
	// boolean used to check escape characters
	boolean escapeChar = false;
	
	// temporary string used to hold string values
	String tempString = new String();
	
	for (int i = 0; i < in.length(); i++) {
		if (withinQuotes == false) {
			// switch for text outside of quotes
			switch(in.charAt(i)) {
				case '"':
					withinQuotes = true;
					break;
				// if opening bracket, push into stack
				case '{':
					inputStringStack.push(String.valueOf('{'));
					break;
				// if closing bracket, evaluate stack until '{'
				case '}':
					evaluate();
					break;
				// if colon, push into stack
				case ':':
					inputStringStack.push(String.valueOf(':'));
					break;
				// if comma, push into stack
				case ',':
					inputStringStack.push(String.valueOf(','));
					break;
				case ' ':
					break;
				// there shouldn't be anything else outside of quotes
				default:
					  throw new RuntimeException("String formatted incorrectly!");
			}
		}
		else {
			if(escapeChar == false) {
				// switch for text inside of quotes (no escaped characters)
				switch(in.charAt(i)) {
					case '"':
						withinQuotes = false;
						inputStringStack.push(tempString);
						tempString = new String();
						break;
					case '\\':
						tempString += in.charAt(i);
						escapeChar = true;
						break;
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
					case '\"':
					case '\\':
					case 't':
					case 'n':
						tempString += in.charAt(i);
						escapeChar = false;
						break;
					default:
						  throw new RuntimeException("Character escaped incorrectly!");
				}
			}
		}
	}
	// resulting MyJSON should be the last object in the stack
	returnJSON = (MyJSON)inputStringStack.pop();
	return returnJSON;
  }
  
  private void evaluate() {
	  // new myJSON object
	  MyJSON newJSON = new MyJSON();
	  // temporary JSON object
	  MyJSON tempJSON = new MyJSON();
	  // temporary value string
	  String newValue = new String();
	  // temporary key string
	  String newKey = new String();
	  // check if value is object
	  boolean isObject = false;
	  // check if object is finished
	  boolean objectComplete = false;
	  
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
		  if(inputStringStack.pop().equals(String.valueOf(':'))) {
			  newKey = (String)inputStringStack.pop();
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
	  // push the new JSON object back into the stack
	  inputStringStack.push(newJSON);
  }
  
}
