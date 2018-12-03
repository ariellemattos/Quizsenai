package br.com.senaijandira.quizsenai;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

    LinearLayout layout;
    TextView txtPergunta, txtRelogio;
    Button btnResposta1, btnResposta2;
    MediaPlayer mediaPlayer;

    Integer QtdAcertos, QtdErros = 0;

    int indexPergunta;

    String[] perguntas = {
            "Onde se passa a série Breaking Bad?",
            "Qual e o personagem principal da série?",
            "Quantas temporadas tem a série?",
            "Qual era o nome da lanchonete de Gustavo Fring?"
    };

    String[][] respostas = {
            {"Albuquerque", "Los Angeles"},
            {"Saul Goodman", "Walter White"},
            {"5", "3"},
            {"Los Pollos hermanos", "Mc Donalds"},
    };

    private void iniciarJogo() {

        QtdErros = 0;
        QtdAcertos = 0;

        indexPergunta = 0;
        txtPergunta.setText(perguntas[indexPergunta]);

        btnResposta1.setText(respostas[indexPergunta][0]);
        btnResposta1.setTag(0);

        btnResposta2.setText(respostas[indexPergunta][1]);
        btnResposta2.setTag(1);

        btnResposta1.setOnClickListener(clickBtnResposta);
        btnResposta2.setOnClickListener(clickBtnResposta);

        timer.start();

        mediaPlayer = mediaPlayer.create(this, R.raw.countdown);
        mediaPlayer.start();
    }



    CountDownTimer timer = new CountDownTimer(30000,1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            txtRelogio.setText(""+ millisUntilFinished/1000);
        }

        @Override
        public void onFinish() {
            alert("Tempo acabou", "você demorou para responder");
        }


    };

    int[] gabarito = { 0, 1, 0, 0 };




    View.OnClickListener clickBtnResposta = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            int respostaUsuario = (int)v.getTag();

            if(respostaUsuario == gabarito[indexPergunta]){
                QtdAcertos++;
                alert("Parabéns", "Resposta correta!");
            }else{
               QtdErros++;
                alert("Sorry", "Resposta errada!");
            }

            timer.cancel();

            mediaPlayer.stop();
        }
    };


    public void alert(String titulo, String msg){

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(titulo);
        alert.setMessage("Acertos:" + QtdAcertos.toString() + "Erros:" + QtdErros.toString());

//        Não pode fechar o alert se clicar fora da caixa
        alert.setCancelable(false);

        alert.setPositiveButton("Proximo", new Dialog.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                proximaPergunta();
            }

        });


        alert.create().show();
    }

    public void gameOver(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Game Over");

        alert.setMessage("QtdAcertos: 0 \nQtdErros: 0");

        alert.setNegativeButton("sair", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                finish();
            }
        });

        alert.setPositiveButton("reiniciar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                iniciarJogo();
            }
        });

        alert.create().show();
    }

    public void proximaPergunta(){

        if(indexPergunta == perguntas.length-1) {
            //jogo acabou
            gameOver();
            return;
        }

        indexPergunta++;
        txtPergunta.setText(perguntas[indexPergunta]);

        btnResposta1.setText(respostas[indexPergunta][0]);
        btnResposta2.setText(respostas[indexPergunta][1]);

        timer.start();
        mediaPlayer = mediaPlayer.create(this, R.raw.countdown);
        mediaPlayer.start();
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        txtPergunta = findViewById(R.id.txtPergunta);

        btnResposta1 = findViewById(R.id.btn1);
        btnResposta2 = findViewById(R.id.btn2);

        txtRelogio = findViewById(R.id.txtRelogio);

        iniciarJogo();

    }
}
