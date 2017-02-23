package cn.sswukang.example.util;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 工具类
 *
 * @author sswukang on 2017/2/21 9:49
 * @version 1.0
 */
public class Utils {
    /**
     * 从assets读取文本信息
     */
    public static String getTextFromAssets(Context context, String fileName) throws IOException {
        InputStreamReader inputReader = new InputStreamReader(context.getAssets().open(fileName));
        BufferedReader bufReader = new BufferedReader(inputReader);
        String line;
        String result = "";
        while ((line = bufReader.readLine()) != null)
            result += line;
        return result;
    }
}
