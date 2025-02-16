# Smart Contact Book

## Introduction
The **Smart Contact Book** is a robust contact management system built using **Spring Boot**, providing secure authentication, contact management, and email communication functionalities. This backend API offers role-based access control with **Spring Security**, **OAuth2 authentication**, and CRUD operations for managing contacts efficiently. The frontend is developed separately by my friends.

## Architecture
The application follows a **layered architecture**:
- **Controller Layer**: Exposes REST APIs for authentication and contact management.
- **Service Layer**: Implements business logic.
- **Repository Layer**: Uses **Spring Data JPA** for database interactions.
- **Security Layer**: Implements **JWT-based authentication** and **OAuth2 login** (Google, GitHub).
- **Cloudinary Integration**: Stores contact images.
- **Email Service**: Uses **JavaMail API** for sending emails with attachments.

### Tech Stack
- **Spring Boot** (Backend Framework)
- **Spring Security** (Authentication & Authorization)
- **Spring Data JPA** (Database ORM)
- **MySQL** (Relational Database)
- **JWT & OAuth2** (Authentication & Authorization)
- **Cloudinary SDK** (Image Storage)
- **JavaMail API** (Email Service)
- **Postman** (API Testing)

## Features
- **User Authentication & Authorization**
  - API-based login/logout with JWT.
  - OAuth2 authentication (Google, GitHub).
  - Role-based access control (Admin, User).
- **Contact Management**
  - Create, Read, Update, and Delete contacts.
  - Upload contact images to Cloudinary.
  - Fetch all user-specific contacts.
- **Email Service**
  - Send emails with attachments directly from the application.
- **API Documentation**
  - Postman Collection available for testing APIs.

## API Endpoints
### Authentication
#### 1. User Registration
```http
POST /api/auth/register
```
**Request Body:**
```json
{
  "name": "John Doe",
  "email": "johndoe@example.com",
  "password": "securepassword"
}
```

#### 2. User Login (JWT-Based)
```http
POST /api/auth/login
```
**Request Body:**
```json
{
  "email": "johndoe@example.com",
  "password": "securepassword"
}
```

#### 3. OAuth2 Authentication
- Google: `/oauth2/authorization/google`
- GitHub: `/oauth2/authorization/github`

### Contact Management
#### 4. Create a Contact
```http
POST /api/contacts
```
**Request Body:**
```json
{
  "name": "Alice",
  "email": "alice@example.com",
  "phone": "9876543210",
  "imageUrl": "cloudinary-url"
}
```

#### 5. Get All Contacts (User Specific)
```http
GET /api/contacts
```

#### 6. Get Contact by ID
```http
GET /api/contacts/{id}
```

#### 7. Update Contact
```http
PUT /api/contacts/{id}
```

#### 8. Delete Contact
```http
DELETE /api/contacts/{id}
```

### Email Service
#### 9. Send Email
```http
POST /api/email/send
```
**Request Body:**
```json
{
  "to": "receiver@example.com",
  "subject": "Test Email",
  "message": "Hello, this is a test email."
}
```

## Installation & Setup
### Prerequisites
- Java 17+
- MySQL Database
- Cloudinary Account (For Image Uploads)
- Postman (For API Testing)

### Steps to Run the Project
1. **Clone the repository:**
   ```sh
   git clone https://github.com/NikhilRajOfficial/smart-contact-book.git
   cd smart-contact-book
   ```
2. **Configure the Database** in `application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/smart_contact_db
   spring.datasource.username=root
   spring.datasource.password=yourpassword
   ```
3. **Run the Application:**
   ```sh
   mvn spring-boot:run
   ```
4. **Access APIs using Postman** or any API client.

## Contributing
Feel free to raise issues or contribute via pull requests.

## License
This project is licensed under the MIT License.

