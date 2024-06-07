package org.example.pnubookstore.domain.product.controller;

import lombok.RequiredArgsConstructor;
import org.example.pnubookstore.domain.product.service.SubjectService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SubjectRestController {

    private final SubjectService subjectService;

    @GetMapping(value = "api/colleges")
    public List<String> loadColleges(){
        return subjectService.findColleges();
    }

    @GetMapping(value = "api/departments")
    public List<String> loadDepartments(@RequestParam(value="college") String college){
        return subjectService.findDepartment(college);
    }

    @GetMapping(value = "api/professors")
    public List<String> loadProfessors(
            @RequestParam(value="college") String college,
            @RequestParam(value="department") String department){
        return subjectService.findProfessors(college, department);
    }

    @GetMapping(value = "api/subjects")
    public List<String> loadSubjects(
            @RequestParam(value="college") String college,
            @RequestParam(value="department") String department,
            @RequestParam(value="professor") String professor){
        return subjectService.findSubjectNames(college, department, professor);
    }
}
