package ai.icg.ftclient.model;

public class UserModel {
    private String UserName, PassWord;

    public String getUserName() {
        return UserName;
    }

    public String getPassWord() {
        return PassWord;
    }

    public void setUserName(String username) {
        this.UserName = username;
    }

    public void setPassWord(String password) {
        this.PassWord = password;
    }
}
