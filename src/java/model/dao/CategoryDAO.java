package model.dao;
//metodi che ci servono dopo aver fatto la progettazione

import model.dao.exception.DuplicatedObjectException;
import model.mo.Category;

import java.util.List;

public interface CategoryDAO {

    Category insert(
            String name,
            String description
    ) throws DuplicatedObjectException;

    boolean update(Category category, String field, String value);

    void delete(Category category);

    Category findByCategoryId(Long id);
    List<Category> find(String field, String value);

    List<Category> allCategory();

}
