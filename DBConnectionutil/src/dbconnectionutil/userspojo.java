/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbconnectionutil;

/**
 *
 * @author user
 */
public class userspojo {
    private int userid;
    private String username;
    private String useraddress;
    private int userage;
    private int gendertypeid;
    private int phno;
    private String emailid;

    public userspojo() {
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUseraddress() {
        return useraddress;
    }

    public void setUseraddress(String useraddress) {
        this.useraddress = useraddress;
    }

    public int getUserage() {
        return userage;
    }

    public void setUserage(int userage) {
        this.userage = userage;
    }

    public int getGendertypeid() {
        return gendertypeid;
    }

    public void setGendertypeid(int gendertypeid) {
        this.gendertypeid = gendertypeid;
    }

    public int getPhno() {
        return phno;
    }

    public void setPhno(int phno) {
        this.phno = phno;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }


    public userspojo(int userid, String username, String useraddress, int userage, int gendertypeid, int phno, String emailid) {
        this.userid = userid;
        this.username = username;
        this.useraddress = useraddress;
        this.userage = userage;
        this.gendertypeid = gendertypeid;
        this.phno = phno;
        this.emailid = emailid;
    }
    
    @Override
    public String toString() {
        return "userspojo{" + "userid=" + userid + ", username=" + username + ", useraddress=" + useraddress + ", userage=" + userage + ", gendertypeid=" + gendertypeid + ", phno=" + phno + ", emailid=" + emailid + '}';
    }
    
}
