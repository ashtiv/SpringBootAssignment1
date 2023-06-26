package com.example.SpringBootAssignment1.Service;

import com.example.SpringBootAssignment1.Repository.MyUser;
import com.example.SpringBootAssignment1.Repository.UserSearchCriteria;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final JdbcTemplate jdbcTemplate;

    public UserServiceImpl(JdbcTemplate jdbcTemplate) {

        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<MyUser> getAllUsers() {
        String sql = "SELECT * FROM my_user";
        return jdbcTemplate.query(sql, new MyUserRowMapper());
    }
    @Override
    public MyUser createUser(MyUser myUser) {
        String sql = "INSERT INTO my_user (name, gender, mobile_number, address) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, myUser.getName());
            ps.setString(2, myUser.getGender());
            ps.setString(3, myUser.getMobileNumber());
            ps.setString(4, myUser.getAddress());
            return ps;
        }, keyHolder);

        myUser.setId(keyHolder.getKey().longValue());
        return myUser;
    }
    @Override
    public List<MyUser> searchUsers(UserSearchCriteria criteria) {
        String sql = "SELECT * FROM my_user WHERE 1=1";
        List<Object> args = new ArrayList<>();

        if (criteria.getId() != null) {
            sql += " AND id = ?";
            args.add(criteria.getId());
        }

        if (criteria.getMobileNumber() != null) {
            sql += " AND mobile_number = ?";
            args.add(criteria.getMobileNumber());
        }

        return jdbcTemplate.query(sql, args.toArray(), new BeanPropertyRowMapper<>(MyUser.class));
    }

    @Override
    public MyUser updateUser(Long id, MyUser myUser) {
        String sql = "UPDATE my_user SET name=?, gender=?, mobile_number=?, address=? WHERE id=?";
        jdbcTemplate.update(sql, myUser.getName(), myUser.getGender(), myUser.getMobileNumber(), myUser.getAddress(), id);
        myUser.setId(id);
        return myUser;
    }

    @Override
    public void deleteUser(Long id) {
        String sql = "DELETE FROM my_user WHERE id=?";
        jdbcTemplate.update(sql, id);
    }
    private static class MyUserRowMapper implements RowMapper<MyUser> {
        @Override
        public MyUser mapRow(ResultSet rs, int rowNum) throws SQLException {
            MyUser myUser = new MyUser();
            myUser.setId(rs.getLong("id"));
            myUser.setName(rs.getString("name"));
            myUser.setGender(rs.getString("gender"));
            myUser.setMobileNumber(rs.getString("mobile_number"));
            myUser.setAddress(rs.getString("address"));
            return myUser;
        }
    }
}
