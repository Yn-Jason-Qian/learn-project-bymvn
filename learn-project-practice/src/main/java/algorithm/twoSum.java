package algorithm;

import java.util.Arrays;

/**
 * Created by qianyang on 18-3-28.
 *
 * 给定一个整数数列，找出其中和为特定值的那两个数。

    你可以假设每个输入都只会有一种答案，同样的元素不能被重用。

     给定 nums = [2, 7, 11, 15], target = 9

     因为 nums[0] + nums[1] = 2 + 7 = 9
     所以返回 [0, 1]
 */
public class twoSum {

    public static int[] twoSum(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return new int[0];
        }
        for (int pos1 = 0; pos1 < nums.length; pos1++) {
            for(int pos2 = pos1 + 1; pos2 < nums.length; pos2++) {
                if (nums[pos1] + nums[pos2] == target) {
                    return new int[]{pos1, pos2};
                }
            }
        }
        return new int[0];
    }

    public static int[] twoSumBySort(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return new int[0];
        }
        int[] copy = Arrays.copyOf(nums, nums.length);
        Arrays.sort(copy);
        int left = 0;
        int right = copy.length - 1;
        while (left < right) {
            int sum = copy[left] + copy[right];
            if (sum == target) {
                break;
            } else if (sum < target) {
                left++;
            } else {
                right--;
            }
        }
        if (copy[left] + copy[right] != target) {
            return new int[0];
        }
        int[] result = new int[2];
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == copy[left]) {
                result[0] = i;
                break;
            }
        }
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == copy[right] && i != result[0]) {
                result[1] = i;
                break;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(twoSumBySort(new int[]{2, 5, 5, 11}, 10)));
    }
}
