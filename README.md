pujcovna-stroju
===============

To run the Application:
-------------------------
1) Clean and build the whole project: (cd pujcovna-stroju\pujcovnaStroju)
     mvn clean install

2) Make sure your derby db is available at jdbc:derby://localhost:1527/memory:sample;create=true
           "username: pa165"
            "password: pa165"

3) Run the server part using jetty container:
     (cd pujcovna-stroju\pujcovnaStroju)
     mvn jetty:run-war

4) Web app is now available at http://http://localhost:8084/frontend/


To run the REST client:
-------------------------
1) Go to the directory pujcovnaStroju/pujcovnaStroju_RESTclient  
2) run command mvn exec:java  
3) type 'help' to see available commands  
