import java.util.List;
import java.util.Scanner;

public class DeleteBenhAn {
    private Scanner scanner;

    public DeleteBenhAn(Scanner scanner) {
        this.scanner = scanner;
    }

    public void deleteBenhAn(List<BenhAn> benhAnList) {
        if (benhAnList.isEmpty()) {
            System.out.println("Danh sách bệnh án trống. Không thể xóa.");
            return;
        }

        System.out.print("Nhập mã bệnh án cần xóa: ");
        String idToDelete = scanner.nextLine();

        // Tìm kiếm bệnh án theo mã
        BenhAn benhAnToDelete = null;
        for (BenhAn benhAn : benhAnList) {
            if (benhAn.getId() == Integer.parseInt(idToDelete)) {
                benhAnToDelete = benhAn;
                break;
            }
        }

        if (benhAnToDelete != null) {
            benhAnList.remove(benhAnToDelete);
            System.out.println("Đã xóa bệnh án với mã: " + idToDelete);
        } else {
            System.out.println("Không tìm thấy bệnh án với mã: " + idToDelete);
        }
    }
}