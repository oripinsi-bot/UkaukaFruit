import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Leaderboard {
    private List<ScoreEntry> entries = new ArrayList<>();
    private final String FILE_NAME = "data/players.csv";

    public Leaderboard() {
        loadFromFile();
    }

    public void loadFromFile() {
        entries.clear();
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] parts = line.split(",");
                // คอลัมน์ใน players.csv: [0]Name, [1]Password, [2]LastScore, [3]BestScore
                if (parts.length >= 3) {
                    String name = parts[0].trim();
                    try {
                        // ดึงเฉพาะ Best Score (Index ที่ 3) มาแสดงในตาราง
                        int score = Integer.parseInt(parts[2].trim());
                        entries.add(new ScoreEntry(name, score));
                    } catch (NumberFormatException e) {
                        // ข้ามแถวที่แปลงตัวเลขไม่ผ่าน
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("do not read Leaderboard dai na : " + e.getMessage());
        }

        // เรียงอันดับคะแนนจากมากไปน้อย (Descending Order)
        entries.sort((a, b) -> Integer.compare(b.getScore(), a.getScore()));
    }

    public List<ScoreEntry> getAll() {
        loadFromFile(); // อ่านไฟล์ใหม่ทุกครั้งที่เรียกเพื่อความสดใหม่ของข้อมูล
        return entries;
    }

    // Inner class สำหรับดึงไปแสดงในตาราง JTable
    public static class ScoreEntry {
        private String name;
        private int score;

        public ScoreEntry(String name, int score) {
            this.name = name;
            this.score = score;
        }

        public String getName() { return name; }
        public int getScore() { return score; }
    }
}