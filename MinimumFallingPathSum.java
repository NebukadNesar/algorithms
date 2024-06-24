import java.util.ArrayList;
import java.util.List;

public class MinimumFallingPathSum {
    public static void main(String[] args) {
        System.out.println("findFallingSum() = " + findFallingSum(new int[][]{
                {2, 1, 3},
                {6, 5, 4},
                {7, 8, 9}
        })); //12

       System.out.println("findFallingSum() = " + findFallingSum(new int[][]{
                {2, 2, 1, 2, 2},
                {2, 2, 1, 2, 2},
                {2, 2, 1, 2, 2},
                {2, 2, 1, 2, 2},
                {2, 2, 1, 2, 2}
        })); //7
    }

    private static int findFallingSum(int[][] grid) {
        int n = grid.length;
        List<Integer> memo = new ArrayList<>();
        int result = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            memo.clear();
            memo.add(grid[0][i]);
            int temp = findNext(grid, i, 1, memo);
            result = Math.min(result, temp);
        }
        return result;
    }

    private static int findNext(int[][] grid, int selected, int next, List<Integer> memo) {
        if (memo.size() == grid.length) {
            int sum = memo.stream().mapToInt(value -> value).sum();
            System.out.println("memo = " + memo + ", sum=" + sum);
            return sum;
        }
        int current = next;
        int result = Integer.MAX_VALUE;
        for (int i = 0; i < grid.length; i++) {
            if (i != selected) {
                memo.add(next, grid[next][i]);
                int local = findNext(grid, i, next + 1, memo);
                result = Math.min(result, local);
                next = current;
                memo = memo.subList(0, current);
            }
        }
        return result;
    }

}
