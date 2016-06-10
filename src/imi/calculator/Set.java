package imi.calculator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class Set {

	private ArrayList<Integer> listOfIntegers;
	
	public Set() {
		listOfIntegers = new ArrayList<Integer>();
	}
	
	public void clear() {
		listOfIntegers.clear();
	}
	
	public boolean contains(int toCheck){
		for(int current : listOfIntegers){
			if(current == toCheck){
				return true;
			}
		}
		return false;
		
	}
	
	public void add(int toAdd){
		if(!contains(toAdd)){
			listOfIntegers.add(toAdd);
		}
		Collections.sort(listOfIntegers);
	}
	
	public void remove(int toRemove){
		if(contains(toRemove)){
			for (Iterator iterator = listOfIntegers.iterator(); iterator.hasNext();) {
				Integer integer = (Integer) iterator.next();
				if(integer == toRemove){
					iterator.remove();
				}

			}
		}

	}
	
	public boolean isEmpty(){
		return listOfIntegers.isEmpty();
	}
	
	public String toString(){
	
		String result = "{ ";
		for(int current : listOfIntegers){
			result += current + ", ";
		}
		if(!isEmpty()){
			result = result.substring(0, result.length()-2);
		}
		result += " }";
		return result;
	}
	
	public static void main(String[] args) {
		Set mySet = new Set();
		mySet.add(12);
		mySet.add(120000);
		mySet.add(1233);
		mySet.add(22);
		System.out.println("First add of 12 " + mySet.toString());
		mySet.add(7432); 
		System.out.println("After the add of 7432 " + mySet.toString());
		System.out.println("Print again " + mySet.toString());
		mySet.remove(1233);
		System.out.println("After remove of 1233 " + mySet.toString());
		mySet.remove(16);
		System.out.println("After remove of 16 " + mySet.toString());
		mySet.clear();
		System.out.println("After remove with clear " + mySet.toString());
		mySet.remove(12);
	}
}

