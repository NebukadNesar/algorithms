package matrixrotation;

import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		List<Integer> a1 = new ArrayList<Integer>();
		List<Integer> a2 = new ArrayList<Integer>();
		List<Integer> a3 = new ArrayList<Integer>();
		List<Integer> a4 = new ArrayList<Integer>();
		List<Integer> a5 = new ArrayList<Integer>();
		
		List<List<Integer>> matrix = new ArrayList<List<Integer>>();
		a1.add(11);a1.add(12);a1.add(13);a1.add(14);
		a2.add(21);a2.add(22);a2.add(23);a2.add(24);
		a3.add(31);a3.add(32);a3.add(33);a3.add(34);
		a4.add(41);a4.add(42);a4.add(43);a4.add(44);
		a5.add(411);a5.add(421);a5.add(431);a5.add(441);

		matrix.add(a1);
		matrix.add(a2);
		matrix.add(a3);
		matrix.add(a4);
		matrix.add(a5);

		print(matrix);
		matrixRotation2(matrix, 1); // matrix, rotation count
	}

	static void print(List<List<Integer>> matrix) {
		for (int i = 0; i < matrix.size(); i++) {
			System.out.println("\n");
			for (int j = 0; j < matrix.get(0).size(); j++) {
				System.out.print(" " + matrix.get(i).get(j));
			}
		}
		System.out.println("\n-------------\n");
	}

	
	static void matrixRotation2(List<List<Integer>> matrix, int r) {

		int xax = matrix.size();
		int yax = matrix.get(0).size();
		int min = Math.min(xax, yax);
		int corners = 0;

		corners = min / 2;
		while (r-- > 0) {
			for (int i = 0; i < corners; i++) {
				int start = i, end = i;
				Integer temp = matrix.get(start).get(end);

				for (int y = i + 1; y < xax - i; y++) {
					int yTemp = matrix.get(y).get(i);
					matrix.get(y).set(i, temp.intValue());
					temp = yTemp;
				}

				for (int x = 1 + i; x <= yax - i - 1; x++) {
					int xTemp = matrix.get(xax - i - 1).get(x);
					matrix.get(xax - i - 1).set(x, temp.intValue());
					temp = xTemp;
				}

				for (int yt = xax - i - 1 - 1; yt >= 0 + i; yt--) {
					int ytTemp = matrix.get(yt).get(yax - i - 1);
					matrix.get(yt).set(yax - i - 1, temp.intValue());
					temp = ytTemp;
				}

				for (int xt = yax - i - 1 - 1; xt >= 0 + i; xt--) {
					int xtTemp = matrix.get(i).get(xt);
					matrix.get(i).set(xt, temp.intValue());
					temp = xtTemp;
				}
			}
		}
		print(matrix);
	}
}
