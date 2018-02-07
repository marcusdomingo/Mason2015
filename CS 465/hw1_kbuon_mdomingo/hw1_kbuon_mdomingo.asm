#Authors: Kuntharith Buon (kbuon) and Marcus Domingo (mdomingo)
#Date Modified: 02/13/2017
#Modification: Added description
#CS465 Spring 2017
#Description: This program takes in user input and finds all the odd and even factors and counts them respectively.
#	      The program then takes the counted factors and prints them out to the user.
#	      Assumed is that the input number is a positive integer (>= 1). Although we take care of the case of 0.

.data

Ask_Integer:
	.asciiz "Please enter a positive int: "

Tell_Output:
	.asciiz "User Input: "
	
Odd_Factors:
	.asciiz "\nNumber of odd factors: "

Even_Factors:
	.asciiz "\nNumber of even factors: "

.text

main:

	# to print out Ask_Integer
	la $a0, Ask_Integer #load address Ask_Integer from memory and store it into argument register 0
	li $v0, 4 #loads the value 4 into register $v0 which is the op code for print string
	syscall #reads register $v0 for op code, sees 4 and prints the string located in $a0
	
	# to save input
	li $v0, 5 #loads the value 5 into register $v0 which is the op code for getting an integer from the user
	syscall #reads register $v0 for op code, sees 5 and asks user for input, places integer in $v0
	
	# move input number to a temporary register
	move $t0, $v0 # moves contents from $v0 to $t7
	
	# to print out Tell_Output
	la $a0, Tell_Output #load address Load_Output from memory and store it into argument register 0
	li $v0, 4 #loads the value 4 into register $v0 which is the op code for print string
	syscall #reads register $v0 for op code, sees 4 and prints the string located in $a0
	
	# to print out number that user input
	la $a0, ($t0) #sets $a0 to the integer at $t0
	li $v0, 1 #loads the value 1 into register $v0 which is the op code for print integer
	syscall #reads register $v0 for op code, sees 1 and prints the integer located in $a0
	
Divide:
	la $a0, ($t0) #sets $a0 to the integer at $t0
	addi $a1, $a1, 1 #add 1 each time to iterate through 1 to n where n is the integer
	sub $t1, $a0, $a1 #integer - current number ($a0 - $a1) to check if $a1 is past the integer
	bltz $t1, Done #check if difference is less than zero, if yes then we are done
	la $a2, 0 #sets $a2 to 0
	div $a0, $a1 #divide integer/current number ($a0/$a1)
	mfhi $t2 #put the remainder in $t2
	beq $a2, $t2, Divisible #if $t2 is 0 ($a2 = $t2) then it is divisible
	j Divide #else loop back to Divide
	
Divisible:
	mflo $t2 #put the quotient in $t2
	andi $t3, $t2, 1 #AND the quotient with 1 and store in $t3
	beq $t3, $zero, Even #if $t3 is 0 then it is an Even factor
	addi $t4, $t4, 1 #else increment the Odd factor count
	j Divide #loop back to Divide
Even:
	addi $t5, $t5, 1 #increment the Even factor count
	j Divide #loop back to Divide
Done:
	# to print out Odd_Factors
	la $a0, Odd_Factors #load address Odd_Factors from memory and store it into argument register 0
	li $v0, 4 #loads the value 4 into register $v0 which is the op code for print string
	syscall #reads register $v0 for op code, sees 4 and prints the string located in $a0
	
	# to print out the odd count
	la $a0, ($t4) #sets $a0 to the odd count at $t4
	li $v0, 1 #loads the value 1 into register $v0 which is the op code for print integer
	syscall #reads register $v0 for op code, sees 1 and prints the integer located in $a0
	
	# to print out Even_Factors
	la $a0, Even_Factors #load address Even_Factors from memory and store it into argument register 0
	li $v0, 4 #loads the value 4 into register $v0 which is the op code for print string
	syscall #reads register $v0 for op code, sees 4 and prints the string located in $a0
	
	# to print out the even count
	la $a0, ($t5) #sets $a0 to the even count at $t5
	li $v0, 1 #loads the value 1 into register $v0 which is the op code for print integer
	syscall #reads register $v0 for op code, sees 1 and prints the integer located in $a0

	# to exit off main
	li $v0, 10
	syscall
