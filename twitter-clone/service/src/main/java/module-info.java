open module twitter.service {
  requires transitive twitter.core;
  requires twitter.persistence;

  requires spring.context;
  requires spring.beans;
  requires spring.tx;
  requires com.github.spotbugs.annotations;
  requires spring.webflux;
  requires spring.web;
  requires spring.core;
  requires reactor.core;
  requires org.reactivestreams;

  exports service;
}
