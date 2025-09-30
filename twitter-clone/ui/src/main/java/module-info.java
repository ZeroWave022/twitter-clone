open module twitter.ui {
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
}
