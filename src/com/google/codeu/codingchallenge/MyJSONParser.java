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
	
	// temporary string used to hold string values
	String tempString = new String();
	
	// iterate through string
	for (int i = 0; i < in.length(); i++) {
		// if the character at index i is...
		switch(in.charAt(i)) {
			// if quotes, mark that scanning through quote
			case '"':
				if (withinQuotes == false) {
					withinQuotes = true;
					break;
				}
				// if already in quotes, push string into stack
				else {
					withinQuotes = false;
				    inputStringStack.push(tempString);
					tempString = new String();
					break;
				}
			// if opening bracket, push into stack
			case '{':
				//inputStringStack.push('{');
				break;
			// if closing bracket, evaluate stack until '{'
			case '}':
				returnJSON = (MyJSON)evaluate();
				break;
			// if colon, push into stack
			case ':':
				inputStringStack.push(':');
				break;
			// if comma, push into stack
			case ',':
				inputStringStack.push(',');
				break;
			// if in quotes, add char to tempstring
			default:
				if (withinQuotes == true) {
					tempString += in.charAt(i);
					break;
				}
		}
	}
	return returnJSON;
  }
  
  private JSON evaluate() {
	  
	  // new myJSON object
	  MyJSON newJSON = new MyJSON();
	  // temporary JSON object
	  MyJSON tempJSON = new MyJSON();
	  // temporary value string
	  String newValue = new String();
	  //temporary key string
	  String newKey = new String();
	  //check if value is object
	  boolean isObject = false;
	  
	  if(inputStringStack.isEmpty()) {
		  return newJSON;
	  }
	  // the top of the stack should be a value
	  if(inputStringStack.peek() instanceof String) {
		System.out.println(inputStringStack.peek());
		  newValue = (String)inputStringStack.pop();
	  }
	  else {
		  isObject = true;
		  tempJSON = (MyJSON)inputStringStack.pop();
	  }
	  // the next item should be a colon
	  if((char)inputStringStack.pop() == ':') {
		System.out.println(inputStringStack.peek());
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
	return newJSON;
  }
}
