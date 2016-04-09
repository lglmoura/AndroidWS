package br.edu.iff.pooa20152.androidws;

import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class RestFullUnitTest {
    @Test
    public void doget() throws Exception {
        RestFullHelper http = new RestFullHelper();

        JSONObject json = http.doGet("http://localhost:3000/logins/1.json");

        assertEquals("Luiz", json.getString("nome"));
    }
}