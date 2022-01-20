package github.io.ylongo.algo.maxSubArray;


/**
 * 暴力求解
 */
public class MaxSubArray01 {

	public static void main(String[] args) {

		MaxSubArray01 maxSubArray = new MaxSubArray01();
//		int[] nums = {5, 4, -1, 7, 8}; 
//		int[] nums = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
		int[] nums = {-2, 1};
		int maxSum = maxSubArray.maxSubArray(nums);
		System.out.println(maxSum);
	}

	/**
	 * 当数组数据量比较大的时候会超出时间限制
	 */
	public int maxSubArray(int[] nums) {

		if (nums == null || nums.length == 0) {
			return 0;
		}

		int maxSum = Integer.MIN_VALUE;

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
}
