package com.example.companyemployeespring.service;

import com.example.companyemployeespring.entity.Employee;
import com.example.companyemployeespring.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;


    @Value("${companyemployeespring.upload.path}")
    private String imagePath;


    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    public Employee save(Employee employee, MultipartFile uploadedFile) throws IOException {
        if (!uploadedFile.isEmpty()) {
            String fileName = System.currentTimeMillis() + " " + uploadedFile.getOriginalFilename();
            File newFile = new File(imagePath + fileName);
            uploadedFile.transferTo(newFile);
            employee.setPic_url(fileName);
        }
        return employeeRepository.save(employee);
    }

    public void deleteById(int id) {
        employeeRepository.deleteById(id);
    }
}
