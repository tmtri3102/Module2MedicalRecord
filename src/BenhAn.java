import java.time.LocalDate;
import java.time.LocalTime;

public abstract class BenhAn {
    private int id;
    private int rollNo;
    private String name;
    private LocalDate admissionDate; // ngay nhap vien
    private LocalDate dischargeDate; // ngay ra vien
    private String reason;

    public BenhAn(int id, int rollNo, String name, LocalDate admissionDate, LocalDate dischargeDate, String reason) {
        this.id = id;
        this.rollNo = rollNo;
        this.name = name;
        this.admissionDate = admissionDate;
        this.dischargeDate = dischargeDate;
        this.reason = reason;
    }

    public abstract void displayInfo();

    public LocalDate getAdmissionDate() {
        return admissionDate;
    }

    public void setAdmissionDate(LocalDate admissionDate) {
        this.admissionDate = admissionDate;
    }

    public LocalDate getDischargeDate() {
        return dischargeDate;
    }

    public void setDischargeDate(LocalDate dischargeDate) {
        this.dischargeDate = dischargeDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getRollNo() {
        return rollNo;
    }

    public void setRollNo(int rollNo) {
        this.rollNo = rollNo;
    }

    @Override
    public String toString() {
        return "BenhAn{" +
                "admissionDate=" + admissionDate +
                ", id=" + id +
                ", rollNo=" + rollNo +
                ", name='" + name + '\'' +
                ", dischargeDate=" + dischargeDate +
                ", reason='" + reason + '\'' +
                '}';
    }
}
