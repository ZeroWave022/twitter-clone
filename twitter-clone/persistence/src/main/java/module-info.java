module twitter.persistence {
  requires transitive twitter.core;

  requires java.naming;
  requires jakarta.persistence;

  requires com.fasterxml.jackson.core;
  requires com.fasterxml.jackson.databind;

  requires spring.context;
  requires spring.beans;

  requires transitive org.hibernate.orm.core;

  requires com.github.spotbugs.annotations;

  exports persistence;
}
