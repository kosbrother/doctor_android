package kosbrother.com.doctorguide.entity;

import java.util.HashMap;
import java.util.List;

/**
 * Created by steven on 1/6/16.
 */
public class Hospital {
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
}
