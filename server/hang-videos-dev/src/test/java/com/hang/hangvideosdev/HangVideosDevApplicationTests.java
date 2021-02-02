package com.hang.hangvideosdev;

import com.hang.hangvideosdev.mapper.BgmDao;
import com.hang.hangvideosdev.mapper.UsersDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.sql.DataSource;

@SpringBootTest
class HangVideosDevApplicationTests {

    @Autowired
    private UsersDao usersDao;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private BgmDao bgmDao;

    @Test
    void contextLoads() {
//        System.out.println(usersDao.selectUsername("test-"));
//        redisTemplate.opsForValue().set("USER_REDIS_SESSION" , "uniqueToken", 1000 * 60 * 30);
//        System.out.println(dataSource);
//        System.out.println(redisTemplate.getConnectionFactory().getConnection());
        System.out.println(bgmDao.list());
    }

}
