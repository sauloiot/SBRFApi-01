package com.training.restfullcrud.controller;

import com.training.restfullcrud.exception.EmployeeNotFoundException;
import com.training.restfullcrud.repository.EmployeeRepository;
import com.training.restfullcrud.resource.EmployeeResourceAssembler;
import com.training.restfullcrud.model.Employee;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class EmployeeController {
    private final EmployeeRepository repository;

    private final EmployeeResourceAssembler assembler;

    public EmployeeController(EmployeeRepository repository, EmployeeResourceAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    // Aggregate root

    //get, get all employees
    @GetMapping("/employees")
    public CollectionModel<EntityModel<Employee>> all() {
        System.out.println("HTTP GET: ALl employees requesteds");

        List<EntityModel<Employee>> employees = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return new CollectionModel<>(employees,
                linkTo(methodOn(EmployeeController.class).all()).withSelfRel());
    }


    //post, create a new employee on database
    @PostMapping("/employees")
    public ResponseEntity<?> newEmployee(@RequestBody Employee newEmployee) throws URISyntaxException {

        EntityModel<Employee> resource = assembler.toModel(repository.save(newEmployee));
        System.out.println("HTTP POST: a employee: "+ newEmployee + " created");

        return ResponseEntity
                .created(new URI(resource.getLink("employees").get().getHref()))
                .body(resource);
    }

    // Single item

    //get, get employee by id
    @GetMapping("/employees/{id}")
    public EntityModel one(@PathVariable Long id) {

        System.out.println("HTTP GET: the employee: "+ repository.findById(id) +" requested");

        Employee employee = repository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));

        return assembler.toModel(employee);
    }

    //put, update a employee by id
    @PutMapping("/employees/{id}")
    public ResponseEntity<?> replaceEmployee(@RequestBody Employee newEmployee, @PathVariable Long id) throws URISyntaxException {

        Employee updatedEmployee = repository.findById(id)
                .map(employee -> {
                    employee.setName(newEmployee.getName());
                    employee.setRole(newEmployee.getRole());
                    return repository.save(employee);
                })
                .orElseGet(() -> {
                    newEmployee.setId(id);
                    return repository.save(newEmployee);
                });

        EntityModel<Employee> resource = assembler.toModel(updatedEmployee);

        return ResponseEntity
                .created(new URI(resource.getLink("employees").get().getHref()))
                .body(resource);
    }

    //delete, delete a employee by id
    @DeleteMapping("/employees/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
        System.out.println("HTTP DELETE: " + repository.findById(id)+ " deleted");

        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }








}
