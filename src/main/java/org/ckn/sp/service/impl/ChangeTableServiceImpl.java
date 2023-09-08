package org.ckn.sp.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import org.ckn.sp.bean.ChangeTable;
import org.ckn.sp.fm.bean.SearchConfig;
import org.ckn.sp.fm.bean.SearchConfigInfo;
import org.ckn.sp.fm.dao.SearchConfigInfoMapper;
import org.ckn.sp.service.IChangeTableService;
import org.ckn.sp.util.RegxUtil;
import org.ckn.sp.util.SqlUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ckn
 */
@Service
public class ChangeTableServiceImpl implements IChangeTableService {

    @Override
    public void handleChangeTable(SearchConfig searchConfig,List<SearchConfigInfo> searchConfigInfoList) {
        String searchSql = searchConfig.getSearchSql();
        searchSql = SqlUtil.replaceLineSeparator(searchSql);
        String pageTag = searchConfig.getPageTag();
        List<String> changeTables= RegxUtil.getChangeTable(searchSql);
        if(CollUtil.isEmpty(changeTables)){
            return;
        }
        List<ChangeTable> influenceTable=new ArrayList<>();
        for (String str : changeTables) {
            ChangeTable changeTable=new ChangeTable();
            String[] split = str.split(StrUtil.SPACE);
            String columnName = split[0];
            changeTable.setColumnName(columnName.trim());
            for (int i = 1; i < split.length; i++) {
                String val = split[i];
                String[] valSplit = val.split("=");
                String key = valSplit[0];
                String value = valSplit[1];
                switch (key) {
                    case "axis":
                        changeTable.setAxis(value);
                        break;
                    case "date_rule":
                        changeTable.setRule(value);
                        break;
                    case "tb_name":
                        changeTable.setFinalName(value);
                        break;
                    case "point":
                        changeTable.setPoint(value);
                        break;
                    default:
                        break;
                }
            }
            influenceTable.add(changeTable);
        }
        for (SearchConfigInfo configInfo : searchConfigInfoList) {
            String searchKey = configInfo.getSearchKey();
            for (ChangeTable ct : influenceTable) {
                String columnName = ct.getColumnName();
                if(StrUtil.contains(searchKey,"/*alias "+columnName+"*/") || StrUtil.equals(searchKey,columnName)){
                    configInfo.setChangeTb(true);
                    configInfo.setChangeDateRule(ct.getRule());
                    configInfo.setAxis(ct.getAxis());
                    configInfo.setChangeTbName(ct.getFinalName());
                    configInfo.setPoint(ct.getPoint());
                    SearchConfigInfoMapper.lambdaUpdate().id().equal(configInfo.getId()).update(configInfo);
                }
            }
        }
    }
}
