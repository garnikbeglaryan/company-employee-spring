package com.example.companyemployeespring.controller;

import com.example.companyemployeespring.entity.Employee;
import com.example.companyemployeespring.repository.CompanyRepository;
import com.example.companyemployeespring.repository.EmployeeRepository;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;

@Controller
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private CompanyRepository companyRepository;

    @Value("${companyemployeespring.upload.path}")
    private String imagePath;

    @GetMapping("/employee")
    public String employee(ModelMap map) {
        List<Employee> allEmployee = employeeRepository.findAll();
        map.addAttribute("allEmployee", allEmployee);
        return "/employee";

    }


    @GetMapping("/addEmployee")
    public String addEmployeePage(ModelMap map) {
        map.addAttribute("companies", companyRepository.findAll());
        return "/saveEmployee";
    }

    @PostMapping("/addEmployee")
    public String addEmployee(@ModelAttribute Employee employee,
                              @RequestParam("picture") MultipartFile uploadedFile) throws IOException {

        if (!uploadedFile.isEmpty()) {
            String fileName = System.currentTimeMillis() + " " + uploadedFile.getOriginalFilename();
            File newFile = new File(imagePath + fileName);
            uploadedFile.transferTo(newFile);
            employee.setPic_url(fileName);
        }
        employeeRepository.save(employee);
        return "redirect:/employee";
    }

    @GetMapping("/getImage")
    public @ResponseBody byte[] getImage(@RequestParam("picture") String picture) throws IOException {
        InputStream inputStream = new FileInputStream(imagePath + picture);
        return IOUtils.toByteArray(inputStream);
    }

    @GetMapping("/deleteEmployee/{id}")
    public String deleteCompany(@PathVariable("id") int id) {
        employeeRepository.deleteById(id);
        return "redirect:/employee";
    }
}
