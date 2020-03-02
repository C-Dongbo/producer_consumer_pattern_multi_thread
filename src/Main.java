package src;

public class Main {

    public static void main(String[] args) throws InterruptedException { 
        // Object of a class that has both produce() 
        // and consume() methods 
        final PC pc = new PC(); 
  
        // Create producer thread 
        Thread t1 = new Thread(new Runnable() { 
            @Override
            public void run() 
            { 
                try { 
                    pc.produce(); 
                } 
                catch (InterruptedException e) { 
                    e.printStackTrace(); 
                } catch (Exception e) {
                // TODO Auto-generated catch block
                    e.printStackTrace();
                } 
            } 
        }); 
  
        // Create consumer thread 
        Thread t2 = new Thread(new Runnable() { 
            @Override
            public void run() 
            { 
                try { 
                    pc.consume(); 
                } 
                catch (Exception e) { 
                    e.printStackTrace(); 
                } 
            } 
        }); 
  
        // Start both threads 
        t1.start(); 
        t2.start(); 
  
        // t1 finishes before t2 
        t1.join(); 
        t2.join(); 
    } 
}