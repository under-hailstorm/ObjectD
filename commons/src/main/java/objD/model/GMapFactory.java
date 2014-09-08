package objD.model;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class GMapFactory {

    private static final Logger LOG = LoggerFactory.getLogger(GMapFactory.class);

    public GMap load(InputStream is) {
        try {
            String jsonStr = IOUtils.toString(is);
            JSONObject json = new JSONObject(jsonStr);
            int height = json.getJSONObject("size").getInt("height");
            int width = json.getJSONObject("size").getInt("width");
            GMap result = new GMap(height, width);
            JSONArray respawns = json.getJSONArray("respawn");
            for (int i = 0; i < respawns.length(); i++) {
                JSONObject resp = respawns.getJSONObject(i);
                int row = resp.getInt("row");
                int col = resp.getInt("col");
                Teams team = Teams.valueOf(resp.getString("team"));

                result.addEntry(new RespawnPoint(row, col, team));
            }
            JSONArray walls = json.getJSONArray("wall");
            for (int i = 0; i < walls.length(); i++) {
                JSONObject wall = walls.getJSONObject(i);
                int row = wall.getInt("row");
                int col = wall.getInt("col");
                result.addEntry(new Wall(row, col));
            }

            return result;
        } catch (IOException e) {
            LOG.error("Error loading map", e);
            throw new IllegalStateException(e);
        }
    }
}
