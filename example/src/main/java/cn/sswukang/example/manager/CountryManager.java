package cn.sswukang.example.manager;

import android.content.Context;
import android.databinding.repacked.google.common.reflect.TypeToken;

import com.google.gson.Gson;

import net.sourceforge.pinyin4j.PinyinHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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
     * @return 按国家中文名正序
     */
    public Comparator<Country> comparatorNameCnAcs() {
        return (t1, t2) -> t1.getCountryNameCn().compareTo(t2.getCountryNameCn());
    }

    /**
     * @return 按国家英文缩写正序
     */
    public Comparator<Country> comparatorAbAcs() {
        return (t1, t2) -> t1.getAb().compareTo(t2.getAb());
    }

    /**
     * 英文名排序时，找到每个首字母起始处。
     *
     * @param comparatorEnList 按国家英文名排序列表
     * @param initials         首字母
     * @return 起始处下标
     */
    public int getEnInitialsIndex(List<Country> comparatorEnList, char initials) {
        if (initials >= 'a' && initials <= 'z')
            initials -= 32;
        if (initials < 'A' || initials > 'Z')
            throw new IllegalArgumentException("'initials' not in alphabet");

        int size = comparatorEnList.size();
        for (int i = 0; i < size; i++) {
            char enInitials = comparatorEnList.get(i).getCountryNameEn().charAt(0);
            if (enInitials >= 'a' && enInitials <= 'z')
                enInitials -= 32;
            if (enInitials == initials) {
                return i;
            }
        }

        return size;
    }

    /**
     * 中文名排序时，找到每个拼音首字母起始处。
     *
     * @param comparatorCnList 按国家中文名排序列表
     * @param initials         拼音首字母
     * @return 起始处下标
     */
    public int getCnInitialsIndex(List<Country> comparatorCnList, char initials) {
        if (initials >= 'a' && initials <= 'z')
            initials -= 32;
        if (initials < 'A' || initials > 'Z')
            throw new IllegalArgumentException("'initials' not in alphabet");

        int size = comparatorCnList.size();
        for (int i = 0; i < size; i++) {
            char cnInitials = comparatorCnList.get(i).getCountryNameCn().charAt(0);
            String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(cnInitials);
            if (pinyinArray != null) {
                cnInitials = pinyinArray[0].charAt(0);
            }
            if (cnInitials >= 'a' && cnInitials <= 'z')
                cnInitials -= 32;
            if (cnInitials == initials) {
                return i;
            }
        }
        return size;
    }

}
