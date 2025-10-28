module twitter.ui {
  requires twitter.core;
  requires twitter.service;

  requires javafx.controls;
  requires javafx.fxml;

  requires spring.boot;
  requires spring.boot.autoconfigure;
  requires spring.context;
  requires spring.beans;
  requires spring.core;
  requires javafx.graphics;

  requires com.github.spotbugs.annotations;
  requires javafx.base;
  requires twitter.persistence;

  requires spring.webflux;
  requires reactor.core;
  requires org.reactivestreams;

  opens ui to javafx.graphics, javafx.fxml, spring.core, spring.beans, spring.context;
  opens ui.config to spring.core, spring.beans, spring.context;
}
