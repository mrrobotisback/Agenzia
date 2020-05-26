package model.dao.mySQLJDBCImpl;

import model.dao.exception.DuplicatedObjectException;
import model.mo.Category;
import model.dao.CategoryDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CategoryDAOMySQLJDBCImpl implements CategoryDAO {

    Connection conn;

    public CategoryDAOMySQLJDBCImpl(Connection conn) {
        this.conn = conn;
    }

    public Category insert(
            String name,
            String description
    ) throws DuplicatedObjectException {

        PreparedStatement ps;
        Category category = new Category();
        category.setName(name);
        category.setDescription(description);

        try {

            String sql
                    = " SELECT name "
                    + " FROM category "
                    + " WHERE "
                    + " name = ?";

            ps = conn.prepareStatement(sql);
            int i = 1;
            ps.setString(i++, category.getName());

            ResultSet resultSet = ps.executeQuery(); //eseguo la query appena creata
            boolean exist;
            exist = resultSet.next();
            resultSet.close();

            if (exist) {
                throw new DuplicatedObjectException("categoryDAOJDBCImpl.create: Tentativo di inserimento di una categoria già esistente.");
            }

            sql
                    = " INSERT INTO category "
                    + "   ( "
                    + "     name,"
                    + "     description"
                    + "   ) "
                    + " VALUES (?,?)";


            ps = conn.prepareStatement(sql);
            i = 1;
            ps.setString(i++, category.getName());
            ps.setString(i++, category.getDescription());


            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return category;userId

    }

    @Override
    public boolean update(Category category, String field, String value) {
        PreparedStatement ps;

        try {
            String sql
                    = "UPDATE category "
                    + "SET " + field + " = ?"
                    + "WHERE id = ?";


            ps = conn.prepareStatement(sql);
            int i = 1;
            ps.setString(i++, value);
            ps.setLong(i++, category.getCategoryId());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public void delete(Category category) {

        PreparedStatement ps;

        try {
            String sql
                    = " DELETE"
                    + " FROM category"
                    + " WHERE "
                    + " id=?";

            ps = conn.prepareStatement(sql);
            if (category.getCategoryId() != null) {
                ps.setLong(1, category.getCategoryId());
                ps.executeUpdate();
                ps.close();
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Category findByCategoryId(Long id) {

        PreparedStatement ps;
        Category category = null;

        try {

            String sql
                    = " SELECT * "
                    + "   FROM category "
                    + " WHERE "
                    + "   id = ?";

            ps = conn.prepareStatement(sql);
            ps.setLong(1, id);

            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                category = read(resultSet);
            }
            resultSet.close();
            ps.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return category;

    }

    @Override
    public List<Category> find (String field, String value) {
        PreparedStatement ps;
        Category category;
        ArrayList<Category> categories = new ArrayList<Category>();

        try {

            String sql
                    = " SELECT *"
                    + "   FROM category "
                    + " WHERE "
                    + " " + field + " like " + "'%" + value + "%'";

            ps = conn.prepareStatement(sql);

            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                category = read(resultSet);
                categories.add(category);
            }

            resultSet.close();
            ps.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return categories;
    }

    public List<Category> allCategory() {
        PreparedStatement ps;
        Category category;
        ArrayList<Category> categories= new ArrayList<Category>();

        try {

            String sql
                    = " SELECT * "
                    + "   FROM category ";

            ps = conn.prepareStatement(sql);

            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                category = read(resultSet);
                categories.add(category);
            }

            resultSet.close();
            ps.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return categories;
    }

    Category read(ResultSet rs) {

        Category category = new Category();
        try {
            category.setName(rs.getString("name"));
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        try {
            category.setDescription(rs.getString("description"));
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return category;
    }

}
