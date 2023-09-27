package org.ckn.sp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ckn
 * @date 2023/7/27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OptimizationDTO {
    private String left;
    private String right;
    private Boolean extend;
    private String table;
}
