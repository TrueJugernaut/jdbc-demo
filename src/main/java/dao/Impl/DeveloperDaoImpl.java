package dao.Impl;

import model.Company;
import model.Project;
import util.ConnectionFactory;
import dao.DeveloperDao;
import model.Developer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

public class DeveloperDaoImpl implements DeveloperDao {

    @Override
    public Developer findById(Long id) {
        try (Connection connection = ConnectionFactory.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM developer WHERE id=" + id);
            if (resultSet.next()) {
                return extractDeveloperFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            System.out.println("Something wrong with util");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Set findAll() {
        try (Connection connection = ConnectionFactory.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM developer");

            Set<Developer> developers = new HashSet<>();

            while (resultSet.next()) {
                Developer developer = extractDeveloperFromResultSet(resultSet);
                developers.add(developer);
            }
            return developers;
        } catch (SQLException e) {
            System.out.println("Something wrong with util");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void insert(Developer dev) {
        String req = "INSERT into developers.developer VALUES (NULL, ?, ?, ?, ?, ?, ?, ?)";
        insertUpdate(dev, req);
    }

    @Override
    public void update(Developer dev) {
        String req = "UPDATE developers.developer SET age=?, firstName=?, lastName=?, sex=?, salary=?, company=?, project=? WHERE id=?";
        insertUpdate(dev, req);
    }

    @Override
    public void deleteById(Long id) {
        try (Connection connection = ConnectionFactory.getConnection()) {
            Statement statement = connection.createStatement();
            int i = statement.executeUpdate("DELETE FROM developer WHERE id=" + id);

            if (i == 1) {
                System.out.println("Developer with id " + id + " deleted");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Developer extractDeveloperFromResultSet(ResultSet rs) throws SQLException {
        Developer developer = new Developer();

        developer.setId(rs.getLong("id"));
        developer.setAge(rs.getInt("age"));
        developer.setFirstName(rs.getString("firstName"));
        developer.setLastName(rs.getString("lastName"));
        developer.setSalary(rs.getDouble("salary"));
        developer.setCompanies((Set<Company>) rs.getObject("company"));
        developer.setProjects((Set<Project>) rs.getObject("project"));
        developer.setSex(rs.getString("sex"));
        return developer;
    }

    private void insertUpdate(Developer dev, String req) {
        try (Connection connection = ConnectionFactory.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(req);
            preparedStatement.setInt(1, dev.getAge());
            preparedStatement.setString(2, dev.getFirstName());
            preparedStatement.setString(3, dev.getLastName());
            preparedStatement.setString(4, dev.getSex());
            preparedStatement.setDouble(5, dev.getSalary());
            int i = preparedStatement.executeUpdate();

            if (i == 1) {
                System.out.println("Developer updated/created");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
