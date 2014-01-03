EOS-Security

** Pre-requires
  buildr
  jruby or ruby 1.8+

** Building
  buildr clean install
  copy the war found in web/target to jetty or tomcat
  start with ??

** Sample of configuration file:



*** TODO
  Use enunciate to generate documentation
  Cache required services
  Implement PermissionService
  Implement SecurityServices
  Implement SessionServices
  Implement validation on all services
  Implement security on all services
  Use flyway to generate ddls and sqls
  Finish auto-start without any configuration needed (add derby or hsqldb)
  Try to replace all List to Set interfaces where it make sense
  Validate documentation VS Rests services

