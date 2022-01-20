package github.io.ylongo.algo.maxSubArray;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 暴力求解
 */
public class MaxSubArray02 {

	public static void main(String[] args) {

		MaxSubArray02 maxSubArray = new MaxSubArray02();
//		int[] nums = {5, 4, -1, 7, 8}; // 23
//		int[] nums = {-2, 1, -3, 4, -1, 2, 1, -5, 4}; // 6
//		int[] nums = {-2, 1}; // 1
		int[] nums = {-2, -1}; // -1
		System.out.println(Arrays.toString(nums));
		int maxSum = maxSubArray.maxSubArray(nums);
		System.out.println(maxSum);
	}

	/**
	 * 通过i，j两个指针不断扫描当数组和出现最大值时的边界
	 * <p>
	 * ps：当数组数据量比较大的时候会超出时间限制
	 */
	public int maxSubArray(int[] nums) {

		if (nums == null || nums.length == 0) {
			return 0;
		}

		int maxSum = getMaxNum(nums);
		
		nums = preHandle(nums);
		System.out.println(Arrays.toString(nums));
		
		for (int i = 0; i < nums.length; i++) {
			int tempMaxSum = nums[i];
			// 处理边界情况，如果数组只有一个元素时
			maxSum = Math.max(maxSum, tempMaxSum);

			// 不断的移动j的位置，去发现和为最大值时所在数组的位置
			for (int j = i + 1; j < nums.length; j++) {
				tempMaxSum += nums[j];
				// 当发现和为最大值时赋值给maxSum，否则继续计算i，j之间和的值
				maxSum = Math.max(maxSum, tempMaxSum);
			}
		}

		return maxSum;
	}

	/**
	 * 预处理数组，对数组进行压缩（防止数组过大时出现运行超时的情况）
	 * <p>
	 * 1. 将连续都是正数的数相加合并为一个数
	 * <p>
	 * 2. 将连续都是负数的数相加合并为一个数
	 */
	public int[] preHandle(int[] nums) {

		List<Integer> list = new ArrayList<>();
		int sum = 0;
		// 1表示0或正数，-1表示负数
		int mark = nums[0] < 0 ? -1 : 1;
		for (int num : nums) {
			// eg. 如果为正数，则全部加起来
			if ((num < 0 ? -1 : 1) == mark) {
				sum += num;
			} else { // 如果遇到了负数，则把当前正数的和放到list中收集起来
				list.add(sum);
				mark = num < 0 ? -1 : 1;
				sum = num;
			}
		}
		
		// 处理边界情况，将else中的sum收集起来
		list.add(sum);

		int[] newNums = new int[list.size()];
		for (int i = 0; i < list.size(); i++) {
			newNums[i] = list.get(i);
		}
		
		return newNums;
	}

	/**
	 * 处理全部是负数的情况
	 * <p>
	 * 因为全部是负数时，预处理数组是会把所有的负数加起来，这个时候数组就只有一个数
	 * 但其实该数组中的最大值是其中最大的一个负数
	 */
	public int getMaxNum(int[] nums) {

		int max = nums[0];
		for (int i = 1; i < nums.length; i++) {
			max = Math.max(max, nums[i]);
		}
		return max;
	}
}
