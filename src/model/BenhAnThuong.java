package model;

import java.time.LocalDate;

public class BenhAnThuong extends BenhAn{
    private double cost;

    public BenhAnThuong() {
        super();
    }

    public BenhAnThuong(double cost) {
        super();
        this.cost = cost;
    }

    public BenhAnThuong(String recordCode, String patientCode, String name,
                        LocalDate dateOfAdmission, LocalDate dischargeDate, String reason, double cost) {
        super(recordCode, patientCode, name, dateOfAdmission, dischargeDate, reason);
        this.cost = cost;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "STT: " + super.getId() +
                ", Mã bệnh án:" + super.getRecordCode() +
                ", Mã bệnh nhân: " + super.getPatientCode() +
                ", Tên bệnh nhân: " + super.getName() +
                ", Ngày nhập viện: " + super.getDateOfAdmission() +
                ", Ngày xuất viện: " + super.getDischargeDate() +
                ", Lý do nhập việm: " + super.getReason() +
                ", Phí nhập viện: " + this.getCost() + " VND";
    }
}
