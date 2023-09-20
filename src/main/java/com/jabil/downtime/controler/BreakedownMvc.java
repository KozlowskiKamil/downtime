package com.jabil.downtime.controler;

import com.jabil.downtime.*;
import com.jabil.downtime.dto.BreakdownDto;
import com.jabil.downtime.dto.TechnicianDto;
import com.jabil.downtime.dto.TodoDto;
import com.jabil.downtime.model.Breakdown;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BreakedownMvc {


    private final BreakdownService breakdownService;
    private final BreakdownRepository breakdownRepository;
    private final TechnicianRepository technicianRepository;
    private final TechnicianService technicianService;
    private final TodoService todoService;

    @GetMapping("/list")
    public String breakedownList(Model model) {
        List<BreakdownDto> all = breakdownService.findAllDescending();
        model.addAttribute("breakdowns", all);
        return "list";
    }

    @GetMapping("/addbreakdown")
    public String breakdown(Model model) {
        List<BreakdownDto> all = breakdownService.findAllDistinct();
        model.addAttribute("breakdowns", all);
        List<TechnicianDto> allTechnican = technicianService.findAllTechnican();
        model.addAttribute("technicians", allTechnican);
        return "addbreakdown";
    }

    @PostMapping("/addbreakdown")
    public String addbreakdown(@ModelAttribute BreakdownDto breakdownDto) {
        breakdownService.saveBreakdown(breakdownDto);
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
        List<TodoDto> all = todoService.findAllTodo();
        List<TechnicianDto> technicianDtoList = technicianService.findAllTechnican();
        model.addAttribute("technicians", technicianDtoList);
        model.addAttribute("todos", all);
        return "todo";
    }

    @PostMapping("/todo")
    public String addTask(@ModelAttribute TodoDto todoDto, Model model) {
        todoService.saveToDo(todoDto);
        List<TodoDto> all = todoService.findAllTodo();
        List<TechnicianDto> technicianDtoList = technicianService.findAllTechnican();
        model.addAttribute("technicians", technicianDtoList);
        model.addAttribute("todos", all);
        return "todo";
    }

    @PostMapping("/deletetask")
    public String deleteTask(@RequestParam Long taskId) {
        todoService.deleteTask(taskId);
        return "redirect:/todo";
    }

    @GetMapping("/stats")
    public String stats(Model model) {
        List<TechnicianDto> all = technicianService.findAllTechnican();
        model.addAttribute("technicians", all);
        return "stats";
    }

    @PostMapping("/technician")
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
        List<TodoDto> tasks = todoService.findAllTodo();
        model.addAttribute("todos", tasks);
        model.addAttribute("breakdowns", all);
        model.addAttribute("breakdownStart", breakdownStart);
        model.addAttribute("breakdownEnd", breakdownEnd);
        model.addAttribute("computerNamesWithCounts", countComputerName);
        model.addAttribute("findFailureNameWithCounts", findFailureNameWithCounts);
        return "index";
    }
}