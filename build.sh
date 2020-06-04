mkdir executables
cd server
./mvnw clean package
cd ..
mv server/target/server_program21.war executables/
cd client
./mvnw clean package
cd ..
mv client/target/client_program21.jar executables/
cd socket-server
javac -d . src/com/ceng453/socketserver/game/*.java src/com/ceng453/socketserver/server/*.java src/com/ceng453/socketserver/*.java
jar cvf socket_server_program21.jar com/ceng453/socketserver/*.class com/ceng453/socketserver/server/*.class com/ceng453/socketserver/game/*.class
echo "Main-Class: com.ceng453.socketserver.Main" > MANIFEST.MF
jar cvmf MANIFEST.MF socket_server_program21.jar com/ceng453/socketserver/*.class com/ceng453/socketserver/server/*.class com/ceng453/socketserver/game/*.class
cd ..
mv socket-server/socket_server_program21.jar executables/