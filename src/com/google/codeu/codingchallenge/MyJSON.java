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

import java.util.Collection;
import java.util.HashMap;

final class MyJSON implements JSON {
	
	// HashMap containing keys and values (with values being either MyJSON or Strings).
	private HashMap<String, Object> myPairs = new HashMap<String, Object>();
	
  @Override
  public JSON getObject(String name) {
    // TODO: implement this
	  // if MyPairs contains a value matching this key...
	  if (myPairs.containsKey(name)) {
		  // if the value matching the key is a string...
		  if (myPairs.get(name) instanceof String) {
			  // the value must be an object
			  return null;
		  }
		  // otherwise it must be a string
		  else {
			  return (JSON)myPairs.get(name);
		  }
	  }
	  // if not, there aren't any objects here
	  else {
		  return null;
	  }
  }

  @Override
  public JSON setObject(String name, JSON value) {
    // TODO: implement this
	  if (myPairs.containsKey(name)) {
		  if (myPairs.get(name) instanceof String) {
			  // it's not an object
		  }
		  else {
			  myPairs.replace(name, value);
		  }
	  }
	  else {
		  myPairs.put(name, value);
	  }
    return this;
  }

  @Override
  public String getString(String name) {
    // TODO: implement this
	// if MyPairs contains a value matching this key..
	  if (myPairs.containsKey(name)) {
		// if the value matching the key is a string...
		  if (myPairs.get(name) instanceof String) {
			  // return the string
			  return (String)myPairs.get(name);
		  }
		// otherwise it must be an object
		  else {
			  return null;
		  }
	  }
	// if not, there aren't any strings here
	  else {
		  return null;
	  }
  }

  @Override
  public JSON setString(String name, String value) {
    // TODO: implement this
	  if (myPairs.containsKey(name)) {
		  if (myPairs.get(name) instanceof String) {
			  myPairs.replace(name, value);
		  }
		  else {
			  // it's not a string
		  }
	  }
	  else {
		  myPairs.put(name, value);
	  }
    return this;
  }

  @Override
  public void getObjects(Collection<String> names) {
    // TODO: implement this
  }

  @Override
  public void getStrings(Collection<String> names) {
    // TODO: implement this
  }
}
