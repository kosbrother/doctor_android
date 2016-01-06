package kosbrother.com.doctorguide.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;

import kosbrother.com.doctorguide.entity.Hospital;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by steven on 1/6/16.
 */
public class DoctorApi {
    final static String HOST = "http://130.211.247.159";
    private final static ObjectMapper mapper = new ObjectMapper();

    public static ArrayList<Hospital> readHospitalJson(String jsonString){
        ArrayList<Hospital> hospitalList = new ArrayList<Hospital>();
        try {
            hospitalList = mapper.readValue(jsonString, new TypeReference<ArrayList<Hospital>>() {});
            return hospitalList;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return hospitalList;
    }

    public static String runHttpGet(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }
}
