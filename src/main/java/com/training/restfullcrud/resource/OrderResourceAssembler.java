package com.training.restfullcrud.resource;

import com.training.restfullcrud.controller.OrderController;
import com.training.restfullcrud.model.Order;
import com.training.restfullcrud.model.enums.Status;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.ControllerLinkBuilder.methodOn;


@Component
public class OrderResourceAssembler implements RepresentationModelAssembler<Order, EntityModel<Order>> {
    public OrderResourceAssembler() {
    }

    @Override
    public EntityModel<Order> toModel(Order order) {

        // Unconditional links to single-item resource and aggregate root

        EntityModel<Order> orderResource = new EntityModel<>(order,
                linkTo(methodOn(OrderController.class).one(order.getId())).withSelfRel(),
                linkTo(methodOn(OrderController.class).all()).withRel("orders")
        );

        // Conditional links based on state of the order

        if (order.getStatus() == Status.IN_PROGRESS) {
            orderResource.add(
                    linkTo(methodOn(OrderController.class)
                            .cancel(order.getId())).withRel("cancel"));
            orderResource.add(
                    linkTo(methodOn(OrderController.class)
                            .complete(order.getId())).withRel("complete"));
        }

        return orderResource;
    }
}