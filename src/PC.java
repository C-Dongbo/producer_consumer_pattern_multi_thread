package src;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class PC { 

    // Create a list shared by producer and consumer 
    // Size of list is 10. 
    LinkedList<String> list = new LinkedList<>(); 
    int capacity = 10; 

    // Function called by producer thread 
    public void produce() throws Exception { 
        LinkedList<String> data = getData();
        while (true) { 
            synchronized (this){ 
          // producer thread waits while list is full 
          while (list.size() == capacity) 
              wait(); 

          // to insert the jobs in the list 
          list.add(data.pollFirst()); 
          
          // notifies the consumer thread that now it can start consuming 
          notify(); 

          // makes the working of program easier to  understand 
          Thread.sleep(10); 
        } 
      } 
    } 

    // Function called by consumer thread 
    public void consume() throws InterruptedException, IOException, ExecutionException {
        String outputPath = "./data/ThreadOut.txt";
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputPath)));
        try {
            while (true) { 
                synchronized (this) { 
                // consumer thread waits while list 
                // is empty 
                while (list.size() == 0) 
                    wait(); 
                
                // to retrive the ifrst job in the list 
                    ExecutorService pool = Executors.newFixedThreadPool(10);
                    Set<Future<String>> set = new HashSet<Future<String>>();
                    
                    while(!list.isEmpty()) {
                        String s = list.pollFirst();
                
                        Callable<String> callable = new MultiThread(s);
                        Thread.sleep(1);
                        Future<String> future = pool.submit(callable);
                        set.add(future);

                    }
                    for(Future<String> future : set) {
                        Result result = getResult(future.get());
                        writer.write(result.getInput() + "\t" + result.getResult() + "\t" + result.getStatus() + "\n");
                    }
                    pool.shutdown();
                    list.clear();
                // Wake up producer thread 
                notify(); 
                // and sleep 
                Thread.sleep(10); 
                }
            }
        }finally {
            writer.close();
        }
    } 

    public static LinkedList<String> getData() throws Exception{
        String inputFile = "./data/test.txt";
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile), "UTF-8"));
        LinkedList<String> dataList = new LinkedList<>();
        String line = null;
        while((line = reader.readLine()) != null) {
            dataList.add(line.trim());
        }
        reader.close();
        return dataList;
    }
    public static Result getResult(String s) {
        String[] sInfo = s.split("\t");
        Result answer = new Result(sInfo[0],sInfo[1],sInfo[2]);
        return answer;
    }
}