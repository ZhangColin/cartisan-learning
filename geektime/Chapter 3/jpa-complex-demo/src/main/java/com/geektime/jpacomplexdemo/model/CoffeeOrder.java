package com.geektime.jpacomplexdemo.model;

import com.geektime.jpacomplexdemo.model.Coffee;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
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
public class CoffeeOrder extends BaseEntity implements Serializable {
    private String customer;

    @ManyToMany
    @JoinTable(name="t_order_coffees")
    private List<Coffee> coffees;

    @Column(nullable = false)
    @Enumerated
    private OrderState state;
}
