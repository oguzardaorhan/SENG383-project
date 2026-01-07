package model;

public class Task {
    private String title;
    private String description;
    private String dueDate;
    private int points;
    private String status; // "Pending", "Completed", "Approved"

    public Task(String title, String description, String dueDate, int points, String status) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.points = points;
        this.status = status;
    }

    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getDueDate() { return dueDate; }
    public int getPoints() { return points; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    // Dosyaya yazmak için (Format: Başlık|Açıklama|Tarih|Puan|Durum)
    public String toCSV() {
        return title + "|" + description + "|" + dueDate + "|" + points + "|" + status;
    }

    // Dosyadan okumak için
    public static Task fromCSV(String line) {
        String[] parts = line.split("\\|");
        if (parts.length >= 5) {
            return new Task(parts[0], parts[1], parts[2], Integer.parseInt(parts[3]), parts[4]);
        }
        return null;
    }
}