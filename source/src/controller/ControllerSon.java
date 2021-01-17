package controller;


import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class ControllerSon {

    //Attribue

    //Constructeur

    //Methode
    /**
     * Permet de jouer un son
     * @param fileName : est l'url de du son à jouer
     */
    public void jouerSon(String fileName){
        File sound =new File(fileName);
        /*try{
            if(sound.exists()){
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(sound);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();
            }
            else{
                System.out.println("Fichier audio introuvable");
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }*/
    }
}
