#include <pthread.h>
#include <stdio.h>
#include <unistd.h>

#define TRUE 1
#define FALSE 0
#define N 10
#define debugtxt(FORMAT) printf(" TID %lu: " #FORMAT "\n",pthread_self())
#define debug(FORMAT, ARGS...) printf("TID %lu: " #FORMAT "\n",pthread_self(),ARGS)
int line_counter;


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

//----------- NAO VAI SER USADO, EH SO PARA EVITAR WARNING DE NULL!!!
pthread_mutex_t mutex;
//-----------

// Referencias ao produtor e ao consumidor
const int producer = 0;
const int consumer = 1;
void sleepT(int x) {
	debugtxt("Sleeping ...");
	pthread_cond_wait(&condThreads[x], &mutex);
	debugtxt("Awaken!");
}
void wakeupT(const int x) {
	debug("Waking up %s ...", x == 0 ? "producer" : "consumer");
	pthread_cond_signal(&condThreads[x]);
	debug("Wakeup signal sent to %s!", x == 0 ? "producer" : "consumer");
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
		if(count == N) sleepT(0);
		insert_item(item);
		count = count + 1;
		if(count == 1) wakeupT(1);
	}
	debugtxt("Ending producer");
	return 0;
}

int consumerFunc() {
	debugtxt("Starting consumer");
	int item;
	while(TRUE) {
		if(count == 0) {
			// sleep(3); //Descomentar:forca disputa
			sleepT(1);
		}
		item = remove_item();
		count = count -1;
		if(count == N-1) wakeupT(0);
		consume_item(item);
	}
	debugtxt("Ending consumer");
	return 0;
}

int main() {
   last_produced_item = 0;
   start = 0;
   end = 0;
   void* threadFunc[2];
   threadFunc[0] = producerFunc;
   threadFunc[1] = consumerFunc;
   int i;

   for(i=0;i<2;i++) {
   		pthread_create(&threads[i],
   			NULL,
   			threadFunc[i],
   			NULL);
   }
   pthread_join(threads[1], NULL);	
}