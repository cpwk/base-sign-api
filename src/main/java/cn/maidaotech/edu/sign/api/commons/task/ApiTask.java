package cn.maidaotech.edu.sign.api.commons.task;


import cn.maidaotech.edu.sign.api.commons.context.SessionThreadLocal;

public abstract class ApiTask implements Runnable {

    @Override
    public final void run() {
        SessionThreadLocal.getInstance().set(null);
        try {
            doApiWork();
        } catch (Throwable t) {
            System.err.println(t);
        }
    }

    protected abstract void doApiWork() throws Exception;

}
