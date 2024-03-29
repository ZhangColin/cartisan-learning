package com.geektime.jpacomplexdemo;

import com.geektime.jpacomplexdemo.model.Coffee;
import com.geektime.jpacomplexdemo.model.CoffeeOrder;
import com.geektime.jpacomplexdemo.model.OrderState;
import com.geektime.jpacomplexdemo.repository.CoffeeOrderRepository;
import com.geektime.jpacomplexdemo.repository.CoffeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author colin
 */
@SpringBootApplication
@EnableTransactionManagement
@EnableJpaRepositories
@Slf4j
public class JpaComplexDemoApplication implements ApplicationRunner {
    @Autowired
    private CoffeeRepository coffeeRepository;

    @Autowired
    private CoffeeOrderRepository coffeeOrderRepository;

    public static void main(String[] args) {
        SpringApplication.run(JpaComplexDemoApplication.class, args);
    }

    @Override
//    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        initOrders();
        findOrders();
    }

    private void initOrders() {
        final Coffee espresso = Coffee.builder().name("espresso")
                .price(Money.of(CurrencyUnit.of("CNY"), 20.0))
                .build();

        coffeeRepository.save(espresso);
        log.info("Coffee: {}", espresso);

        final Coffee latte = Coffee.builder().name("latte")
                .price(Money.of(CurrencyUnit.of("CNY"), 30.0))
                .build();

        coffeeRepository.save(latte);
        log.info("Coffee: {}", latte);

        CoffeeOrder order = CoffeeOrder.builder()
                .customer("colin")
                .coffees(Collections.singletonList(espresso))
                .state(OrderState.INIT)
                .build();
        coffeeOrderRepository.save(order);
        log.info("Order: {}", order);

        order = CoffeeOrder.builder()
                .customer("colin")
                .coffees(Arrays.asList(espresso, latte))
                .state(OrderState.INIT)
                .build();
        coffeeOrderRepository.save(order);
        log.info("Order: {}", order);
    }

    private void findOrders() {
        coffeeRepository.findAll(Sort.by(Sort.Direction.DESC, "id"))
                .forEach(c->log.info("Loading {}", c));

        List<CoffeeOrder> list = coffeeOrderRepository.findTop3ByOrderByUpdateTimeDescIdAsc();
        log.info("findTop3ByOrderByUpdateTimeDescIdAsc: {}", getJoinedOrderId(list));

        list = coffeeOrderRepository.findByCustomerOrderById("colin");
        log.info("findByCustomerOrderById: {}", getJoinedOrderId(list));

        // 不开启事务会因为没Session而报LazyInitializationException
        list.forEach(o->{
            log.info("Order {}", o.getId());
            o.getCoffees().forEach(c->log.info("    Coffee {}", c));
        });

        list = coffeeOrderRepository.findByCoffees_Name("latte");
        log.info("findByItems_Name: {}", getJoinedOrderId(list));
    }

    private String getJoinedOrderId(List<CoffeeOrder> list) {
        return list.stream().map(o -> o.getId().toString()).collect(Collectors.joining(","));
    }
}
