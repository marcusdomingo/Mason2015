#Authors: Kuntharith Buon (kbuon) and Marcus Domingo (mdomingo)
#Date Modified: 04/06/2017
#Modification: Fixed Bugs
#CS465 Spring 2017
#Description: This program takes in a number, displays the binary representation, and determine if the
#binary representation is a palindrome through recursion.
#*************************************
#Description of algo: The algo checks each mirroring bit of the number to see if they match with each
#other.  If they match then the function calls on itself to perform this for the next set of bits until
#there are no more bits to check and returns 1 then unravels.  If the bits at any point don't match each
#other the function returns a 0 and immediately unravels.
#*************************************
.data
	
Ask_Check:
	.asciiz "Number to check? "
Pattern:
	.asciiz "Binary pattern: "
Palindrome:
	.asciiz "\nIt is a binary palindrome.\n"
Not_Palindrome:
	.asciiz "\nIt is not a binary palindrome.\n"
	
#make function global
.globl binary_palindrome
	
.text

main:
	# to print out Ask_Check
	la $a0, Ask_Check #load address Ask_Check from memory and store it into argument register 0
	li $v0, 4 #loads the value 4 into register $v0 which is the op code for print string
	syscall #reads register $v0 for op code, sees 4 and prints the string located in $a0
	
	# to save input
	li $v0, 5 #loads the value 5 into register $v0 which is the op code for getting an integer from the user
	syscall #reads register $v0 for op code, sees 5 and asks user for input, places integer in $v0
	
	# move input number to a temporary register
	move $t0, $v0 # moves contents from $v0 to $t0
	
	# to print out Pattern
	la $a0, Pattern #load address Pattern from memory and store it into argument register 0
	li $v0, 4 #loads the value 4 into register $v0 which is the op code for print string
	syscall #reads register $v0 for op code, sees 4 and prints the string located in $a0
	
	# to print in binary
	la $a0, ($t0)
	li $v0, 35 #loads the value 35 into register $v0 which is the op code for printing an integer in binary
	syscall #reads register $v0 for op code, sees 35 and prints in binary
	
	la $a0, ($t0)		#load the number into argument 1 ($a0)
	la $a1, 0		#load i = 0 into argument 2 ($a1)
	addi $sp, $sp, -4	#make room in stack pointer
	sw $ra, 0($sp)		#store return address in stack pointer
	jal binary_palindrome	#jump to palindrome function and link
	lw $ra, 0($sp)		#restore return address from stack pointer
	addi $sp, $sp, 4	#fill in empty space in stack pointer
	
	beqz $v0, is_not	#if the return value is 0 then go to not a palindrome
is:
	# to print out Palindrome
	la $a0, Palindrome #load address Palindrome from memory and store it into argument register 0
	li $v0, 4 #loads the value 4 into register $v0 which is the op code for print string
	syscall #reads register $v0 for op code, sees 4 and prints the string located in $a0

	j exit #jump to exit program
is_not:
	# to print out Not_Palindrome
	la $a0, Not_Palindrome #load address Not_Palindrome from memory and store it into argument register 0
	li $v0, 4 #loads the value 4 into register $v0 which is the op code for print string
	syscall #reads register $v0 for op code, sees 4 and prints the string located in $a0
exit:
	# to exit off main
	li $v0, 10
	syscall

binary_palindrome:
	bge $a1, 16, base_case	#if i >= 16 jump to base_case
	addi $sp, $sp, -4	#make room in stack pointer
	sw $ra, 0($sp)		#store return address in stack pointer
	li $t0, 31		#load 31 into $t0
	sub $t0, $t0, $a1	#store 31-i into $t0
	srlv $t1, $a0, $t0	#shift the number to the right by (31-i), store in $t1
	srlv $t2, $a0, $a1	#shift the number to the right by i, store in $t2
	andi $t1, $t1, 1	#get the lsb of number shifted by (31-i)
	andi $t2, $t2, 1	#get the lsb of number shifted by i
	bne $t1, $t2, else	#if the lsb's aren't equal then jump to else
	addi $a1, $a1, 1	#increment i by one in argument 2 ($a1)
	jal binary_palindrome	#recursive call
	lw $ra, 0($sp)		#restore return address from stack pointer
	addi $sp, $sp, 4	#fill in empty space in stack pointer
	j return		#jump to return
		
else:
	li $v0, 0		#load 0 into the return register
	j return		#jump to return
base_case:
	li $v0, 1		#load 1 into the return register
return:
	jr $ra			#return to the last place jal was called; unravels recursive call 