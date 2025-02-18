package com.example.coach.modele;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.coach.outils.MesOutils;
import com.example.coach.outils.Serializer;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Date;

/**
 * Classe qui permet de créer un Profil utilisateur dans le but de calculer son img
 * (Indide Masse Grasse) en valorisant les paramètres necessaires au calcul.
 */
public class Profil implements Serializable {

    // Constantes
    private static final Integer minFemme = 15; // maigre si en dessous
    private static final Integer maxFemme = 30; // gros si au dessus
    private static final Integer minHomme = 10; // maigre si en dessous
    private static final Integer maxHomme = 25; // gros si au dessus

    private Integer poids;
    private Integer taille;
    private Integer age;
    private Integer sexe;
    private float img = 0;
    private String message = "";
    private Date dateMesure;

    /**
     * Constructeur : valorise directement les proriétés poids, taille, age, sexe
     * @param poids
     * @param taille
     * @param age
     * @param sexe 1 pour homme, 0 pour femme
     */
    public Profil(Integer poids, Integer taille, Integer age, Integer sexe, Date dateMesure) {
        this.poids = poids;
        this.taille = taille;
        this.age = age;
        this.sexe = sexe;
        this.dateMesure = dateMesure;
    }


    public Integer getPoids() {
        return poids;
    }

    public Integer getTaille() {
        return taille;
    }

    public Integer getAge() {
        return age;
    }

    public Integer getSexe() {
        return sexe;
    }

    public Date getDateMesure() {
        return dateMesure;
    }

    /**
     * Retourne img après l'avoir calculé s'il est vide
     * @return img
     */
    public float getImg() {
        if(this.img == 0){
            float tailleFloat = (float)this.getTaille() / 100 ;
            img = (float)((1.2 * this.getPoids() / Math.pow(tailleFloat, 2) ) + (0.23 * this.getAge()) - (10.83 * this.getSexe()) - 5.4) ;
        }
        return img;
    }

    /**
     * retourne le message correspondant à l'img
     * @return message "normal", "trop faible", "trop élevé"
     */
    public String getMessage() {
        img = getImg();
        if(this.getSexe() == 0){
            if(img < 15){
                this.message = "trop faible";
            } else if (img >= 15 && img <= 30) {
                this.message = "normal";
            } else {
                this.message = "trop élevé";
            }
        } else if(this.getSexe() == 1){
            if(img < 10){
                this.message = "trop faible";
            } else if (img >= 10 && img <= 25) {
                this.message = "normal";
            } else {
                this.message = "trop élevé";
            }
        }
        return message;
    }

    /**
     * Convertit le contenu du profil en JSONObject
     * @return profil au format JSONObject
     */
    public JSONObject convertToJSONObject(){
        JSONObject jsonProfil = new JSONObject();
       try {
           jsonProfil.put("datemesure", MesOutils.convertDateToString(dateMesure));
           jsonProfil.put("poids", poids);
           jsonProfil.put("taille", taille);
           jsonProfil.put("age", age);
           jsonProfil.put("sexe", sexe);
       }catch (JSONException e){
           Log.d("erreur", "************* classe Profil, méthode convertToJSONObject, erreur de conversion");
       }
       return jsonProfil;
    }

}
