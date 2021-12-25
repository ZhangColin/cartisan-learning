package com.geektime.springbucks.repository;


import com.geektime.springbucks.model.Coffee;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author colin
 */
public interface CoffeeRepository extends JpaRepository<Coffee, Long> {
}
