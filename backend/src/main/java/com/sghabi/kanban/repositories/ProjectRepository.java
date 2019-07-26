package com.sghabi.kanban.repositories;

import com.sghabi.kanban.domain.Project;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long> {
    Project findByProjectIdentifier(String projectIdentifier);

    @Override
    Iterable<Project> findAll();
}