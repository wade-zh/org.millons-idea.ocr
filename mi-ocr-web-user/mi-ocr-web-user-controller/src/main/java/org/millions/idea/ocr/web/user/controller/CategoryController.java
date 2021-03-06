/***
 * @pName mi-ocr-web-user
 * @name CategoryController
 * @user HongWei
 * @date 2018/6/25
 * @desc
 */
package org.millions.idea.ocr.web.user.controller;

import org.millions.idea.ocr.web.user.biz.IChannelsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private IChannelsService backCategoryService;

    @GetMapping("/initCache")
    public String initCache(){
        backCategoryService.initCache();
        return "OK";
    }

}
