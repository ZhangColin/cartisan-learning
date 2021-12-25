package com.geektime.springbucks;

import com.geektime.springbucks.model.Coffee;
import com.geektime.springbucks.model.CoffeeOrder;
import com.geektime.springbucks.model.OrderState;
import com.geektime.springbucks.repository.CoffeeRepository;
import com.geektime.springbucks.service.CoffeeOrderService;
import com.geektime.springbucks.service.CoffeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Optional;

/**
 * @author colin
 */
@Slf4j
@EnableTransactionManagement
@EnableJpaRepositories
@SpringBootApplication
public class SpringBucksApplication implements ApplicationRunner {
    @Autowired
    private CoffeeOrderService orderService;

    @Autowired
    private CoffeeService coffeeService;

    @Autowired
    private CoffeeRepository coffeeRepository;

    public static void main(String[] args) {
        SpringApplication.run(SpringBucksApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("All Coffee: {}", coffeeRepository.findAll());

        final Optional<Coffee> latte = coffeeService.findOneCoffee("Latte");

        if (latte.isPresent()) {
            final CoffeeOrder order = orderService.createOrder("colin", latte.get());
            log.info("Update INIT TO PAID: {}", orderService.updateState(order, OrderState.PAID));
            log.info("Update PAID TO INIT: {}", orderService.updateState(order, OrderState.INIT));
        }
    }
}
