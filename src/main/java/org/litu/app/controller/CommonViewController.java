package org.litu.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/public")
public class CommonViewController {
    /**
     * 返回图标选择的
     *
     * @return
     */
    @GetMapping("/iconSelect")
    public String iconSelect() {
        return "core/icon";
    }
}
