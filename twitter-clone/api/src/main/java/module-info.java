module twitter.api {
  requires twitter.persistence;
  requires twitter.core;

  requires spring.boot;
  requires spring.boot.autoconfigure;
  requires spring.context;
  requires spring.beans;
  requires spring.security.core;
  requires twitter.service;
  requires jjwt.api;
  requires spring.web;
  requires org.apache.tomcat.embed.core;
  requires spring.core;
  requires spring.security.web;
  requires spring.security.config;
  requires spring.security.crypto;

  opens api to spring.core, spring.beans, spring.context;
  opens api.config to spring.core, spring.beans, spring.context;
  opens api.service to spring.core, spring.beans, spring.context;
  opens api.controller to spring.core, spring.beans, spring.context;
}
