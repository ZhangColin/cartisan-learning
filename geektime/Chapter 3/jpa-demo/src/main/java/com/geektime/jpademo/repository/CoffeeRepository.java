package com.geektime.jpademo.repository;

import com.geektime.jpademo.model.Coffee;
import org.springframework.data.repository.CrudRepository;

/**
 * @author colin
 */
public interface CoffeeRepository extends CrudRepository<Coffee, Long> {
}
