package com.example.demo.controller;

import com.example.demo.model.Student;
import com.example.demo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/")
    public String showForm(Model model) {
        model.addAttribute("student", new Student());
        return "student_form";
    }

    @PostMapping("/saveStudent")
    public String saveStudent(@ModelAttribute Student student, Model model) {
        studentService.saveStudent(student);
        model.addAttribute("message", "Student details saved successfully!");
        model.addAttribute("student", new Student()); // Reset form
        return "student_form";
    }

    @GetMapping("/students")
    public String listStudents(Model model) {
        List<Student> students = studentService.getAllStudents();
        model.addAttribute("students", students);
        return "students_list";
    }

    @GetMapping("/filterStudents")
    public String filterStudents(@RequestParam(required = false) String batchNumber,
                                 @RequestParam(required = false) String course, Model model) {
        List<Student> students;
        if (batchNumber != null && !batchNumber.isEmpty()) {
            students = studentService.getStudentsByBatch(batchNumber);
        } else if (course != null && !course.isEmpty()) {
            students = studentService.getStudentsByCourse(course);
        } else {
            students = studentService.getAllStudents();
        }
        model.addAttribute("students", students);
        return "students_list";
    }
}
