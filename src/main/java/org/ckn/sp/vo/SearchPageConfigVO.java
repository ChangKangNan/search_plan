package org.ckn.sp.vo;

import lombok.Data;
import org.ckn.sp.dto.SearchConfigInfoDTO;

import java.io.Serializable;
import java.util.List;

/**
 * 页面搜索配置
 *
 * @author ckn
 * @date 2020/07/14
 */
@Data
public class SearchPageConfigVO implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<SearchConfigInfoDTO> configInfoList;
}
