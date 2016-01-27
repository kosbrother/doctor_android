package kosbrother.com.doctorguide.entity;

import java.util.Date;

/**
 * Created by steven on 1/27/16.
 */
public class Comment {
    public int id;
    public int dr_friendly;
    public int dr_speciality;
    public int div_equipment;
    public int div_environment;
    public int div_speciality;
    public int div_friendly;

    public int doctor_id;
    public int hospital_id;
    public int division_id;
    public int user_id;

    public String div_comment;
    public String dr_comment;

    public String user_name;
    public String hospital_name;
    public String division_name;
    public String doctor_name;

    public boolean is_recommend;

    public Date updated_at;
}
