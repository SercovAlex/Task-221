package hiber.dao;

import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }
   @Override
   @SuppressWarnings("unchecked")
   public User userByCar(String model, int series) {
      TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("FROM User where car.series in (select series from Car where model = :model and series = :series)"); //TypedQuery используется для описания запроса с заранее известным типом
      query.setParameter("model", model);
      query.setParameter("series", series);
      User user = null;
      try {
         user = query.getSingleResult();
      } catch (Exception ex) {
         System.out.printf("User с Car(model – %s, series – %s) не найден\n", model, series);
         //ex.printStackTrace();
      }
      return user;
   }

}
