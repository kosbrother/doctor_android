package kosbrother.com.doctorguide.entity.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by steven on 1/6/16.
 */
public class RealmDoctor extends RealmObject {
    @PrimaryKey
    private int id;

    private String name;
    private String address;
    private String hospital;
    private int hospitalId;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getHospital() {
        return hospital;
    }

    public int getHospitalId() {
        return hospitalId;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setAddress(String address){
        this.address = address;
    }

    public void setHospital(String hospital){
        this.hospital = hospital;
    }

    public void setHospitalId(int hospitalId){
        this.hospitalId = hospitalId;
    }
}
