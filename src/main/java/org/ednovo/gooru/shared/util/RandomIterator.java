package org.ednovo.gooru.shared.util;

import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;


public class RandomIterator {
	//data members
		Vector used; //records the numbers that have been generated so far
		int max; //highest number generated will be max-1
		Random select = new Random();
		 
		//constructor
		public RandomIterator(int max) {
		used = new Vector(max);
		this.max = max;
		}
		 
		//returns next random number, or -1 if all possible numbers have been generated
		public int next() {
		int next=-1;
		boolean foundInt = false;
		if (used.size() == max)
		return -1;
		while (!foundInt) {
		next = select.nextInt(max);
		if (!used.contains(new Integer(next))) { //check whether number has already been generated 
		used.add(new Integer(next));
		foundInt = true;
		}
		}
		return next;
		}
		 
		//test code
		public static ArrayList<Integer> getRandomList(int size) {
		Integer[] set = getRandomNumArray(size);
		
		ArrayList<Integer> resultSet=new ArrayList<Integer>();
		RandomIterator ri = new RandomIterator(set.length);
		int next = ri.next();
		int k=0;
		while (next != -1) {
		if (next == -1)
		break;
		resultSet.add(set[next]);
		k++;
		next = ri.next();
		}
		return resultSet;
		}
		
		
		public static Integer[] getRandomNumArray(int size){
			
			Integer[] randomNum = new Integer[size];
			
			for(int i=0;i<randomNum.length;i++){
				randomNum[i]=new Integer(i);
			}
			return randomNum;
		}
		
        public static String[] getRandomStringArray(int size){
			
        	String[] randomNum = new String[size];
			
			for(int i=0;i<randomNum.length;i++){
				randomNum[i]=new String(String.valueOf(i));
			}
			return randomNum;
		}
}
