package com.jabil.downtime.controler;

import com.jabil.downtime.BreakdownRepository;
import com.jabil.downtime.BreakdownService;
import com.jabil.downtime.dto.BreakdownDto;
import com.jabil.downtime.model.Breakdown;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class BreakedownMvc {


    private final BreakdownService breakdownService;
    private final BreakdownRepository breakdownRepository;

    @GetMapping("/list")
    public String breakedownList(Model model) {
        List<BreakdownDto> all = breakdownService.findAllDescending();
        model.addAttribute("breakdowns" , all);
        return "list";
    }

    @GetMapping("/")
    public String dashboard(Model model) {
        List<BreakdownDto> all = breakdownService.findAllDescending();
        List<Breakdown> breakdownStart = breakdownService.findAllByOngoing(true);
        List<Breakdown> breakdownEnd = breakdownService.findAllByOngoing(false);
        List<Object[]> countComputerName = breakdownRepository.findComputerNamesWithCounts();
        model.addAttribute("breakdowns" , all);
        model.addAttribute("breakdownStart" , breakdownStart);
        model.addAttribute("breakdownEnd" , breakdownEnd);
        model.addAttribute("computerNamesWithCounts", countComputerName);

        return "index";
    }

}