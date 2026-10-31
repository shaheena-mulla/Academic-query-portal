# Academic Query Resolution Portal

## Overview
This project provides a complete academic query resolution system with both frontend (HTML/JavaScript) and backend (Java) components.

## Features Implemented
- Ticket creation with categories and departments
- Attachment handling
- Status tracking (Submitted, In Progress, Waiting for Student, Resolved, Escalated)
- Comment system with timestamps
- Ticket history tracking
- Department assignment and reassignment
- Dashboard with statistics
- Admin dashboard with department summaries
- Responsive design

## Files Included

### Frontend (Browser-based)
- `index.html` - Complete portal with all features implemented in HTML/CSS/JavaScript
  - Uses localStorage for data persistence
  - Responsive design that works on mobile devices
  - Real-time dashboard updates

### Backend (Java)
- `Ticket.java` - Ticket model class with:
  - Full ticket lifecycle management
  - Comments and history tracking
  - Timestamp support
  - Status and department management
  
- `TicketManager.java` - Ticket management system with:
  - CRUD operations for tickets
  - Search and filtering capabilities
  - Statistics and reporting
  - Data persistence to file system
  
- `Main.java` - Console demonstration application showing:
  - How to integrate the ticket system
  - Sample usage of all features
  - Menu-driven interface for testing

## How to Use

### Frontend Version
1. Simply open `index.html` in any modern web browser
2. No server required - runs entirely in the browser
3. Data persists between sessions using localStorage

### Backend Version (Java)
1. Compile the Java files:
   ```
   javac *.java
   ```
2. Run the demonstration application:
   ```
   java Main
   ```

## Preparing for Java Development
This project demonstrates key Java concepts:
- Object-oriented programming (classes, objects, encapsulation)
- Collections (Lists, Maps)
- File I/O operations
- Exception handling
- Date/time management
- MVC pattern (Model: Ticket, Controller: TicketManager)

## Enhancements for Production
To convert this to a production system, you would typically:
1. Replace localStorage with a database (MySQL, PostgreSQL, etc.)
2. Add user authentication and authorization
3. Implement a REST API
4. Add email notifications
5. Create a proper Java web application with Spring Boot
6. Add unit tests