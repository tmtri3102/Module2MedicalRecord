import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class AddBenhAn {
    private static final String FILE_PATH = "data/medical_records.csv";
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final Pattern ID_PATTERN = Pattern.compile("^BA-\\d{3}$");
    private static final Pattern PATIENT_ID_PATTERN = Pattern.compile("^BN-\\d{3}$");



    public void addBenhAn() {
        Scanner scanner = new Scanner(System.in);
        List<BenhAn> benhAnList = readBenhAnList();
        int newRollNo = benhAnList.isEmpty() ? 1 : benhAnList.get(benhAnList.size() - 1).getRollNo() + 1;

        while (true) {
            try {
                System.out.print("Ma Benh An: ");
                String id = scanner.nextLine();
                if (!ID_PATTERN.matcher(id).matches()) {
                    System.out.println("Vui long nhap theo dinh dang BA-XXX.");
                    continue;
                }

                if (benhAnList.stream().anyMatch(ba -> ba.getId() == Integer.parseInt(id))) {
                    throw new DuplicateMedicalRecordException("Benh an da ton tai");
                }

                System.out.print("Ma Benh Nhan: ");
                String patientId = scanner.nextLine();
                if (!PATIENT_ID_PATTERN.matcher(patientId).matches()) {
                    System.out.println("Vui long nhap theo dinh dang BN-XXX.");
                    continue;
                }

                System.out.print("Ten Benh Nhan: ");
                String name = scanner.nextLine();

                LocalDate admissionDate;
                LocalDate dischargeDate;
                try {
                    System.out.print("Ngay nhap vien: ");
                    admissionDate = LocalDate.parse(scanner.nextLine(), DATE_FORMAT);
                    System.out.print("Ngay ra vien: ");
                    dischargeDate = LocalDate.parse(scanner.nextLine(), DATE_FORMAT);
                    if (admissionDate.isAfter(dischargeDate)) {
                        System.out.println("Ngày nhập viện phải sớm hơn hoặc bằng ngày ra viện. Vui lòng nhập lại.");
                        continue;
                    }
                } catch (DateTimeParseException e) {
                    System.out.println("Vui long nhap theo dinh dang dd/MM/YYYY.");
                    continue;
                }

                System.out.print("Ly do nhap vien: ");
                String reason = scanner.nextLine();

                System.out.print("Chọn loại bệnh án để thêm (1: Thường, 2: VIP): ");
                int type = scanner.nextInt();
                scanner.nextLine();

                if (type == 1) {
                    System.out.print("Phi nam vien: ");
                    double cost = scanner.nextDouble();
                    scanner.nextLine();
                    BenhAnThuong benhAnThuong = new BenhAnThuong(Integer.parseInt(id), newRollNo, name, admissionDate, dischargeDate, reason, cost);
                    writeToCsv(benhAnThuong);
                } else if (type == 2) {
                    System.out.print("Nhập gói VIP (VIP 1, VIP 2, VIP 3): ");
                    String vipPackage = scanner.nextLine();
                    if (!vipPackage.matches("VIP 1|VIP 2|VIP 3")) {
                        System.out.println("Gói VIP không hợp lệ. Vui lòng nhập lại.");
                        continue;
                    }

                    System.out.print("Nhập thời hạn gói VIP (dd/MM/yyyy): ");
                    LocalDate packageExpiryDate = LocalDate.parse(scanner.nextLine(), DATE_FORMAT);
                    BenhAnVip benhAnVip = new BenhAnVip(Integer.parseInt(id), newRollNo, name, admissionDate, dischargeDate, reason, vipPackage, packageExpiryDate);
                    writeToCsv(benhAnVip);
                } else {
                    System.out.println("Loại bệnh án không hợp lệ. Vui lòng chọn lại.");
                    continue;
                }
                System.out.println("Bệnh án đã được thêm thành công.");
                break;

            } catch (DuplicateMedicalRecordException e) {
                System.out.println(e.getMessage());
            } catch (IOException e) {
                System.out.println("Lỗi ghi file: " + e.getMessage());
                break;
            }
        }
    }

    private List<BenhAn> readBenhAnList() {
        List<BenhAn> benhAnList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] details = line.split(",");
                int rollNo = Integer.parseInt(details[0].trim());
                String id = details[1].trim();
                String name = details[2].trim();
                LocalDate admissionDate = LocalDate.parse(details[3].trim(), DATE_FORMAT);
                LocalDate dischargeDate = LocalDate.parse(details[4].trim(), DATE_FORMAT);
                String reason = details[5].trim();

                if (details.length == 8) {
                    double cost = Double.parseDouble(details[6].trim());
                    benhAnList.add(new BenhAnThuong(Integer.parseInt(id), rollNo, name, admissionDate, dischargeDate, reason, cost));
                } else if (details.length == 9) {
                    String vipPackage = details[6].trim();
                    LocalDate packageExpiryDate = LocalDate.parse(details[7].trim(), DATE_FORMAT);
                    benhAnList.add(new BenhAnVip(Integer.parseInt(id), rollNo, name, admissionDate, dischargeDate, reason, vipPackage, packageExpiryDate));
                }
            }
        } catch (IOException e) {
            System.out.println("Lỗi đọc file. Vui long nhap thong tin. " + e.getMessage());
        }
        return benhAnList;
    }

    private void writeToCsv(BenhAn benhAn) throws IOException {
        try (FileWriter writer = new FileWriter(FILE_PATH, true)) {
            if (benhAn instanceof BenhAnThuong) {
                BenhAnThuong benhAnThuong = (BenhAnThuong) benhAn;
                writer.write(String.format("%d,%s,%s,%s,%s,%s,%.2f\n",
                        benhAnThuong.getRollNo(),
                        benhAnThuong.getId(),
                        benhAnThuong.getName(),
                        benhAnThuong.getAdmissionDate().format(DATE_FORMAT),
                        benhAnThuong.getDischargeDate().format(DATE_FORMAT),
                        benhAnThuong.getReason(),
                        benhAnThuong.getCost()));
            } else if (benhAn instanceof BenhAnVip) {
                BenhAnVip benhAnVip = (BenhAnVip) benhAn;
                writer.write(String.format("%d,%s,%s,%s,%s,%s,%s,%s\n",
                        benhAnVip.getRollNo(),
                        benhAnVip.getId(),
                        benhAnVip.getName(),
                        benhAnVip.getAdmissionDate().format(DATE_FORMAT),
                        benhAnVip.getDischargeDate().format(DATE_FORMAT),
                        benhAnVip.getReason(),
                        benhAnVip.getVipPackage(),
                        benhAnVip.getPackageExpiryDate().format(DATE_FORMAT)));
            }
        }
    }

    public static class DuplicateMedicalRecordException extends Exception {
        public DuplicateMedicalRecordException(String message) {
            super(message);
        }
    }
}
