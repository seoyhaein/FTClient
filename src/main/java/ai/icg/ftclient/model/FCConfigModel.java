package ai.icg.ftclient.model;

import unlimited.fc.client.api.TransferHook;

public class FCConfigModel {

    private boolean showconsolelog = false;
    private boolean autoresume = false;
    private String ip = null;
    private Integer port = null;
    private String id = null;
    private String pwd = null;
    private Integer transfermode = null;
    private Integer startrate = null;
    private Integer targetrate = null;
    private boolean verifyintegrity = false;

    //private int fcclientid = 0;

    //getter
    public boolean getShowConsoleLog(){
        return showconsolelog;
    }

    public boolean getAutoResume(){
        return autoresume;
    }

    public String getIp(){
        return ip;
    }

    public Integer getPort(){
        return port;
    }

    public String getId(){
        return id;
    }

    public String getPwd(){
        return pwd;
    }

    public Integer getTransferMode(){
        return transfermode;
    }

    public Integer getStartRate(){
        return startrate;
    }

    public Integer getTargetRate(){
        return targetrate;
    }

    public boolean getVerifyIntegrity(){
        return verifyintegrity;
    }

   /* public int getFCClientId(){
        return fcclientid;
    }*/

    //setter
    public void setShowConsoleLog(boolean showconsolelog){
        this.showconsolelog = showconsolelog;
    }

    public void setAutoResume(boolean autoresume){
        this.autoresume = autoresume;
    }

    public void setIp(String ip){
        this.ip = ip;
    }

    public void setPort(Integer port){
        this.port = port;
    }

    public void setId(String id){
        this.id = id;
    }

    public void setPwd(String pwd){
        this.pwd = pwd;
    }

    public void setTransferMode(Integer transfermode){
        this.transfermode = transfermode;
    }

    public void setStartRate(Integer startrate){
        this.startrate = startrate;
    }

    public void setTargetRate(Integer targetrate){
        this.targetrate = targetrate;
    }

    public void setVerifyIntegrity(boolean verifyintegrity){
        this.verifyintegrity = verifyintegrity;
    }

   /* public void setFCClientId(int fccclientid){
        this.fcclientid = fccclientid;
    }*/

}
