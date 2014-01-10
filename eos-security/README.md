EOS-Security

** Pre-requires
  buildr
  jruby or ruby 1.8+

** Building
  buildr clean install
  copy the war found in web/target to jetty or tomcat
  start with ??

** Sample of configuration file:
  TODO

** API Documentation
All documentation can be found [here](http://docs.eossecurity.apiary.io/ "EOS Security API Documentation") 

*** TODO
  Cache required services
  Implement validation on all services
  Implement security on all services
  Use flyway to generate ddls and sqls
  Finish auto-start without any configuration needed (add derby or hsqldb)
  Try to replace all List to Set interfaces where it make sense
  Validate documentation VS Rests services
  Resolve all TODO's
