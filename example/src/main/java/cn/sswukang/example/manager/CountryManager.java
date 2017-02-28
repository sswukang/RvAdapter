package cn.sswukang.example.manager;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.sswukang.example.model.Country;
import cn.sswukang.example.util.Utils;


/**
 * 城市列表
 *
 * @author sswukang on 2017/2/21 9:09
 * @version 1.0
 */
public class CountryManager {

    private List<Country> countryList;

    private CountryManager() {
        countryList = new ArrayList<>();
    }

    private static class LazyHolder {
        private static final CountryManager INSTANCE = new CountryManager();
    }

    public static CountryManager getInstance() {
        return CountryManager.LazyHolder.INSTANCE;
    }

    /**
     * 初始化数据
     */
    public void init(Context context) {
        try {
            String json = Utils.getTextFromAssets(context, "countrycode.json");
            countryList = new Gson().fromJson(json, new TypeToken<List<Country>>() {
            }.getType());
        } catch (IOException ignore) {
            countryList = new ArrayList<>();
        }
    }

    /**
     * @return 国家列表
     */
    public List<Country> getCountryList() {
        return countryList;
    }

    /**
     * @return 按国家id正序
     */
    public Comparator<Country> comparatorIdAcs() {
        return (t1, t2) -> t1.getCountryId() - t2.getCountryId();
    }

    /**
     * @return 按国家编码正序
     */
    public Comparator<Country> comparatorCodeAcs() {
        return (t1, t2) -> t1.getCountryCode() - t2.getCountryCode();
    }

    /**
     * @return 按国家英文名正序
     */
    public Comparator<Country> comparatorNameEnAcs() {
        return (t1, t2) -> t1.getCountryNameEn().compareTo(t2.getCountryNameEn());
    }

    /**
     * 得到列表的所有首字母集合
     *
     * @return 首字母列表
     */
    public List<String> getInitialsList() {
        Map<Character, String> map = new HashMap<>();
        for (Country country : getCountryList()) {
            String nameEn = country.getCountryNameEn();
            map.put(nameEn.charAt(0), nameEn.substring(0, 1));
        }
        return new ArrayList<>(map.values());
    }

    /**
     * 按英文名排序的每个首字母起始处。
     *
     * @param comparatorList 排序列表
     * @param initials       首字母
     * @return 起始处下标
     */
    public int getInitialsIndex(List<Country> comparatorList, char initials) {
        if (initials >= 'a' && initials <= 'z')
            initials -= 32;
        if (initials < 'A' || initials > 'Z')
            throw new IllegalArgumentException("'initials' not in alphabet");

        int size = comparatorList.size();
        for (int i = 0; i < size; i++) {
            char enInitials = comparatorList.get(i).getCountryNameEn().charAt(0);
            if (enInitials >= 'a' && enInitials <= 'z')
                enInitials -= 32;
            if (enInitials == initials) {
                return i;
            }
        }

        return size;
    }

}
