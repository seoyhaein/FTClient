package ai.icg.ftclient.model;

public class FTServerModel {
    private String ServerIp;
    private int ServerPort;

    public String getServerIp() {
        return ServerIp;
    }

    public int getServerPort() {
        return ServerPort;
    }

    public void setServerIp(String serverip) {
        this.ServerIp = serverip;
    }

    public void setServerPort(int serverport) {
        this.ServerPort = serverport;
    }
}
