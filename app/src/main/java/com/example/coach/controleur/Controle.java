package com.example.coach.controleur;

import android.content.Context;

import com.example.coach.modele.Profil;
import com.example.coach.outils.Serializer;

/**
 * Classe singleton Controle : répond aux attentes de l'activity
 */
public final class Controle {

    private static Controle instance = null;
    private static Profil profil;
    private static Serializer serializer;
    private static final String nomFic = "saveprofil";

    private Controle(Context context) {
        super();
        recupSerialize(context);
    }

    /**
     * Création d'une instance unique de la classe
     * @return l'instance unique
     */
    public final static Controle getInstance(Context context){
        if(instance == null){
           return instance = new Controle(context);
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
    public void creerProfil(Integer poids, Integer taille, Integer age, Integer sexe, Context context){

        this.profil = new Profil(poids, taille, age, sexe);
        serializer.serialize(nomFic, profil, context );
    }

    /**
     * getter sur le résultat du calcul de l'IMG pour le profil
     * @return img du profil
     */
    public float getImg(){
        if(profil == null){
           return 0;
        }
        return profil.getImg();
    }

    /**
     * getter sur le message correspondant à l'img du profil
     * @return message du profil
     */
    public String getMessage(){
        if(profil == null){
           return "";
        }
        return profil.getMessage();
    }

    public Integer getPoids(){
        if(profil == null){
            return null;
        }else{
            return profil.getPoids();
        }
    }

    public Integer getTaille(){
        if(profil == null){
            return null;
        }else{
            return profil.getTaille();
        }

    }

    public Integer getAge(){
        if(profil == null){
            return null;
        }else{
            return profil.getAge();
        }
    }

    public Integer getSexe(){
        if(profil == null){
            return null;
        }else{
            return profil.getSexe();
        }
    }

    private static void recupSerialize(Context context){
        profil = (Profil)serializer.deSerialize(nomFic, context);
    }


}

