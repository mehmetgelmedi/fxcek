package sample.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MEG on 12.07.2017.
 */
public class JsonAyristir {
    private static List<String> sehirList;

    public JsonAyristir(){
        sehirList=new ArrayList<String>();
    }

    public List<String> sehirleriGetir(String jsonDosyasi) {

        String jsonData = "";
        BufferedReader br = null;
        try {
            String line;
            br = new BufferedReader(new FileReader(jsonDosyasi));
            while ((line = br.readLine()) != null) {
                jsonData += line + "\n";
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)
                    br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        try {
            JSONObject obj = new JSONObject(jsonData);
            JSONArray array = obj.getJSONArray("iller");
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                String il = object.getString("il");
                //System.out.println(il);
                sehirList.add(il);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return sehirList;
    }
}
