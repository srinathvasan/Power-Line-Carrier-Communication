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
public class meterspojo {
    private int meterrecid;
    private String metername;
    private int hubid;
    private int nodeid;
    private int metertypeid;
    private int phasetypeid;
    private int currenttypeid;
    private int meterparentid;
    private float meterlatitude;
    private float meterlongitude;
    private String meterlocation;
    private String landmark;
    
     public meterspojo() {
    }

    public int getMeterrecid() {
        return meterrecid;
    }

    public void setMeterrecid(int meterrecid) {
        this.meterrecid = meterrecid;
    }

    public String getMetername() {
        return metername;
    }

    public void setMetername(String metername) {
        this.metername = metername;
    }

    public int getHubid() {
        return hubid;
    }

    public void setHubid(int hubid) {
        this.hubid = hubid;
    }

    public int getNodeid() {
        return nodeid;
    }

    public void setNodeid(int nodeid) {
        this.nodeid = nodeid;
    }

    public int getMetertypeid() {
        return metertypeid;
    }

    public void setMetertypeid(int metertypeid) {
        this.metertypeid = metertypeid;
    }

    public int getPhasetypeid() {
        return phasetypeid;
    }

    public void setPhasetypeid(int phasetypeid) {
        this.phasetypeid = phasetypeid;
    }

    public int getCurrenttypeid() {
        return currenttypeid;
    }

    public void setCurrenttypeid(int currenttypeid) {
        this.currenttypeid = currenttypeid;
    }

    public int getMeterparentid() {
        return meterparentid;
    }

    public void setMeterparentid(int meterparentid) {
        this.meterparentid = meterparentid;
    }

    public float getMeterlatitude() {
        return meterlatitude;
    }

    public void setMeterlatitude(float meterlatitude) {
        this.meterlatitude = meterlatitude;
    }

    public float getMeterlongitude() {
        return meterlongitude;
    }

    public void setMeterlongitude(float meterlongitude) {
        this.meterlongitude = meterlongitude;
    }

    public String getMeterlocation() {
        return meterlocation;
    }

    public void setMeterlocation(String meterlocation) {
        this.meterlocation = meterlocation;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public meterspojo(int meterrecid, String metername, int hubid, int nodeid, int metertypeid, int phasetypeid, int currenttypeid, int meterparentid, float meterlatitude, float meterlongitude, String meterlocation, String landmark) {
        this.meterrecid = meterrecid;
        this.metername = metername;
        this.hubid = hubid;
        this.nodeid = nodeid;
        this.metertypeid = metertypeid;
        this.phasetypeid = phasetypeid;
        this.currenttypeid = currenttypeid;
        this.meterparentid = meterparentid;
        this.meterlatitude = meterlatitude;
        this.meterlongitude = meterlongitude;
        this.meterlocation = meterlocation;
        this.landmark = landmark;
    }

    @Override
    public String toString() {
        return "meterspojo{" + "meterrecid=" + meterrecid + ", metername=" + metername + ", hubid=" + hubid + ", nodeid=" + nodeid + ", metertypeid=" + metertypeid + ", phasetypeid=" + phasetypeid + ", currenttypeid=" + currenttypeid + ", meterparentid=" + meterparentid + ", meterlatitude=" + meterlatitude + ", meterlongitude=" + meterlongitude + ", meterlocation=" + meterlocation + ", landmark=" + landmark + '}';
    }
           
}
