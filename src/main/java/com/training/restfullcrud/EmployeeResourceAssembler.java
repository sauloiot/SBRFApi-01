package com.training.restfullcrud;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;


import static org.springframework.hateoas.server.mvc.ControllerLinkBuilder.*;


@Component
class EmployeeResourceAssembler implements RepresentationModelAssembler<Employee, EntityModel<Employee>> {
    public EmployeeResourceAssembler() {
    }

    @Override
    public EntityModel<Employee> toModel(Employee employee) {

        return new EntityModel<>(employee,
                linkTo(methodOn(EmployeeController.class).one(employee.getId())).withSelfRel(),
                linkTo(methodOn(EmployeeController.class).all()).withRel("employees"));
    }
}