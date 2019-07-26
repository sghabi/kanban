package com.sghabi.kanban.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.sghabi.kanban.domain.Project;
import com.sghabi.kanban.services.ProjectService;
import com.sghabi.kanban.services.ValidationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ValidationService validationService;

    @PostMapping
    public ResponseEntity<?> createNewProject(@Valid @RequestBody Project project, BindingResult result) {
        ResponseEntity<?> errorMap = validationService.ValidateRequest(result);
        if (errorMap != null)
            return errorMap;

        projectService.saveOrUpdateProject(project);
        return new ResponseEntity<Project>(project, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> updateProject(@Valid @RequestBody Project project, BindingResult result) {
        ResponseEntity<?> errorMap = validationService.ValidateRequest(result);
        if (errorMap != null)
            return errorMap;

        projectService.saveOrUpdateProject(project);
        return new ResponseEntity<Project>(project, HttpStatus.OK);
    }

    @GetMapping("/{projectIdentifier}")
    public ResponseEntity<?> getProjectByIdentifier(@PathVariable String projectIdentifier) {
        Project project = this.projectService.findProjectByIdentifier(projectIdentifier.toUpperCase());
        return new ResponseEntity<Project>(project, HttpStatus.OK);
    }

    @GetMapping
    public Iterable<Project> getAllProjects() {
        return this.projectService.findAllProjects();
    }

    @DeleteMapping("/{projectIdentifier}")
    public ResponseEntity<?> deleteProject(@PathVariable String projectIdentifier) {
        projectService.deleteProjectByIdentifier(projectIdentifier);
        return new ResponseEntity<String>("Project with ID: '" + projectIdentifier + "' successfully deleted",
                HttpStatus.OK);

    }
}