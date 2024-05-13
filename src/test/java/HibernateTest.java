import org.example.entity.User;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.junit.Test;

public class HibernateTest {
    @Test
    public void test() {
        Session session = HibernateUtil.getSession();
        User user = new User();
        user.setName("jiang");
        user.setPassword("123456");
        try {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            System.out.println("save failed");
            e.printStackTrace();
        } finally{
            HibernateUtil.closeSession();
        }
    }
}
