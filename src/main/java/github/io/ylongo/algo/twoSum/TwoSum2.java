package github.io.ylongo.algo.twoSum;

import java.util.Arrays;

/**
 * 给定一个整数数组 nums 和一个整数目标值 target，请你在该数组中找出 和为目标值 target 的那两个整数，并返回它们的数组下标。
 * <p>
 * 你可以假设每种输入只会对应一个答案。但是，数组中同一个元素在答案里不能重复出现。
 * <p>
 * 你可以按任意顺序返回答案。
 * <p>
 * <p>
 * <p>
 * 示例 1：
 * <p>
 * 输入：nums = [2,7,11,15], target = 9
 * 输出：[0,1]
 * 解释：因为 nums[0] + nums[1] == 9 ，返回 [0, 1] 。
 * 示例 2：
 * <p>
 * 输入：nums = [3,2,4], target = 6
 * 输出：[1,2]
 * 示例 3：
 * <p>
 * 输入：nums = [3,3], target = 6
 * 输出：[0,1]
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/two-sum
 */
public class TwoSum2 {

	public static void main(String[] args) {
		TwoSum2 twoSum = new TwoSum2();
//		int[] nums = {15, 2, 11, 7, 10, 5, 9};
		int[] nums = {3, 2, 3};
		int target = 6;
//		int[] result = twoSum.twoSum(nums, target);
		int[] clone = nums.clone();
		Arrays.sort(clone);
		System.out.println(Arrays.toString(clone));
		System.out.println(twoSum.binarySearch(clone, 0, target));
		int[] res = twoSum.twoSum(nums, target);
		System.out.println(Arrays.toString(res));
	}


	/**
	 * 时间复杂度：O(nlogn)
	 */
	public int[] twoSum(int[] nums, int target) {

		int[] result = new int[2];
		int length = nums.length;
		int[] sortedNums = nums.clone();
		Arrays.sort(sortedNums);

		for (int i = 0; i < length; i++) {
			int searchIndex = binarySearch(sortedNums, i + 1, target - sortedNums[i]);
			if (searchIndex != -1) {
				result[0] = reMapping(nums, sortedNums[i], -1);
				result[1] = reMapping(nums, sortedNums[searchIndex], result[0]);
				return result;
			}
		}

		return result;
	}

	public int reMapping(int[] nums, int target, int forbiddenIndex) {
		for (int i = 0; i < nums.length; i++) {
			if (target == nums[i] && i != forbiddenIndex) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * 时间复杂度：O(logN)
	 */
	public int binarySearch(int[] nums, int beginIndex, int target) {

		int leftIndex = beginIndex;
		int rightIndex = nums.length - 1;

		while (leftIndex <= rightIndex) {
			int midIndex = (rightIndex - leftIndex) / 2 + leftIndex;
			if (nums[midIndex] == target) {
				return midIndex;
			} else if (nums[midIndex] > target) {
				rightIndex = midIndex - 1;
			} else {
				leftIndex = midIndex + 1;
			}
		}

		return -1;
	}
}
