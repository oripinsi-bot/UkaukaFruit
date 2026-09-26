import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
 * PlayPanel หน้าจอหลักสำหรับเล่นเกม คุมการเคลื่อนไหวของผู้เล่น
 */
public class PlayPanel extends JPanel {

    private Main game;
    private ArrayList<FallingObject> fallingObjects;
    private Random random;

    public PlayPanel(Main game) { //Constructor
        this.game = game;
        this.fallingObjects = new ArrayList<>();
        this.random = new Random();
       
    }

    public void startGame() {
        //รีคะแนน/เวลา อันเก่า
    }

    private void gameTick() {
        // เช็คชนตระกร้า/การเคลื่อนไหว
    }

    private void spawnFallingObject() {
        //สุ่มชนิดวัตถุ
    }

    private void endGame() {
      //ไปหน้าเกมโอเวอร์
    }

   
}