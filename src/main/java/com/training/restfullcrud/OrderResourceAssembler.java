package com.training.restfullcrud;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.ControllerLinkBuilder.methodOn;


@Component
class OrderResourceAssembler implements RepresentationModelAssembler<Order, EntityModel<Order>> {
    public OrderResourceAssembler() {
    }

    @Override
    public EntityModel<Order> toModel(Order order) {

        return new EntityModel<>(order,
                linkTo(methodOn(EmployeeController.class).one(order.getId())).withSelfRel(),
                linkTo(methodOn(EmployeeController.class).all()).withRel("employees"));
    }
}