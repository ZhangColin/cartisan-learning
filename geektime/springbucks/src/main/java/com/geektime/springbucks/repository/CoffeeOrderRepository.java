package com.geektime.springbucks.repository;

import com.geektime.springbucks.model.CoffeeOrder;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author colin
 */
public interface CoffeeOrderRepository extends JpaRepository<CoffeeOrder, Long> {
}
