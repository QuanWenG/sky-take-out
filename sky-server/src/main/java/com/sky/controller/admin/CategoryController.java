package com.sky.controller.admin;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/admin/category")
@Slf4j
public class CategoryController {

   @Autowired
   private CategoryService categoryService;

   @GetMapping("/page")
   public Result<PageResult> page(CategoryPageQueryDTO categoryPageQueryDTO){
      log.info("菜品分页查询:{}",categoryPageQueryDTO);
      PageResult pageResult = categoryService.pageQuery(categoryPageQueryDTO);
      return Result.success(pageResult);
   }

   @GetMapping("/list")
   public Result<List<Category>> list(Integer type){
      log.info("根据类型查询套餐:{}",type);
      List<Category> list = categoryService.listByType(type);
      return Result.success(list);
   }

   @PostMapping()
   public Result save(@RequestBody CategoryDTO categoryDTO){
      log.info("新增分类:{}",categoryDTO);
      categoryService.save(categoryDTO);
      return Result.success();
   }

   @PostMapping("/status/{status}")
   public Result OnOrOff(@PathVariable Integer status,Long id){
      log.info("启动禁用:{},{}",status,id);
      categoryService.OnOrOff(status,id);
      return Result.success();
   }

   @PutMapping
   public Result update(@RequestBody CategoryDTO categoryDTO){
      log.info("修改分类:{}",categoryDTO);
      categoryService.update(categoryDTO);
      return Result.success();
   }

   @DeleteMapping()
   public Result delete(Long id){
      log.info("根据id删除分类:{}",id);
      categoryService.delete(id);
      return Result.success();
   }



}
