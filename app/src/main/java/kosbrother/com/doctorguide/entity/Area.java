package kosbrother.com.doctorguide.entity;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by steven on 1/6/16.
 */
public class Area {

    static String json = "[{\"id\":1,\"name\":\"臺北市\",\"latitude\":\"25.032964\",\"longitude\":\"121.565427\"},{\"id\":2,\"name\":\"新北市\",\"latitude\":\"25.016983\",\"longitude\":\"121.462787\"},{\"id\":3,\"name\":\"桃園市\",\"latitude\":\"24.993628\",\"longitude\":\"121.30098\"},{\"id\":4,\"name\":\"臺中市\",\"latitude\":\"24.147736\",\"longitude\":\"120.673648\"},{\"id\":5,\"name\":\"臺南市\",\"latitude\":\"22.9999\",\"longitude\":\"120.226876\"},{\"id\":6,\"name\":\"高雄市\",\"latitude\":\"22.627278\",\"longitude\":\"120.301435\"},{\"id\":7,\"name\":\"基隆市\",\"latitude\":\"25.127603\",\"longitude\":\"121.739183\"},{\"id\":8,\"name\":\"嘉義市\",\"latitude\":\"23.480075\",\"longitude\":\"120.449111\"},{\"id\":9,\"name\":\"新竹市\",\"latitude\":\"24.81383\",\"longitude\":\"120.967475\"},{\"id\":10,\"name\":\"新竹縣\",\"latitude\":\"24.838723\",\"longitude\":\"121.017725\"},{\"id\":11,\"name\":\"苗栗縣\",\"latitude\":\"24.560159\",\"longitude\":\"120.821427\"},{\"id\":12,\"name\":\"彰化縣\",\"latitude\":\"24.051796\",\"longitude\":\"120.516135\"},{\"id\":13,\"name\":\"南投縣\",\"latitude\":\"23.960998\",\"longitude\":\"120.971864\"},{\"id\":14,\"name\":\"雲林縣\",\"latitude\":\"23.709203\",\"longitude\":\"120.431337\"},{\"id\":15,\"name\":\"嘉義縣\",\"latitude\":\"23.451843\",\"longitude\":\"120.255462\"},{\"id\":16,\"name\":\"屏東縣\",\"latitude\":\"22.551976\",\"longitude\":\"120.54876\"},{\"id\":17,\"name\":\"宜蘭縣\",\"latitude\":\"24.702107\",\"longitude\":\"121.73775\"},{\"id\":18,\"name\":\"花蓮縣\",\"latitude\":\"23.987159\",\"longitude\":\"121.601571\"},{\"id\":19,\"name\":\"臺東縣\",\"latitude\":\"22.797245\",\"longitude\":\"121.07137\"},{\"id\":20,\"name\":\"澎湖縣\",\"latitude\":\"23.57119\",\"longitude\":\"119.579316\"},{\"id\":21,\"name\":\"金門縣\",\"latitude\":\"24.449373\",\"longitude\":\"118.376635\"},{\"id\":22,\"name\":\"連江縣\",\"latitude\":\"26.197364\",\"longitude\":\"119.539704\"}]";
    public int id;
    public String name;
    public float latitude;
    public float longitude;

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
