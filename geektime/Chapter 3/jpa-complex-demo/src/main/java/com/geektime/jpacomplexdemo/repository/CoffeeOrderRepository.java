package com.geektime.jpacomplexdemo.repository;


import com.geektime.jpacomplexdemo.model.CoffeeOrder;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author colin
 */
public interface CoffeeOrderRepository extends BaseRepository<CoffeeOrder, Long> {
    List<CoffeeOrder> findByCustomerOrderById(String customer);
    List<CoffeeOrder> findByCoffees_Name(String name);
}
