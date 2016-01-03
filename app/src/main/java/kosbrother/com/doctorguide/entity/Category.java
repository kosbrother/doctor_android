package kosbrother.com.doctorguide.entity;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;

import kosbrother.com.doctorguide.R;

/**
 * Created by steven on 1/3/16.
 */
public class Category {
    static String message = "[{\"id\":1,\"name\":\"家醫科\"},{\"id\":2,\"name\":\"內科\"},{\"id\":3,\"name\":\"外科\"},{\"id\":4,\"name\":\"兒科\"},{\"id\":5,\"name\":\"婦產科\"},{\"id\":6,\"name\":\"中醫科\"},{\"id\":7,\"name\":\"泌尿科\"},{\"id\":8,\"name\":\"耳鼻喉科\"},{\"id\":9,\"name\":\"眼科\"},{\"id\":10,\"name\":\"皮膚科\"},{\"id\":11,\"name\":\"骨科\"},{\"id\":12,\"name\":\"神經科\"},{\"id\":13,\"name\":\"精神科\"},{\"id\":14,\"name\":\"復健科\"},{\"id\":15,\"name\":\"牙科\"},{\"id\":16,\"name\":\"整形外科\"},{\"id\":17,\"name\":\"腫瘤科\"},{\"id\":18,\"name\":\"急診醫學科\"},{\"id\":19,\"name\":\"職業醫學科\"},{\"id\":20,\"name\":\"麻醉科\"},{\"id\":21,\"name\":\"老人醫學科\"},{\"id\":22,\"name\":\"美容門診\"},{\"id\":23,\"name\":\"心理諮商\"},{\"id\":24,\"name\":\"病理科\"}]\n";
    static int[] categoryRscources = new int[] {
            R.mipmap.ic_category_family_medicine,
            R.mipmap.ic_category_medicine,
            R.mipmap.ic_category_surgical,
            R.mipmap.ic_category_pediatrics,
            R.mipmap.ic_category_gynecology,
            R.mipmap.ic_category_chinese_medicine,
            R.mipmap.ic_category_urology,
            R.mipmap.ic_category_throat,
            R.mipmap.ic_category_ophthalmology,
            R.mipmap.ic_category_skin,
            R.mipmap.ic_category_bone,
            R.mipmap.ic_category_neurology,
            R.mipmap.ic_category_psychiatry,
            R.mipmap.ic_category_rehabilitation,
            R.mipmap.ic_category_tooth,
            R.mipmap.ic_category_plastic,
            R.mipmap.ic_category_tumor,
            R.mipmap.ic_category_emergency,
            R.mipmap.ic_category_occupation,
            R.mipmap.ic_category_anesthesia,
            R.mipmap.ic_category_old,
            R.mipmap.ic_category_beauty,
            R.mipmap.ic_category_psychology,
            R.mipmap.ic_category_pathology
    };
    public int id;
    public String name;
    public int resourceId;

    public Category(int id, String name, int resourceId) {
        this.id = id;
        this.name = name;
        this.resourceId = resourceId;
    }

    public static String getCategoryName(int id) {
        HashMap hash = new HashMap();
        JSONArray categoryArray;
        try {
            categoryArray = new JSONArray(message.toString());
            for (int i = 0; i < categoryArray.length(); i++) {
                int category_id = categoryArray.getJSONObject(i).getInt("id");
                String name = categoryArray.getJSONObject(i).getString("name");
                hash.put(category_id, name);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return (String) hash.get(id);
    }

    public static ArrayList<Category> getCategories() {
        ArrayList<Category> cateogries = new ArrayList<Category>();
        JSONArray categoryArray;
        try {
            categoryArray = new JSONArray(message.toString());
            for (int i = 0; i < categoryArray.length(); i++) {
                int category_id = categoryArray.getJSONObject(i).getInt("id");
                String name = categoryArray.getJSONObject(i).getString("name");
                Category cat = new Category(category_id, name,categoryRscources[i]);
                cateogries.add(cat);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return cateogries;
    }
}
