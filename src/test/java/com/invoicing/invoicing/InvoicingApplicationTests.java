package com.invoicing.invoicing;

import com.invoicing.invoicing.controller.ProjectController;
import com.invoicing.invoicing.model.Project;
import com.invoicing.invoicing.model.Task;
import com.invoicing.invoicing.repository.TaskRepository;
import com.sun.xml.bind.v2.runtime.output.SAXOutput;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class InvoicingApplicationTests {

	@Test
	void contextLoads() throws Exception {
	}
/*
	@Test
	void createProject(){

		//  Arrange
		ProjectController controller = new ProjectController();

		// Act
		Project project = new Project("Unit testing",
				"UNIT TESTING is a level of software testing where individual units/ components of a software are tested.");
		Project response = controller.createProject(project);
		System.out.println(project.toString());
		System.out.println(response.toString());
		assertEquals(project, response);
	}*/

}
