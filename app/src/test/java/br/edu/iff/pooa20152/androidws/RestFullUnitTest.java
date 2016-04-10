package br.edu.iff.pooa20152.androidws;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class RestFullUnitTest {

    RestFullHelper http;
    JSONObject json;
    String id;

    @Before
    public void setUp() throws Exception {
        http = new RestFullHelper();
        json = http.doPost("http://localhost:3000/logins.json",getParams());
        id = Integer.toString(json.getInt("id")).trim();

    }
    @After
    public void tearDown() throws Exception {


    }

    @Test
    public void doget() throws Exception {

        json = http.doGet("http://localhost:3000/logins/"+id+".json");

        assertEquals("654321", json.getString("senha"));
        http.doDelete("http://localhost:3000/logins/"+id+".json");
    }

    @Test
    public void doDelete() throws Exception {



        json = http.doDelete("http://localhost:3000/logins/"+id+".json");

        assertEquals(null, json);
    }

    @Test
    public void doPost() throws Exception{


        assertEquals("654321", json.getString("senha"));
        http.doDelete("http://localhost:3000/logins/"+id+".json");

    }
    @Test
    public void doPut() throws Exception{

        JSONObject oPut = new JSONObject();
        oPut.put("nome","Gustavo:"+id);
        oPut.put("senha","123456:"+id);
        oPut.put("username","Gustavo:"+id);

        json = http.doPut("http://localhost:3000/logins/"+id+".json",oPut);

        assertEquals("Gustavo:"+id, json.getString("nome"));
        http.doDelete("http://localhost:3000/logins/"+id+".json");

    }

    private JSONObject getParams(){
        JSONObject params = new JSONObject();
        try {

            params.put("nome", "luiz");
            params.put("senha", "654321");
            params.put("username", "luizlogin");

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return params;
    }
}