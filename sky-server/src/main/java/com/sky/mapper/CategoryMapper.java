package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CategoryMapper {

    @Delete("delete from category where id = #{id}")
    void delete(Long id);

    Page<Category> pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);

    /**
     * 根据分类查询
     * @param type
     * @return
     */
    @Select("select * from category where type = #{type}")
    List<Category> listByType(Integer type);

    @Insert("insert into category(type, name, sort, status, create_time, update_time, create_user, update_user) " +
            "VALUES (#{type},#{name},#{sort},#{status},#{createTime},#{updateTime},#{createUser},#{updateUser}) ")
    @AutoFill(value = OperationType.INSERT)
    void save(Category category);

    @AutoFill(value = OperationType.UPDATE)
    void update(Category category);
}
