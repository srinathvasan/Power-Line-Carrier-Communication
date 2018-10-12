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
public class hubdetailspojo {
    private int hubid;
    private String hubname;
    private int hubtypeid;
    private String hubIP;
    private String hubdescription;
    private int userid;
    private float hublatitude;
    private float hublongitude;
    private String hublocation;
    private String hublandmark;

    public hubdetailspojo() {
    }

    public int getHubid() {
        return hubid;
    }

    public void setHubid(int hubid) {
        this.hubid = hubid;
    }

    public String getHubname() {
        return hubname;
    }

    public void setHubname(String hubname) {
        this.hubname = hubname;
    }

    public int getHubtypeid() {
        return hubtypeid;
    }

    public void setHubtypeid(int hubtypeid) {
        this.hubtypeid = hubtypeid;
    }

    public String getHubIP() {
        return hubIP;
    }

    public void setHubIP(String hubIP) {
        this.hubIP = hubIP;
    }

    public String getHubdescription() {
        return hubdescription;
    }

    public void setHubdescription(String hubdescription) {
        this.hubdescription = hubdescription;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public float getHublatitude() {
        return hublatitude;
    }

    public void setHublatitude(float hublatitude) {
        this.hublatitude = hublatitude;
    }

    public float getHublongitude() {
        return hublongitude;
    }

    public void setHublongitude(float hublongitude) {
        this.hublongitude = hublongitude;
    }

    public String getHublocation() {
        return hublocation;
    }

    public void setHublocation(String hublocation) {
        this.hublocation = hublocation;
    }

    public String getHublandmark() {
        return hublandmark;
    }

    public void setHublandmark(String hublandmark) {
        this.hublandmark = hublandmark;
    }

    public hubdetailspojo(int hubid, String hubname, int hubtypeid, String hubIP, String hubdescription, int userid, float hublatitude, float hublongitude, String hublocation, String hublandmark) {
        this.hubid = hubid;
        this.hubname = hubname;
        this.hubtypeid = hubtypeid;
        this.hubIP = hubIP;
        this.hubdescription = hubdescription;
        this.userid = userid;
        this.hublatitude = hublatitude;
        this.hublongitude = hublongitude;
        this.hublocation = hublocation;
        this.hublandmark = hublandmark;
    }

    @Override
    public String toString() {
        return "hubdetailspojo{" + "hubid=" + hubid + ", hubname=" + hubname + ", hubtypeid=" + hubtypeid + ", hubIP=" + hubIP + ", hubdescription=" + hubdescription + ", userid=" + userid + ", hublatitude=" + hublatitude + ", hublongitude=" + hublongitude + ", hublocation=" + hublocation + ", hublandmark=" + hublandmark + '}';
    }
    
}
