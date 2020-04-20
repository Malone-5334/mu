package com.baselibrary.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Author: SXF
 * E-mail: xue.com.fei@outlook.com
 * CreatedTime: 2018/6/21 16:10
 * <p>
 * TextUtil
 */
public class TextUtil {
    private TextUtil() {
        throw new UnsupportedOperationException("TextUtil cannot be instantiated");
    }

    public static String openText(String path) {
        InputStreamReader isr = null;
        BufferedReader br = null;
        StringBuffer sb = null;
        try {
            isr = new InputStreamReader(new FileInputStream(path));
            br = new BufferedReader(isr);
            sb = new StringBuffer();
            for (String content = null; (content = br.readLine()) != null; ) {
                sb.append(content);
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();

            try {
                if (br != null) {
                    br.close();
                }

                if (isr != null) {
                    isr.close();
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        }
        return null;
    }


}
