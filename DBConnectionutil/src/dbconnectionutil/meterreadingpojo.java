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
public class meterreadingpojo {
  private  int meterreadingrecid;
  private  int nodeid;
  private  float voltage;
  private  float current;
  private  float power;
  private  String datetime;

    public meterreadingpojo() {
    }

    public int getMeterreadingrecid() {
        return meterreadingrecid;
    }

    public void setMeterreadingrecid(int meterreadingrecid) {
        this.meterreadingrecid = meterreadingrecid;
    }

    public int getNodeid() {
        return nodeid;
    }

    public void setNodeid(int nodeid) {
        this.nodeid = nodeid;
    }

    public float getVoltage() {
        return voltage;
    }

    public void setVoltage(float voltage) {
        this.voltage = voltage;
    }

    public float getCurrent() {
        return current;
    }

    public void setCurrent(float current) {
        this.current = current;
    }

    public float getPower() {
        return power;
    }

    public void setPower(float power) {
        this.power = power;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public meterreadingpojo(int meterreadingrecid, int nodeid, float voltage, float current, float power, String datetime) {
        this.meterreadingrecid = meterreadingrecid;
        this.nodeid = nodeid;
        this.voltage = voltage;
        this.current = current;
        this.power = power;
        this.datetime = datetime;
    }

    @Override
    public String toString() {
        return "meterreadingpojo{" + "meterreadingrecid=" + meterreadingrecid + ", nodeid=" + nodeid + ", voltage=" + voltage + ", current=" + current + ", power=" + power + ", datetime=" + datetime + '}';
    }
    
    
}
