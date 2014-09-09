package objD.model;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class GMapTest {

    private static final MapDirection[] allDirections = {
            MapDirection.NORTH_WEST, MapDirection.NORTH, MapDirection.NORTH_EAST,
            MapDirection.SOUTH_EAST, MapDirection.SOUTH, MapDirection.SOUTH_WEST};

    private final GMap testMap = new GMap(4, 3);//4rows, 3 columns in rows 0,2; 2 columns in rows 1,3

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {new EmptyEntry(0, 0), new EmptyEntry[]{null, null, null, new EmptyEntry(1, 0), new EmptyEntry(2, 0), null}},
                {new EmptyEntry(2, 1), new EmptyEntry[]{new EmptyEntry(1, 0), new EmptyEntry(0, 1), new EmptyEntry(1, 1), new EmptyEntry(3, 1), null, new EmptyEntry(3, 0)}},
                {new EmptyEntry(3, 1), new EmptyEntry[]{new EmptyEntry(2, 1), new EmptyEntry(1, 1), new EmptyEntry(2, 2), null, null, null}},
                {new EmptyEntry(2, 2), new EmptyEntry[]{new EmptyEntry(1, 1), new EmptyEntry(0, 2), null, null, null, new EmptyEntry(3, 1)}},
        };
    }

    @Test(dataProvider = "testData")
    public void getClosestEntry(EmptyEntry initialEntry, EmptyEntry[] expectedResult) {
        for (int i = 0; i < allDirections.length; i++) {
            Assert.assertEquals(testMap.getClosestEntry(initialEntry, allDirections[i]), expectedResult[i], "Failed for direction " + allDirections[i]);
        }
    }
}
