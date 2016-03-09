package kosbrother.com.doctorguide.entity;

import java.io.Serializable;
import java.util.List;

public class Division implements Serializable{
    public int id;
    public String name;
    public int hospital_id;
    public String hospital_name;
    public String hospital_grade;
    public int category_id;

    public int comment_num;
    public int recommend_num;
    public float avg;

    public float avg_equipment;
    public float avg_environment;
    public float avg_friendly;
    public float avg_speciality;

    public float dr_avg_score;
    public float dr_avg_friendly;
    public float dr_avg_speciality;

    public List<Doctor> doctors;
}
