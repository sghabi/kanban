package com.sghabi.kanban.services;

import com.sghabi.kanban.domain.Project;
import com.sghabi.kanban.exceptions.ProjectIdException;
import com.sghabi.kanban.repositories.ProjectRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public Project saveOrUpdateProject(Project project) {
        try {
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            return projectRepository.save(project);
        } catch (Exception e) {
            throw new ProjectIdException(
                    "Project ID '" + project.getProjectIdentifier().toUpperCase() + "' is already used");
        }
    }

    public Project findProjectByIdentifier(String projectId) {
        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());
        if (project == null)
            throw new ProjectIdException("Project ID '" + projectId.toUpperCase() + "' doesn't exist");
        return projectRepository.findByProjectIdentifier(projectId);
    }

    public Iterable<Project> findAllProjects() {
        return projectRepository.findAll();
    }
}