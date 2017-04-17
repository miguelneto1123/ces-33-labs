#include <pthread.h>
#include <stdio.h>
#include <unistd.h>
#include <semaphore.h>

#define TRUE 1
#define FALSE 0
#define N 10
#define debugtxt(FORMAT) printf("TID %lu: " #FORMAT "\n",pthread_self())
#define debug(FORMAT, ARGS...) printf("TID %lu: " #FORMAT "\n",pthread_self(),ARGS)

// Funcoes e variaveis do buffer
int start;
int end;
int buffer[N];
int count;
int last_produced_item;
int last_consumed_item;

void insert_item(int item) {
	debug("Inserting item %d",item);
	buffer[end]=item;
	end=(end+1)%N;
	debugtxt("Item inserted!");
}
int remove_item() {
	debugtxt("Removing item ...");
	int item = buffer[start];
	start=(start+1)%N;
	debug("Item %d removed!",item);
	return item;
}

// Funcoes e variaveis das threads
pthread_t threads[2];
pthread_cond_t condThreads[2];
// HANDLE handleThread[2];
// DWORD threadId[2];
const int producer = 0;
const int consumer = 1;
// HANDLE full;
// HANDLE empty;
// HANDLE mutex;
sem_t semaphore;

// // Truque para sabermos qual o semaforo foi chamado e poder imprimi-lo
// #define up(SEM) _up(SEM,#SEM)
// #define down(SEM) _down(SEM,#SEM)

// void _up(HANDLE sem, const char * name) {
// 	debug("Up %s ...",name);
// 	ReleaseSemaphore(sem,1,NULL);
// 	debug("Up %s complete!",name);
// }
// void _down(HANDLE sem, const char * name) {
// 	debug("Down %s ...",name);
// 	WaitForSingleObject(sem,INFINITE);
// 	debug("Down %s complete!",name);
// }

// Produtor e consumidor ...
int produce_item() {
	debugtxt("Producing item ...");
	last_produced_item++;
	debug("Produced item %d",last_produced_item);
	return last_produced_item;
}
void consume_item(int item) {
	debugtxt("Consuming item ...");
	last_consumed_item = item;
	debug("Consumed item %d",item);
}

int producerFunc() {
	debugtxt("Starting producer");
	int item;
	while(TRUE) {
		item=produce_item();
		// down(empty);
		// down(mutex);
		sem_wait(&semaphore);
		insert_item(item);
		// up(mutex);
		// up(full);
		sem_post(&semaphore);
	}
	debugtxt("Ending producer");
	return 0;
}

int consumerFunc() {
	debugtxt("Starting consumer");
	int item;
	while(TRUE) {
		// down(full);
		// down(mutex);
		sem_wait(&semaphore);
		item = remove_item();
		// up(mutex);
		// up(empty);
		sem_post(&semaphore);
		consume_item(item);
	}
	debugtxt("Ending consumer");
	return 0;
}

int main() {
	int i;

	last_produced_item = 0;
	start = 0;
	end = 0;
	// Criando semaforos ...
	// full = CreateSemaphore( 
	// 		NULL,           // default security attributes
	// 		0,			// initial count
	// 		N,  			// maximum count
	// 		NULL);
	// empty = CreateSemaphore( 
	// 		NULL,           // default security attributes
	// 		N,			// initial count
	// 		N,  			// maximum count
	// 		NULL);
	// mutex = CreateSemaphore( 
	// 		NULL,           // default security attributes
	// 		1,			// initial count
	// 		1,  			// maximum count
	// 		NULL);
	sem_init(&semaphore, 0, 1);

	void* threadFunc[2];
	threadFunc[0] = producerFunc;
	threadFunc[1] = consumerFunc;
	
	// LPTHREAD_START_ROUTINE threadFunc[2] = { producerFunc, consumerFunc };
	for(i=0;i<2;i++) {
   		pthread_create(&threads[i],
   			NULL,
   			threadFunc[i],
   			NULL);
		// handleThread[i] = CreateThread( 
            // NULL,               // default security attributes
            // 0,                  // use default stack size  
            // threadFunc[i],      // thread function pointer
            // &i,     			// argument to thread function 
            // 0,                  // use default creation flags 
            // &threadId[i]);   // returns the thread identifier 
	}
	
	// WaitForMultipleObjects(2, handleThread, TRUE, INFINITE);
	pthread_join(threads[1], NULL);	
	// for(i=0;i<2;i++) {
	// 	CloseHandle(handleThread[i]);
	// }
	// CloseHandle(empty);
	// CloseHandle(full);
	// CloseHandle(mutex);
	
}