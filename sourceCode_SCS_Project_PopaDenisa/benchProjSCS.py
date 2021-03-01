import random
import sys
import threading
import time
import csv
from queue import Queue
import  os
import  affinity

class Book:
    def __init__(self, book_id, title):
        self.title = title
        self.book_id = book_id

    def __str__(self):
        return 'Book({}, {})'.format(self.book_id, self.title)

    def __repr__(self):
        return 'Book( {}, {})'.format(self.book_id, self.title)

    def __eq__(self, other):
        return self.book_id == other.book_id and self.title == other.title


def func1():
    counter = 0

    for i in range(10000):
        counter += i

    return counter


def func2():
    books = []
    for i in range(10000):
        books.append(Book(i, "nameBook" + str(i)))
    # print(books.__str__())
    return books


def func3Find():
    arrBook = func2()
    for i in range(10000):
        if arrBook.__getitem__(i).title == "nameBook456":
            return True
            break;


def resStack():
    resultStack = []
    for i in range(0, 100):
        start_time = time.perf_counter_ns()
        func1()
        resultStack.append(time.perf_counter_ns() - start_time)
        print(resultStack[i], "ns which is ", resultStack[i] * (10 ** (-9)), "seconds")

    sumst = 0.0;

    for i in range(0, 100):
        sumst = sumst + resultStack[i]
    averageTimeCounter = sumst / 100
    return averageTimeCounter


def resHeap():
    print("Results for heap:")
    resultHeap = []
    for i in range(0, 100):
        start_time = time.perf_counter_ns()
        func2()
        resultHeap.append(time.perf_counter_ns() - start_time)
        print(resultHeap[i], "ns which is ", resultHeap[i] * (10 ** (-9)), "seconds")

    sumHeap = 0.0;

    for i in range(0, 100):
        sumHeap = sumHeap + resultHeap[i]

    averageTimeBook = sumHeap / 100
    return averageTimeBook


def findHeapRes():
    print("Results for finding:")
    resultAccess = []
    for i in range(0, 100):
        start_time = time.perf_counter_ns()
        print(func3Find())
        resultAccess.append(time.perf_counter_ns() - start_time)
        print(resultAccess[i], "ns which is ", resultAccess[i] * (10 ** (-9)), "seconds")

    sumAccess = 0.0;

    for i in range(0, 100):
        sumAccess = sumAccess + resultAccess[i]

    averageTimeAcess = sumAccess / 100
    return averageTimeAcess


class thread(threading.Thread):

    def __init__(self, thread_name, thread_ID):
        threading.Thread.__init__(self)
        self.thread_name = thread_name
        self.thread_ID = thread_ID

        # helper function to execute the threads

    def run(self):
        amount = 0
        for i in range(0, 10000):
            amount = amount + 1

        print(amount)


def funcSingleThread(id):
    thread1 = thread("Thread" + str(id), id)
    print(str(thread1.thread_name) + " " + str(thread1.thread_ID));
    thread1.start()
    thread1.join()


def resSingleThread():
    resultThread = []
    for i in range(0, 9):
        start_time = time.perf_counter_ns()
        funcSingleThread(i)
        resultThread.append(time.perf_counter_ns() - start_time)
        print(resultThread[i], "ns which is ", resultThread[i] * (10 ** (-9)), "seconds")

    sumThread = 0.0;

    for i in range(0, 9):
        sumThread = sumThread + resultThread[i]

    averageTimeThread = sumThread / 10
    return averageTimeThread


exitFlag = 0


class multiThread(threading.Thread):
    def __init__(self, threadID, name, counter):
        threading.Thread.__init__(self)
        self.threadID = threadID
        self.name = name
        self.counter = counter

    def run(self):
        print("Running " + self.name )
        # print_time(self.name, 5, self.counter)
        for i in range(1,5):
            print( self.name, self.threadID, i)
            time.sleep(self.counter)

        print("Exiting " + self.name)


def funcMultiThread(index):
    thread1 = multiThread(index, "Thread"+str(index+1), 1)
    thread2 = multiThread(index+1, "Thread"+str(index+2), 2)

    # Start new Threads
    thread1.start()
    thread2.start()
    thread1.join()
    thread2.join()


def resultMultiTh():
       resultMThread = []
       for i in range(0, 1):
            start_time = time.perf_counter_ns()
            funcMultiThread(i)
            resultMThread.append(time.perf_counter_ns() - start_time)
            print(resultMThread[i], "ns which is ", resultMThread[i] * (10 ** (-9)), "seconds")
       sumThreadMulti = 0.0;

       for i in range(0, 1):
         sumThreadMulti = sumThreadMulti + resultMThread[i]

       averageTimeThreadM = sumThreadMulti/2


       return averageTimeThreadM

