// Marcus Domingo
// G00987958
// Date Modified: 10/1/16

#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include "fp.h"

// All E's were calculated with the range 14 to -14 excluding 0
// Just a different alteration to what the specs say

int computeFP(float val) {
// input: float value to be represented
// output: integer version in our representation
//
// Perform this the same way we did in class -
//    either dividing or multiplying the value by 2
//    until it is in the correct range (between 1 and 2).
//    Your exponent is the number of times this operation
//    was performed.   
// Deal with rounding by simply truncating the number.
// Check for overflow and underflow - 
//    with 4 exponent bits, we have overflow if the number to be 
//       stored is > 14
//    for overflow (E > 14), return -1
//    For underflow (E < 1), return 0 
	
	// initialize E
	int E = 0;
	
	// check val if > 1 or < 1
	// and increment E accordingly
	if(val > 1.0)
	{
		while(val >= 2)
		{
			val = val / 2;
			E++;
		}
	}
	else if(val < 1.0)
	{
		while(val <= 1)
		{
			val = val * 2;
			E--;
		}
	}

	// check for overflow and underflow
	if(E > 14)
		return -1;
	if(E == 0 || E < -14)
		return 0;

	// initialize
	int fract_size = (1 << FRACTION_BITS);
	int bias = (1 << (EXPONENT_BITS - 1)) - 1;
	int exponent = E + bias;
	int frac = 0;
	int j;

	// used similar algorithm noticed in output_all_values.c
	// checks if the current M is equal with 1 + j/128
	// also checks if current M is inbetween 1 + j/128 and 1 + (j+1)/128
	// if either are true then j is denominator
	for (j = 0; j < fract_size; j++)
	{
		float M = (1+ (float)j/fract_size);
		float M1 = (1+ (float)(j+1)/fract_size);
 
		if(M == val)
		{
			frac = j;
			break;
		}
		if(val > M && val < M1)
		{
			frac = j;
			break;
		}
	}

	//put exponent in int shift 7 bits then put the fraction in
	int x = 0 | exponent;
	x = x << 7;
	x = x | frac;

	//return
	return x;

  return 2;
}

float getFP(int val) {
// Using the defined representation, compute the floating point
//    value
// For denormalized values (including 0), simply return 0.
// For special values, return -1;

	// initialize
	// get exponent and shift to the right 7 to get exponent ignore sign bit
	// get fraction by ignoring sign bit and 5 exponent bits
	int fract_size = (1 << FRACTION_BITS);
	int bias = (1<< (EXPONENT_BITS-1))-1;
	int exponent = (val & 0x0F80) >> 7;
	int fraction = val & 0x007F;

	// calculate M and E
	float E = powf(2.0,(float)exponent - bias);
	float M = (1 + (float)fraction/fract_size);

	// calculate the float
	float x = M*E;

	// return
	return x;


 return 2.0;
}

int multVals(int source1, int source2) {
// You must implement this by using the algorithm
//    described in class:
//    Add the exponents:  E = E1+E2 
//    multiply the fractional values: M = M1*M2
//    if M too large, divide it by 2 and increment E
//    save the result
// Be sure to check for overflow - return -1 in this case
// Be sure to check for underflow - return 0 in this case

	// initialize
	// calculate both exponents and shift to the right 7 to get exponent
	// ignore sign bit
	// get both fractions by ignoring sign bit and 5 exponent bits
	int fract_size = (1 << FRACTION_BITS);
	int bias = (1<< (EXPONENT_BITS-1))-1;
	int exponent1 = (source1 & 0x0F80) >> 7;
	int fraction1 = source1 & 0x007F;
	int exponent2 = (source2 & 0x0F80) >> 7;
	int fraction2 = source2 & 0x007F;

	// calculate E and M as explained in the above description
	int E1 = exponent1 - bias;
	int E2 = exponent2 - bias;
	int E = E1 + E2;
	float M1 = (1 + (float)fraction1/fract_size);
	float M2 = (1 + (float)fraction2/fract_size);
	float M = M1 * M2;

	// check if M is greater than 2 and divide
	// increment E
	if(M > 2)
	{
		M = M / 2;
		E++;
	}

	// check for overflow and underflow
	if(E > 14)
		return -1;
	if(E == 0 || E < -14)
		return 0;

	// initialize
	int frac = 0;
	int exponentMulti = E + bias;
	int j;

	// used similar algorithm noticed in output_all_values.c
	// checks if the current M is equal with 1 + j/128
	// also checks if current M is inbetween 1 + j/128 and 1 + (j+1)/128
	// if either are true then j is denominator
	for (j = 0; j < fract_size; j++)
	{
		float newM = (1+ (float)j/fract_size);
		float newM1 = (1+ (float)(j+1)/fract_size);
		
		if(newM == M)
		{
	 		frac = j;
			break;
		}
		if(M > newM && M < newM1)
		{
			frac = j;
			break;
		}
	}

	// put exponent in int shift 7 bits then put the fraction in
	int x = 0 | exponentMulti;
	x = x << 7;
	x = x | frac;

	// return
	return x;

 return 2;
}

