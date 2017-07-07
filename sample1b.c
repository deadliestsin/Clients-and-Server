#include <stdio.h>
#include <stdlib.h>

int main() {
	char buffer[500];
	int  n1, n2, n3;

	gets(buffer);
	n1 = atoi(buffer);
	
	gets(buffer);
	n2 = atoi(buffer);

	n3 = n1 + n2;
	printf("%d\n", n3);
}	