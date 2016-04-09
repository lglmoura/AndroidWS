package br.edu.iff.pooa20152.androidws;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btLogin = (Button) findViewById(R.id.btLogin);
        btLogin.setOnClickListener(onClickLogin());
    }

    private View.OnClickListener onClickLogin() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tLogin = (TextView)
                        findViewById(R.id.tLogin);
                TextView tSenha = (TextView)
                        findViewById(R.id.tSenha);
                String login = tLogin.getText().toString();
                String senha = tSenha.getText().toString();

                if ("luiz".equals(login) && "123456".equals(senha)) {
                    // Navega para a próxima tela
                    /*

                    Intent intent = new Intent(getContext(), BemVindoActivity.class);
                    Bundle params = new Bundle();
                    params.putString("nome", "Luiz Gustavo");
                    params.putString("senha", senha);
                    intent.putExtras(params);
                    startActivity(intent);
                    */
                } else {
                    alert("Login e senha incorretos.");
                }
            }
        };
    }

    private Context getContext() {


        return this;
    }


    private void alert(String s) {

        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }
}
