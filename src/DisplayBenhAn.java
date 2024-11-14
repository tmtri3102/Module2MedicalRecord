import java.util.List;

public class DisplayBenhAn {
    public void displayBenhAnList(List<BenhAn> benhAnList) {
        if (benhAnList.isEmpty()) {
            System.out.println("Danh sách bệnh án trống.");
            return;
        }

        System.out.println("DANH SÁCH BỆNH ÁN:");
        System.out.printf("%-10s | %-15s | %-20s | %-15s | %-15s | %-20s | %-15s | %-20s%n",
                "Số Thứ Tự", "Mã Bệnh Án", "Tên Bệnh Nhân",
                "Ngày Nhập Viện", "Ngày Xuất Viện", "Lý Do Nhập Viện",
                "Chi Phí/Gói VIP", "Ngày Hết Hạn Gói VIP");
        System.out.println("-".repeat(130));

        for (BenhAn benhAn : benhAnList) {
            if (benhAn instanceof BenhAnThuong) {
                BenhAnThuong benhAnThuong = (BenhAnThuong) benhAn;
                System.out.printf("%-10d | %-15s | %-20s | %-15s | %-15s | %-20s | %-15.2f | %-20s%n",
                        benhAnThuong.getRollNo(),
                        benhAnThuong.getId(),
                        benhAnThuong.getName(),
                        benhAnThuong.getAdmissionDate(),
                        benhAnThuong.getDischargeDate(),
                        benhAnThuong.getReason(),
                        benhAnThuong.getCost(),
                        "N/A"
                );
            } else if (benhAn instanceof BenhAnVip) {
                BenhAnVip benhAnVip = (BenhAnVip) benhAn;
                System.out.printf("%-10d | %-15s | %-20s | %-15s | %-15s | %-20s | %-15s | %-20s%n",
                        benhAnVip.getRollNo(),
                        benhAnVip.getId(),
                        benhAnVip.getName(),
                        benhAnVip.getAdmissionDate(),
                        benhAnVip.getDischargeDate(),
                        benhAnVip.getReason(),
                        benhAnVip.getVipPackage(),
                        benhAnVip.getPackageExpiryDate()
                );
            }
        }
    }
}