#include <stdio.h>
#include <time.h>

int main(){
	FILE * ent = fopen("input.txt", "r");
	char read[40];
	int beg = time(NULL);

	while(!feof(ent)){
		fgets(read, 40, ent);
		puts(read);
	}

	printf("Elapsed time: %d\n", (int)(time(NULL)-beg));
	fclose(ent);
}