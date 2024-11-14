package storage;

import model.*;
import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class RecordStorage implements IRecordStorage{
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final String COMMA_DELIMITER = ",";
    private static final String DATA_RECORDS_CSV = "data/medical_records.csv";
    private static final String FILE_HEADER = "STT,Mã bệnh án,Tên,Mã bệnh nhân,Tên bệnh nhân,Ngày nhập viện," +
            "Ngày xuất viện,Lý do nhập viện,Loại bênh nhân";

    private RecordStorage(){}
    private static RecordStorage instance;
    public static RecordStorage getInstance(){
        if(instance == null){
            instance = new RecordStorage();
        }
        return instance;
    }


    @Override
    public void writeRecord(List<BenhAn> benhAnList) {
        try {
            // create file
            BufferedWriter writer = new BufferedWriter(new FileWriter(DATA_RECORDS_CSV));
            // write header
            writer.write(FILE_HEADER);
            writer.newLine();

            // write body
            for (BenhAn benhAn : benhAnList) {
                writer.write(String.valueOf(benhAn.getId()));
                writer.write(COMMA_DELIMITER);
                writer.write(benhAn.getRecordCode());
                writer.write(COMMA_DELIMITER);
                writer.write(benhAn.getPatientCode());
                writer.write(COMMA_DELIMITER);
                writer.write(benhAn.getName());
                writer.write(COMMA_DELIMITER);
                writer.write(benhAn.getDateOfAdmission().format(DATE_FORMATTER));
                writer.write(COMMA_DELIMITER);
                writer.write(benhAn.getDischargeDate().format(DATE_FORMATTER));
                writer.write(COMMA_DELIMITER);
                writer.write(benhAn.getReason());
                if (benhAn instanceof BenhAnThuong) {
                    writer.write(COMMA_DELIMITER);
                    writer.write(Double.toString(((BenhAnThuong) benhAn).getCost()));
                } else if (benhAn instanceof BenhAnVIP) {
                    writer.write(COMMA_DELIMITER);
                    writer.write(((BenhAnVIP) benhAn).getVIPPackage());
                }

                writer.newLine();
            }

            writer.flush();
            writer.close();
        } catch (IOException e) {
            System.out.println("Error while writing CSV file !!!");
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        } catch (Exception e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
    }

    @Override
    public List<BenhAn> readRecord() {
        return List.of();
    }
}
