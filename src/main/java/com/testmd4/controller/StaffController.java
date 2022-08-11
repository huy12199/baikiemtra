package com.testmd4.controller;

import com.testmd4.model.Branch;
import com.testmd4.model.Staff;
import com.testmd4.service.BranchService;
import com.testmd4.service.StaffService;
import com.testmd4.validate.StaffValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/staff")
public class StaffController {
   @Autowired
   private StaffService staffService;
    @Autowired
    private BranchService branchService;
    @Autowired
    private StaffValidate staffValidate;

    @ModelAttribute(name = "branch")
    public List<Branch> branches() {
        return branchService.getAll();
    }

    @ModelAttribute(name = "staff")
    public Staff staff() {
        return new Staff();
    }

    @GetMapping("")
    public ModelAndView index(@RequestParam(defaultValue = "0") int page) {
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("staffs", staffService.getAll(PageRequest.of(page, 10, Sort.by("staffAge"))));
        return modelAndView;
    }

    @GetMapping("/create")
    public String showCreate() {
        return "create";
    }

    @PostMapping("/create")
    public ModelAndView create(@Valid @ModelAttribute("staff") Staff staff, BindingResult bindingResult) {
        staffValidate.validate(staff, bindingResult);
        if (bindingResult.hasFieldErrors()) {
            ModelAndView modelAndView = new ModelAndView("/create");
            return modelAndView;
        }
        staffService.save(staff);
        ModelAndView modelAndView = new ModelAndView("redirect:/staff");
        return modelAndView;
    }
    @GetMapping("/{id}/view")
    public ModelAndView viewStaff(@PathVariable int id){
        ModelAndView modelAndView = new ModelAndView("view");
        modelAndView.addObject("staffView",staffService.findById(id));
        return modelAndView;
    }
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable int id, Model model) {
        model.addAttribute("staffEdit", staffService.findById(id));
        return "/edit";
    }
    @PostMapping("/update")
    public ModelAndView update(@Valid @ModelAttribute("staffEdit") Staff staff, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            ModelAndView modelAndView = new ModelAndView("/edit");
            modelAndView.addObject("staffEdit", staff);
            return modelAndView;
        }
        staffService.save(staff);
        ModelAndView modelAndView = new ModelAndView("redirect:/staff");
        return modelAndView;
    }
    @GetMapping("/{id}/delete")
    public String delete(@PathVariable int id, Model model) {
        model.addAttribute("staffDelete", staffService.findById(id));
        return "delete";
    }
    @PostMapping("/delete")
    public String delete( @RequestParam ("idStaff") long idStaff, RedirectAttributes redirect) {

        staffService.delete(idStaff);
        redirect.addFlashAttribute("success", "Removed staff successfully!");
        return "redirect:/staff";
    }
    @PostMapping("/search")
    public String search(@RequestParam(value = "search", required = false ) String search, Model model) {
        List<Staff> staffs = staffService.findByStaffNameContaining(search);
        model.addAttribute("staffs", staffs);
        return "index";
    }
}
