package github.io.ylongo.algo.maxSubArray;


import java.util.ArrayList;
import java.util.List;

/**
 * 贪心算法O(n)
 */
public class MaxSubArray03 {

	public static void main(String[] args) {

		MaxSubArray03 maxSubArray = new MaxSubArray03();
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

		int maxSum = Integer.MIN_VALUE;
		int sum = 0;
		
		for (int num : nums) {
			sum += num;
			
			// 已经获得的最大值与此次循环获得的最大值进行比较
			maxSum = Math.max(sum, maxSum);

			if (sum < 0) { // 如果此次循环获得的最小值已经小于0了，则获取到的sum值对增加和的大小已经无意义，直接重新赋值
				sum = 0;
			}
		}
		
		return maxSum;
	}

}
