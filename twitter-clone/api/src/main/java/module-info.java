module twitter.api {
  requires spring.boot;
  requires spring.boot.autoconfigure;
  requires spring.context;
  requires spring.beans;

  opens api to spring.core, spring.beans, spring.context;
}
