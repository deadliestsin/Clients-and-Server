#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int main() {
	char buffer[500];
	int  n;

	gets(buffer);
	n = strlen(buffer);
	
	printf("%d\n", n);
}