package kosbrother.com.doctorguide.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public class Hospital implements Serializable {
    public int id;
    public String name;
    public String address;
    public String grade;
    public String phone;
    public String assess;
    public HashMap<String,Object> cHours;
    public List<String> ss;
    public List<Division> divisions;
    public float latitude;
    public float longitude;
    public int comment_num;
    public int recommend_num;
    public float avg;
}
