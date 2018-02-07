// Marcus Domingo and G00987958
// CS 262, Lab Section 208
// Lab 2

#include <stdio.h>

int main()
{
	double cost;
	int percent;
	double tip;
	double total;

	printf("Enter the cost of the meal: ");
	scanf("%lf", &cost);
	printf("Enter the percentage tip: ");
	scanf("%d", &percent);
	
	tip = ((double) percent/100) * cost;
	total = tip + cost;

	printf("Price of Meal: %.2lf\nPercentage Tip: %d\nTip: %.2lf\nTotal: %.2lf\n", cost, percent, tip, total);

	return 0;
}