BUF_SIZE = 10
q = Queue(BUF_SIZE)
nrprod=0
# nrcons=0
class ProducerThread(threading.Thread):
    def __init__(self, group=None, target=None, name=None,
                 args=(), kwargs=None, verbose=None):
        super(ProducerThread,self).__init__()
        self.target = target
        self.name = name

    def run(self):
        global nrprod
        while nrprod<10:
            if not q.full():
                item = random.randint(1,10)
                q.put(item)
                print('produce putting ' + str(item)
                              + ' : ' + str(q.qsize()) + ' items in queue')
                nrprod=nrprod+1
                time.sleep(random.random())
        return

nrcons=0
class ConsumerThread(threading.Thread):
    def __init__(self, group=None, target=None, name=None,
                 args=(), kwargs=None, verbose=None):
        super(ConsumerThread,self).__init__()
        self.target = target
        self.name = name
        return

    def run(self):
        global nrcons
        while nrcons<10:
            if not q.empty():
                item = q.get()
                print('consumer Getting ' + str(item)
                              + ' : ' + str(q.qsize()) + ' items in queue')
                nrcons=nrcons+1
                time.sleep(random.random())
        return




def contextSwitch():

    p = ProducerThread(name='producer')
    c = ConsumerThread(name='consumer')

    p.start()
    time.sleep(2)
    c.start()
    time.sleep(2)
    p.join()
    c.join()


def resultContextSwitch():
    resultsw = []
    for i in range(0, 5):
        start_time = time.perf_counter_ns()
        contextSwitch()
        resultsw.append(time.perf_counter_ns() - start_time)
        print(resultsw[i], "ns which is ", resultsw[i] * (10 ** (-9)), "seconds")
    sumThreadsw = 0.0;

    for i in range(0, 5):
        sumThreadsw = sumThreadsw + resultsw[i]

    averageTimesw= sumThreadsw/ 5

    return averageTimesw


print("Number of CPUs:", os.cpu_count())

def migration():
    id1=1
    id2=2
    thread1 = thread("Thread" + str(id1), id1)
    print(str(thread1.thread_name) + " " + str(thread1.thread_ID));

    thread2 = thread("Thread" + str(id2), id2)
    print(str(thread2.thread_name) + " " + str(thread2.thread_ID));
    pid=0
    aff1 = affinity.get_process_affinity_mask(pid)
    print("Thread1 is eligibl to run on:", aff1)

    affinity.set_process_affinity_mask(0, 100)
    print("CPU affinity mask is modified for process id % s" % pid)
    # pid = 0
    aff = affinity.get_process_affinity_mask(pid)
    print("Now, process for th1 is eligibl to run on:", aff)

    pid = 0
    aff2 = affinity.get_process_affinity_mask(pid)
    print("Thread2 is eligibl to run on:", aff2)

    affinity.set_process_affinity_mask(0, 45)
    print("CPU affinity mask is modified for process id % s" % pid)
    # pid = 0
    aff2 = affinity.get_process_affinity_mask(pid)
    print("Now, process for th2is eligibl to run on:", aff2)

    thread1.start()
    thread2.start()
    thread1.join()
    thread2.join()

def resultMigration():
    resultMigr = []
    for i in range(0, 100):
        start_time = time.perf_counter_ns()
        migration()
        resultMigr.append(time.perf_counter_ns() - start_time)
        print(resultMigr[i], "ns which is ", resultMigr[i] * (10 ** (-9)), "seconds")
    sumThreadMigr= 0.0;

    for i in range(0, 100):
        sumThreadMigr= sumThreadMigr + resultMigr[i]

    averageTimeMigration= sumThreadMigr/ 100

    return averageTimeMigration


if __name__ == '__main__':
    with open('pythonResultBench.csv', 'w', newline='') as file:
        writer = csv.writer(file)
        writer.writerow(["testName""averagebenchmarkResult", "nanoseconds", " seconds", ])
        writer.writerow(["Stack counter", resStack(), resStack() * (10 ** (-9))])
        writer.writerow(["Heap Book", resHeap(), resHeap() * (10 ** (-9))])
        writer.writerow(["Find Book", findHeapRes(), findHeapRes() * (10 ** (-9))])
        writer.writerow(["Single Thread", resSingleThread(), resSingleThread() * (10 ** (-9))])
        #writer.writerow(["Context Switch Sleep", resultMultiTh(), resultMultiTh() * (10 ** (-9))])
        writer.writerow(["Context Switch producer consumer", resultContextSwitch(), resultContextSwitch() * (10 ** (-9))])
        writer.writerow( ["Migration", resultMigration(), resultMigration() * (10 ** (-9))])
    print("exit")