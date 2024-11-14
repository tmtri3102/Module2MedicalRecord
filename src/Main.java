

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static List<BenhAn> benhAnList = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        AddBenhAn addController = new AddBenhAn();
        DeleteBenhAn deleteController = new DeleteBenhAn(scanner);
        DisplayBenhAn displayController = new DisplayBenhAn();

        boolean running = true;
        while (running) {
            System.out.println("CHUONG TRINH QUAN LY BENH AN");
            System.out.println("Chon chuc nang theo so :");
            System.out.println("1. Them benh an");
            System.out.println("2. Xoa");
            System.out.println("3. Xem danh sách bệnh án");
            System.out.println("4. Thoat");
            System.out.print("Chon chuc nang: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addController.addBenhAn();
                    break;
                case 2:
                    deleteController.deleteBenhAn(benhAnList);
                    break;
                case 3:
                    displayController.displayBenhAnList(benhAnList);
                    break;
                case 4:
                    running = false;
                    break;
                default:
                    System.out.println("Lua chon khong hop le.");
                    break;
            }
        }
    }


}
