import java.time.LocalDate;

public class BenhAnThuong extends BenhAn {
    private double cost; // Phí nằm viện

    // Constructor
    public BenhAnThuong(int id, int rollNo, String name, LocalDate admissionDate, LocalDate dischargeDate, String reason, double cost) {
        super(id, rollNo, name, admissionDate, dischargeDate, reason);
        this.cost = cost;
    }

    // Getter and Setter
    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
    @Override
    public void displayInfo() {
        System.out.println("Bệnh án thường:");
        System.out.println("ID: " + getId());
        System.out.println("Roll number: " + getRollNo());
        System.out.println("Patient name: " + getName());
        System.out.println("Admission Date: " + getAdmissionDate());
        System.out.println("Discharge Date: " + getDischargeDate());
        System.out.println("Reason do nhập viện: " + getReason());
        System.out.println("Cost: " + cost + " VND");
    }

}
