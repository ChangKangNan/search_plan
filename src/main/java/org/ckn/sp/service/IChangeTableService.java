package org.ckn.sp.service;

import org.ckn.sp.fm.bean.SearchConfig;
import org.ckn.sp.fm.bean.SearchConfigInfo;
import java.util.*;
/**
 * @author ckn
 */
public interface IChangeTableService {
    void handleChangeTable(SearchConfig searchConfig, List<SearchConfigInfo> searchConfigInfoList);
}
