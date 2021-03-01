//#include <iostream>
#include<stdio.h>
#include<time.h>
#include <math.h>
#include <stdlib.h>
#include <windows.h>
#include<string.h>
char fname[21] = "resultAllBenchC.csv";

int N = 10000;
int nano = pow(10, 9);

int allocStack(int N) {
	int amount = 0;


	for (int i = 0; i < N; i++) {
		amount++;
	}
	return amount;
}

struct Book {
	int id;
	char* title;
};
struct Book* arr_Book=(Book*)malloc(N*sizeof(Book));

void Book_init() {

	for (int i = 0; i < N; i++) {
		arr_Book[i].id = i;
		char name[1000] = "";
		strcat(name, "Book");
		char it[1000] = "";
		sprintf(it, "%d", i);
		//printf("%s\n", it);
		arr_Book[i].title = strcat(name, it);
		//printf("%s\t%d\n", arr_Book[i].title, arr_Book[i].id);
	}

}
#ifdef WIN32


double get_time_stack()
{
	LARGE_INTEGER t1, t2, f;
	QueryPerformanceCounter(&t1);

	int a = allocStack(N);

	QueryPerformanceCounter(&t2);
	QueryPerformanceFrequency(&f);
	return (double)(t2.QuadPart - t1.QuadPart) / (double)f.QuadPart;
}

double get_time_Heap()
{
	LARGE_INTEGER t1, t2, f;
	QueryPerformanceCounter(&t1);

	Book_init();

	QueryPerformanceCounter(&t2);
	QueryPerformanceFrequency(&f);
	return (double)(t2.QuadPart - t1.QuadPart) / (double)f.QuadPart;
}

#else

#include <sys/time.h>
#include <sys/resource.h>

double get_time()
{
	struct timeval t;
	struct timezone tzp;
	gettimeofday(&t, &tzp);
	return t.tv_sec + t.tv_usec * 1e-6;
}

#endif



void create_marks_csv(char* fname,  int num_of_test, char* test, char* res_nanosec, char* res_sec,  char mode) {

	printf("\n Creating %s.csv file", fname);

	FILE* fp;

	int i, j;

	//filename = strcat(filename, ".csv");

	fp = fopen(fname, &mode);//cu append!!!
	if (num_of_test == 1)
	{
		fprintf(fp, "Name Test, result Nanosec, result Seconds");
		fprintf(fp, "\n");
	}
	

	
	fprintf(fp, "%s, %s, %s\n", test, res_nanosec, res_sec );


	fclose(fp);

	printf("\n %sfile created", fname);

}

void stackFunc() {
	double resultStack[100];
	double valNanoStack[100];
	double sumStack = 0;
	for (int i = 0; i < 100; i++) {
		resultStack[i] = get_time_stack();
		valNanoStack[i] = resultStack[i] * nano;
		sumStack = sumStack + valNanoStack[i];
	}
	double avgStack = sumStack / 100;

	char num1[16];
	char num2[16];

	printf("%d\n", allocStack(N));

	snprintf(num1, 16, "%lf", avgStack);
	printf(" %s nanoseconds", num1);


	snprintf(num2, 16, "%.10lf", avgStack / nano);
	printf("\n%s seconds", num2);
	//char fname[14] = "resultMem.csv";
	char test1[6] = "stack";
	char mod[3] = "w+";
	create_marks_csv(fname, 1, test1, num1, num2,*mod);
}

void heapBook() {
	
	double resultHeap[100];
	double valNanoHeap[100];
	double sumHeap = 0;
	for (int i = 0; i < 100; i++) {
		resultHeap[i] = get_time_Heap();
		valNanoHeap[i] = resultHeap[i] * nano;
		sumHeap = sumHeap + valNanoHeap[i];
	}
	double avgHeap = sumHeap / 100;

	char num1[16];
	char num2[16];

	//printf("%d\n", allocStack(N));

	snprintf(num1, 16, "%lf", avgHeap);
	printf("\n %sheap  nanoseconds", num1);


	snprintf(num2, 16, "%.10lf", avgHeap / nano);
	printf("\n%s heap seconds", num2);
	//char fname[18] = "resultMemHeap.csv";
	char test1[6] = "heap";
	char mod[3] = "a";
	create_marks_csv(fname, 2, test1, num1, num2,*mod);
}
bool find_Book_acces() {
	for (int i = 0; i < N; i++) {
		if (arr_Book[i].title == "Book426")
			return true;
		
	  }
	//return false;
}

