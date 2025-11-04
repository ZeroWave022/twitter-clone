module twitter.core {
  requires com.github.spotbugs.annotations;

  requires jakarta.persistence;

  requires org.hibernate.orm.core;

  opens core to org.hibernate.orm.core;

  exports core;
  exports core.util;
  exports core.payload.request;
  exports core.payload.response;
}
