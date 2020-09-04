This project is an ecommerce with j2ee + tomcat, mysql database. 

Step for start prj:
1) Rename .env.dev in .env
2) into docker/init create my.cnf file with basic configuration.
3) under tomcat folder add your tomcat-users.xml and context.xml
4) Add in /etc/hosts  agenziaviaggi.it
5) Create logs folder /agenzia/logs
6) cp env.properties.dev in env.properties in /agenzia/src/ change you crypt string
7) you need to make deploy now

It is in docker container if you would like start enter in 
docker folder and use: 

```
make start
``` 

This command start docker container and build it.
But you need to create your own .env file and volumes in project.