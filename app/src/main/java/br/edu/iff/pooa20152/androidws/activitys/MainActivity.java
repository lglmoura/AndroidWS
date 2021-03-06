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
    private EditText etCnpj;

    private final String TAG = "MAIN";
    private Button btConsultar;
    private Button btSalvar;
    private Button btLimpar;
    private Button btDeletar;
    private String durl;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        etCodigo = (EditText) findViewById(R.id.etCodigo);
        etNome = (EditText) findViewById(R.id.etNome);
        etEndereco = (EditText) findViewById(R.id.etEndereco);
        etNumero = (EditText) findViewById(R.id.etNumero);
        etCnpj = (EditText) findViewById(R.id.etCnpj);
        durl = getString(R.string.URL);

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

                limpar();

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

        Log.i(TAG, "Deletar ORDER");

        JSONObject params = null;

        FabricanteTask bgtDel = new FabricanteTask(
                durl + "/fabricantes/"
                        + etCodigo.getText().toString() + ".json",
                RestFullHelper.DELETAR, params);
        bgtDel.execute();
        limpar();
    }

    private void limpar(){

        System.out.println(getString(R.string.sbtDeletar));

        etCodigo.setText("");
        etNome.setText("");
        etEndereco.setText("");
        etNumero.setText("");
        etCnpj.setText("");

    }

    private void gettInformationtoAPI() {


        JSONObject params = null;

        FabricanteTask bgtGet = new FabricanteTask(
                durl + "/fabricantes/"
                        + etCodigo.getText().toString() + ".json",
                RestFullHelper.GET, params);

        bgtGet.execute();

    }

    private void postInformationtoAPI() {

        Log.d(TAG, "POSTING ORDER");

        JSONObject params = new JSONObject();

        try {
            params.put("nome", etNome.getText().toString());
            params.put("endereco", etEndereco.getText().toString());
            params.put("numero", etNumero.getText().toString());
            params.put("cnpj", etCnpj.getText().toString());

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        FabricanteTask bgtPost = new FabricanteTask(
                durl + "/fabricantes.json", RestFullHelper.POST, params);
        bgtPost.execute();

    }

    private void putInformationtoAPI() {

        Log.i(TAG, "PUT ORDER");

        JSONObject params = new JSONObject();

        try {
            params.put("nome", etNome.getText().toString());
            params.put("endereco", etEndereco.getText().toString());
            params.put("numero", etNumero.getText().toString());
            params.put("cnpj",etCnpj.getText().toString());

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        FabricanteTask bgtPut = new FabricanteTask(
                durl + "/fabricantes/"
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

    public class FabricanteTask extends AsyncTask<String, String, JSONObject> {

        String url = null;
        String method = null;
        JSONObject params1 = null;

        ProgressDialog dialog;

        public FabricanteTask(String url, String method, JSONObject params1) {
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
                    etNumero.setText(empregador.getString("numero"));
                    etCnpj.setText(empregador.getString("cnpj"));
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
