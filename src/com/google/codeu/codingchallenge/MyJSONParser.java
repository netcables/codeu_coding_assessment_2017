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
import java.util.Scanner;
import java.util.Stack;

final class MyJSONParser implements JSONParser {

	// the provided string in stack form
    private Stack<Object> inputStringStack;
    
  @Override
  public JSON parse(String in) throws IOException {
    // TODO: implement this
	  
	  // new empty MyJSON
	  MyJSON newJSON = new MyJSON();
	  
	  // scanner used for going through input string
	  Scanner inputStringScanner = new Scanner(in);
	  inputStringScanner = inputStringScanner.useDelimiter("\\s*");
	  
	  // temporary strings used for object creation
	  
	  if (inputStringScanner.hasNext()){
          String aToken = inputStringScanner.next();
          char item = aToken.charAt(0);
	  }
    return newJSON;
  }
  
}
