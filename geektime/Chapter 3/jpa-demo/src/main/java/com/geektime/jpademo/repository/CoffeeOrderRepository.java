package com.geektime.jpademo.repository;

import com.geektime.jpademo.model.Coffee;
import com.geektime.jpademo.model.CoffeeOrder;
import org.springframework.data.repository.CrudRepository;

/**
 * @author colin
 */
public interface CoffeeOrderRepository extends CrudRepository<CoffeeOrder, Long> {
}
