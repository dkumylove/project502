package org.choongang.upbit.controllers;

import lombok.RequiredArgsConstructor;
import org.choongang.commons.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/upbit")
@RequiredArgsConstructor
public class UpBitController {

    private final Utils utils;

    @GetMapping("/main")
    public String index(Model model) {

        model.addAttribute("addCommonScript", new String[] {"upbit"});

        return utils.tpl("upbit/main");
    }
}