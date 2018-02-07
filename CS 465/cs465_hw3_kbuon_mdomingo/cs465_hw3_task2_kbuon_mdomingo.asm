#Authors: Kuntharith Buon (kbuon) and Marcus Domingo (mdomingo)
#Date Modified: 04/06/2017
#Modification: Fixed Bugs
#CS465 Spring 2017
#Description: This program accepts two 16 bit numbers, multiplies them, and prints out the product
#*************************************
#Description of algo: The algo shifts the bits first to allow for overflow into the msb.  This
#still follows the optimized version because the addition is still taking place in the top bits
#in the product/multiplier just with the shifting taking place first.  Whether the shifting takes
#place first or last doesn't affect the numbers that don't produce an overflow bit, but it does
#protect the numbers that produce an overflow bit when the shifting takes place first.
#*************************************
.data
	
Ask_Multiplicand:
	.asciiz "Multiplicand? "
Ask_Multiplier:
	.asciiz "Multiplier? "
Ask_Value:
	.asciiz "What is the value? "
Product:
	.asciiz "Product: "
	
#make subroutine global
.globl opt_multiply
	
.text

main:
	# to print out Ask_Multiplicand
	la $a0, Ask_Multiplicand #load address Ask_Multiplicand from memory and store it into argument register 0
	li $v0, 4 #loads the value 4 into register $v0 which is the op code for print string
	syscall #reads register $v0 for op code, sees 4 and prints the string located in $a0
	
	# to save input
	li $v0, 5 #loads the value 5 into register $v0 which is the op code for getting an integer from the user
	syscall #reads register $v0 for op code, sees 5 and asks user for input, places integer in $v0
	
	# move input number to a temporary register
	move $t0, $v0 # moves contents from $v0 to $t0
	
	# to print out Ask_Multiplier
	la $a0, Ask_Multiplier #load address Ask_Multiplier from memory and store it into argument register 0
	li $v0, 4 #loads the value 4 into register $v0 which is the op code for print string
	syscall #reads register $v0 for op code, sees 4 and prints the string located in $a0
	
	# to save input
	li $v0, 5 #loads the value 5 into register $v0 which is the op code for getting an integer from the user
	syscall #reads register $v0 for op code, sees 5 and asks user for input, places integer in $v0
	
	# move input number to a temporary register
	move $t1, $v0 # moves contents from $v0 to $t1
	
	la $a0, ($t0) 		#load the array of char into argument 1 ($a0)
	la $a1, ($t1) 		#load the base integer into argument 2 ($a1)
	addi $sp, $sp, -4	#make room in stack pointer
	sw $ra, 0($sp)		#store return address in stack pointer
	jal opt_multiply	#jump to multiply function and link
	lw $ra, 0($sp)		#restore return address from stack pointer
	addi $sp, $sp, 4	#fill in empty space in stack pointer
	la $t0, ($v0)		#store the prodcut in temporary

	# to print out Product
	la $a0, Product #load address Product from memory and store it into argument register 0
	li $v0, 4 #loads the value 4 into register $v0 which is the op code for print string
	syscall #reads register $v0 for op code, sees 4 and prints the string located in $a0
	
	# to print out the total
	la $a0, ($t0) #sets $a0 to the total at $v0
	li $v0, 36 #loads the value 36 into register $v0 which is the op code for print unsigned integer
	syscall #reads register $v0 for op code, sees 1 and prints the integer located in $a0
	
	# to exit off main
	li $v0, 10
	syscall
	
opt_multiply:
	la $t7, 0		#load in 0 to $t7 for iteration counter
	la $t0, ($a0)		#load multiplicand into $t0
	la $t1, ($a1)		#load multiplier into $t1
	la $t2, ($a1)		#load multiplier into $t2 (this one used to check lsb)
	sll $t3, $a0, 15	#load shifted multiplicand by 15 bits left (not 16 to avoid overflow) into $t3
	
multiply:
	andi $t4, $t2, 1	#and multiplier with 1 to get lsb
	srl $t2, $t2, 1		#shift lsb multiplier to the right by 1
	srl $t1, $t1, 1		#shift product/multiplier to the right by 1 to avoid overflow
	bne $t4, 1, check	#if the lsb isn't 1 then jump to check iteration
	addu $t1, $t1, $t3	#add the multiplicand to the left half of the product/multiplier
check:
	addi $t7, $t7, 1	#add 1 to the iteration counter
	blt $t7, 16, multiply	#if the interation is less than 16 then repeat
	la $v0, ($t1)		#load the prodcut into $v0
	jr $ra			#jump return to +4 after jal call
