package priv.xin.xd.expansionService.excel.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import priv.xin.xd.expansionService.excel.service.ExcelService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ：lu
 * @date ：2021/11/13 18:34
 */
@RestController
@RequestMapping("excel")
public class ExcelController {
    @Resource
    private ExcelService excelService;

    @RequestMapping(value = "/export/dbTable", method = RequestMethod.GET)
    public void start(HttpServletResponse response) {
        excelService.exportDBTableInfo(response);
    }
}
