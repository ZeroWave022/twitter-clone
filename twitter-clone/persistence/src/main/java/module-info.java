module twitter.persistence {
  requires transitive twitter.core;

  requires com.fasterxml.jackson.core;
  requires com.fasterxml.jackson.databind;

  requires spring.context;
  requires spring.beans;

  exports persistence;
}
