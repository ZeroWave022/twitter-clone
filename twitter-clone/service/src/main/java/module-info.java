module twitter.service {
  requires transitive twitter.core;
  requires transitive twitter.persistence;

  requires spring.context;
  requires spring.beans;
  requires spring.tx;

  exports service;
}
