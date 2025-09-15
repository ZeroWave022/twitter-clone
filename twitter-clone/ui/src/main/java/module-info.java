module twitter.ui {
  requires twitter.core;
  requires javafx.controls;
  requires javafx.fxml;

  requires spring.boot;
  requires spring.boot.autoconfigure;
  requires spring.context;
  requires spring.beans;
  requires spring.core;

  opens ui to javafx.graphics, javafx.fxml, spring.core, spring.beans, spring.context;
}
