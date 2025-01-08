package lab4;

public class Appointment {
    private String deptName;
    private int newMgrNo;
    private double newMgrComm;

    // Constructor
    public Appointment(String deptName, int newMgrNo, double newMgrComm) {
        this.deptName = deptName;
        this.newMgrNo = newMgrNo;
        this.newMgrComm = newMgrComm;
    }

    // Getter methods
    public String getDeptName() {
        return deptName;
    }

    public int getNewMgrNo() {
        return newMgrNo;
    }

    public double getNewMgrComm() {
        return newMgrComm;
    }
}