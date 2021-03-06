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
    static String message = "[{\"id\":1,\"name\":\"家醫科\",\"intro\":\"感冒、腸胃不適、尿道發炎及一般急性病的診斷治療；高血壓、糖尿病、痛風、高血脂等慢性病的追蹤治療；各種疫苗接種及家庭常見疾病診治及諮詢。\"},{\"id\":2,\"name\":\"內科\",\"intro\":\"凡一般初次身體不適，疲勞、無力、胸悶及有急、慢性病病症等，身體不舒服而本身無法區別是何種內科專科者皆可看診\"},{\"id\":3,\"name\":\"外科\",\"intro\":\"一般外傷、甲狀腺腫大、表皮及軟部組織腫瘤、甲溝炎、便血、痔瘡、疝氣、腹腔內器官手術（胃腸出血及腫瘤、腸阻塞、盲腸炎、腹膜炎、肝膽腫瘤、膽道結石、脾臟及胰臟疾病、腹腔內腫瘤）。\\n提供腹腔內各臟器(胃、腸、肝、膽、胰、脾、闌尾)、軟體組織及皮膚腫瘤、疝氣、乳房、甲狀腺、副甲狀腺、靜脈曲張、腹部及消化道外傷、腹部急症、等各類疾病之門診及手術治療，一般外科檢查室包括膽道鏡、乳房超音波及血流探測檢查。\"},{\"id\":4,\"name\":\"兒科\",\"intro\":\"小兒內科：凡十八歲以下、身體不適者皆可掛一般兒科門診、小兒科各診均可看一般兒科，另外並有各次專科之專科醫師診治，包含遺傳、內分泌、新生兒、感染、血液、腫瘤、腎臟、腸胃、心臟、神經、過敏及免疫等次專科。\\n小兒外科：凡十八歲以下小兒疝氣、隱睪、尿道下裂、斜頸、表皮腫瘤、盲腸炎、腹部腫瘤及各種先天性胸腹部異常、肛門閉鎖、巨結腸症、膽道閉鎖、囊腫等。\"},{\"id\":5,\"name\":\"婦產科\",\"intro\":\"例行婦科檢查和子宮頸抹片、不規則陰道出血、白帶或不正常分泌物、月經失調、尿失禁、不孕症、人工受孕、流產、精液檢查、精子分離、染色體檢查、腹部疼痛、婦科腫瘤、女性結紮、避孕、優生保健遺傳諮詢、羊膜腔穿刺術、更年期障礙、孕婦產前後檢查。\"},{\"id\":6,\"name\":\"中醫科\",\"intro\":\"肝炎、肝硬化、糖尿病、高血壓、腎臟病、心臟病、消化系統疾病、氣喘、咳嗽、過敏性鼻炎、婦女病、小兒發育不良、關節炎、風濕痛、腰痛、坐骨神經痛、神經衰弱、減肥、失眠、骨折、脫臼、內傷、扭挫傷、腰酸背痛、筋骨酸痛、性功能障礙\"},{\"id\":7,\"name\":\"泌尿科\",\"intro\":\"腎臟移植、泌尿道外傷、腫瘤、前列腺（攝護腺）腫大、睪丸異常、副睪丸炎及睪丸炎、包莖及精索靜脈曲張、陰囊水腫、隱睪、尿道裂或狹窄、血尿、頻尿、小便 無力、女性尿失禁、泌尿管結石、膀胱炎、陰莖截除、外性徵異常、男性性機能異常、男性不孕、包莖、性病、膀胱機能異常、浮游腎、內視鏡手術、碎石機手術。\"},{\"id\":8,\"name\":\"耳鼻喉科\",\"intro\":\"暈胘、聲音沙啞、吞嚥困難、鼻塞、鼻竇炎、扁挑腺病變、聽力障礙、耳鳴、頭頸部腫瘤、舌及口腔咽喉疾病、語言障礙。\"},{\"id\":9,\"name\":\"眼科\",\"intro\":\"學齡前兒童眼晴異常、弱視、斜視、近視、遠視、亂視、老花眼、色盲、兔眼的檢查、訓練、手術矯正治療及驗光配鏡、砂眼、結膜炎、角膜炎、瞼緣炎、視神經 炎、眼球震顫、眼瞼下垂、倒睫、眼球突出、眼瞼缺損及疤痕、瞼內外翻、翼狀臠肉、淚囊病、淚管阻塞、眼瘤、眼肌麻痺、晶體脫位或摘除、眼壓過高、眼痙攣、 視野缺損、葡萄膜層疾病、視網膜疾病出血破裂剝離、糖尿病視網膜症、急慢性青光眼手術及雷射治療、白內障、超音波乳化手術、眼結核及眼梅毒、飛蚊症。\"},{\"id\":10,\"name\":\"皮膚科\",\"intro\":\"濕疹、皮膚過敏、毛髮、指甲的病變、皮膚黴菌、細菌病毒之感染、青春痘、老人斑、色素疾病、皮膚腫瘤切除、乾癬照光治療\"},{\"id\":11,\"name\":\"骨科\",\"intro\":\"骨折、脫臼各種肌肉韌帶、關節挫傷外傷及相關的併發症、各種肌肉骨骼酸痛、肌肉腰背疼痛、關節炎、骨畸型、骨腫瘤、骨骼關節之各種感染、骨質鬆、脊椎病 變、坐骨神經痛、脊椎側彎、老人脊椎退化併神經病變、肢體不等長、各運動傷害、肩關節、踝關節習慣性半脫臼或脫臼、十字韌帶斷裂、關節積血、腫脹、肌肉萎 縮或纖維化、手部腕部、足部及掌指(趾)骨各種急慢性骨折、癒合不良、肌腱斷裂、指(趾)甲變形或手掌燧神經壓迫麻木、小兒或成人先天性或創傷性肢體未端 畸型或骨病變發育不良如多指(趾)、併指(趾)、缺指(趾)、上臂神經叢損傷、髖部發育不良骨折後肢體末端交感神經異常、脫臼後關節不穩定及各種肌肉、肌 腱、韌帶急慢性挫傷、拉傷、扭傷等、小兒先天性骨骼關節病變及發育不良\"},{\"id\":12,\"name\":\"神經科\",\"intro\":\"顱骨畸形及缺損、脊髓脊椎骨外傷、腦瘤、顱內出血、多汗症、三叉神經疼、坐骨神經疼、四肢酸麻、背部疼痛。暈眩 頭痛 腦中風 失智症 神經痛 記憶障礙、睡眠障礙、巴金森氏症\\n症狀參考：心悸、胸悶、胸痛、冒冷汗、心搏過速、呼吸困難、頭暈、昏倒、水腫、運動不適。\"},{\"id\":13,\"name\":\"精神科\",\"intro\":\"失眠症、腦神經衰弱、健忘、心理困擾、憂鬱症、自殺行為、焦慮、情緒困擾、酒癮、行為異常、幻聽、妄想、老人痴呆、兒童青少年問題、家庭婚姻問題、壓力大、失眠、神經衰弱、記憶力減退、精神官能症、心身症、肥胖症、厭食症、暴食症、智能不足、躁鬱症、高職業壓力群。\"},{\"id\":14,\"name\":\"復健科\",\"intro\":\"腰酸、背痛、頸部酸痛、坐骨神經痛、骨刺、關節炎、肌肉拉傷、關節扭傷、運動傷害、五十肩、骨折後遺症（關節攣縮、疼痛、神經麻痺）、頭部外傷後遺症、腦 中風、脊髓損傷、腦性麻痺、斜頸、發展遲緩、截肢、灼傷或外傷後導致功能或活動障礙、肢體復健、手功能障礙、各種職能治療、失語症、構音異常、聲音沙啞、 各種語言治療。\"},{\"id\":15,\"name\":\"牙科\",\"intro\":\"一般拔牙、牙髓治療、牙體膺形、口腔診斷、牙周病、口腔癌、張口受限、顳顎關節障礙、下頜骨脫位、各種義齒製做、潔牙手術、舌根短、家庭牙醫、齒顎矯正、兒童牙科。\"},{\"id\":16,\"name\":\"整形外科\",\"intro\":\"先天性畸形、傷殘或腫瘤、乳房術後重建、灼燙傷各種疤痕、手指缺損或肌腱斷裂、足趾缺失或肌腱斷裂、多趾症或趾畸形、蹼趾、鍾樣趾畸形足、顱顏外科、拉皮、換膚等各項美容整形手術、雙眼皮、眼袋去除手術、隆鼻、除痣、疤痕美容、臉部拉皮、抽脂、脂肪注射、豐胸縮乳、乳頭美容、腹部拉皮、電波拉皮回春、肉毒桿菌去皺、玻尿酸填充。\"},{\"id\":17,\"name\":\"腫瘤科\",\"intro\":\"一般常規血液或血球檢驗、紅血球異常、白血球過多、白血球不足、血小板過多或過少、血癌、淋巴腺腫大、淋巴瘤、骨髓瘤及其他骨髓異常、紫斑、不明原因出血、血液凝固異常、貧血、多血症、血友病、全部血球不足症及不明原因皮下腫塊或其他疑似血液或血球異常、頭頸部腫瘤、婦科腫瘤、腫瘤內科、乳房腫瘤、肝膽胰腫瘤、大腸直腸腫瘤、胃部腫瘤、肺及胸腔腫瘤、腦腫瘤、骨骼腫瘤等。\\n 症狀參考：臉色蒼白、頭暈、容易疲勞、發燒、異常性出汗、肝脾或淋巴腫大、皮下出血、瘀青、異常性出汗、體重減輕、體表各部位腫塊或淋巴腫大、頭痛、視力模糊、神經學病變、 鼻出血、聲音沙啞、吞嚥困難、久咳、胸痛、咳血痰中有血絲、腹痛、嘔血、便血、大便習慣改變、尿血、小便困難、陰道異常出血、骨頭疼痛、皮膚傷口久不癒合。\"},{\"id\":18,\"name\":\"急診醫學科\",\"intro\":\"緊急狀況已不能等待一般門診須立即處置，檢傷護理人員會依檢傷分類來決定，而不是以掛號先後順序來決定。檢傷Ⅰ級－復甦急救，病況危急，例如：心跳停止意識不清等（需立即處理）、檢傷Ⅱ級－危急，如重大創傷、車禍大出血，不會立即危及生命，但生命徵象穩定（等候時間10分鐘）、檢傷Ⅲ級－緊急，如輕度呼吸窘迫、呼吸困難者（等候時間30分鐘）、檢傷Ⅳ級－次緊急，如螫傷、無傷口之組織傷害等（等候時間60分鐘）、檢傷v級－非緊急，如牙痛、傷口換藥，不符合急診條件（等候時間120分鐘，應延後處理或勸說看門診）\\n症狀參考：胸痛、疑似中風3小時內、無生命徵象、重大外、傷血流不止的傷口\"},{\"id\":19,\"name\":\"職業醫學科\",\"intro\":\"1.提供職業傷病門診服務：懷疑因工作或環境所引起之疾病。 \\n2.職業病調查與完整職業病診斷：疾病與職業之間的因果關係調查。 \\n3.事業單位駐廠服務：提供事業單位醫療診治、職業性危害暴露調查、健康促進活動等輔導參予。\\n4.勞工基本職業健康照護服務：提供事業單位職業傷病預防及勞工健康輔導、醫療等服務。 \\n5.職場復工服務：職業傷病勞工對於返回職場有疑慮及困難者。 \\n6.職業傷病勞工個案管理服務。 \\n7.勞動力減損評估：評估遭受傷病之病患其勞動力減損比例。 \\n8.職業醫學專科醫師訓練：培訓職業醫學專科人才。\"},{\"id\":20,\"name\":\"麻醉科\",\"intro\":\"麻醉學本身包含了各項基礎醫學，例如生理學、藥理學及解剖學等知識，臨床上更擴及各科，如內、外、婦、兒科，乃至其次專科知識。醫師的職責則是在手術期間提供安寧無干擾之生理環境，讓病患在無知覺而舒適下，使手術治療順利完成，提供手術過程最佳的安全保障。現代麻醉專科的臨床服務範疇，已延伸至重症醫療，呼吸治療及心肺腦復甦急救以及疼痛等醫學的領域，成為提升醫療照顧品質不可或缺的重要環節，專業觸角已延展至各醫療體系，兼具輔助和積極醫療之使命。\"},{\"id\":21,\"name\":\"老人醫學科\",\"intro\":\"更年期障礙、骨質疏鬆、腰酸背痛、失眠焦慮、尿失禁、退化性關節炎、性功能障礙、高血壓、糖尿病等慢性疾病預防與控制。\"},{\"id\":22,\"name\":\"美容門診\",\"intro\":\"脈衝染料雷射雷射除血管瘤、脈衝染料雷射除痘疤,酒糟,退紅、雷射除毛(腋毛,腿毛,手毛)、雷射洗眉,除刺青、雷射除斑(雀斑,曬斑,母斑)、雷射除痣,汗管瘤,息肉,扁平疣、飛梭雷射改善青春痘疤,毛孔粗大,飛梭雷射改善頸部紋路,鬆弛、淨膚雷射（毛孔粗大,淨膚）、脈衝光除斑,除毛、UltraShape 標靶震波溶脂(腹部、兩側腰部及大腿之贅肉)\"},{\"id\":23,\"name\":\"心理諮商\",\"intro\":\"失眠、適應障礙、焦慮、畏懼、恐慌、強迫、憂鬱、躁鬱、妄想、思覺失調症、過動、自閉症、行為問題、老人失智、各種心身症、暴食厭食、性問題、酒癮、藥癮、心理與職能衡鑑、個別心理治療、團體與婚姻心理治療、精神藥物治療、急性住院與日間病房\"},{\"id\":24,\"name\":\"病理科\",\"intro\":\"1.組織切片：將組織以石臘包埋後切成薄片，脫臘後以H\\u0026E染色，以供病理診斷。 \\n2.冷凍切片：利用急速冷凍檢體的方法進行快速切片，提供緊急病理診斷。 \\n3.電子顯微鏡檢驗：觀察組織微細構造的變化，做更精確的診斷，主要供檢查腎臟疾病及各種腫瘤之診斷。 \\n4.細胞學檢查：收集剝落到體腔或分泌物中的細胞，或以細針抽吸腫瘤細胞，染色後進行病理診斷。 \\n5.免疫螢光染色：利用螢光分子標示抗體的方法，偵測組織中的抗原。 \\n6.免疫組織化學染色：利用免疫學的原理和技術，偵測組織中的抗原，可區分腫瘤組織的發生源，以及判定腫瘤分化程度。 \\n7.特殊染色：以各種化學染色作為輔助病理診斷的工具。 \\n8.大體解剖。\"}]\n";
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
    public String intro;

    public Category(int id, String name, int resourceId, String intro) {
        this.id = id;
        this.name = name;
        this.resourceId = resourceId;
        this.intro = intro;
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

    public static Category getCategoryById(int id){
        ArrayList<Category> categories  = getCategories();
        for(Category c:categories){
            if(c.id == id)
                return c;
        }
        return null;
    }

    public static ArrayList<Category> getCategories() {
        ArrayList<Category> cateogries = new ArrayList<Category>();
        JSONArray categoryArray;
        try {
            categoryArray = new JSONArray(message.toString());
            for (int i = 0; i < categoryArray.length(); i++) {
                int category_id = categoryArray.getJSONObject(i).getInt("id");
                String name = categoryArray.getJSONObject(i).getString("name");
                String intro = categoryArray.getJSONObject(i).getString("intro");
                Category cat = new Category(category_id, name,categoryRscources[i],intro);
                cateogries.add(cat);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return cateogries;
    }
}
