package kosbrother.com.doctorguide.entity;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by steven on 1/6/16.
 */
public class Area {

    static String json = "[{\"id\":1,\"name\":\"臺北市\"},{\"id\":2,\"name\":\"新北市\"},{\"id\":3,\"name\":\"桃園市\"},{\"id\":4,\"name\":\"臺中市\"},{\"id\":5,\"name\":\"臺南市\"},{\"id\":6,\"name\":\"高雄市\"},{\"id\":7,\"name\":\"基隆市\"},{\"id\":8,\"name\":\"嘉義市\"},{\"id\":9,\"name\":\"新竹市\"},{\"id\":10,\"name\":\"新竹縣\"},{\"id\":11,\"name\":\"苗栗縣\"},{\"id\":12,\"name\":\"彰化縣\"},{\"id\":13,\"name\":\"南投縣\"},{\"id\":14,\"name\":\"雲林縣\"},{\"id\":15,\"name\":\"嘉義縣\"},{\"id\":16,\"name\":\"屏東縣\"},{\"id\":17,\"name\":\"宜蘭縣\"},{\"id\":18,\"name\":\"花蓮縣\"},{\"id\":19,\"name\":\"臺東縣\"},{\"id\":20,\"name\":\"澎湖縣\"},{\"id\":21,\"name\":\"金門縣\"},{\"id\":22,\"name\":\"連江縣\"}]";

    public int id;
    public String name;

    public static ArrayList<Area> getAreas(){
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<Area> areas = new ArrayList<Area>();
        try {
            areas = mapper.readValue(json, new TypeReference<ArrayList<Area>>() {});
            return areas;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return areas;
    }

    public static ArrayList<String> getAreaStrings(){
        ArrayList<String> areas = new ArrayList<>();
        ArrayList<Area> areaList = getAreas();
        for (Area item:areaList) {
            areas.add(item.name);
        }
        return areas;
    }
}
