package objD.model;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GMapFactoryTest {

    private static final String TEST_DATA_DIR = "test-data";

    @Test
    public void load() throws FileNotFoundException {
        GMapFactory factory = new GMapFactory();
        GMap map = factory.load(new FileInputStream(TEST_DATA_DIR + "/sample.map"));

        System.out.println(map.toString());
        Assert.assertEquals(map.getRowNum(), 7);
        Assert.assertEquals(map.getColNum(), 8);


        Assert.assertEquals(map.getEntry(0, 0), new RespawnPoint(0, 0, 1));
        Assert.assertEquals(map.getEntry(4, 3), new RespawnPoint(4, 3, 2));
        Assert.assertEquals(map.getEntry(1, 2), new Wall(1, 2));

    }
}
