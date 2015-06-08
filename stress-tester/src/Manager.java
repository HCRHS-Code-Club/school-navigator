public class Manager implements Runnable{

    private boolean stopped = false;
    private long totalPings;
    private long oldTime;
    private long time;
    private long totalSuccesses = 0;
    private long totalFailures = 0;
    private Worker[] workers;
    private long pings;

    public Manager(long pings, int threads) {
        this.pings = pings;
        workers = new Worker[threads];
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
                if(totalPings >= pings)
                    stop();
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

    public long getTotalPings() {
        return totalPings;
    }

    public synchronized long getTotalSuccesses() {
        return totalSuccesses;
    }

    public synchronized long getTotalFailures() {
        return totalFailures;
    }
}
