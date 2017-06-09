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
const int producer = 0;
const int consumer = 1;
sem_t spaceRemaining;
sem_t entryCount;
sem_t mutex;

// // Truque para sabermos qual o semaforo foi chamado e poder imprimi-lo
#define up(SEM, NAME) _up(SEM,NAME)
#define down(SEM, NAME) _down(SEM,NAME)

void _up(sem_t* sem, const char * name) {
	debug("Up %s ...",name);
	sleep(5);
	sem_post(sem);
	debug("Up %s complete!",name);
}
void _down(sem_t* sem, const char * name) {
	debug("Down %s ...",name);
	sem_wait(sem);
	debug("Down %s complete!",name);
}

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
		down(&spaceRemaining, "spaceRemaining");
		down(&mutex, "mutex");
		insert_item(item);
		up(&mutex, "mutex");
		up(&entryCount, "entryCount");
	}
	debugtxt("Ending producer");
	return 0;
}

int consumerFunc() {
	debugtxt("Starting consumer");
	int item;
	while(TRUE) {
		down(&entryCount, "entryCount");
		down(&mutex, "mutex");
		item = remove_item();
		up(&mutex, "mutex");
		up(&spaceRemaining, "spaceRemaining");
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

	sem_init(&mutex, 0, 1);
	sem_init(&spaceRemaining, 0, N);
	sem_init(&entryCount, 0, 0);

	void* threadFunc[2];
	threadFunc[0] = producerFunc;
	threadFunc[1] = consumerFunc;

	for(i=0;i<2;i++) {
   		pthread_create(&threads[i],
   			NULL,
   			threadFunc[i],
   			NULL);
	}
	pthread_join(threads[1], NULL);	
}