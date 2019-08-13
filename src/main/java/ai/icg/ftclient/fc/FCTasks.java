package ai.icg.ftclient.fc;

import javafx.concurrent.Task;
import unlimited.fc.client.api.TransferHook;
import unlimited.fc.client.api.TransferMode;

import java.util.Observable;
import java.util.Observer;

public class FCTasks extends Task<Void> implements Observer {

    //for debugging
    private FCConfig fcConfig = populateconfigmodel();

    public FCConfig populateconfigmodel() {
        FCConfig fccmodel = new FCConfig();

        fccmodel.setIp("15.164.166.2");
        fccmodel.setPort(991);
        fccmodel.setShowConsoleLog(true);
        fccmodel.setAutoResume(true);
        fccmodel.setId("ichthys");
        fccmodel.setPwd("123");
        fccmodel.setTransferMode(TransferMode.AUTO);
        fccmodel.setStartRate(10000);
        fccmodel.setTargetRate(10000);
        fccmodel.setVerifyIntegrity(true);
        fccmodel.setAutoResume(true);

        return fccmodel;
    }
    //***********

    private FC fc = null;
    private String filepath = null;
    private TransferHook hook = null;

    public FCTasks(String filepath) {
        this.filepath = filepath;
    }

    public String getFilepath() {
        return this.filepath;
    }

    //TODO iscanceled 구현해야함.
    @Override
    protected Void call() throws Exception {

        fc = new FC(fcConfig);

        hook = fc.FileUpload(this.filepath);
        hook.addObserver(this);

        try {
            synchronized (this) {
                wait();
            }
        } catch (InterruptedException ex) {
        }

        fc.FcClose();

        return null;
    }

    //TODO check
    @Override
    protected void succeeded() {
        super.succeeded();
        updateMessage("succeeded");
    }

    @Override
    protected void cancelled() {
        super.cancelled();
        updateMessage("Cancelled!");
    }

    @Override
    protected void failed() {
        super.failed();
        updateMessage("Failed!");
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof TransferHook) {
            TransferHook h = (TransferHook) o;
            if (!h.isTransferComplete() && !h.isTransferCancelled() &&
                    !h.isTransferError()) {
                if (h.getStatusCode() == h.TRANSFERRING &&
                        h.getCurrentPercent() != 100) {
                    printProgress(h.getRate(), h.getCurrentPercent());

                } else if (h.getStatusCode() == h.DONEFILE) {
                    printProgress(h.getRate(), 100);

                } else if (h.getStatusCode() == h.FINISHED) {
                    synchronized (this) {
                        notifyAll();
                    }
                }
            }

        }

    }

    //TODO updateprogress 구현해야함.
    public void printProgress(int rate, int percent) {
        System.out.println("Thread Name: " + Thread.currentThread().getName() + " * " + rate + " kbps  " + percent + " %");
    }
}
