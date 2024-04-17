FROM openjdk
WORKDIR /usr/src/myapp
COPY . /usr/src/myapp/
CMD [ "java", "-jar", "private-chat-app.jar" ]
EXPOSE 5555