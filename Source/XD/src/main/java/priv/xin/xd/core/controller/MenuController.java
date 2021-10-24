package priv.xin.xd.core.controller;

import org.springframework.web.bind.annotation.*;
import priv.xin.xd.check.MessageLevel;
import priv.xin.xd.check.system.Message;
import priv.xin.xd.common.result.Result;
import priv.xin.xd.core.service.ISysMenuService;
import priv.xin.xd.core.entity.Menu;
import priv.xin.xd.core.entity.ex.MenuEx;

import javax.annotation.Resource;

/**
 * @author ：lu
 * @date ：2021/7/21 13:16
 */
@RestController
@RequestMapping("sys/menu")
public class MenuController {

    @Resource
    private ISysMenuService sysMenuService;

    /**
     * @param menu 查询条件+
     * @return
     */
    @RequestMapping(value = "/tree", method = RequestMethod.GET)
    public Result treeList(Menu menu) {
        Result result = sysMenuService.queryTreeList(menu);
        if (!result.getStatus()) {
            return result.message(MessageLevel.INFO, Message.MENU_QUERY_ERROR);
        }
        Object menuTree = result.getData("menuTree");
        return result.clearData()
                .data("menuTree", menuTree)
                .message(MessageLevel.INFO, Message.MENU_QUERY_SUCCESS);
    }

    @RequestMapping(value = "/{menuId}", method = RequestMethod.GET)
    public Result detail(@PathVariable("menuId") String menuId) {
        Result result = sysMenuService.queryDetail(menuId);
        if (!result.getStatus()) {
            return result.message(MessageLevel.INFO, Message.MENU_QUERY_ERROR);
        }
        Object menuDetail = result.getData("menuDetail");
        return result.clearData()
                .data("menuDetail", menuDetail)
                .message(MessageLevel.INFO, Message.MENU_QUERY_SUCCESS);
    }

    @RequestMapping(value = "/{menuId}", method = RequestMethod.PUT)
    public Result update(@PathVariable("menuId") String menuId, @RequestBody MenuEx menuEx) {
        Result result = sysMenuService.updateMenuDetail(menuId, menuEx);
        if (!result.getStatus()) {
            return result.message(MessageLevel.INFO, Message.MENU_EDIT_ERROR);
        }
        return result.message(MessageLevel.INFO, Message.MENU_EDIT_SUCCESS);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public Result insert(@RequestBody MenuEx menuEx) {
        Result result = sysMenuService.insertMenuDetail(menuEx);
        if (!result.getStatus()) {
            return result.message(MessageLevel.INFO, Message.MENU_INSERT_ERROR);
        }
        return result.message(MessageLevel.INFO, Message.MENU_INSERT_SUCCESS);
    }

    @RequestMapping(value = "/{menuId}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable("menuId") String menuId) {
        Result result = sysMenuService.deleteByMenuId(menuId);
        if (!result.getStatus()) {
            return result.message(MessageLevel.INFO, Message.MENU_DELETE_ERROR);
        }
        return result.message(MessageLevel.INFO, Message.MENU_DELETE_SUCCESS);
    }

}
