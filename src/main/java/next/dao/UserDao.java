package next.dao;

import next.model.User;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by sungjuho on 2017. 4. 2..
 */
public interface UserDao {
    void insert(User user);

    User findByUserId(String userId);

    List<User> findAll() throws SQLException;

    void update(User user);
}
