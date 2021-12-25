package com.geektime.springbucks.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @author colin
 */
@Entity
@Table(name = "t_orders")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class CoffeeOrder extends BaseEntity implements Serializable {
    private String customer;

    @ManyToMany
    @JoinTable(name="t_order_coffees")
    @OrderBy("id")
    private List<Coffee> coffees;

    @Column(nullable = false)
    @Enumerated
    private OrderState state;
}
