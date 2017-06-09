#include <string.h>
#include <errno.h>
#include <sys/types.h>
#include <unistd.h>
#include <stdio.h>
#define TRUE 1

int n = 0; int pid;
char *argv[30];char temp[256];
int argc;
char history[100][256];
int historyIndex = 0;

void type_prompt()
{  
   printf("[shell-PID=%i]$ ", getpid());
}

void addCommandToHistory(char *command){
   strcpy(history[historyIndex++], command);
}

void printHistory() {
   int i;
   for (i = 0; i < historyIndex; i++){
      printf("%d %s\n", i, history[i]);
   }
}

void read_command(char *argv[])
{  
   n = 0;
   gets(temp);
   if (strcmp(temp, "history") == 0){
      addCommandToHistory("history");
      printHistory();
      argv[0] = NULL;
      return;
   }
   if (temp[0] == '!'){
      if (temp[1] == '!'){
         if (strcmp(history[historyIndex-1], "history") == 0){
            addCommandToHistory("history");
            printHistory();
            argv[0] = NULL;
            return;
         }
         else{
            argv[n++] = strtok(history[historyIndex-1], " ");
            addCommandToHistory(history[historyIndex-1]);
         }
      }
      else {
         char *read;
         read = strtok(temp, "!");
         int index = atoi(read);
         argv[n++] = strtok(history[index], " ");
      }
   }
   else{
      argv[n++] = strtok (temp," ");
      addCommandToHistory(temp);
   }
   while (argv[n-1] != NULL)
      argv[n++] = strtok (NULL, " ");
}

int main()
{
   int status;
   argv[0] = NULL;
   puts("Terminal shell UNIX. Este terminal não aceita comandos executados em segundo plano (com &) nem comandos em pipe (com |)");
   while (TRUE) /* repeat forever */ {
      type_prompt(); /* display prompt on screen */
      read_command(argv); /* read input from terminal */
      if ((pid = fork()) != 0) /* fork off child process */ {
         printf("Esperando o filho: pid = %i\n", pid);
         waitpid(-1, &status, 0); /* wait for child to exit */
         printf("Espera terminada\n");
      } 
      else {
         if (execvp(argv[0], argv) == -1) /* execute command */
            printf("Não executou. Erro: %s\n", strerror(errno));
      }
   }
   return 0;
}
