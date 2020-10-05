This project is an ecommerce with j2ee + tomcat, mysql database. 

Step for start prj:
1) Rename .env.dev in .env under docker folder.
2) Add in /etc/hosts  agenziaviaggi.it
3) Create logs folder /agenzia/logs
4) cp env.properties.dev in env.properties in /agenzia/src/ change you crypt string
5) you need to make deploy now

It is in docker container if you would like start enter in 
docker folder and use: 

```
make start
``` 

This command start docker container and build it.
But you need to create your own .env file and volumes in project.