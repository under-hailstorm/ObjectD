package objD.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Wall extends BaseMapEntry {
    public Wall(int rowNum, int colNum) {
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

        Wall that = (Wall) obj;
        return new EqualsBuilder()
                .append(rowNum, that.rowNum)
                .append(colNum, that.colNum)
                .isEquals();
    }
}
