package com.training.restfullcrud.config;

import com.training.restfullcrud.model.enums.Status;
import com.training.restfullcrud.model.Employee;
import com.training.restfullcrud.model.Order;
import com.training.restfullcrud.repository.EmployeeRepository;
import com.training.restfullcrud.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;


@Configuration
@Slf4j
@Profile("test")
public class LoadDatabase {
    @Bean
    CommandLineRunner initDatabse(EmployeeRepository employeeRepository, OrderRepository orderRepository){
        return args -> {
            log.info("Preloading "+ employeeRepository.save(new Employee("Bilbo","Baggins", "burglar")));
            log.info("Preloading "+ employeeRepository.save(new Employee("Frodo","Baggins", "thief")));
            log.info("Preloading "+ employeeRepository.save(new Employee("Gandalf","the white", "wizard")));

            orderRepository.save(new Order("MacBook Pro", Status.COMPLETED));
            orderRepository.save(new Order("iPhone", Status.IN_PROGRESS));
            orderRepository.save(new Order("iPad", Status.IN_PROGRESS));

            orderRepository.findAll().forEach(order -> {
                log.info("Preloaded " + order);
            });


        };
    }
}
