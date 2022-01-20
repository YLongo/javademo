package github.io.ylongo.algo.twoSum;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TwoSum3 {

	public static void main(String[] args) {
		TwoSum3 twoSum = new TwoSum3();
		int[] nums = {15, 2, 11, 7, 10, 5, 9};
//		int[] nums = {3, 2, 3};
		int target = 6;
		int[] result = twoSum.twoSum(nums, target);
		System.out.println(Arrays.toString(result));
	}


	public int[] twoSum(int[] nums, int target) {

		Map<Integer, Integer> numMap = new HashMap<>();

		for (int i = 0; i < nums.length; i++) {
			numMap.put(nums[i], i);
		}

		for (int i = 0; i < nums.length; i++) {

			int key = target - nums[i];
			Integer index = numMap.get(key);
			if (index != null && index != i) {
				return new int[]{i, index};
			}
		}

		return new int[]{-1, -1};
	}
}
