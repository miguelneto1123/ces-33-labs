#include <stdio.h>
#include <time.h>

int main(){
	int beg = time(NULL), i;

	for(i = 0; i < 2147483647; i++);

	printf("Elapsed time: %d\n", (int)(time(NULL)-beg));
}