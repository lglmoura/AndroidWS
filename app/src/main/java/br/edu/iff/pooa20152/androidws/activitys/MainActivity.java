package br.edu.iff.pooa20152.androidws.activitys;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import br.edu.iff.pooa20152.androidws.R;
import br.edu.iff.pooa20152.androidws.helper.RestFullHelper;

public class MainActivity extends AppCompatActivity {

    private EditText etCodigo;
    private EditText etNome;
    private EditText etEndereco;
    private EditText etNumero;
    private Button btConsultar;
    private Button btSalvar;
    private Button btLimpar;
    private Button btDeletar;
    private String durl = "http://doml-pooa20152.herokuapp.com";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        etCodigo = (EditText) findViewById(R.id.etCodigo);
        etNome = (EditText) findViewById(R.id.etNome);
        etEndereco = (EditText) findViewById(R.id.etEndereco);

        btConsultar = (Button) findViewById(R.id.btConsutar);
        btConsultar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String filtro = etCodigo.getText().toString();
                if (!filtro.equalsIgnoreCase("")) {

                    gettInformationtoAPI();

                }
            }

        });

        btSalvar = (Button) findViewById(R.id.btSalvar);

        btSalvar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(etCodigo.getText().toString()))

                    postInformationtoAPI();

                else
                    putInformationtoAPI();
            }

        });

        btLimpar = (Button) findViewById(R.id.btLimpar);
        btLimpar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                etCodigo.setText("");
                etNome.setText("");
                etEndereco.setText("");

            }
        });

        btDeletar = (Button) findViewById(R.id.btDeletar);
        btDeletar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                deletarInformationtoAPI();

            }
        });
    }

    private void deletarInformationtoAPI() {

        Log.i("Deletar====", "Deletar ORDER");

        JSONObject params = null;

        EmpregadorTask bgtDel = new EmpregadorTask(
                durl + "/empregadors/"
                        + etCodigo.getText().toString() + ".json",
                RestFullHelper.DELETAR, params);
        bgtDel.execute();

    }

    private void gettInformationtoAPI() {

       // Log.i("Http", "GETTING ORDER: "+durl + "/empregadors/"
       //         + etCodigo.getText().toString() + ".json");


        JSONObject params = null;

        EmpregadorTask bgtGet = new EmpregadorTask(
                durl + "/empregadors/"
                        + etCodigo.getText().toString() + ".json",
                RestFullHelper.GET, params);

        bgtGet.execute();

    }

    private void postInformationtoAPI() {

        Log.i("Post====", "POSTING ORDER");

        JSONObject params = new JSONObject();

        try {
            params.put("nome", etNome.getText().toString());
            params.put("endereco", etEndereco.getText().toString());
            params.put("numero", "adadf");

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        EmpregadorTask bgtPost = new EmpregadorTask(
                durl + "/empregadors", RestFullHelper.POST, params);
        bgtPost.execute();

    }

    private void putInformationtoAPI() {

        Log.i("Put====", "PUT ORDER");

        JSONObject params = new JSONObject();

        try {
            params.put("nome", etNome.getText().toString());
            params.put("endereco", etEndereco.getText().toString());
            params.put("numero", "adadf");

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        EmpregadorTask bgtPut = new EmpregadorTask(
                durl + "/empregadors/"
                        + etCodigo.getText().toString() + ".json",
                RestFullHelper.PUT, params);
        bgtPut.execute();

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

    public class EmpregadorTask extends AsyncTask<String, String, JSONObject> {

        String url = null;
        String method = null;
        JSONObject params1 = null;

        ProgressDialog dialog;

        public EmpregadorTask(String url, String method, JSONObject params1) {
            this.url = url;
            this.method = method;
            this.params1 = params1;

        }

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(MainActivity.this);
            dialog.show();
        }

        @Override
        protected void onPostExecute(JSONObject empregador) {

            if (empregador != null) {

                try {
                    etCodigo.setText(empregador.getString("id"));
                    etNome.setText(empregador.getString("nome"));
                    etEndereco.setText(empregador.getString("endereco"));
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }

            dialog.dismiss();
        }

        @Override
        protected JSONObject doInBackground(String... params) {
            RestFullHelper http = new RestFullHelper();

            return http.getJSON(url, method, params1);

        }
    }
}
