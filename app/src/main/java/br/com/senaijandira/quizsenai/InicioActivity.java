package br.com.senaijandira.quizsenai;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

/**
 * Created by 17259195 on 06/08/2018.
 */

public class InicioActivity extends Activity {

    MediaPlayer mediaPlayer;
    ImageView imgQuiz;
    Animation shakeAnim;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Definindo o conteudo na tela passando o arquivo xml
        setContentView(R.layout.activity_inicio);



            //  Inicando a musica do jogo
        mediaPlayer = MediaPlayer.create(this, R.raw.game_music);
            // Para musica ficar em looping
        mediaPlayer.setLooping(true);
            //  Inicia a musica
         mediaPlayer.start();

         imgQuiz = findViewById(R.id.imgQuiz);

         Animation fadein = AnimationUtils.loadAnimation(this, R.anim.fadein);
         imgQuiz.startAnimation(fadein);

            // Carregando a animação "Shake"
         shakeAnim = AnimationUtils.loadAnimation(this, R.anim.shake);

        // Evento de clique na imagem
        imgQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            // Iniciar  a animação ao clicar na imagem
                imgQuiz.startAnimation(shakeAnim);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mediaPlayer.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(!mediaPlayer.isPlaying()){
            mediaPlayer.pause();
        }
    }

    public void inicarJogo(View v){
            // Parar a musica quando aberta outra tela
        mediaPlayer.stop();
            //  Abrir uma nova tela
        Intent intent = new Intent (this, MainActivity.class);
        startActivity(intent);
        finish();

        //Toast.makeText(this, "Opa meu consagrado",Toast.LENGTH_SHORT ).show();
    }

}
















