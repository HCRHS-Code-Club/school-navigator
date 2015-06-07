public class Manager implements Runnable{

    private boolean stopped = false;
    private long totalPings;
    private long oldTime;
    private long time;
    private long totalSuccesses = 0;
    private long totalFailures = 0;
    private Worker[] workers = new Worker[10];

    public Manager() {
        for(int i = 0; i < workers.length; i++) {
            workers[i] = new Worker();
        }
    }

    @Override
    public void run() {
        for(int i = 0; i < workers.length; i++) {
            new Thread(workers[i]).start();
        }
        time = System.currentTimeMillis();
        oldTime = time;
        while (!isStopped()) {
            time = System.currentTimeMillis();
            if(time - oldTime <= 500) {
                totalPings = 0;
                for(int i = 0; i < workers.length; i++) {
                    totalPings += workers[i].getPings();
                }
                System.out.printf("\rTotal Pings: %d", totalPings);
                oldTime = time;
            }
        }
    }

    public synchronized void stop() {
        for(int i = 0; i < workers.length; i++) {
            workers[i].stop();
            totalSuccesses += workers[i].getSuccesses();
            totalFailures += workers[i].getFailures();
        }
        stopped = true;
    }

    public synchronized boolean isStopped() {
        return stopped;
    }

    public synchronized long getTotalSuccesses() {
        return totalSuccesses;
    }

    public synchronized long getTotalFailures() {
        return totalFailures;
    }
}
