package model;

public class User {
    private String username;
    private String role; // "Child", "Parent", "Teacher"
    private int totalPoints;
    private int currentLevel;

    // Constructor (Kurucu Metot)
    public User(String username, String role) {
        this.username = username;
        this.role = role;
        this.totalPoints = 0;
        this.currentLevel = 1;
    }

    // Getter ve Setter Metotları
    public String getUsername() { return username; }
    public String getRole() { return role; }
    public int getTotalPoints() { return totalPoints; }
    public int getCurrentLevel() { return currentLevel; }

    public void setTotalPoints(int points) { this.totalPoints = points; }
    public void setCurrentLevel(int level) { this.currentLevel = level; }
}
