# docker-compose-mywebapp-done
example of docker-compose running my web app

to start postgres db separately:
1. open cmd in dir "docker-first-image"
2. docker build -t mydb db
   This will build image named mydb, create "mytestdb", table "listofparts", and fill it
3. to run it in the container: docker run -it -p 5432:5432 --name some-postgres -e POSTGRES_PASSWORD=mysecretpassword mydb
   This will run it in interactive mode so you can see errors. If there is no errors, container with db will start and you can check it in    "docker ps"
4. Check the connection to db from your IDE or anything on localhost:8080/5432
5. Stop the container (docker stop ....). Remove the container (docker rm ....). Remove image (docker rmi .....).
----------------------------------------

to start all using docker-compose: 
1. open cmd in dir "docker-first-image"
2. docker-compose up --build
   this will build the images and start the containers. 
3  Go to localhost:8080/servlet-simple, it should work just fine
