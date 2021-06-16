package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;

public class ProfilNeighbourActivity extends AppCompatActivity {

    Neighbour mNeighbour;

    private ImageView mImageProfil;
    private ImageButton mReturnButton;
    private TextView mNameProfil;
    private FloatingActionButton mFavorisButton;

    private TextView mNameCard;
    private TextView mAddressCard;
    private TextView mPhoneCard;
    private TextView mWebsiteCard;
    private TextView mTextDescription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_neighbour);

        /**
         * pour recuperer et creer l'activity en passant par le RecyclerView suite au clic sur un neighbour
         */
        mNeighbour = (Neighbour) getIntent().getSerializableExtra("neighbour");

        mImageProfil = (ImageView) findViewById(R.id.activity_profil_neighbour_toolbar_img);
        mReturnButton = (ImageButton) findViewById(R.id.activity_profil_neighbour_toolbar_returnButton);
        mNameProfil = (TextView) findViewById(R.id.activity_profil_neighbour_toolbar_name_txt);
        mFavorisButton = (FloatingActionButton) findViewById(R.id.activity_profil_neighbour_favoris_button);
        mNameCard = (TextView) findViewById(R.id.info_card_name_txt);
        mAddressCard = (TextView) findViewById(R.id.info_card_address_txt);
        mPhoneCard = (TextView) findViewById(R.id.info_card_phone_txt);
        mWebsiteCard = (TextView) findViewById(R.id.info_card_website_txt);
        mTextDescription = (TextView) findViewById(R.id.info_description_text_txt);


        mNameProfil.setText(mNeighbour.getName());
        mNameCard.setText(mNeighbour.getName());
        mAddressCard.setText(mNeighbour.getAddress());
        mPhoneCard.setText(mNeighbour.getPhoneNumber());
        mWebsiteCard.setText(" www.facebook.fr/" + mNeighbour.getName().toLowerCase());
        mTextDescription.setText(mNeighbour.getAboutMe());

        // Glide : pour recuperer l'image (Avatar de type url) du neighbour
        Glide.with(this).load(mNeighbour.getAvatarUrl()).into(mImageProfil);

        // action du bouton retour : fini et tue cette activity, donc retour à l'ecran précedent !
        mReturnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getApplicationContext();
                Toast.makeText(context, "retour à la liste des voisins", Toast.LENGTH_SHORT).show();
                finish();
            }
        });


        // change button style if favoris
        if (mNeighbour.isFavoris() == false) {
            mFavorisButton.setImageResource(R.drawable.ic_star_border_black_24dp);
        } else mFavorisButton.setImageResource(R.drawable.ic_star_yellow_24dp);


        // action du bouton favoris
        mFavorisButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mNeighbour.isFavoris() == false) {
                    mFavorisButton.setImageResource(R.drawable.ic_star_yellow_24dp);

                    DI.getNeighbourApiService().addFavorisNeighbour(mNeighbour);
                    mNeighbour.setFavoris(true);
                    Context context = getApplicationContext();
                    Toast.makeText(context, "Ajouté aux favoris", Toast.LENGTH_SHORT).show();

                } else if (mNeighbour.isFavoris() == true) {
                    mFavorisButton.setImageResource(R.drawable.ic_star_border_black_24dp);

                    DI.getNeighbourApiService().deleteFavorisNeighbour(mNeighbour);
                    mNeighbour.setFavoris(false);
                    Context context = getApplicationContext();
                    Toast.makeText(context, "Supprimé des favoris", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}