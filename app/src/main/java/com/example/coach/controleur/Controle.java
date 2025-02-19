package com.example.coach.controleur;

import android.content.Context;
import android.util.Log;

import com.example.coach.modele.AccesDistant;
import com.example.coach.modele.Profil;
import com.example.coach.outils.Serializer;
import com.example.coach.vue.CalculActivity;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

/**
 * Classe singleton Controle : répond aux attentes de l'activity
 */
public final class Controle {

    private static Controle instance;
    private ArrayList<Profil> lesProfils = new ArrayList<Profil>();
    //private static Profil profil;
    private static Serializer serializer;
    private static final String nomFic = "saveprofil";
    private static AccesDistant accesDistant;
    //private AccesLocal accesLocal;
    //private static Context context;



    private Controle() {
        super();
    }

    /**
     * Création d'une instance unique de la classe
     * @return l'instance unique
     */
    public final static Controle getInstance(){
        if(instance == null){
           instance = new Controle();
           Log.d("Controle getInstance", "************ new Controle : in if");
           accesDistant = AccesDistant.getInstance();
           accesDistant.envoi("tous", new JSONObject());
            Log.d("Controle getInstance", "************ new Controle : out if");
        }
        return instance;
    }

    /**
     * Création du profil par rapport aux informations saisies
     * @param poids
     * @param taille en cm
     * @param age
     * @param sexe 1 pour homme, 0 pour femme
     */
    public void creerProfil(Integer poids, Integer taille, Integer age, Integer sexe){
        Profil unProfil = new Profil(poids, taille, age, sexe, new Date());
        lesProfils.add(unProfil);
        accesDistant.envoi("enreg", unProfil.convertToJSONObject());
    }

    /**
     * getter sur le résultat du calcul de l'IMG du dernier profil
     * @return img du profil
     */
    public float getImg(){
        if(lesProfils.size() == 0){
            return 0;
        }else {
            return lesProfils.get(lesProfils.size()-1).getImg();
        }
    }

    /**
     * getter sur le message correspondant à l'img du profil
     * @return message du profil
     */
    public String getMessage(){
        if(lesProfils.size() == 0){
            return "";
        }else {
            return lesProfils.get(lesProfils.size()-1).getMessage();
        }

    }

    //private static void recupSerialize(Context context){
    //    profil = (Profil)serializer.deSerialize(nomFic, context);
    //}

    ///**
    //* Valorisation du profil
    // * @param profil
    // */
    //public void setProfil(Profil profil){
    //    Controle.profil = profil;
    //   ((CalculActivity)context).recupProfil();
    //}

    /**
     * getter sur lesPRofils
     * @return lesProfils
     */
    public ArrayList<Profil> getLesProfils() {
        return lesProfils;
    }

    /**
     * setter sur lesProfils
     * @param lesProfils
     */
    public void setLesProfils(ArrayList<Profil> lesProfils) {
        this.lesProfils = lesProfils;
    }

    /**
     * Suppression d'un profil
     * @param profil
     */
    public void delProfil(Profil profil){
        accesDistant.envoi("suppr", profil.convertToJSONObject());
        lesProfils.remove(profil);
    }
}

