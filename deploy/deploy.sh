function deploy(){
  tomcat;
  COLOR='\033[0;31m'
  NC='\033[0m' # No Color
  # tput setaf 1; echo 'entriamo nella directory del progetto';
  echo -e "${COLOR}entriamo nella directory del progetto${NC}";
  cd  ~/universita/$1;
  echo -e "${COLOR}Creiamo la cartella build${NC}";
  mkdir build;
  echo -e "${COLOR}copiamo le cartelle src e web dentro build${NC}";
  rsync -av --progress * build --exclude build --exclude logs;
  echo -e "${COLOR}entriamo in build/web${NC}";
  cd build/web;
  echo -e "${COLOR}entriamo in WEB-INF${NC}";
  cd WEB-INF;
  echo -e "${COLOR}creiamo la cartella Classes${NC}";
  mkdir classes;
  echo -e "${COLOR}Creiamo la cartella lib${NC}";
  mkdir lib;
  echo -e "${COLOR}Copiamo la libreria mysql dentro lib per la connessione al db${NC}";
  cp ~/universita/mysql-connector-java-8.0.18.jar lib/;
  echo -e "${COLOR}back di 1 folder${NC}";
  cd ..;
  echo -e "${COLOR}cp di src/conf dentro META-INF${NC}";
  cp -r ../src/conf/* META-INF;
  echo -e "${COLOR}cp di src/java dentro WEB-INF/classes${NC}";
  cp -r ../src/java/* WEB-INF/classes;
  echo -e "${COLOR}cp del properties file dentro WEB-INF${NC}";
  cp    ../src/env.properties WEB-INF/classes;
  echo -e "${COLOR}back di 1 folder${NC}";
  cd ..;
  echo -e "${COLOR}rm di src${NC}";
  rm -rf src/;
  echo -e "${COLOR}Creazione del file sources.txt (lista di tutte le classi .java${NC}";
  find -name "*.java" > sources.txt;
  echo -e "${COLOR}compile di tutte le classi con lib servlet${NC}";
  javac -cp /opt/tomcat/lib/servlet-api.jar:/opt/tomcat/lib/websocket-api.jar:/opt/tomcat/lib/javax.json.jar:/opt/tomcat/lib/gson-2.8.0.jar @sources.txt -Xlint;
  echo -e "${COLOR}rimozione di tutti i .java${NC}";
  find . -name "*.java" | xargs rm -f;
  echo -e "${COLOR}rimozione del file sources.txt${NC}";
  find . -name "sources.txt" | xargs rm -f;
  echo -e "${COLOR}entro in web${NC}";
  cd web;
  echo -e "${COLOR}Creo il paccheto war con il secondo parametro passato${NC}";
  jar -cvf $2.war *;
  echo -e "${COLOR}Rimuovo il vecchio war";
  rm -f ~/universita/$1/war/*;
  echo -e "${COLOR}Copio il nuovo war";
  cp $2.war ~/universita/$1/war/;
  echo -e "${COLOR}sposto il pacchetto war dentro /opt/tomcat/webapps${NC}";
  mv $2.war /opt/tomcat/webapps/;
  echo -e "${COLOR}Torno di 2 folder${NC}";
  cd ..;
  cd ..;
  echo -e "${COLOR}Ora sono in "$PWD"${NC}";
  echo -e "${COLOR}Rimuovo la cartella build${NC}";
  rm -rf build/;
  echo -e "${COLOR} Cp il war dentro al container tomcat";
  docker cp ~/universita/agenzia/war/$2.war tomcat:/usr/local/tomcat/webapps/$2.war;
  #echo -e "${COLOR}Riavvio il docker${NC}";
  #dockerClear && cd docker && make start && cd ..;
  echo -e "${COLOR}Restarto i servizi tomcat mysql";
  #change owner after deploy
  sudo chown tomcat:tomcat -R /opt/tomcat/webapps/$2
  #restartTomcat;
  tomcat
  notify-send -i face-surprise 'Hai deploiato '$1;
  figlet -t -k -f /usr/share/figlet/small.flf "MISSION COMPLETE" | lolcat;
  #toilet -f bigascii12  MISSION COMPLITE;
}
