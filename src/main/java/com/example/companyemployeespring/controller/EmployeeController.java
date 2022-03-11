package com.example.companyemployeespring.controller;

import com.example.companyemployeespring.entity.Employee;
import com.example.companyemployeespring.service.CompanyService;
import com.example.companyemployeespring.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
@RequiredArgsConstructor
public class EmployeeController {


    private final EmployeeService employeeService;
    private final CompanyService companyService;


    @Value("${companyemployeespring.upload.path}")
    private String imagePath;

    @GetMapping("/employee")
    public String employee(ModelMap map) {
        map.addAttribute("allEmployee", employeeService.findAll());
        return "/employee";
    }


    @GetMapping("/addEmployee")
    public String addEmployeePage(ModelMap map) {
        map.addAttribute("companies", companyService.findAll());
        return "/saveEmployee";
    }

    @PostMapping("/addEmployee")
    public String addEmployee(@ModelAttribute Employee employee,
                              @RequestParam("picture") MultipartFile uploadedFile) throws IOException {
        employeeService.save(employee, uploadedFile);
        return "redirect:/employee";
    }

    @GetMapping("/getImage")
    public @ResponseBody byte[] getImage(@RequestParam("picture") String picture) throws IOException {
        InputStream inputStream = new FileInputStream(imagePath + picture);
        return IOUtils.toByteArray(inputStream);
    }

    @GetMapping("/deleteEmployee/{id}")
    public String deleteCompany(@PathVariable("id") int id) {
        employeeService.deleteById(id);
        return "redirect:/employee";
    }
}
