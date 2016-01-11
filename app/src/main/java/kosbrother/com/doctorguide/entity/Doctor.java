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
    public boolean isCollected;
    public String exp;
    public String spe;
    public List<Division> divisions;
    public float latitude;
    public float longitude;
}