double get_time_find()
{
	LARGE_INTEGER t1, t2, f;
	QueryPerformanceCounter(&t1);

	bool ok= find_Book_acces;

	QueryPerformanceCounter(&t2);
	QueryPerformanceFrequency(&f);
	return (double)(t2.QuadPart - t1.QuadPart) / (double)f.QuadPart;
}

void findBook_time() {
	double resultFind[100];
	double valNanoFind[100];
	double sumFind = 0;
	for (int i = 0; i < 100; i++) {
		resultFind[i] = get_time_find();
		valNanoFind[i] = resultFind[i] * nano;
		sumFind = sumFind + valNanoFind[i];
	}
	double avgFind = sumFind / 100;

	char num1[16];
	char num2[16];

	bool x = find_Book_acces();
	printf("\n%s", x ? "true" : "false");

	snprintf(num1, 16, "%lf", avgFind);
	printf("\n %sheap  nanoseconds", num1);


	snprintf(num2, 16, "%.10lf", avgFind / nano);
	printf("\n%s heap seconds", num2);
	//char fname[18] = "resultMemFind.csv";
	char test1[10] = "accesHeap";
	char mod[3] = "a";
	create_marks_csv(fname, 3, test1, num1, num2, *mod);
}
DWORD Sum;
/* data is shared by the thread(s) */
/* thread runs in this separate function */
DWORD WINAPI Summation(LPVOID Param) {
	DWORD Upper = *(DWORD*)Param;
	for (DWORD i = 0; i < Upper; i++)
		Sum += i;
	return 0;
}
/* thread runs in this separate function */
DWORD WINAPI ThreadRun(LPVOID Param) {
	//printf("\n%s\n", "Thread running...");
	return 0;
}
void creationThread() {
	DWORD ThreadId;
	HANDLE ThreadHandle;
	
	
	//int P= 100;
	/* create the thread */
	ThreadHandle = CreateThread(NULL,  0,ThreadRun, &N, 0, &ThreadId);
	

	if (ThreadHandle == NULL) {
		printf("%s", "Thread Creation failed & error NO ->", GetLastError());
		printf("\n");
	}
	else {
	printf("\nThread Creation Success\n");
		printf("Thread Id -> %d\n", ThreadId);
		 CloseHandle(ThreadHandle);
		 printf("\n sum = %d\n", Sum);
	}
   
}

#define BUFFER_SIZE 10
#define PRODUCER_SLEEP_TIME_MS 500
#define CONSUMER_SLEEP_TIME_MS 2000

LONG Buffer[BUFFER_SIZE];
LONG LastItemProduced;
ULONG QueueSize;
ULONG QueueStartOffset;

ULONG TotalItemsProduced;
ULONG TotalItemsConsumed;

CONDITION_VARIABLE BufferNotEmpty;
CONDITION_VARIABLE BufferNotFull;
CRITICAL_SECTION   BufferLock;

BOOL StopRequested;

