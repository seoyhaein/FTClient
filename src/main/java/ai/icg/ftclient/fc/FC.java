package ai.icg.ftclient.fc;

import ai.icg.ftclient.utils.StringUtils;
import unlimited.fc.client.api.FCClient;
import unlimited.fc.client.api.TransferHook;
import java.io.IOException;

public class FC {

    private FCClient fcClient = null;
    private  TransferHook transferHook = null;

    public FC(FCConfig model) throws IOException {

        fcClient = new FCClient(model.getIp(), model.getPort());
        fcClient.setShowConsoleLog(model.getShowConsoleLog());
        fcClient.initialize();
        fcClient.setAutoResume(model.getAutoResume());
        fcClient.connect();
        fcClient.login(model.getId(), model.getPwd());
        fcClient.setMode(model.getTransferMode());
        fcClient.setStartRate(model.getStartRate());
        fcClient.setTargetRate(model.getTargetRate());
        fcClient.setVerifyIntegrity(model.getVerifyIntegrity());
    }

    public TransferHook FileUpload(String filepath) {

        if (this.fcClient == null || StringUtils.isNullOrEmpty(filepath))
            return null;
        try {
            this.transferHook = this.fcClient.upload(filepath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this.transferHook;
    }

    public void FcClientVerbose(TransferHook h) throws IOException {
        if(h ==null)
            return;

        while (true) {

            if (!h.isTransferComplete() && !h.isTransferCancelled() &&
                    !h.isTransferError()) {
                if (h.getStatusCode() == h.TRANSFERRING &&
                        h.getCurrentPercent() != 100) {

                    printProgress(h.getRate(), h.getCurrentPercent());
                }
                else if (h.getStatusCode() == h.DONEFILE) {
                    printProgress(h.getRate(), 100);
                }
            }
            else {
                // all files complete
                System.out.println("exit -----");
                break;

            }


           /* if(h.isTransferComplete()){
                System.out.println(h.getStatusCode());
                System.out.println("Goody bye");
                break;
            }


            // you could display some progress information here using the various methods in
            // the TransferHook object

            if (h.getStatusCode()==h.TRANSFERRING) {
                System.out.println(h.getPercent() + "% "+h.getRate()+" Kbps");
            }

            try {
                Thread.sleep(1000);
            }
            catch (InterruptedException ex) {
            }
        }

        if (h.isTransferError()) {
            System.out.println(h.getErrorMessage());*/

        }



       /*while(!this.transferHook.isTransferComplete()) {
           if (!this.transferHook.isTransferComplete() && !this.transferHook.isTransferCancelled() &&
                   !this.transferHook.isTransferError()) {
               if (this.transferHook.getStatusCode() == this.transferHook.TRANSFERRING &&
                       this.transferHook.getCurrentPercent() != 100) {

                   printProgress(this.transferHook.getRate(), this.transferHook.getCurrentPercent());
               } else if (this.transferHook.getStatusCode() == this.transferHook.DONEFILE) {
                   printProgress(this.transferHook.getRate(), 100);
                   break;
               }

           }
       }*/
    }

    public void FcClientVerbose1(TransferHook h) {
        if (h == null)
            return;
        while (!h.isTransferComplete()) {

            if (!h.isTransferComplete() && !h.isTransferCancelled() &&
                    !h.isTransferError()) {
                if (h.getStatusCode() == h.TRANSFERRING &&
                        h.getCurrentPercent() != 100) {
                    System.out.println("11111111111111111111111");

                    printProgress(h.getRate(), h.getCurrentPercent());
                } else if (h.getStatusCode() == h.DONEFILE) {
                    System.out.println("22222222222222222222222");
                    printProgress(h.getRate(), 100);
                    //synchronized (this) {
                    //    notifyAll();
                    //}
                }
            } else {
                System.out.println("---------------------------------");
                System.out.println("Status code :  " + h.getStatusCode());
                // all files complete
                //synchronized (this) {
                //    notifyAll();
            }

        }
    }

    public void printProgress(int rate, int percent) {

        System.out.println(percent + " %" + rate + " kbps");
    }

    public void FcClose() throws IOException {
        if (this.fcClient == null)
            return;

        this.fcClient.disconnect();
        this.fcClient.finish();
        this.fcClient.finishAll();
    }
}
