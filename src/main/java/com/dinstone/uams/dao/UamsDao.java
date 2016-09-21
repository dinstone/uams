
package com.dinstone.uams.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import com.dinstone.uams.model.LocalAccount;
import com.dinstone.uams.model.UserProfile;

@Service
public class UamsDao {

    @Resource
    private JdbcTemplate jdbcTemplate;

    public int insertUserProfile(final UserProfile profile) {
        final String sql = "insert into tb_user_profile (nickname,email,create_time,update_time) values(?,?,?,?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(new PreparedStatementCreator() {

            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                ps.setString(1, profile.getNickname());
                ps.setString(2, profile.getEmail());
                ps.setTimestamp(3, new Timestamp(profile.getCreateTime().getTime()));
                ps.setTimestamp(4, new Timestamp(profile.getUpdateTime().getTime()));
                return ps;
            }
        }, keyHolder);

        return keyHolder.getKey().intValue();
    }

    public void insertUserAccount(final int userId, final LocalAccount account) {
        String sql = "insert into tb_local_account (username,password,user_id,status,create_time,update_time) values (?,?,?,?,?,?)";
        jdbcTemplate.update(
            sql,
            new Object[] { account.getUsername(), account.getPassword(), userId, 1, account.getCreateTime(),
                    account.getUpdateTime() });
    }

    public LocalAccount findLocalAccount(String username) {
        String sql = "select * from tb_local_account where username=?";
        List<LocalAccount> uacs = jdbcTemplate.query(sql, new Object[] { username },
            BeanPropertyRowMapper.newInstance(LocalAccount.class));
        if (uacs != null && uacs.size() > 0) {
            return uacs.get(0);
        }
        return null;
    }

    public UserProfile findUserProfile(int userId) {
        String sql = "select * from tb_user_profile where id=?";
        List<UserProfile> uacs = jdbcTemplate.query(sql, new Object[] { userId },
            BeanPropertyRowMapper.newInstance(UserProfile.class));
        if (uacs != null && uacs.size() > 0) {
            return uacs.get(0);
        }
        return null;
    }
}
