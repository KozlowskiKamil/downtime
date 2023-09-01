package com.jabil.downtime.controler;

import com.jabil.downtime.BreakdownService;
import com.jabil.downtime.dto.BreakdownDto;
import com.jabil.downtime.model.Breakdown;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BreakedownMvc {


    private final BreakdownService breakdownService;

    @GetMapping("/list")
    public String breakedownList(Model model) {
        List<BreakdownDto> all = breakdownService.findAllDescending();
        model.addAttribute("breakdowns" , all);
        return "list";
    }

    @GetMapping("/")
    public String dashboard(Model model) {
        List<BreakdownDto> all = breakdownService.findAll();
        List<Breakdown> show = breakdownService.findAllByOngoing(true);
        model.addAttribute("breakdowns" , all);
        model.addAttribute("show" , show);
        return "index";
    }

}