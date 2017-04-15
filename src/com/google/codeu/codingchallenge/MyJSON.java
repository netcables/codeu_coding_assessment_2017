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
import java.util.Map;

final class MyJSON implements JSON {
	
	// HashMap containing JSON-lite strings
	private Map<String, String> stringMap = new HashMap<>();
	
	// HashMap containing JSON-lite objects
	private Map<String, JSON> objectMap = new HashMap<>();
	
  @Override
  public JSON getObject(String name) {
	  // if the object isn't in this object, state that it wasn't found
	  if (objectMap.containsKey(name)) {
		  return objectMap.get(name);
	  }
	  else {
		  throw new NullPointerException("The object \"" + name + "\" was not found!");
	  }
  }

  @Override
  public JSON setObject(String name, JSON value) {
	  // if the key for this object doesn't already exist, add it
	  if (objectMap.containsKey(name)) {
		  objectMap.replace(name, value);
	  }
	  else {
		  objectMap.put(name, value);
	  }
	  return this;
  }

  @Override
  public String getString(String name) {
	  // if the string isn't in this object, state that it wasn't found
	  if (stringMap.containsKey(name)) {
		  return stringMap.get(name);
	  }
	  else {
		  throw new NullPointerException("The string \"" + name + "\" was not found!");
	  }
  }

  @Override
  public JSON setString(String name, String value) {
	  // if the key for this string doesn't already exist, add it
	  if (stringMap.containsKey(name)) {
		  stringMap.replace(name, value);
	  }
	  else {
		  stringMap.put(name, value);
	  }
	  return this;
  }

  @Override
  public void getObjects(Collection<String> names) {
	  names = objectMap.keySet();
  }

  @Override
  public void getStrings(Collection<String> names) {
	  names = stringMap.keySet();
  }
}
