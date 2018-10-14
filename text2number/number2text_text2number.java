package com.codility;

import org.junit.Test;
import org.junit.Assert;
import java.util.ArrayList;
import java.util.Stack;
import java.util.HashMap;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class Solution {

    public int main() {
		Converter m = new Converter();
		//examples
		String test2 = "seven hundred thirty eight";
		float ss = m.convertString(test2);
		System.out.println(ss);
		
		float a = 12231232.2f;
		String s = m.convertNumber(a);
		System.out.println(s);
		
		return 0;
    }



    public static class Tests {
        Converter c = new Converter();
        
        @Test
        public void sampleTest() {
            Assert.assertEquals((1+2), 3);
            new Solution().main();
        }
        
        @Test
    	public void runN2STests() {
    		String test0 = "eleven";
    		float testint0 = 11.0f;
    		assertEquals(test0, c.convertNumber(testint0).trim());
    
    		String test1 = "one hundred twenty two thousand five";
    		float testint1 = 122005.0f;
    		assertEquals(test1, c.convertNumber(testint1).trim());
    
    		String test2 = "nine million four hundred twenty two thousand fifty five";
    		float testint2 = 9422055.0f;
    		assertEquals(test2, c.convertNumber(testint2).trim());
    
    		String test3 = "eleven thousand one hundred eleven";
    		float testint3 = 11111.0f;
    		assertEquals(test3, c.convertNumber(testint3).trim());
    
    		String test4 = "eighty eight";
    		float testint4 = 88.0f;
    		assertEquals(test4, c.convertNumber(testint4).trim());
    
    	}
    
    	@Test
    	public void runS2NTests1() {
    		String test0 = "one hundred twenty two thousand five";
    		float testint0 = 122005.0f;
    		assertEquals(testint0, c.convertString(test0), 0.1f);
    
    		String test1 = "one hundred twenty two thousand five";
    		float testint1 = 122005.0f;
    		assertEquals(testint1, c.convertString(test1), 0.1f);
    
    		String test2 = "seven hundred thirty eight"; // 'seven hundred thirty eight point nine' -> 738.9f will fail due
    												     // to not implemented decimal (point) parsing..
    		float testint2 = 738.0f;
    		assertEquals(testint2, c.convertString(test2), 0.1f);
    
    	}
    }
        
    /**
    *
    * convertion is not application for large number such as 10^7 and bigger,
    *there are two methods for convertion, number to text and text to number
    */
    public static class Converter {
    
    	/**
    	 * Convert numbers to string sentences E.g: 123.2 -> 'one hundred twenty three point two'
    	 * 19992334 -> nineteen million nine hundred ninety two thousand three
    	 * hundred thirty three
    	 * 
    	 * Decimal implementation included. e.g: 1.1f will be 'one pint one'
    	 * 
    	 * @param number
    	 * @return
    	 */
    	public String convertNumber(float number) {
    		String result = "";
    		String[] cells; // 8797.123 cell1=8797, cell2=.123
    		ArrayList<String> parts = new ArrayList<>();
    
    		if (number % 1 > 0) {
    			cells = Float.toString(number).split("\\.");
    		} else {
    			cells = new String[1];
    			cells[0] = "" + (int) number;
    		}
    		for (int i = 0; i < cells.length; i++) {
    			System.out.println(cells[i]);
    		}
    		for (int i = 0; i < cells.length; i++) {
    			if (i == 1) {
    				result += "point ";
    			}
    
    			String cell = cells[i];
    			int count = (int) Math.ceil(cell.length() / 3.0); // each parts is a 000 format
    			while (count > 0) {
    				int len = cell.length();
    				if (len >= 3) {
    					String obj = cell.substring(len - 3, len);
    					parts.add(obj);
    					cell = cell.substring(0, len - 3);
    				} else {
    					String obj = cell.substring(0, len);
    					parts.add(obj);
    				}
    				count--;
    			}
    
    			Stack<String> stack = new Stack<>();
    			int turn = 0;
    
    			recurse(turn, parts, stack);
    
    			while (!stack.isEmpty()) {
    				result += stack.pop();
    			}
    		}
    
    		return result;
    	}
    
    	/**
    	 * Convert string sentence to numbers e.g: one hundred million fourty four ->
    	 * 100000044
    	 * 
    	 * No decimal part implementation included, e.g: 'one point one' will fail.
    	 * 
    	 * @param sentence
    	 * @return
    	 */
    	public float convertString(String sentence) {
    
    		String[] parts = formatInput(sentence);
    		int localResult = 0;
    		float result = 0.0f;
    		int turn = 0;
    
    		while (parts.length > turn) {
    			String part = parts[turn];
    			if (special(part)) {
    				if (part.equals(Constants.ions[0])) {
    					localResult = localResult * 100;
    				} else {
    					localResult = localResult * s2n(part);
    					result += localResult;
    					localResult = 0;
    				}
    			} else {
    				localResult += s2n(part);
    			}
    			turn++;
    		}
    		result += localResult;
    
    		return result;
    	}
    
    	/**
    	 * iterate over cells
    	 * 
    	 * @param turn
    	 * @param parts
    	 * @param result
    	 */
    	public void recurse(int turn, ArrayList<String> parts, Stack<String> result) {
    		StringBuilder localresult = new StringBuilder();
    
    		if (parts.size() > 0) {
    			String[] part1 = parts.remove(0).split("");
    
    			if (part1.length < 3) {
    				StringBuilder ss = new StringBuilder();
    				for (int i = 0; i < part1.length; i++) {
    					ss.append(part1[i]);
    				}
    				int val = Integer.parseInt(ss.toString());
    				if (val <= Constants.lastnumber)
    					localresult.append(n2s(val));
    				else {
    					int firstpart = Integer.parseInt(part1[0]);
    					int secondpart = Integer.parseInt(part1[1]);
    					localresult.append(n2s(firstpart * Constants.multiplierrate));
    					localresult.append(" ");
    					if (secondpart > 0) {
    						localresult.append(n2s(secondpart));
    					}
    				}
    				localresult.append(" ");
    				if (parts.size() > 0 || turn > 0) {
    					localresult.append(Constants.ions[turn]);
    					localresult.append(" ");
    				}
    			} else {
    				for (int i = 0; i < part1.length; i++) {
    					int val = Integer.parseInt(part1[i]);
    					if (val > 0) {
    						if (i == 0) {
    							localresult.append(n2s(val));
    							localresult.append(" ");
    							localresult.append(Constants.ions[i]);
    							localresult.append(" ");
    						}
    						if (i == 1) {
    							if (val == 1) {
    								String tenVal = part1[i] + part1[i + 1];
    								val = Integer.parseInt(tenVal);
    								localresult.append(n2s(val));
    								localresult.append(" ");
    								break;
    							} else {
    								localresult.append(n2s(val * Constants.multiplierrate));
    								localresult.append(" ");
    							}
    						}
    						if (i == 2) {
    							localresult.append(n2s(val));
    							localresult.append(" ");
    						}
    					}
    				}
    				if (turn > 0) {
    					localresult.append(Constants.ions[turn]);
    					localresult.append(" ");
    				}
    			}
    		}
    
    		turn++;
    		result.add(localresult.toString());
    
    		if (parts.size() > 0) {
    			recurse(turn, parts, result);
    		}
    	}
    
    	/**
    	 * format string sentence, remove empty spaces
    	 * 
    	 * @param sentence
    	 * @return
    	 */
    	private String[] formatInput(String sentence) {
    		String[] list = sentence.toLowerCase().split("\\s+");
    		return list;
    	}
    
    	/**
    	 * special ones are in 'ions' list, they are multipliers
    	 * 
    	 * @param arg
    	 * @return
    	 */
    	private boolean special(String arg) {
    		for (int i = 0; i < Constants.ions.length; i++) {
    			if (arg.equals(Constants.ions[i]))
    				return true;
    		}
    		return false;
    	}
    
    	/**
    	 * get string match of
    	 * 
         * if there is no match then throws exception
    	 * @param i
    	 * @return
    	 */
    	private String n2s(int i) {
    		return Constants.maps.get(i);
    	}
    
    	/**
    	 * get number match of
    	 * if there is no match then throws exception
    	 * @param i
    	 * @return
    	 */
    	private Integer s2n(String i) {
    		return Constants.mapn.get(i);
    	}
    
    }




    public static class Constants {
    
    	// special numbers used as multipliers
    	public static final String[] ions = { "hundred", "thousand", "million", "billion" };
    	public static final int multiplierrate = 10; // multiplier to create numbers 10,20,30 ... 90;
    	public static final int lastnumber = 19; // last distinct number
    
    	public static HashMap<Integer, String> maps = new HashMap<>();
    	public static HashMap<String, Integer> mapn = new HashMap<>();
    
    	private static String one = "one";
    	private static String two = "two";
    	private static String three = "three";
    	private static String four = "four";
    	private static String five = "five";
    	private static String six = "six";
    	private static String seven = "seven";
    	private static String eight = "eight";
    	private static String nine = "nine";
    	private static String zero = "zero";
    	private static String ten = "ten";
    	private static String eleven = "eleven";
    	private static String twelwe = "twelwe";
    	private static String thirten = "thirten";
    	private static String fourten = "fourten";
    	private static String fiften = "fiften";
    	private static String sixten = "sixten";
    	private static String seventen = "seventen";
    	private static String eighten = "eighten";
    	private static String nineten = "nineten";
    	private static String twenty = "twenty";
    	private static String thrity = "thirty";
    	private static String fourty = "fourty";
    	private static String fifty = "fifty";
    	private static String sixty = "sixty";
    	private static String seventy = "seventy";
    	private static String eighty = "eighty";
    	private static String ninety = "ninety";
    	private static String hundred = "hundred";
    	private static String thousand = "thousand";
    	private static String million = "million";
    
    	static {
    		fillN2SMap();
    		fillS2NMap();
    	}
    
    	private static void fillN2SMap() {
    
    		maps.put(0, zero);
    		maps.put(1, one);
    		maps.put(2, two);
    		maps.put(3, three);
    		maps.put(4, four);
    		maps.put(5, five);
    		maps.put(6, six);
    		maps.put(7, seven);
    		maps.put(8, eight);
    		maps.put(9, nine);
    		maps.put(10, ten);
    		maps.put(11, eleven);
    		maps.put(12, twelwe);
    		maps.put(13, thirten);
    		maps.put(14, fourten);
    		maps.put(15, fiften);
    		maps.put(16, sixten);
    		maps.put(17, seventen);
    		maps.put(18, eighten);
    		maps.put(19, nineten);
    		maps.put(20, twenty);
    		maps.put(30, thrity);
    		maps.put(40, fourty);
    		maps.put(50, fifty);
    		maps.put(60, sixty);
    		maps.put(70, seventy);
    		maps.put(80, eighty);
    		maps.put(90, ninety);
    	}
    
    	private static void fillS2NMap() {
    		mapn.put(zero, 0);
    		mapn.put(one, 1);
    		mapn.put(two, 2);
    		mapn.put(three, 3);
    		mapn.put(four, 4);
    		mapn.put(five, 5);
    		mapn.put(six, 6);
    		mapn.put(seven, 7);
    		mapn.put(eight, 8);
    		mapn.put(nine, 9);
    		mapn.put(ten, 10);
    		mapn.put(eleven, 11);
    		mapn.put(twelwe, 12);
    		mapn.put(thirten, 13);
    		mapn.put(fourten, 14);
    		mapn.put(fiften, 15);
    		mapn.put(sixten, 16);
    		mapn.put(seventen, 17);
    		mapn.put(eighten, 18);
    		mapn.put(nineten, 19);
    		mapn.put(twenty, 20);
    		mapn.put(thrity, 30);
    		mapn.put(fourty, 40);
    		mapn.put(fifty, 50);
    		mapn.put(sixty, 60);
    		mapn.put(seventy, 70);
    		mapn.put(eighty, 80);
    		mapn.put(ninety, 90);
    		mapn.put(hundred, 100);
    		mapn.put(thousand, 1000);
    		mapn.put(million, 1000000);
    	}
    
    }
}
