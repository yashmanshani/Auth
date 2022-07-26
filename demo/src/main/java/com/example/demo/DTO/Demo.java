package com.example.demo.DTO;


public class Demo {

	public static void main(String[] args) {
		int[] arr = {1,3,5};
		//System.out.println(binarySearch(arr, 4, 0, arr.length));
		String pass = "hello";
		System.out.println(pass.hashCode());
		
	}
	public static boolean binarySearch(int[] arr, int x, int start, int end) {
		if(start>end)
			return false;
        if (end==start) {
        	if(arr[start] == x) 
        		return true;
            return false;
        }
        int mid = start+((end-start)/2);
        if (arr[mid] == x)
            return true;
        else if (arr[mid] > x) 
            return binarySearch(arr, x, start, mid-1);
        else // (arr[mid] < x)
            return binarySearch(arr, x, mid+1, end);
    }
}
