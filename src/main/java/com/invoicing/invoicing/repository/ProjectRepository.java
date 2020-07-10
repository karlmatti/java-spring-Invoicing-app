package com.invoicing.invoicing.repository;

import com.invoicing.invoicing.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@Component
public interface ProjectRepository extends JpaRepository<Project, Long> {
}
