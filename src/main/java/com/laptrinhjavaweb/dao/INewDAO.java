package com.laptrinhjavaweb.dao;

import java.util.List;

import com.laptrinhjavaweb.model.NewModel;
import com.laptrinhjavaweb.paging.Pageble;

public interface INewDAO extends GenericDAO<NewModel> {
	NewModel findOne(Long id);

	List<NewModel> findByCategoryId(Long categoryId);

	Long save(NewModel newModel);
	void update(NewModel updateNew);
	void delete(long id);
	//List<NewModel> findAll(Integer offset, Integer limit, String sortName, String sortBy);
	List<NewModel> findAll(Pageble pageble);
	int getTotalItem();
}
