#Author: Marcus Domingo
#Date 01/27/17
#CS465 Spring 2017

.data

hello_str:
	.asciiz "Hello World!\n Spring 2017 is going to be fun!"

.text

main:
li $v0, 4

la $a0, hello_str
syscall

# To exit off main
li $v0, 10
syscall