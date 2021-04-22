package ltd.newbee.mall.entity;

/**
 * @Author Richard
 * @Date 2021/4/22 9:55 AM
 */
public class AdminUser {
    private Integer adminUserId;
    private String  loginUserName;
    private String  loginPassword;
    private String  nickName;
    private Byte    locked;
    
    public Integer getAdminUserId() {
        return adminUserId;
    }
    
    public void setAdminUserId(Integer adminUserId) {
        this.adminUserId = adminUserId;
    }
    
    public String getLoginUserName() {
        return loginUserName;
    }
    
    public void setLoginUserName(String loginUserName) {
        this.loginUserName = loginUserName == null ? null : loginUserName.trim();
    }
    
    public String getLoginPassword() {
        return loginPassword;
    }
    
    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword == null ? null : loginPassword.trim();
    }
    
    public String getNickName() {
        return nickName;
    }
    
    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }
    
    public Byte getLocked() {
        return locked;
    }
    
    public void setLocked(Byte locked) {
        this.locked = locked;
    }
    
    @Override
    public String toString() {
        return "AdminUser{" +
                "adminUserId=" + adminUserId +
                ", loginUserName='" + loginUserName + '\'' +
                ", loginPassword='" + loginPassword + '\'' +
                ", nickName='" + nickName + '\'' +
                ", locked=" + locked +
                '}';
    }
}
