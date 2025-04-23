# 🚆 Smart Train Ticket Booking System

A user-friendly desktop application built using Python's Tkinter library to facilitate efficient train ticket booking, management, and cancellation. The system supports both user and admin roles, with different functionalities and a clean graphical interface.

---

## 🧩 Problem Statement

Managing train bookings manually or through basic text interfaces can be inefficient and prone to errors. This project addresses the need for a smart, GUI-based system where users can book and cancel train tickets, while admins manage trains and schedules in real time.

---

## ✨ Key Features

- 🔐 **Login & Signup**: Separate user and admin login systems
- 🚆 **Train Management**: Admins can add or remove trains
- 🗺️ **Route Search**: Users can search trains by source and destination
- 🎟️ **Booking System**: Book tickets with optional food services
- 📄 **View Bookings**: Users can view their current bookings
- ❌ **Cancel Bookings**: Easy cancellation of individual bookings
- 👤 **GUI-Based Interface**: Built using Python’s Tkinter for simplicity

---

## 🧰 Tools and Technologies Used

- **Programming Language:** Python 3.x
- **GUI Library:** Tkinter
- **UUID Module:** For generating unique booking IDs

---

## 📦 Dependencies

- Python 3.x
- No third-party libraries needed (Standard Python packages only)

---

## 🧪 Testing Methodology

- Manual functional testing for all user/admin actions
- Boundary and input validation testing for fields (e.g., seat count, empty entries)
- GUI flow testing using multiple user accounts

---

## 🧠 Target Users

- Students and learners building GUI-based desktop applications
- Admin staff of a small-scale transport management setup
- Python enthusiasts exploring real-world projects

---

## 🛠 SDLC Model Used
We adopted the Iterative Model:

The development process was carried out in cyclical phases, allowing refinement after each iteration.

New features were added and tested in small modules.

Feedback from early versions helped improve functionality and UI in later iterations.

This approach enabled early detection of issues and allowed us to remain flexible with requirements.

---

## 🔍 Requirement Gathering Approach

- Identified core needs for ticket booking and train management
- Interviewed peers and gathered user stories for feature inclusion
- Validated requirements through feedback and iterative UI prototyping

---

## 🚧 Development Challenges & Solutions

| Challenge | Solution |
|----------|----------|
| Designing intuitive GUI | Iteratively tested with users for layout feedback |
| Handling bookings without database | Used in-memory data structures (dict/list) for simplicity |
| Booking conflicts and seat count | Validated seat availability before confirming |

---

## 📈 Future Enhancements
Integration with SQLite or MySQL for persistent data storage

Ticket PDF generation and printable confirmations

Email notifications for booking confirmation

Dynamic fare calculation based on distance

Better UI with modern styling (e.g., ttk themes or PyQt/PySide)

---

## 👨‍💻 Team Members
- Harsh Vardhan Saini (Frontend & Backend)
  SAP: 500120369
- Gautam Singh Chauhan (Documentation)
  SAP: 500121434
