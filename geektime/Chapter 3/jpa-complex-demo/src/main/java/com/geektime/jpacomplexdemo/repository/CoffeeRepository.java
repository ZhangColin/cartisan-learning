package com.geektime.jpacomplexdemo.repository;


import com.geektime.jpacomplexdemo.model.Coffee;
import org.springframework.data.repository.CrudRepository;

/**
 * @author colin
 */
public interface CoffeeRepository extends BaseRepository<Coffee, Long> {
}
