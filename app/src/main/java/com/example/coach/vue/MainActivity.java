package com.example.coach.vue;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coach.R;
import com.example.coach.controleur.Controle;

/**
 * Classe d'affichage (activity) MainActivity
 * Permet le calcul d'un IMG
 */
public class MainActivity extends AppCompatActivity {

    private EditText txtPoids;
    private EditText txtTaille;
    private EditText txtAge;
    private RadioButton rdHomme;
    private RadioButton rdFemme;
    private TextView lblIMG;
    private ImageView imgSmiley;
    private Button btnCalc;
    private Controle controle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.init();
    }

    /**
     * Initialisations à l'ouverture :
     * - récupération des objets graphiques
     * - création du contrôleur
     * - demande d'écoute (évenements sur objets graphiques)
     */
    public void init(){
        txtPoids = (EditText)findViewById(R.id.txtPoids);
        txtTaille = (EditText)findViewById(R.id.txtTaille);
        txtAge = (EditText)findViewById(R.id.txtAge);
        rdHomme = (RadioButton)findViewById(R.id.rdHomme);
        rdFemme = (RadioButton)findViewById(R.id.rdFemme);
        lblIMG= (TextView)findViewById(R.id.lblIMG);
        imgSmiley = (ImageView)findViewById(R.id.imgSmiley);
        btnCalc = (Button)findViewById(R.id.btnCalc);
        controle = Controle.getInstance(this);
        ecouteCalcul();
        //recupProfil();
    }

    /**
     * Ecoute l'événement clic sur le bouton btnCalc
     */
    private void ecouteCalcul(){
        btnCalc.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Integer poids = 0;
                Integer taille = 0;
                Integer age = 0;
                Integer sexe = 0;

                try{
                    poids = Integer.parseInt(txtPoids.getText().toString());
                    taille = Integer.parseInt(txtTaille.getText().toString());
                    age = Integer.parseInt(txtAge.getText().toString());
                }catch(Exception e){}

                if(rdHomme.isChecked()){
                    sexe = 1;
                }

                if(poids == 0 || taille == 0 || age == 0){
                    Toast.makeText(MainActivity.this, "Veuillez saisir tous les champs", Toast.LENGTH_SHORT).show();
                }else{
                    afficheResult(poids, taille, age, sexe);
                }
            }
        });
    }

    /**
     * Affiche l'image et le message correspondant au calcul de l'img
     * @param poids
     * @param taille
     * @param age
     * @param sexe
     */
    public void afficheResult(Integer poids, Integer taille, Integer age, Integer sexe) {
        controle.creerProfil(poids, taille, age, sexe);
        String message = controle.getMessage();
        float img = controle.getImg();

        if (message == "trop faible") {
            imgSmiley.setImageResource(R.drawable.maigre);
        } else if (message == "normal") {
            imgSmiley.setImageResource(R.drawable.normal);
        } else if (message == "trop élevé") {
            imgSmiley.setImageResource(R.drawable.graisse);
        }

        String imgToSting = String.format("%.01f", img);

        if (message == "normal") {
            lblIMG.setTextColor(Color.GREEN);
        } else {
            lblIMG.setTextColor(Color.RED);
        }

        lblIMG.setText(imgToSting + " :IMG " + message);
    }

    public void recupProfil(){
        if(controle.getTaille() != null){
            txtPoids.setText(controle.getPoids().toString());
            txtTaille.setText(controle.getTaille().toString());
            txtAge.setText(controle.getAge().toString());
            if(controle.getSexe() == 0){
                rdFemme.setChecked(true);
            }else{
                rdHomme.setChecked(true);
            }
        }
        btnCalc.performClick();
    }

}