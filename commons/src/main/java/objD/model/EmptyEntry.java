package objD.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class EmptyEntry extends BaseMapEntry {
    public EmptyEntry(int rowNum, int colNum) {
        super(rowNum, colNum);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).
                append(rowNum).
                append(colNum).
                toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        EmptyEntry that = (EmptyEntry) obj;
        return new EqualsBuilder()
                .append(rowNum, that.rowNum)
                .append(colNum, that.colNum)
                .isEquals();
    }

    @Override
    public String toString() {
        return "EmptyEntry{rowNum = " + rowNum +", colNum = " + colNum + "}" ;
    }
}
