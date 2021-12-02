package priv.xin.xd.expansionService.excel.service;

import javax.servlet.http.HttpServletResponse;

/**
 * @author ：lu
 * @date ：2021/11/13 15:50
 */
public interface ExcelService {

    public void exportDBTableInfo(HttpServletResponse response);
}