int addVals(int source1, int source2) {
// Do this function last - it is the most difficult!
// You must implement this as described in class:
//     If needed, adjust one of the two number so that 
//        they have the same exponent E
//     Add the two fractional parts:  F1' + F2 = F
//              (assumes F1' is the adjusted F1)
//     Adjust the sum F and E so that F is in the correct range
//     
// As described in the handout, you only need to implement this for
//    positive, normalized numbers
// Also, return -1 if the sum overflows

	// initialize
	// calculate both exponents and shift to the right 7 to get exponent
	// ignore sign bit
	// get both fractions by ignoring sign bit and 5 exponent bits
	int fract_size = (1 << FRACTION_BITS);
	int bias = (1<< (EXPONENT_BITS-1))-1;
	int exponent1 = (source1 & 0x0F80) >> 7;
	int fraction1 = source1 & 0x007F;
	int exponent2 = (source2 & 0x0F80) >> 7;
	int fraction2 = source2 & 0x007F;
	
	// get both Es
	// get both Ms
	int E1 = exponent1 - bias;
	int E2 = exponent2 - bias;
	int Ediff;
	int E;
	float M1 = (1 + (float)fraction1/fract_size);
	float M2 = (1 + (float)fraction2/fract_size);
	float M;

	// if E1 = E2 then just at the Ms
	if(E1 == E2)
	{
		M = M1 + M2;
	}

	// if E1 > E2
	// find difference and modify M1 to have the same E value as M2
	// add Ms
	if(E1 > E2)
	{
		Ediff = E1 - E2;
		E = E2;
		M1 = M1 * powf(2.0, Ediff);
		M = M1 + M2;
	}
	// else modify M2 to have the same E value as M1
	// add Ms
	else
	{
		Ediff = E2 - E1;
		E = E1;
		M2 = M2 * powf(2.0, Ediff);
		M = M1 + M2;
	}

	// check val if > 1 or < 1
	// and increment E accordingly
	if(M > 1.0)
	{
		while(M >= 2)
		{
			M = M / 2;
			E++;
		}
	}
	else if(M < 1.0)
	{
		while(M < 1)
		{
			M = M * 2;
			E--;
		}
	}

	// check for overflow and underflow
	if(E > 14)
		return -1;
	if(E == 0 || E < -14)
		return 0;

	// initialize
	int exponentAdd = E + bias;
	int j;
	int frac = 0;

	// used similar algorithm noticed in output_all_values.c
	// checks if the current M is equal with 1 + j/128
	// also checks if current M is inbetween 1 + j/128 and 1 + (j+1)/128
	// if either are true then j is denominator
	for (j = 0; j < fract_size; j++)
	{
		float newM = (1+ (float)j/fract_size);
		float newM1 = (1+ (float)(j+1)/fract_size);
	
		if(newM == M)
		{
			frac = j;
			break;
		}
		if(M > newM && M < newM1)
		{
			frac = j;
			break;
		}
	}

	// put exponent in int shift 7 bits then put the fraction in
	int x = 0 | exponentAdd;
	x = x << 7;
	x = x | frac;
	
	// return
	return x;

  return 2;
}

// COMMENTS
// should have made another function for finding the j/128 value for cleanliness
