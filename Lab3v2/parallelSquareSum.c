#include <semaphore.h>
#include <pthread.h>
#include <stdio.h>
#include <time.h>

#define TRUE 1
#define FALSE 0
#define N 2147483647
#define NUMBER_THREADS 1


int get_tid(pthread_t tid)
{
   
    long long int * threadid = (int *) (void *) &tid;
    int res = *threadid%1000;
    return res;
}

// Funcoes e variaveis do buffer
int start = 0;
int buffer[N];
long long globalResult = 0;

sem_t mutex;

int getParticle() {
	//printf("Thread %d - Getting Particle %d...\n", get_tid(pthread_self()), start);
	int particle = buffer[start];
	start = start+1;
	//printf ("Thread %d - Got Particle %d\n", get_tid(pthread_self()), particle);
	return particle;
}

void generatingNumbers() {
	int i;
	for (i = 0; i < N; i++) {
		buffer[i] = 2;
	}
}
#define up(SEM) _up(SEM,#SEM)
#define down(SEM) _down(SEM,#SEM)

void _up(sem_t* sem, const char * name) {
	sem_post(sem);
}
void _down(sem_t* sem, const char * name) {
	sem_wait(sem);
}


void computeSquaredSum(int particle) {
	//printf("Thread %d - Computing particle: %d: %d\n", get_tid(pthread_self()), particle, particle*particle);

	globalResult += particle*particle;
	//printf("Thread %d - Partial Result: %d\n", get_tid(pthread_self()),  globalResult);
}


void* consumerFunc( void* ptr ) {
	int newParticle;
	while(start < N) {
		//down(&mutex);
		if (start < N) {
			newParticle = getParticle();
			computeSquaredSum(newParticle);
		}
		//up(&mutex);		
	}
	pthread_exit(0);
	return 0;
}

int main() {
	int i;
	generatingNumbers();
	start = 0;

	// Criando semaforo
	sem_init (&mutex,
			1,
			1);
	pthread_t consumerThread[NUMBER_THREADS];

	clock_t begin = clock();
	int x = 0;
	for (x = 0; x < NUMBER_THREADS; x++) {
		if (pthread_create(&consumerThread[x], NULL, consumerFunc, NULL)) {
			printf ("Error creating thread\n");
		}
	}
    
	for (x = 0; x < NUMBER_THREADS; x++){
		pthread_join(consumerThread[NUMBER_THREADS-1], NULL);
	}
	clock_t end = clock();
	double finalTime = (1.00*(end - begin))/CLOCKS_PER_SEC;
	printf ("Result: %ld\n", globalResult);
	printf ("Runtime: %lf\n", finalTime);
	sem_destroy(&mutex);

}