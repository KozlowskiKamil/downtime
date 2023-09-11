package com.jabil.downtime.controler;

import com.jabil.downtime.BreakdownRepository;
import com.jabil.downtime.BreakdownService;
import com.jabil.downtime.TechnicianRepository;
import com.jabil.downtime.TechnicianService;
import com.jabil.downtime.dto.BreakdownDto;
import com.jabil.downtime.dto.TechnicianDto;
import com.jabil.downtime.model.Breakdown;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BreakedownMvc {


    private final BreakdownService breakdownService;
    private final BreakdownRepository breakdownRepository;
    private final TechnicianRepository technicianRepository;
    private final TechnicianService technicianService;

    @GetMapping("/list")
    public String breakedownList(Model model) {
        List<BreakdownDto> all = breakdownService.findAllDescending();
        model.addAttribute("breakdowns", all);
        return "list";
    }

    @GetMapping("/addbreakdown")
    public String breakdown(Model model) {
        List<BreakdownDto> all = breakdownService.findAllDescending();
        model.addAttribute("breakdowns", all);
        return "addbreakdown";
    }

    @GetMapping("/technician")
    public String technician(Model model) {
        List<TechnicianDto> all = technicianService.findAllTechnican();
        model.addAttribute("technicians", all);
        return "technician";
    }

    @GetMapping("/todo")
    public String todo(Model model) {
        List<TechnicianDto> all = technicianService.findAllTechnican();
        model.addAttribute("technicians", all);
        return "todo";
    }

    @GetMapping("/stats")
    public String stats(Model model) {
        List<TechnicianDto> all = technicianService.findAllTechnican();
        model.addAttribute("technicians", all);
        return "stats";
    }

    @PostMapping("/add")
    public String addTechnician(@ModelAttribute TechnicianDto technicianDto, Model model) {
        String s = technicianService.registerUserId(technicianDto);
        List<TechnicianDto> all = technicianService.findAllTechnican();
        model.addAttribute("technicians", all);
        model.addAttribute("message", s);
        return "technician";
    }

    @GetMapping("/")
    public String dashboard(Model model) {
        List<BreakdownDto> all = breakdownService.findAllDescending();
        List<Breakdown> breakdownStart = breakdownService.findAllByOngoing(true);
        List<Breakdown> breakdownEnd = breakdownService.findAllByOngoing(false);
        List<Object[]> countComputerName = breakdownRepository.findComputerNamesWithCounts();
        List<Object[]> findFailureNameWithCounts = breakdownRepository.findFailureNameWithCounts();
        model.addAttribute("breakdowns", all);
        model.addAttribute("breakdownStart", breakdownStart);
        model.addAttribute("breakdownEnd", breakdownEnd);
        model.addAttribute("computerNamesWithCounts", countComputerName);
        model.addAttribute("findFailureNameWithCounts", findFailureNameWithCounts);

        return "index";
    }

}