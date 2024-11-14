package controller;

import model.BenhAn;
import model.BenhAnThuong;
import model.BenhAnVIP;
import storage.RecordStorage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RecordController {
    static RecordStorage recordStorage = RecordStorage.getInstance();
    public static List<BenhAn> benhAnList = recordStorage.readRecord();

    public void add() {
        System.out.println("1. Bệnh nhân thường");
        System.out.println("2. Bệnh nhân VIP");

        Scanner scanner = new Scanner(System.in);
        System.out.print("Chọn loại bênh nhân: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        BenhAn benhAn = null;

        switch (choice) {
            case 1:
                benhAn = new BenhAnThuong();
                break;
            case 2:
                benhAn = new BenhAnVIP();
                break;
            default:
                System.out.println("Hãy nhập số tương ứng với loại bệnh nhân");
        }

        if (benhAn != null) {
            // input mã bệnh án
            boolean recordCodeMatch = false;
            while (!recordCodeMatch) {
                System.out.print("Nhập mã bệnh án (BA-XXX): ");
                String recordCode = scanner.nextLine();

                Pattern recordCodePattern = Pattern.compile("^BA-[0-9]{3}$");
                Matcher matcher = recordCodePattern.matcher(recordCode);

                if (matcher.matches()) {
                    // check xem trung ma benh nhan khong
                    boolean codeExists = false;
                    for (BenhAn record2 : benhAnList) {
                        if (record2.getRecordCode().equals(recordCode)) {
                            codeExists = true;
                            break;
                        }
                    }
                    if (codeExists) {
                        System.out.println("Bệnh án đã tồn tại!");
                    } else {
                        recordCodeMatch = true;
                        benhAn.setRecordCode(recordCode);
                    }
                } else {
                    System.out.println("BA-XXX cho mã bệnh án, với XXX là các kí tự số");
                }
            }

            // input mã bệnh nhân
            boolean patientCodeMatch = false;
            while (!patientCodeMatch) {
                System.out.print("Nhập mã bệnh nhân: ");
                String patientCode = scanner.nextLine();

                Pattern patientCodePattern = Pattern.compile("^BN-[0-9]{3}$");
                Matcher matcher = patientCodePattern.matcher(patientCode);

                if (matcher.matches()) {
                    patientCodeMatch = true;
                    benhAn.setPatientCode(patientCode);
                } else {
                    System.out.println("Dịnh dạng BN-XXX cho mã bệnh nhân, với XXX là các kí tự số");
                }
            }

            // input tên bệnh nhân
            System.out.print("Nhập tên bệnh nhân: ");
            String patientName = scanner.nextLine();
            benhAn.setName(patientName);

            // input ngày nhập viện
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            while (true) {
                try {
                    System.out.print("Nhập ngày nhập viện (dd/MM/yyyy): ");
                    String dateString1 = scanner.nextLine();
                    LocalDate dateOfAdmission = LocalDate.parse(dateString1, formatter);
                    benhAn.setDateOfAdmission(dateOfAdmission);
                    break;
                } catch (DateTimeParseException e) {
                    System.out.println("Định dạng ngày không hợp lệ. Vui lòng nhập lại.");
                }
            }

            // input ngày ra viện
            boolean isValidDate = false;
            while (!isValidDate) {
                try {
                    System.out.print("Nhập ngày xuất viện (dd/MM/yyyy): ");
                    String dateString2 = scanner.nextLine();
                    LocalDate dischargeDate = LocalDate.parse(dateString2, formatter);

                    if (dischargeDate.isAfter(benhAn.getDateOfAdmission())
                            || dischargeDate.isEqual(benhAn.getDateOfAdmission())) {
                        benhAn.setDischargeDate(dischargeDate);
                        isValidDate = true;
                    } else {
                        System.out.println("Ngày xuất viện không được trước ngày nhập viện.");
                    }
                } catch (DateTimeParseException e) {
                    System.out.println("Định dạng ngày không hợp lệ. Vui lòng nhập lại.");
                }
            }


            // input lý do nhập viện
            System.out.print("Lý do nhập viện: ");
            String reason = scanner.nextLine();
            benhAn.setReason(reason);

            // input theo loại bệnh nhân
            if (benhAn instanceof BenhAnThuong) {
                System.out.print("Nhập phí nằm viện: ");
                double cost = scanner.nextDouble();
                scanner.nextLine();
                ((BenhAnThuong) benhAn).setCost(cost);
            } else {
                System.out.println("Chọn loại VIP: ");
                System.out.println("1. VIP I");
                System.out.println("2. VIP II");
                System.out.println("3. VIP III");
                System.out.print("Chọn: ");
                int VIPChoice = scanner.nextInt();
                String vipType = "";
                switch (VIPChoice) {
                    case 1:
                        vipType = "VIP I";
                        break;
                    case 2:
                        vipType = "VIP II";
                        break;
                    case 3:
                        vipType = "VIP III";
                        break;
                    default:
                        System.out.println("Nhập số tương ứng với loại VIP");
                }
                ((BenhAnVIP) benhAn).setVIPPackage(vipType);
            }
        }

        // ghi file
        benhAnList.add(benhAn);
        System.out.println("Đã thêm bệnh án vào file!");
        recordStorage.writeRecord(benhAnList);
    }

    public void delete() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Nhập mã bệnh án muốn xoá (BA-XXX): ");
            String recordCode = scanner.nextLine();

            Pattern recordCodePattern = Pattern.compile("^BA-[0-9]{3}$");
            Matcher matcher = recordCodePattern.matcher(recordCode);

            if (matcher.matches()) {
                BenhAn recordToRemove = null;
                for (BenhAn record2 : benhAnList) {
                    if (record2.getRecordCode().equals(recordCode)) {
                        recordToRemove = record2;
                        break;
                    }
                }

                if (recordToRemove != null) {
                    while (true) {
                        System.out.println("Xác nhận xoá?");
                        System.out.println("1. Yes");
                        System.out.println("2. No");
                        System.out.print("Chọn: ");
                        int choice = scanner.nextInt();
                        scanner.nextLine();

                        switch (choice) {
                            case 1:
                                System.out.println("Đã xoá bệnh án " + recordToRemove.getRecordCode());
                                benhAnList.remove(recordToRemove);
                                recordStorage.writeRecord(benhAnList);
                                showRecords();
                                break;
                            case 2:
                                System.out.println("Quay về menu chính");
                                break;
                            default:
                                System.out.println("Nhập số 1 hoac 2");
                        }

                        if (choice == 1 || choice == 2) {
                            break;
                        }
                    }
                } else {
                    System.out.println("Không tìm thấy bênh án " + recordCode);
                }
                break;
            } else {
                System.out.println("Dịnh dạng BA-XXX cho mã bệnh án, với XXX là các kí tự số");
            }
        }

    }

    public void showRecords() {
        List<BenhAn> benhAnList = recordStorage.readRecord();
        if (RecordController.benhAnList.isEmpty()) {
            System.out.println("Không có bệnh án nào!");
        } else {
            for (BenhAn benhAn : RecordController.benhAnList) {
                System.out.println(benhAn);
            }
        }
    }
}
