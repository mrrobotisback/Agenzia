FROM tomcat:latest
MAINTAINER nik

RUN apt-get update
RUN apt-get -y install vim
RUN rm -rf $CATALINA_HOME/webapps.dist/ROOT/
RUN cp -avT $CATALINA_HOME/webapps.dist/ $CATALINA_HOME/webapps/