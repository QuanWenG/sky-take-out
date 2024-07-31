package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.entity.Employee;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.CategoryMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private SetmealMapper setmealMapper;

    @Override
    public PageResult pageQuery(CategoryPageQueryDTO categoryPageQueryDTO) {
        PageHelper.startPage(categoryPageQueryDTO.getPage(),categoryPageQueryDTO.getPageSize());
        Page<Category> page= categoryMapper.pageQuery(categoryPageQueryDTO);

        long total = page.getTotal();
        List<Category> records = page.getResult();
        return new PageResult(total,records);
    }

    /**
     * 根据分类查询
     * @param type
     * @return
     */
    @Override
    public List<Category> listByType(Integer type) {
        List<Category> list = categoryMapper.listByType(type);
        return list;
    }

    @Override
    public void save(CategoryDTO categoryDTO) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO,category);
//
        category.setStatus(StatusConstant.ENABLE);
        categoryMapper.save(category);
    }

    @Override
    public void OnOrOff(Integer status,Long id) {
        Category category = new Category();
        category.setId(id);
        category.setStatus(status);
//        category.setUpdateUser(BaseContext.getCurrentId());
//        category.setUpdateTime(LocalDateTime.now());
        categoryMapper.update(category);
    }

    @Override
    public void update(CategoryDTO categoryDTO) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO,category);
//        category.setUpdateTime(LocalDateTime.now());
//        category.setUpdateUser(BaseContext.getCurrentId());
        categoryMapper.update(category);
    }

    @Override
    public void delete(Long id) {
        Long count = dishMapper.countById(id);
        if (count > 0){
            throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
        }

        count = setmealMapper.countById(id);
        if (count > 0){
            throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
        }

        categoryMapper.delete(id);
    }

}

