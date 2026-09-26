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
    }

    private List<ScoreEntry> entries = new ArrayList<>();

    public void addScore(String name, int score) {
        //เพิ่มรายการใหม่ เรียงลำดับใหม่ แล้วบันทึกลงไฟล์ scores.txt
        //ทำการตรวจสอบชื่อ
        //ทำการตรวจสอบคะแนนที่มีและล่าสุดที่รับเข้ามา
    }

    public List<ScoreEntry> getAll() {
        //คืนข้อมูลที่เรียงจากคะแนนมากไปน้อยแล้ว
        return entries;
    }

    public int getRankOf(int score) {
        //หาตำแหน่งอันดับของคะแนนนี้ในข้อมูล
        return 0;
    }

}
