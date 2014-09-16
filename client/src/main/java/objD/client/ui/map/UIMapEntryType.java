package objD.client.ui.map;

public enum UIMapEntryType {
    EMPTY("emptyCell");

    private UIMapEntryType(String imageKey) {
        this.imageKey = imageKey;
    }

    private String imageKey;

    public String getImageKey() {
        return imageKey;
    }
}
