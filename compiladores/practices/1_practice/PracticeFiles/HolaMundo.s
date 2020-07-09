	.file	"HolaMundo.c"
	.text
	.section	.rodata
	.align 8
.LC0:
	.string	"Pr%ctica 1\nC%crdenas C%crdenas Jorge\nMurrieta Villegas Alfonso\nReza Chavarria Sergio Gabriel \nValdespino Mendieta Joaquin\n"
.LC1:
	.string	"Saludos profesor :)"
	.text
	.globl	datos
	.type	datos, @function
datos:
.LFB0:
	.cfi_startproc
	pushq	%rbp
	.cfi_def_cfa_offset 16
	.cfi_offset 6, -16
	movq	%rsp, %rbp
	.cfi_def_cfa_register 6
	movl	$163, %ecx
	movl	$163, %edx
	movl	$163, %esi
	leaq	.LC0(%rip), %rdi
	movl	$0, %eax
	call	printf@PLT
	leaq	.LC1(%rip), %rdi
	movl	$0, %eax
	call	printf@PLT
	nop
	popq	%rbp
	.cfi_def_cfa 7, 8
	ret
	.cfi_endproc
.LFE0:
	.size	datos, .-datos
	.section	.rodata
.LC2:
	.string	"Hola Mundo"
.LC3:
	.string	"Impresi%cn del macros: %d\n"
	.text
	.globl	main
	.type	main, @function
main:
.LFB1:
	.cfi_startproc
	pushq	%rbp
	.cfi_def_cfa_offset 16
	.cfi_offset 6, -16
	movq	%rsp, %rbp
	.cfi_def_cfa_register 6
	subq	$16, %rsp
	movl	%edi, -4(%rbp)
	movq	%rsi, -16(%rbp)
	leaq	.LC2(%rip), %rdi
	call	puts@PLT
	movl	$365, %edx
	movl	$163, %esi
	leaq	.LC3(%rip), %rdi
	movl	$0, %eax
	call	printf@PLT
	movl	$0, %eax
	call	datos
	movl	$0, %eax
	leave
	.cfi_def_cfa 7, 8
	ret
	.cfi_endproc
.LFE1:
	.size	main, .-main
	.ident	"GCC: (Ubuntu 7.4.0-1ubuntu1~18.04.1) 7.4.0"
	.section	.note.GNU-stack,"",@progbits
