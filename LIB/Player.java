import java.io.*;
import java.util.ArrayList;
import java.util.List;


    /**
     * 
     * Player
     */
    public class Player {
    private static final String FILE_NAME = "data/players.csv";

    private String name;
    private String password;
    private int score;
   // private int lastScore;
   // private int bestScore;

    public Player(String name, String password) {
        this(name, password, 0);
    }

    public Player(String name, String password, int score ) {
        this.name = name;
        this.password = password;
        this.score = score ;
      //  this.lastScore = lastScore;
      //  this.bestScore = bestScore;
    }

    public String getName() { return name; }
    public int getScore() { return score; }
    public void setScore(int score) { this.score = score;}

    public void updateScore(int newScore) {
        if (newScore > this.score) {
            this.score = newScore;
        }
    }

    //public int getLastScore() { return lastScore; }
    //public int getBestScore() { return bestScore; }

 /*    public void setLastScore(int score) {
        this.lastScore = score;
        if (score > this.bestScore) {
            this.bestScore = score;
        }
    } */

    /**
     * อ่านไฟล์ players.csvเพื่อเช็ก Username และ Password
     */
    public static Player authenticate(String name, String password) {
        File file = new File(FILE_NAME);
        if (!file.exists()) return null;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    String fileUser = parts[0].trim();
                    String filePass = parts[1].trim();

                    if (fileUser.equals(name) && filePass.equals(password)) {
                        /*int last = Integer.parseInt(parts[2].trim());
                        int best = Integer.parseInt(parts[3].trim());*/
                        int score = Integer.parseInt(parts[2].trim());
                        return new Player(fileUser, filePass, score);
                    }
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * สมัครสมาชิกแล้วเขียนบันทึกลงต่อท้ายไฟล์ 
     */
    public static boolean register(String name, String password) {
        File file = new File(FILE_NAME);

        // ตรวจสอบชื่อซ้ำ
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length > 0 && parts[0].trim().equalsIgnoreCase(name.trim())) {
                        return false; // พบชื่อซ้ำ
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        // เขียนข้อมูลใหม่ลงต่อท้ายไฟล์ (format: name,password,lastScore,bestScore)
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            writer.write(name + "," + password + ",0,0");
            writer.newLine();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * อัปเดตคะแนนล่าสุดกลับลงไฟล์ players.csv
     */
    public void save() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        List<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2 && parts[0].trim().equals(this.name)) {
                    lines.add(this.name + "," + this.password + "," + this.score);
                } else {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
            for (String l : lines) {
                writer.write(l);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    public static List<Player> getAllPlayersSorted() {
        List<Player> players = new ArrayList<>();
        File file = new File(FILE_NAME);

        if (!file.exists()) return players;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    String name = parts[0].trim();
                    String pass = parts[1].trim();
                   /* int last = Integer.parseInt(parts[2].trim());
                    int best = Integer.parseInt(parts[3].trim()); */
                    int score = Integer.parseInt(parts[2].trim());
                    players.add(new Player(name, pass, score));
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error reading leaderboard: " + e.getMessage());
        }

       players.sort((p1, p2) -> Integer.compare(p2.getScore(), p1.getScore()));
        return players;
    }



}
