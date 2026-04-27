# 🧑‍💼 User Management System

A simple **User Management Dashboard** built using **Spring Boot** and **JavaScript (Fetch API)**.
The system provides authentication and role-based authorization with full CRUD operations.

---

## 🚀 Features

* 🔐 JWT Authentication & Authorization
* 👥 Role-Based Access Control (ADMIN, ACCOUNTANT, USER)
* 📡 RESTful APIs
* 🧑‍💻 Admin Dashboard
* 🔄 CRUD Operations (Create, Read, Update, Delete)
* 🌐 Frontend integration using Fetch API

---

## 🛠️ Tech Stack

* Backend: Spring Boot
* Security: Spring Security + JWT
* Database: MySQL
* Frontend: HTML, CSS, JavaScript (Fetch API)

---

## 📂 Project Structure

* **Controller** → Handles HTTP requests
* **Service** → Business logic
* **Repository** → Database access
* **Security** → JWT & authentication

---

## 📡 API Endpoints

### 🔐 Authentication

* POST `/api/auth/login` → Login and get JWT token

---

### 👤 Users

* POST `/api/users` → Create new user (ADMIN)
* GET `/api/users` → Get all users (ADMIN, ACCOUNTANT)
* GET `/api/users/{id}` → Get user by ID
* PUT `/api/users/{id}` → Update user
* DELETE `/api/users/{id}` → Delete user (ADMIN)

---

## 🔑 Example Request (Login)

POST `/api/auth/login`

```json
{
  "username": "admin",
  "password": "1234"
}
```

### ✅ Response

```json
{
  "token": "your_jwt_token"
}
```

---

## 🔒 Authorization

All protected endpoints require JWT token in header:

```
Authorization: Bearer <your_token>
```

---

## 🔐 Roles & Permissions

### 👑 ADMIN

* Full access
* Manage all users (CRUD)

### 💼 ACCOUNTANT

* View users
* Limited access

### 👤 USER

* Basic access
* Limited permissions

---

## ⚙️ Setup & Run

1. Clone the repository:

```
git clone https://github.com/your-username/user-management-system.git
```

2. Open the project in your IDE

3. Run the application:

```
UserManagementSystemApplication.java
```

4. Access the app:

```
http://localhost:8080
```

---

## 🧪 Testing

You can test the APIs using tools like Postman.

---

## 📸 Notes

This is a simple project created for learning:

* Spring Boot
* Spring Security
* JWT Authentication
* REST APIs

---

## 🔥 Future Improvements

* Pagination & Search
* UI Enhancements
* Deploy to cloud (e.g., AWS)
* Swagger API Documentation

