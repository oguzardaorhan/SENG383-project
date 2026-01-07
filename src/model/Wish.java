package model;

public class Wish {
    private String title;
    private String type; // "Activity" veya "Product"
    private String description;
    private int requiredLevel;
    private String status;

    public Wish(String title, String type, String description, int requiredLevel, String status) {
        this.title = title;
        this.type = type;
        this.description = description;
        this.requiredLevel = requiredLevel;
        this.status = status;
    }

    public String getTitle() { return title; }
    public String getType() { return type; }
    public String getDescription() { return description; }
    public int getRequiredLevel() { return requiredLevel; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    // Kaydetme Formatı
    public String toCSV() {
        return title + "|" + type + "|" + description + "|" + requiredLevel + "|" + status;
    }

    // Okuma Formatı
    public static Wish fromCSV(String line) {
        String[] parts = line.split("\\|");
        if (parts.length >= 5) {
            return new Wish(parts[0], parts[1], parts[2], Integer.parseInt(parts[3]), parts[4]);
        }
        return null;
    }
}
