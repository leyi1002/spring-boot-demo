import com.jay.boot.Application;
import com.jay.boot.bean.Zoo;
import com.jay.boot.dao.read.ZooReadDao;
import com.jay.boot.dao.write.ZooWriteDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by Administrator on 2018/3/2.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class ApplicationTest {

    @Autowired
    private ZooReadDao zooReadDao;
    @Autowired
    private ZooWriteDao zooWriteDao;

    @Test
    public void est(){
        Zoo zoo = new Zoo();
        zoo.setPlace("Beijing");
        zoo.setAnimalsCount(30);
        Integer insert = zooWriteDao.insert(zoo);
        Zoo beijing = zooReadDao.findByName("ShangHai");
        System.out.println("insert:"+insert);
        System.out.println("select:"+beijing);
    }

    @Test
    public void factory(){
        Zoo ShangHai = zooReadDao.findByName("ShangHai");
        System.out.println("select:"+ShangHai);
    }

}
