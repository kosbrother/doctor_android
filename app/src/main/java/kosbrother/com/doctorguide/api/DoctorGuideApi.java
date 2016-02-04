package kosbrother.com.doctorguide.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import kosbrother.com.doctorguide.entity.Comment;
import kosbrother.com.doctorguide.entity.Division;
import kosbrother.com.doctorguide.entity.Doctor;
import kosbrother.com.doctorguide.entity.Hospital;
import kosbrother.com.doctorguide.entity.User;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by steven on 1/6/16.
 */
public class DoctorGuideApi {
    final static String HOST = "http://130.211.247.159";
    private final static ObjectMapper mapper = new ObjectMapper();

    public static ArrayList<Comment> getUserComments(String email){
        ArrayList<Comment> comments = new ArrayList<Comment>();
        try {
            String message = runHttpGet(HOST + "/api/v1/comments/user_comments.json?user=" + email);
            comments = mapper.readValue(message, new TypeReference<ArrayList<Comment>>() {});
            return comments;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return comments;
    }

    public static int creatUser(User user){
        OkHttpClient client = new OkHttpClient();
        FormBody.Builder formBody = new FormBody.Builder()
                .add("name", user.name)
                .add("email", user.email);
        if(user.pic_url != null)
            formBody.add("pic_url", user.pic_url);

        Request request = new Request.Builder()
                .url(HOST + "/api/v1/users.json")
                .post(formBody.build())
                .build();

        Response response = null;
        try {
            response = client.newCall(request).execute();
            response.body().string();
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
        } catch (IOException e) {
            e.printStackTrace();
            return response.code();
        }
        return 200;
    }

    public static Comment getComment(int comment_id){
        Comment comment = new Comment();
        try {
            String message = runHttpGet(HOST + "/api/v1/comments/"+ comment_id +".json");
            comment = mapper.readValue(message, new TypeReference<Comment>() {});
            return comment;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return comment;
    }

    public static ArrayList<Comment> getHospitalComments(int hospital_id){
        ArrayList<Comment> comments = new ArrayList<Comment>();
        try {
            String message = runHttpGet(HOST + "/api/v1/hospitals/"+ hospital_id +"/comments.json");
            comments = mapper.readValue(message, new TypeReference<ArrayList<Comment>>() {});
            return comments;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return comments;
    }

    public static ArrayList<Comment> getDivisionComments(int division_id,int hospital_id){
        ArrayList<Comment> comments = new ArrayList<Comment>();
        try {
            String message = runHttpGet(HOST + "/api/v1/divisions/"+ division_id +"/comments.json?hospital_id="+hospital_id);
            comments = mapper.readValue(message, new TypeReference<ArrayList<Comment>>() {});
            return comments;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return comments;
    }

    public static ArrayList<Comment> getDoctorComments(int doctor_id){
        ArrayList<Comment> comments = new ArrayList<Comment>();
        try {
            String message = runHttpGet(HOST + "/api/v1/doctors/"+ doctor_id +"/comments.json");
            comments = mapper.readValue(message, new TypeReference<ArrayList<Comment>>() {});
            return comments;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return comments;
    }

    public static Doctor getDoctorScore(int doctorId) {
        Doctor doctor = null;
        try {
            String message = runHttpGet(HOST + "/api/v1/doctors/" + doctorId + "/score.json");
            doctor = mapper.readValue(message, new TypeReference<Doctor>() {});
            return doctor;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return doctor;
    }

    public static Division getDivisionScore(int divisionId, int hospitalId) {
        Division division = null;
        try {
            String message = runHttpGet(HOST + "/api/v1/divisions/" + divisionId + "/score.json?hospital_id=" + hospitalId);
            division = mapper.readValue(message, new TypeReference<Division>() {});
            return division;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return division;
    }

    public static Doctor getDoctorInfo(int doctorId){
        Doctor doctor = null;
        try {
            String message = runHttpGet(HOST + "/api/v1/doctors/" + doctorId + ".json");
            doctor = mapper.readValue(message, new TypeReference<Doctor>() {});
            return doctor;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return doctor;
    }

    public static Hospital getHospitalInfo(int hospitalId){
        Hospital hospital = null;
        try {
            String message = runHttpGet(HOST + "/api/v1/hospitals/" + hospitalId + ".json");
            hospital = mapper.readValue(message, new TypeReference<Hospital>() {
            });
            return hospital;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return hospital;
    }

    public static ArrayList<Hospital> getHospitalsByAreaAndCategory(int areaId, int categoryId, int page, double latitude, double longitude, String order){
        ArrayList<Hospital> hospitals = new ArrayList<Hospital>();
        try {
            String message = runHttpGet(HOST + "/api/v1/hospitals/by_area_category.json?area_id="+ areaId +"&category_id=" + categoryId + "&page="+page + "&latitude="+latitude+"&longitude="+longitude+"&order="+order);
            hospitals = readHospitalJson(message);
            return hospitals;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return hospitals;
    }

    public static ArrayList<Doctor> getDoctorsByAreaAndCategory(int areaId, int categoryId, int page, double latitude, double longitude, String order){
        ArrayList<Doctor> doctors = new ArrayList<Doctor>();
        try {
            String message = runHttpGet(HOST + "/api/v1/doctors/by_area_category.json?area_id="+ areaId +"&category_id=" + categoryId + "&page="+page + "&latitude="+latitude+"&longitude="+longitude+"&order="+order);
            doctors = readDoctorJson(message);
            return doctors;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return doctors;
    }

    public static ArrayList<Doctor> getDoctorsByHospitalAndDivision(int hospitalID, int divisionId){
        ArrayList<Doctor> doctors = new ArrayList<Doctor>();
        try {
            String message = runHttpGet(HOST + "/api/v1/doctors/by_hospital_division.json?hospital_id="+ hospitalID +"&division_id=" + divisionId);
            doctors = readDoctorJson(message);
            return doctors;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return doctors;
    }

    public static ArrayList<Division> getDivisionByHospitalAndCategory(int hospitalId, int categoryId){
        ArrayList<Division> divisions = new ArrayList<Division>();
        try {
            String message = runHttpGet(HOST + "/api/v1/divisions/by_hospital_category.json?hospital_id="+ hospitalId +"&category_id=" + categoryId);
            divisions = readDivisionJson(message);
            return divisions;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return divisions;
    }

    public static  ArrayList<Division> getDivisionsWithDoctorsByHospital(int hospitalId){
        ArrayList<Division> divisions = new ArrayList<Division>();
        try {
            String message = runHttpGet(HOST + "/api/v1/hospitals/"+ hospitalId+"/divisions_with_doctors.json");
            divisions = readDivisionJson(message);
            return divisions;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return divisions;
    }

    public static ArrayList<Division> getDivisionByHospital(int hospitalId){
        ArrayList<Division> divisions = new ArrayList<Division>();
        try {
            String message = runHttpGet(HOST + "/api/v1/divisions/by_hospital.json?hospital_id="+ hospitalId);
            divisions = readDivisionJson(message);
            return divisions;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return divisions;
    }

    private static ArrayList<Division> readDivisionJson(String jsonString){
        ArrayList<Division> divisionsList = new ArrayList<Division>();
        try {
            divisionsList = mapper.readValue(jsonString, new TypeReference<ArrayList<Division>>() {
            });
            return divisionsList;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return divisionsList;
    }


    private static ArrayList<Doctor> readDoctorJson(String jsonString){
        ArrayList<Doctor> doctorsList = new ArrayList<Doctor>();
        try {
            doctorsList = mapper.readValue(jsonString, new TypeReference<ArrayList<Doctor>>() {});
            return doctorsList;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return doctorsList;
    }

    private static ArrayList<Hospital> readHospitalJson(String jsonString){
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

    public static Boolean postComment(HashMap<String, String> submitParams) {
        OkHttpClient client = new OkHttpClient();
        FormBody.Builder formBody = new FormBody.Builder();
        for(String key : submitParams.keySet()){
            formBody.add(key,submitParams.get(key));
        }

        Request request = new Request.Builder()
                .url(HOST + "/api/v1/comments.json")
                .post(formBody.build())
                .build();

        Response response = null;
        try {
            response = client.newCall(request).execute();
            response.body().string();
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static Boolean postFeedback(String subject, String content){
        OkHttpClient client = new OkHttpClient();
        FormBody.Builder formBody = new FormBody.Builder()
                .add("subject", subject)
                .add("content", content);
        Request request = new Request.Builder()
                .url(HOST + "/api/v1/feedbacks.json")
                .post(formBody.build())
                .build();

        Response response = null;
        try {
            response = client.newCall(request).execute();
            response.body().string();
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
