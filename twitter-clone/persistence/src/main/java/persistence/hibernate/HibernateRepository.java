package persistence.hibernate;

import core.Post;
import core.User;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

// Heavily inspired by Hibernate's Getting Started tutorial
// The SessionFactory is instantiated in the same way in our project
// See: https://docs.jboss.org/hibernate/orm/7.1/quickstart/html_single/
public class HibernateRepository {
  protected SessionFactory sessionFactory;

  @SuppressFBWarnings(value = "CT_CONSTRUCTOR_THROW")
  protected HibernateRepository() {
    final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().build();
    try {
      sessionFactory = new MetadataSources(registry).addAnnotatedClasses(User.class, Post.class)
          .buildMetadata().buildSessionFactory();
    } catch (Exception e) {
      StandardServiceRegistryBuilder.destroy(registry);
      throw new RuntimeException("Failed to create SessionFactory", e);
    }
  }

  protected void tearDown() {
    if (sessionFactory != null) {
      sessionFactory.close();
    }
  }

  public void dropDatabase() {
    sessionFactory.inTransaction(session -> {
      session.createMutationQuery("DELETE FROM Post").executeUpdate();
      session.createMutationQuery("DELETE FROM User").executeUpdate();
    });
  }
}
