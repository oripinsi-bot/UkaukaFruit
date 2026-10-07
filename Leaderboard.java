import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Leaderboard.java -จัดการรายชื่ออันดับคะแนน
 */
public class Leaderboard {

    /* สิ่งที่ต้องมี : 
    
    เมื่อจบเกมหรือเกมโอเวอร์ให้เพิ่มรายการใหม่ เรียงลำดับใหม่ แล้วบันทึกลงไฟล์ scores.txt

    ถ้าชื่อไม่มีตรงใน scores.txt ให้เก็บในarryใหม่และเริ่มจัดอันดับ
    ถ้าชื่อซ้ำ ให้ทำการคีย์ข้อมูลทับชื่อเมื่อมีคะแนนมากกว่าอันเก่า แต่ถ้าน้อยกว่าก็จะไม่เก็บข้อมูลและเริ่มจัดอันดับ

    */

    public static class ScoreEntry {

        //ทำการเก็บชื่อเก็บคะแนน

        private String name;
        private int score;

        public ScoreEntry(String name, int score) {
            this.name = name;
            this.score = score;
        }

        public String getName() {
            return name;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        @Override
        public String toString() {
            return name + ":" + score;
        }
        
    }

    private List<ScoreEntry> entries = new ArrayList<>();
    private final String FILE_NAME = "scores.txt";

    public Leaderboard() {
        loadFromFile();
    }

    public void addScore(String name, int score) {

        //เพิ่มรายการใหม่ เรียงลำดับใหม่ แล้วบันทึกลงไฟล์ scores.txt

        //ทำการตรวจสอบชื่อ
        if (name == null || name.trim().isEmpty()) {
            return;
        }

        name = name.trim();

        for (ScoreEntry entry : entries) {

            //ทำการตรวจสอบคะแนนที่มีและล่าสุดที่รับเข้ามา
            if (entry.getName().equalsIgnoreCase(name)) {

                // ถ้าคะแนนใหม่มากกว่าคะแนนเก่าใช้ตะแนนใหม่
                if (score > entry.getScore()) {
                    entry.setScore(score);
                }

                // ถ้าคะแนนน้อยกว่าหรือเท่ากันให้ใช้อันเก่า
                sortEntries();
                saveToFile();

                return;
            }
        }

        // ถ้าเป็นชื่อใหม่ให้เพิ่มรายการใหม่
        entries.add(new ScoreEntry(name, score));

        // เรียงคะแนน
        sortEntries();

        // บันทึกไฟล์
        saveToFile();

    }

    public List<ScoreEntry> getAll() {
        //คืนข้อมูลที่เรียงจากคะแนนมากไปน้อยแล้ว
        sortEntries();

        return entries;
    }

    public int getRankOf(int score) {
        //หาตำแหน่งอันดับของคะแนนนี้ในข้อมูล
        sortEntries();

        for (int i = 0; i < entries.size(); i++) {

            if (entries.get(i).getScore() == score) {
                return i + 1;
            }
        }

        return -1;
    }

    //ฟังชันจากมากไปน้อย
    private void sortEntries() {

        Collections.sort(entries, new Comparator<ScoreEntry>() {

            @Override
            public int compare(ScoreEntry a, ScoreEntry b) {
                return Integer.compare(b.getScore(), a.getScore());
            }
        });
    }

    //ดึงข้อมูลไฟล์คะแนน
    private void loadFromFile() {

        File file = new File(FILE_NAME);

        // ถ้ายังไม่มีไฟล์ให้สร้างใหม่
        if (!file.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(
                new FileReader(file))) {

            String line;

            while ((line = reader.readLine()) != null) {

                line = line.trim();

                if (line.isEmpty()) {
                    continue;
                }

                String[] data = line.split(",");

                if (data.length == 2) {

                    String name = data[0].trim();

                    try {
                        int score = Integer.parseInt(data[1].trim());

                        entries.add(new ScoreEntry(name, score));

                    } catch (NumberFormatException e) {
                        // ข้ามข้อมูลคะแนนที่ไม่ใช่ตัวเลข
                    }
                }
            }

            sortEntries();

        } catch (IOException e) {
            System.out.println("ไม่สามารถอ่านไฟล์ scores.txt ได้");
            e.printStackTrace();
        }
    }

    //บันทึกคะแนน
    private void saveToFile() {

        try (PrintWriter writer = new PrintWriter(
                new FileWriter(FILE_NAME))) {

            for (ScoreEntry entry : entries) {
                writer.println(
                    entry.getName() + "," + entry.getScore()
                );
            }

        } catch (IOException e) {
            System.out.println("ไม่สามารถบันทึกไฟล์ scores.txt ได้");
            e.printStackTrace();
        }
    }

}
