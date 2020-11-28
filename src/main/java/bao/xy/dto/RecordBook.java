package bao.xy.dto;

import bao.xy.model.Book;
import bao.xy.model.Record;

public class RecordBook extends Book {
    private Record record;

    public RecordBook() {}

    public RecordBook(Record record) {
        this.record = record;
    }


    public Record getRecord() {
        return record;
    }

    public void setRecord(Record record) {
        this.record = record;
    }

    @Override
    public String toString() {
        return "RecordBook{" +
                "record=" + record +
                ", book=" + super.toString() +
                '}';
    }
}
