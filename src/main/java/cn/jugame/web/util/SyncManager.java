package cn.jugame.web.util;

import java.util.List;

public class SyncManager {
    private List<Runnable> runnableList;
    public SyncManager(){}

    public SyncManager(List<Runnable> runnableList) {
        this.runnableList = runnableList;
    }

    public void setRunnable(List<Runnable> runnableList) {
        this.runnableList = runnableList;
    }
    public void run() {
        for(Runnable runnable: runnableList) {
            runThread(runnable);
        }
    }
    private void runThread(Runnable runnable) {
        synchronized (this) {
            runnable.run();
        }

    }
}
