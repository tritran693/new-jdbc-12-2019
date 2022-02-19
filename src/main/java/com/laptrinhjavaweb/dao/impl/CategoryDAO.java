package com.laptrinhjavaweb.dao.impl;

import java.util.List;

import com.laptrinhjavaweb.dao.ICategoryDAO;
import com.laptrinhjavaweb.mapper.CategoryMapper;
import com.laptrinhjavaweb.mapper.NewMapper;
import com.laptrinhjavaweb.mapper.RowMapper;
import com.laptrinhjavaweb.model.CategoryModel;
import com.laptrinhjavaweb.model.NewModel;

public class CategoryDAO extends AbstractDAO<CategoryModel> implements ICategoryDAO {

		public List<CategoryModel> findAll(){
			String sql = "SELECT * FROM category";
			return query(sql, new CategoryMapper());
		}

		@Override
		public CategoryModel findOne(long id) {
			String sql = "SELECT * FROM category WHERE id = ? ";
			List<CategoryModel> category = query(sql, new CategoryMapper(), id);
			return category.isEmpty() ? null : category.get(0);
		}

		@Override
		public CategoryModel findOneByCode(String code) {
			String sql = "SELECT * FROM category WHERE code = ? ";
			List<CategoryModel> category = query(sql, new CategoryMapper(), code);
			return category.isEmpty() ? null : category.get(0);
		}
		
}
