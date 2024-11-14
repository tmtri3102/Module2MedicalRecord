import java.time.LocalDate;

public class BenhAnVip extends BenhAn{
    private String vipPackage; // Gói VIP (VIP1, VIP2, VIP3)
    private LocalDate packageExpiryDate; // Thời hạn gói VIP

    public BenhAnVip(int id, int rollNo, String name, LocalDate admissionDate, LocalDate dischargeDate, String reason, String vipPackageLocalDate, LocalDate packageExpiryDate) {
        super(id, rollNo, name, admissionDate, dischargeDate, reason);
        this.packageExpiryDate = packageExpiryDate;
        this.vipPackage = vipPackage;
    }

    public LocalDate getPackageExpiryDate() {
        return packageExpiryDate;
    }

    public void setPackageExpiryDate(LocalDate packageExpiryDate) {
        this.packageExpiryDate = packageExpiryDate;
    }

    public String getVipPackage() {
        return vipPackage;
    }

    public void setVipPackage(String vipPackage) {
        this.vipPackage = vipPackage;
    }
    @Override
    public void displayInfo() {
        System.out.println("Bệnh án VIP:");
        System.out.println("ID: " + getId());
        System.out.println("Roll number: " + getRollNo());
        System.out.println("Patient name: " + getName());
        System.out.println("Admission Date: " + getAdmissionDate());
        System.out.println("Discharge Date: " + getDischargeDate());
        System.out.println("Reason: " + getReason());
        System.out.println("VIP Package: " + vipPackage);
        System.out.println("Package Expiry Date: " + packageExpiryDate);
    }
}
