package storage;

import model.BenhAn;

import java.util.List;

public interface IRecordStorage {
    void writeRecord(List<BenhAn> benhAnList);
    List<BenhAn> readRecord();
}
