mkdir executables
cd server
./mvnw clean package
cd ..
mv server/target/server_program21.war executables/
cd client
./mvnw clean package
cd ..
mv client/target/client_program21.jar executables/