package objD.model;

public class BaseMapEntry implements MapEntry {

    protected final int rowNum;
    protected final int colNum;

    public BaseMapEntry(int rowNum, int colNum) {
        this.rowNum = rowNum;
        this.colNum = colNum;
    }

    @Override
    public int getRowNum() {
        return rowNum;
    }

    @Override
    public int getColNum() {
        return colNum;
    }
}
