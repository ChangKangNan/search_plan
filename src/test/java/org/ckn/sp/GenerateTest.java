package org.ckn.sp;

import cn.ft.ckn.fastmapper.bean.GenerateTemplateConfig;
import cn.ft.ckn.fastmapper.util.GenerateTemplate;

/**
 * @author ckn
 */
public class GenerateTest {
    public static void main(String[] args) {
        GenerateTemplateConfig config=new GenerateTemplateConfig();
        config.setBasePackage("org.ckn.sp");
        config.setDBInfo("jdbc:mysql://localhost:3306/search_plan?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai&useInformationSchema=true",
                "root","123456","com.mysql.jdbc.Driver");
        GenerateTemplate.generate(config);
    }
}
