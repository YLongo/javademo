package github.io.ylongo.algo.mergeSortedArray;

import org.apache.solr.util.SolrPluginUtils;
import org.apache.solr.util.SpatialUtils;

import java.util.Arrays;

/**
 * 暴力破解O(n * m)
 */
public class MergeSortedArray01 {

	public static void main(String[] args) {

		MergeSortedArray01 mergeSortedArray01 = new MergeSortedArray01();

//		int[] nums1 = {1, 2, 3, 0, 0, 0}; int m = 3;
//		int[] nums2 = {2, 5, 6}; int n = 3;

		int[] nums1 = {4, 0, 0, 0, 0, 0}; int m = 1;
		int[] nums2 = {1, 2, 3, 5, 6}; int n = 5;

		mergeSortedArray01.merge(nums1, m, nums2, n);

		System.out.println(Arrays.toString(nums1));
	}

	public void merge(int[] nums1, int m, int[] nums2, int n) {

		// 先合并两个数据
		for (int i = 0; i < n; i++) {
			nums1[m + i] = nums2[i];
		}

		System.out.println(Arrays.toString(nums1));

		int i = 0;
		int j = m;
		// i,j指针需要移动的次数
		int countI = 0;
		int countJ = 0;
		
		while (countI < m && countJ < n){
			
			int leftNum = nums1[i];
			int rightNum = nums1[j];

			// 判断nums2中的每一个数是否大于nums1其中的一个数
			if (rightNum >= leftNum) {
				i++;
				countI++;
			} else { 
				// 如果nums[j] < nums[i]，则将i到j-1之间的数往后移动一位，然后将j位置的数放到i位置上去
				// 因为j之后的数是有序的，所以暂时不需要移动
				for (int k = j; k > i; k--) {
					nums1[k] = nums1[k - 1];
				}
				nums1[i] = rightNum;
				countJ++;
				j++;
				i++;
			}
		}
	}

}
