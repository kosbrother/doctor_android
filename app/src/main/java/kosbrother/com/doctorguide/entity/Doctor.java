package kosbrother.com.doctorguide.entity;

import java.util.List;

/**
 * Created by steven on 1/6/16.
 */
public class Doctor {
    public int id;
    public String name;
    public String address;
    public String hospital;
    public int hospital_id;
    public boolean isCollected;
    public String exp;
    public String spe;
    public List<Division> divisions;
    public float latitude;
    public float longitude;
    public int comment_num;
    public int recommend_num;
    public float avg;

    public float avg_friendly;
    public float avg_speciality;
}
