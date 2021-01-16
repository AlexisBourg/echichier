package controller;


import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class ControllerSon {


    public void jouerSon(String fileName){
        File sound =new File(fileName);
        try{
            if(sound.exists()){
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(sound);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();
            }
            else{
                //sound.createNewFile();
                System.out.println("Fichier audio introuvable");
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
