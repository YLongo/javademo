package github.io.ylongo.algo.maxSubArray;


import java.util.ArrayList;
import java.util.List;

/**
 * 动态规划O(n)
 */
public class MaxSubArray04 {

	public static void main(String[] args) {

		MaxSubArray04 maxSubArray = new MaxSubArray04();
		int[] nums1 = {5, 4, -1, 7, 8}; // 23
		int[] nums2 = {-2, 1, -3, 4, -1, 2, 1, -5, 4}; // 6
		int[] nums3 = {-2, 1}; // 1
		int[] nums4 = {-2, -1}; // -1
		List<int[]> numsList = new ArrayList<>();
		numsList.add(nums1);
		numsList.add(nums2);
		numsList.add(nums3);
		numsList.add(nums4);

		for (int[] nums : numsList) {
			int maxSum = maxSubArray.maxSubArray(nums);
			System.out.println(maxSum);
		}
		
	}

	public int maxSubArray(int[] nums) {
		
		if (nums == null || nums.length == 0) {
			return 0;
		}

		int[] dp = new int[nums.length];
		dp[0] = nums[0];

		for (int i = 1; i < nums.length; i++) {
			dp[i] = Math.max(nums[i], dp[i - 1] + nums[i]);
		}

		int maxSum = Integer.MIN_VALUE;
		for (int num : dp) {
			maxSum = Math.max(maxSum, num);
		}
		return maxSum;
	}

}
