package com.geektime.springbucks.service;

import com.geektime.springbucks.model.Coffee;
import com.geektime.springbucks.model.CoffeeOrder;
import com.geektime.springbucks.model.OrderState;
import com.geektime.springbucks.repository.CoffeeOrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author colin
 */
@Slf4j
@Service
@Transactional
public class CoffeeOrderService {
    private CoffeeOrderRepository repository;

    public CoffeeOrderService(CoffeeOrderRepository repository) {
        this.repository = repository;
    }

    public CoffeeOrder createOrder(String customer, Coffee... coffees) {
        final CoffeeOrder order = CoffeeOrder.builder().customer(customer)
                .coffees(new ArrayList<>(Arrays.asList(coffees)))
                .state(OrderState.INIT)
                .build();

        final CoffeeOrder saved = repository.save(order);

        log.info("New Order: {}", saved);

        return saved;
    }

    public boolean updateState(CoffeeOrder order, OrderState state) {
        if (state.compareTo(order.getState()) <= 0) {
            log.warn("Wrong State order: {}, {}", state, order.getState());
            return false;
        }

        order.setState(state);

        repository.save(order);
        log.info("Updated Order: {}", order);

        return true;
    }
}
