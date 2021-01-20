package algorithm.interview;

/**
 * 在一个二维数组中，每一行都是按从左到右递增排序，每一列都是按从上到下递增排序。
 * 请完成一个函数，输入一个这样的二维数组和一个整数，判断数组中是否包含该整数。
 */
public class FindInPartiallySortedMatrix {

    public static void main(String[] args) {
        System.out.println(find(null, 4, 4, 5));
    }

    public static int[][] createArr() {
        int[][] arr =
                {{1,2,8,9},
                {2,4,9,12},
                {4,7,10,13},
                {6,8,11,15}};
        return arr;
    }


    public static boolean find(int[][] arr, int rows, int columns, int number) {
        if (arr != null && rows > 0 && columns > 0) {
            int row = 0;
            int column = columns - 1;
            while (row < rows && column >= 0) {
                int data = arr[row][column];
                System.out.println(data);
                if (data == number) {
                    return true;
                } else if (number < data) {
                    column--;
                } else {
                    row++;
                }
            }
        }
        return false;
    }
}
