# Private Chat App Backend (Spring Boot)

This is the backend service for the **Private Chat App**, a real-time messaging platform. The backend is built with Spring Boot and provides RESTful APIs and WebSocket endpoints for user management, authentication, and private messaging. The frontend is a separate React application, available here: [private-chat-app-react](https://github.com/ShubratoDn/private-chat-app-react).

---

## Table of Contents
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Database Models](#database-models)
- [API Endpoints](#api-endpoints)
- [WebSocket Messaging](#websocket-messaging)
- [Configuration](#configuration)
- [Running the Project](#running-the-project)
- [Connecting with the React Frontend](#connecting-with-the-react-frontend)
- [Contributing](#contributing)
- [License](#license)

---

## Features
- User registration and authentication (session-based)
- Real-time private messaging using WebSocket (STOMP)
- RESTful APIs for user and message management
- Chat history retrieval
- User search
- MySQL database integration
- Environment-based configuration (dev, prod)

---

## Tech Stack
- **Java 17**
- **Spring Boot 3.1.1**
- Spring Web, Spring Data JPA, Spring WebSocket
- MySQL
- Lombok
- Maven
- [Frontend: React](https://github.com/ShubratoDn/private-chat-app-react)

---

## Project Structure
```
private-chat-app/
├── src/
│   ├── main/
│   │   ├── java/com/chatapp/
│   │   │   ├── controllers/      # REST and WebSocket controllers
│   │   │   ├── services/         # Business logic
│   │   │   ├── repositories/     # JPA repositories
│   │   │   ├── entities/         # JPA entities (User, Message)
│   │   │   ├── DTO/              # Data Transfer Objects
│   │   │   ├── payloads/         # Request/response payloads
│   │   │   ├── helpers/          # Utility classes
│   │   │   └── configs/          # CORS, WebSocket configs
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── application-dev.properties
│   │       └── application-prod.properties
│   └── test/
│       └── java/com/chatapp/     # Test classes
├── pom.xml
└── README.md
```

---

## Database Models
### User
- `id` (int, PK)
- `username` (String, unique, required)
- `password` (String, required)
- `image` (String, required)
- `joinDate` (Timestamp)

### Message
- `id` (int, PK)
- `sender` (User, FK)
- `receiver` (User, FK)
- `content` (String)
- `timestamp` (Timestamp)

---

## API Endpoints
All endpoints are prefixed with `/api/v1`.

### Authentication & User
- `POST /register` — Register a new user
- `POST /login` — Login (session-based)
- `GET /getUser/{userId}` — Get user by ID
- `GET /search/{username}` — Search users by username

### Chat & Messages
- `GET /chatStarted` — Get users with whom the logged-in user has started chats
- `GET /chatStarted/{userId}` — Get chat list for a specific user
- `GET /get-my-message/{userId}` — Get messages for a user
- `GET /get-our-message/{senderId}/{receiverId}` — Get messages between two users
- `POST /addMessage` — Add a new message

### Session
- Session is managed via HTTP session (not JWT). Ensure your frontend supports cookies/session.

---

## WebSocket Messaging
- WebSocket endpoint: `/ws` (configured in `WebSocketConfig.java`)
- Uses STOMP protocol
- Send message: client sends to `/app/chat`
- Receive message: client subscribes to `/user/queue/message`
- See [Spring WebSocket Guide](https://spring.io/guides/gs/messaging-stomp-websocket/) for integration details

---

## Configuration
- **application.properties**: Common settings (port, profile)
- **application-dev.properties**: Development DB config (local MySQL)
- **application-prod.properties**: Production DB config (e.g., Railway)

Example (dev):
```
spring.datasource.url=jdbc:mysql://localhost:3306/private-chat-app
spring.datasource.username=root
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update
```

Set the active profile via environment variable `ENV` (defaults to `dev`).

---

## Running the Project
### Prerequisites
- Java 17+
- Maven
- MySQL (local or remote)

### Steps
1. **Clone the repository**
   ```bash
   git clone <this-repo-url>
   cd private-chat-app
   ```
2. **Configure the database**
   - Update `src/main/resources/application-dev.properties` or `application-prod.properties` with your DB credentials.
3. **Build and run**
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```
   The backend will start on [http://localhost:1234](http://localhost:1234) by default.

---

## Connecting with the React Frontend
- Use the [private-chat-app-react](https://github.com/ShubratoDn/private-chat-app-react) repository for the UI.
- Ensure both backend and frontend are running and configured to communicate (CORS is enabled in backend).
- The frontend should connect to the backend REST API and WebSocket endpoint (`/ws`).

---

## Contributing
Pull requests are welcome! For major changes, please open an issue first to discuss what you would like to change.

---

## License
This project is open source and available under the [MIT License](LICENSE).

---

## Contact
For questions or support, please open an issue or contact the maintainer. 
=======
# Private Chat App - Java Spring Boot Backend

This repository contains the back-end codebase for the Private Chat App, built using Java Spring Boot. The application provides APIs for handling user authentication, message storage, and real-time communication.

## Features

- **User Authentication**: Secure login and registration system to authenticate users.
- **Real-time Communication**: API endpoints for real-time messaging between users.
- **Data Persistence**: Utilizes Spring JPA and MySQL for data storage and management.

## Installation

1. Clone the repository:

    ```
    git clone https://github.com/ShubratoDn/private-chat-app-java.git
    ```

2. Import the project into your preferred Java IDE (e.g., IntelliJ IDEA, Eclipse).

3. Set up a MySQL database and configure the database connection in `application.properties`.

4. Build and run the application.

5. Access the API endpoints for integrating with the front end.

## Contributing

Contributions are welcome! Please fork the repository and submit a pull request with your changes.

## License

This project is licensed under the [MIT License](LICENSE). See the [LICENSE](LICENSE) file for details.

