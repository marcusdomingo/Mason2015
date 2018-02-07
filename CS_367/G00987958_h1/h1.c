// Marcus Domingo G00987958
// CS 367
// HW1
// Last modified 9/6/2016

#include <stdio.h>
#include <stdlib.h>

int main()
{
	//init variables
	int *nums, *orderedNums;
	int size = 0, i = 0, j = 0, n = 0, k = 0, storeNum = 0, storage = 0;

	//prompt for input and scan for k and n
	printf("Please input a string of numbers with a space between each number:\n");
	scanf("%d", &k);
	scanf("%d", &n);

	//check if n is a bad size
	if(n < 0)
	{
	 	printf("bad size\n");
	  	exit(0);
	}

	//check if k is bad index
	if(k <= 0 || k > n)
	{       
		printf("bad index\n");
		exit(0);
	}
	
	//initialize the memory for the arrays
	nums = (int *)malloc(sizeof(int)*n);
	orderedNums = (int *)malloc(sizeof(int)*k);
	
	//scan for n more numbers
	for(i = 0; i < n; i++)
		scanf("%d", &nums[i]);

	//iterate through original array once and sort into another array of
	//size k
	for(i = 0; i < n; i++)
	{
		storage = nums[i];

		for(j = 0; j < k; j++)
		{
			storeNum = storage;
		
			if(size != k)
			{
				orderedNums[size] = storeNum;
				size++;
				break;
			}
			else if(storeNum <= orderedNums[j])
			{
				storage = orderedNums[j];
				orderedNums[j] = storeNum;
			}
			else if(size != k)
			{
				orderedNums[j] = storeNum;
				size++;
				break;
			}
		}
	}

	//print the k value
	printf("The kth value is: %d\n", orderedNums[k-1]);
	
	//exit program
	exit(0);
}
