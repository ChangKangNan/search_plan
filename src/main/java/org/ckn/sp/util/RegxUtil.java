package org.ckn.sp.util;

import cn.hutool.core.util.StrUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.ckn.sp.constants.StrConstants.FROM;
import static org.ckn.sp.constants.StrConstants.SELECT;

/**
 * @author ckn
 * 正则结果获取类
 */
public class RegxUtil {
    public static final String JOIN_SPLIT = "(?i)( left join )|( right join )|( inner join )|(\nleft join )|(\nright join )|(\ninner join )";
    public static final String JOIN_SPLIT_AND_PREFIX = "(?i)(?= left join )|(?= right join )|(?= inner join )|(?=\nleft join )|(?=\nright join )|(?=\ninner join )";
    public static final Pattern ReplaceToReg = Pattern.compile(".*\\s+((\\S+)/\\*\\s*REPLACE TO (.+)\\*/)(.*)", Pattern.CASE_INSENSITIVE);
    public static final Pattern AliasReg = Pattern.compile("\\s+((\\w)+)(\\s+|\n)on", Pattern.CASE_INSENSITIVE);
    public static final Pattern NameReg = Pattern.compile("(\\s+?)(left join|right join|inner join)\\s+((\\w)+)(\\s+|/\\*)", Pattern.CASE_INSENSITIVE);
    public static final Pattern ReplaceRemarkReg = Pattern.compile("/\\*\\s*REPLACE TO (.+)\\*/", Pattern.CASE_INSENSITIVE);
    public static final Pattern MainTableNameReg = Pattern.compile("(\\s+|\n)((\\w)+)(\\s+|/\\*|\n)", Pattern.CASE_INSENSITIVE);
    public static final Pattern matchColumnAliasReg = Pattern.compile(".*/\\*alias\\s(.*)\\*/", Pattern.CASE_INSENSITIVE);

    public static List<String> getRemarks(String markSql) {
        String sql = markSql.substring(StrUtil.indexOfIgnoreCase(markSql, SELECT), StrUtil.indexOfIgnoreCase(markSql, FROM));
        List<String> stringList = new ArrayList<>();
        Pattern p = Pattern.compile("#(.*)");
        Matcher m = p.matcher(sql);
        while (m.find()) {
            stringList.add(m.group());
        }
        return stringList;
    }


    public static String getUseOrg(String sql) {
        String useOrg = "";
        Pattern p = Pattern.compile("#LIMIT_ORG:(.*)");
        Matcher m = p.matcher(sql);
        while (m.find()) {
            useOrg = m.group(1).trim();
        }
        return useOrg;
    }

    public static String getUseOrgs(String sql) {
        String useOrgs = "";
        Pattern p = Pattern.compile("#LIMIT_ORGS:(.*)");
        Matcher m = p.matcher(sql);
        while (m.find()) {
            useOrgs = m.group(1).trim();
        }
        return useOrgs;
    }

    public static Integer getVersion(String originalSql) {
        Pattern p = Pattern.compile("#VERSION(.*)");
        Matcher m = p.matcher(originalSql);
        Integer version = null;
        while (m.find()) {
            String substring = m.group(1).trim();
            version = Integer.parseInt(substring);
        }
        return version;
    }

    public static String getDataSourceName(String originalSql) {
        Pattern p = Pattern.compile("#DS:(.*)");
        Matcher m = p.matcher(originalSql);
        if (m.find()) {
            return m.group(1).trim();
        }
        return null;
    }


    public static List<String> getInnerCondition(String sql) {
        List<String> infos = new ArrayList<>();
        Pattern p = Pattern.compile("#INNER_CONDITION:(.*)");
        Matcher m = p.matcher(sql);
        while (m.find()) {
            infos.add(m.group(1).trim());
        }
        return infos;
    }

    public static List<String> getChangeTable(String sql) {
        List<String> infos = new ArrayList<>();
        Pattern p = Pattern.compile("#CHANGE_TABLE:(.*)");
        Matcher m = p.matcher(sql);
        while (m.find()) {
            infos.add(m.group(1).trim());
        }
        return infos;
    }


    public static String getMainTableNameBySub(String cup) {
        Matcher matcher = MainTableNameReg.matcher(cup);
        if (matcher.find()) {
            return matcher.group(2);
        }
        return null;
    }


    public static String getReplaceRemark(String cup) {
        Matcher matcher = ReplaceRemarkReg.matcher(cup);
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }


    public static String getAliasNameBySub(String cup) {
        Matcher matcher = AliasReg.matcher(cup);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    public static String getNameBySub(String cup) {
        Matcher matcher = NameReg.matcher(cup);
        if (matcher.find()) {
            return matcher.group(3);
        }
        return null;
    }
}
