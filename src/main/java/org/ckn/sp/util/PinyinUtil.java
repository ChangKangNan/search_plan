package org.ckn.sp.util;

import lombok.extern.slf4j.Slf4j;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;

/**
 * @author ckn
 */
@Slf4j
public class PinyinUtil {
    public static String getPinyin(String str) {
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        String formatStr = str.replaceAll("([a-zA-Z0-9]+)", "$1_");
        String regEx = "([`!@#$%^&*()+=|{}':;',//[//].<>/?！@#￥%……&*（）——+|{}【】‘；：”“’。，、？-])";
        formatStr = formatStr.replaceAll(regEx, "_");
        try {
            String yuPinyinString = PinyinHelper.toHanYuPinyinString(formatStr + "_", format, "_", true);
            return yuPinyinString.substring(0, yuPinyinString.length() - 1);
        } catch (Exception e) {
            log.info("对应的文件拼音翻译错误!");
        }
        return "";
    }
}
