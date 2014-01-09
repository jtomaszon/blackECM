# Dependencies and profile functions

# Repositories
repositories.remote << "https://repository.jboss.org/nexus/content/repositories/thirdparty-releases"
repositories.remote << "http://repo1.maven.org/maven2"


## Dependencies
#PERSISTENCE = ['javax:javaee-api:jar:6.0', 'javax.validation:validation-api:jar:1.1.0.Final', 'com.eos:eos-commons-jpa:jar:0.0.1']
# Somehow javaee-api gives the exception: Caused by: java.lang.ClassFormatError: Absent Code attribute in method that is not native or abstract in class file javax/persistence/PersistenceException
PERSISTENCE = ['org.hibernate.javax.persistence:hibernate-jpa-2.0-api:jar:1.0.1.Final', 'javax.validation:validation-api:jar:1.1.0.Final']

SPRING = group('spring-core', 'spring-orm', 'spring-context', 'spring-tx', 'spring-beans', 'spring-web',
	:under => 'org.springframework', :version => '3.2.4.RELEASE')

SCANNOTATION = 'org.scannotation:scannotation:jar:1.0.3'

SLF4J = 'org.slf4j:slf4j-api:jar:1.7.5'
#LOG4J = [transitive('org.apache.logging.log4j:log4j-slf4j-impl:jar:2.0-beta9')]
LOG4J = [transitive('log4j:log4j:jar:1.2.17'), 'org.slf4j:slf4j-log4j12:jar:1.7.5']

HIBERNATE = transitive('org.hibernate:hibernate-entitymanager:jar:4.2.6.Final')

REST = ['javax.ws.rs:javax.ws.rs-api:jar:2.0', 'javax.servlet:javax.servlet-api:jar:3.1.0']
# Resteasy
RESTEASY = transitive('org.jboss.resteasy:resteasy-servlet-initializer:jar:3.0.4.Final', 'org.jboss.resteasy:resteasy-jackson-provider:jar:3.0.4.Final')
REST_IMPL = RESTEASY

# Apache utils
COMMONS_CODEC='commons-codec:commons-codec:jar:1.9'

# Test dependencies
TEST = ['org.springframework:spring-test:jar:3.2.4.RELEASE', 'org.hsqldb:hsqldb:jar:2.3.1']

# Duplicate entries
EXCLUSIONS = ['javassist:javassist:jar:3.12.1.GA', 'org.jboss.logging:jboss-logging:jar:3.1.0.CR2', 'org.slf4j:slf4j-api:jar:1.5.8', 'org.slf4j:slf4j-simple:jar:1.5.8']
