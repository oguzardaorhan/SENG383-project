package data;

import model.Task;
import model.User;
import model.Wish;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DataManager {
    private static DataManager instance;

    // Dosya İsimleri
    private static final String TASK_FILE = "Tasks.txt";
    private static final String WISH_FILE = "Wishes.txt";

    private User currentUser;
    private List<Task> tasks;
    private List<Wish> wishes;

    private DataManager() {
        tasks = new ArrayList<>();
        wishes = new ArrayList<>();

        // Uygulama açılırken verileri yükle
        loadData();
    }

    public static DataManager getInstance() {
        if (instance == null) instance = new DataManager();
        return instance;
    }

    public void setCurrentUser(User user) { this.currentUser = user; }
    public User getCurrentUser() { return currentUser; }

    // --- TASK İŞLEMLERİ ---
    public List<Task> getTasks() { return tasks; }

    public void addTask(Task t) {
        tasks.add(t);
        saveTasks(); // Her eklemede kaydet
    }

    public void saveTasks() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TASK_FILE))) {
            for (Task t : tasks) {
                writer.write(t.toCSV());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // --- WISH İŞLEMLERİ ---
    public List<Wish> getWishes() { return wishes; }

    public void addWish(Wish w) {
        wishes.add(w);
        saveWishes(); // Her eklemede kaydet
    }

    public void saveWishes() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(WISH_FILE))) {
            for (Wish w : wishes) {
                writer.write(w.toCSV());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // --- DOSYA OKUMA (LOAD) ---
    private void loadData() {
        loadTasksFromFile();
        loadWishesFromFile();

        // Eğer dosyalar boşsa veya yoksa örnek veri yükle (İlk açılış için)
        if (tasks.isEmpty() && wishes.isEmpty()) {
            loadMockData();
            saveTasks();  // Örnek veriyi dosyaya yaz ki bir dahakine oradan okusun
            saveWishes();
        }
    }

    private void loadTasksFromFile() {
        File file = new File(TASK_FILE);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Task t = Task.fromCSV(line);
                if (t != null) tasks.add(t);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadWishesFromFile() {
        File file = new File(WISH_FILE);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Wish w = Wish.fromCSV(line);
                if (w != null) wishes.add(w);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadMockData() {
        System.out.println("İlk açılış: Örnek veriler yükleniyor...");
        tasks.add(new Task("Clean your room", "Organize toys", "2025-11-13", 20, "Completed"));
        tasks.add(new Task("Do homework", "Math exercises", "2025-11-13", 30, "Pending"));
        wishes.add(new Wish("New LEGO Set", "Product", "Star Wars set", 3, "Approved"));
    }
}