DWORD WINAPI ProducerThreadProc(PVOID p)
{
	ULONG ProducerId = (ULONG)(ULONG_PTR)p;

	while (TotalItemsProduced<10)
	{
		// Produce a new item.

		Sleep(rand() % PRODUCER_SLEEP_TIME_MS);

		ULONG Item = InterlockedIncrement(&LastItemProduced);

		EnterCriticalSection(&BufferLock);

		while (QueueSize == BUFFER_SIZE && StopRequested == FALSE)
		{
			// Buffer is full - sleep so consumers can get items.
			SleepConditionVariableCS(&BufferNotFull, &BufferLock, INFINITE);
		}

		if (StopRequested == TRUE)
		{
			LeaveCriticalSection(&BufferLock);
			break;
		}

		// Insert the item at the end of the queue and increment size.

		Buffer[(QueueStartOffset + QueueSize) % BUFFER_SIZE] = Item;
		QueueSize++;
		TotalItemsProduced++;

		printf("Producer %u: item %2d, queue size %2u\r\n", ProducerId, Item, QueueSize);

		LeaveCriticalSection(&BufferLock);

		// If a consumer is waiting, wake it.

		WakeConditionVariable(&BufferNotEmpty);
	}

	printf("Producer %u exiting\r\n", ProducerId);
	return 0;
}

DWORD WINAPI ConsumerThreadProc(PVOID p)
{
	ULONG ConsumerId = (ULONG)(ULONG_PTR)p;

	while (TotalItemsConsumed<10)
	{
		EnterCriticalSection(&BufferLock);

		while (QueueSize == 0 && StopRequested == FALSE)
		{
			// Buffer is empty - sleep so producers can create items.
			SleepConditionVariableCS(&BufferNotEmpty, &BufferLock, INFINITE);
		}

		if (StopRequested == TRUE && QueueSize == 0)
		{
			LeaveCriticalSection(&BufferLock);
			break;
		}

		// Consume the first available item.

		LONG Item = Buffer[QueueStartOffset];

		QueueSize--;
		QueueStartOffset++;
		TotalItemsConsumed++;

		if (QueueStartOffset == BUFFER_SIZE)
		{
			QueueStartOffset = 0;
		}

		printf("Consumer %u: item %2d, queue size %2u\r\n",
			ConsumerId, Item, QueueSize);

		LeaveCriticalSection(&BufferLock);

		// If a producer is waiting, wake it.

		WakeConditionVariable(&BufferNotFull);

		// Simulate processing of the item.

		Sleep(rand() % CONSUMER_SLEEP_TIME_MS);
	}

	printf("Consumer %u exiting\r\n", ConsumerId);
	return 0;
}

void creationMultiThread() {
	InitializeConditionVariable(&BufferNotEmpty);
	InitializeConditionVariable(&BufferNotFull);

	InitializeCriticalSection(&BufferLock);

	DWORD id;
	HANDLE hProducer1 = CreateThread(NULL, 0, ProducerThreadProc, (PVOID)1, 0, &id);
	HANDLE hConsumer1 = CreateThread(NULL, 0, ConsumerThreadProc, (PVOID)1, 0, &id);
	//HANDLE hConsumer2 = CreateThread(NULL, 0, ConsumerThreadProc, (PVOID)2, 0, &id);

	//puts("Press enter to stop...");
	//getchar();

	EnterCriticalSection(&BufferLock);
	
	LeaveCriticalSection(&BufferLock);

	WakeAllConditionVariable(&BufferNotFull);
	WakeAllConditionVariable(&BufferNotEmpty);

	WaitForSingleObject(hProducer1, INFINITE);
	WaitForSingleObject(hConsumer1, INFINITE);
	//WaitForSingleObject(hConsumer2, INFINITE);
     StopRequested = TRUE;
	
	printf("TotalItemsProduced: %u, TotalItemsConsumed: %u\r\n",
		TotalItemsProduced, TotalItemsConsumed);
	
}
double get_time_thread()
{
	LARGE_INTEGER t1, t2, f;
	QueryPerformanceCounter(&t1);

	creationThread();

	QueryPerformanceCounter(&t2);
	QueryPerformanceFrequency(&f);
	return (double)(t2.QuadPart - t1.QuadPart) / (double)f.QuadPart;
}

