package com.geektime.mybatisdemo.mapper;

import com.geektime.mybatisdemo.model.Coffee;
import org.apache.ibatis.annotations.*;

/**
 * @author colin
 */
@Mapper
public interface CoffeeMapper {
    @Insert("insert into t_coffees(name, price, create_time, update_time) " +
            "values(#{name}, #{price}, now(), now())")
    @Options(useGeneratedKeys = true)
    int save(Coffee coffee);

    @Select("select * from t_coffees where id = #{id}")
    @Results({
            @Result(id=true, column = "id", property = "id"),
            @Result(column = "create_time", property="createTime"),
            // map-underscore-to-camel-case = true 可以实现一样的效果
            // @Result(column = "update_time", property="updateTime")
    })
    Coffee findById(@Param("id")Long id);
}
