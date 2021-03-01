//

import com.hang.mapper.BgmDao;
import com.hang.mapper.UsersDao;
import com.hang.mapper.UsersReportDao;
import com.hang.pojo.Bgm;
import com.hang.pojo.Users;
import com.hang.pojo.vo.Reports;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class testMybatis {

    private InputStream in=null;
    private SqlSession session =null;

    @Before
    public void getSession() throws IOException {
            in = Resources.getResourceAsStream("mybatis/mybatis-config.xml");
            SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
            SqlSessionFactory factory = builder.build(in);
            session = factory.openSession(true);
    }

    @After
    public void close() throws IOException {
        in.close();
        session.close();
    }
    @Test
    public void testQuery(){
        UsersDao mapper = session.getMapper(UsersDao.class);
        String username="%test%";
        String nickname="%test%";
        List<Users> users = mapper.usersList(username, nickname);
        System.out.println(users.size());
    }
    @Test
    public void testInsert(){
        BgmDao bgmDao=session.getMapper(BgmDao.class);
        Bgm bgm=new Bgm();
        bgm.setAuthor("asd");
        bgm.setId("11");
        bgm.setPath("asd");
        bgm.setName("asd");
        System.out.println(bgmDao.insert(bgm));
    }

    @Test
    public void queryList(){
        BgmDao bgmDao=session.getMapper(BgmDao.class);
        List<Bgm> bgms = bgmDao.selectAll();
        System.out.println(bgms.size());
    }
    @Test
    public void test(){
        UsersReportDao usersReportDao=session.getMapper(UsersReportDao.class);
        List<Reports> reports = usersReportDao.selectAllVideoReport();
        System.out.println(reports.size());
    }
}