void resThread_time() {
	double resultThread[100];
	double valNanoThread[100];
	double sumThread = 0;
	for (int i = 0; i < 100; i++) {
		resultThread[i] = get_time_thread();
		valNanoThread[i] = resultThread[i] * nano;
		sumThread = sumThread + valNanoThread[i];
	}
	double avgThread= sumThread/ 100;

	char num1[16];
	char num2[16];


	snprintf(num1, 16, "%lf", avgThread);
	printf("\n %s thread  nanoseconds", num1);


	snprintf(num2, 16, "%.10lf", avgThread / nano);
	printf("\n%s thread heap seconds", num2);
	//char fname[18] = "resultThread.csv";
	char test1[13] = "createThread";
	char mod[3] = "a";
	create_marks_csv(fname, 4, test1, num1, num2, *mod);
}
double get_time_contextSwitch()
{
	LARGE_INTEGER t1, t2, f;
	QueryPerformanceCounter(&t1);

	creationMultiThread();

	QueryPerformanceCounter(&t2);
	QueryPerformanceFrequency(&f);
	return (double)(t2.QuadPart - t1.QuadPart) / (double)f.QuadPart;
}

void contextSwThr() {
	double resultMultiTh[5];
	double valNanoMultiTh[5];
	double sumMultiTh = 0;
	for (int i = 0; i < 5; i++) {
		resultMultiTh[i] = get_time_contextSwitch();
		valNanoMultiTh[i] = resultMultiTh[i] * nano;
		sumMultiTh= sumMultiTh + valNanoMultiTh[i];
	}
	double avgMulti = sumMultiTh / 5;

	char num1[16];
	char num2[16];


	snprintf(num1, 16, "%lf", avgMulti);
	printf("\n %scontext switch  nanoseconds", num1);


	snprintf(num2, 16, "%.10lf", avgMulti / nano);
	printf("\n%s context seconds", num2);
	//char fname[21] = "resultContextsSW.csv";
	char test1[14] = "contextSwitch";
	char mod[3] = "a";
	create_marks_csv(fname, 5, test1, num1, num2,*mod);
}


void migration() {
		DWORD ThreadId1;
		HANDLE ThreadHandle1;
		DWORD ThreadId2;
		HANDLE ThreadHandle2;

		//int P= 100;
		/* create the thread */
		ThreadHandle1 = CreateThread(NULL, 0, ThreadRun, (PVOID)1, 0, &ThreadId1);
		DWORD_PTR p = SetThreadAffinityMask(ThreadHandle1, DWORD_PTR(1) << 1);
		if (p == 0) {
			DWORD err = GetLastError();
			printf("Set affinity failed, err %s \n:", err);
		}
		ThreadHandle2 = CreateThread(NULL, 0, ThreadRun, (PVOID)2, 0, &ThreadId2);
		p = SetThreadAffinityMask(ThreadHandle2, DWORD_PTR(1) << 2);
		if (p == 0) {
			DWORD err = GetLastError();
			printf("Set affinity failed, err %s \n:", err);
		}
	
	
}
double get_time_migration()
{
	LARGE_INTEGER t1, t2, f;
	QueryPerformanceCounter(&t1);

	migration();

	QueryPerformanceCounter(&t2);
	QueryPerformanceFrequency(&f);
	return (double)(t2.QuadPart - t1.QuadPart) / (double)f.QuadPart;
}

void resMigration() {
	double resultMigration[100];
	double valNanoMigration[100];
	double sumMigration = 0;
	for (int i = 0; i < 100; i++) {
		resultMigration[i] = get_time_migration();
		valNanoMigration[i] = resultMigration[i] * nano;
		sumMigration = sumMigration + valNanoMigration[i];
	}
	double avgMigr = sumMigration / 100;

	char num1[16];
	char num2[16];


	snprintf(num1, 16, "%lf", avgMigr);
	printf("\n %s migration switch  nanoseconds", num1);


	snprintf(num2, 16, "%.10lf", avgMigr / nano);
	printf("\n%s migration seconds", num2);
	//char fname[21] = "resultContextsSW.csv";
	char test1[14] = "migration";
	char mod[3] = "a";
	create_marks_csv(fname, 6, test1, num1, num2, *mod);
}
int main()
{
	printf("Hello World!\n");
	stackFunc();
	heapBook();
	findBook_time();
	resThread_time();
	contextSwThr();
	resMigration();
	return 0;


}
