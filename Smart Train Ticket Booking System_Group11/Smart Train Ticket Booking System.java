import tkinter as tk
from tkinter import messagebox, simpledialog
import uuid

# ------------------ User Class ------------------ #
class User:
    def __init__(self, username, password):
        self.username = username
        self.password = password
        self.bookings = []

# ------------------ App System ------------------ #
class TrainAppSystem:
    def __init__(self):
        self.users = {}
        self.admin_credentials = {"admin": "admin123"}
        self.trains = {}
        self.sample_trains()
        
    def sample_trains(self):
        self.trains["T001"] = {
            "id": "T001",
            "name": "Express Line",
            "source": "CityA",
            "destination": "CityB",
            "seats": 100,
            "fare": 250
    }
        self.trains["T002"] = {
            "id": "T002",
            "name": "Morning Star",
            "source": "CityB",
            "destination": "CityC",
            "seats": 120,
            "fare": 300
    }
        self.trains["T003"] = {
            "id": "T003",
            "name": "Night Rider",
            "source": "CityA",
            "destination": "CityC",
            "seats": 90,
            "fare": 400
    }

    def add_train(self, train_id, name, source, destination, seats, fare):
        self.trains[train_id] = {
            "name": name,
            "source": source,
            "destination": destination,
            "seats": seats,
            "fare": fare
        }

    def remove_train(self, train_id):
        if train_id in self.trains:
            del self.trains[train_id]

# ------------------ Login & Signup Window ------------------ #
class LoginWindow:
    def __init__(self, root, app_system, on_login):
        self.root = root
        self.app_system = app_system
        self.on_login = on_login

        self.root.title("Train Management - Login")
        self.root.geometry("400x300")

        tk.Label(root, text="Username").pack(pady=5)
        self.username_entry = tk.Entry(root)
        self.username_entry.pack(pady=5)

        tk.Label(root, text="Password").pack(pady=5)
        self.password_entry = tk.Entry(root, show='*')
        self.password_entry.pack(pady=5)

        tk.Button(root, text="Login", command=self.login).pack(pady=10)
        tk.Button(root, text="Signup", command=self.signup).pack(pady=5)

    def login(self):
        username = self.username_entry.get()
        password = self.password_entry.get()

        if username in self.app_system.admin_credentials:
            if self.app_system.admin_credentials[username] == password:
                messagebox.showinfo("Login", "Admin Login Successful")
                self.root.destroy()
                self.on_login(username, is_admin=True)
                return

        if username in self.app_system.users and self.app_system.users[username].password == password:
            messagebox.showinfo("Login", "User Login Successful")
            self.root.destroy()
            self.on_login(username, is_admin=False)
        else:
            messagebox.showerror("Login Failed", "Invalid username or password.")

    def signup(self):
        username = self.username_entry.get()
        password = self.password_entry.get()

        if username in self.app_system.users:
            messagebox.showerror("Signup Failed", "User already exists.")
        else:
            self.app_system.users[username] = User(username, password)
            messagebox.showinfo("Signup Success", "Account created successfully.")

# ------------------ Main App Window ------------------ #
class TrainGUI:
    def __init__(self, root, username, app_system, is_admin=False):
        self.root = root
        self.username = username
        self.app_system = app_system
        self.is_admin = is_admin

        self.root.title(f"Train Management System - Logged in as {self.username}")
        self.root.geometry("600x400")

        tk.Label(root, text=f"Welcome, {self.username}!", font=("Arial", 14)).pack(pady=20)

        if self.is_admin:
            tk.Button(root, text="Add Train", command=self.add_train).pack(pady=5)
            tk.Button(root, text="Remove Train", command=self.remove_train).pack(pady=5)
            tk.Button(root, text="View All Trains", command=self.view_trains).pack(pady=5)
        else:
            tk.Button(root, text="Search and Book Train", command=self.search_and_book).pack(pady=5)
            tk.Button(root, text="View Bookings", command=self.view_bookings).pack(pady=5)
            tk.Button(root, text="Cancel Booking", command=self.cancel_booking).pack(pady=5)

        tk.Button(root, text="Logout", command=root.destroy).pack(pady=20)

    def add_train(self):
        train_id = simpledialog.askstring("Train ID", "Enter Train ID")
        name = simpledialog.askstring("Train Name", "Enter Train Name")
        source = simpledialog.askstring("Source", "Enter Source Station")
        destination = simpledialog.askstring("Destination", "Enter Destination Station")
        seats = simpledialog.askinteger("Seats", "Enter Number of Seats")
        fare = simpledialog.askinteger("Fare", "Enter Fare")
        if train_id and name and source and destination and seats is not None and fare is not None:
            self.app_system.add_train(train_id, name, source, destination, seats, fare)
            messagebox.showinfo("Success", "Train added successfully!")

    def remove_train(self):
        train_id = simpledialog.askstring("Remove Train", "Enter Train ID to remove")
        if train_id in self.app_system.trains:
            self.app_system.remove_train(train_id)
            messagebox.showinfo("Removed", "Train removed successfully.")
        else:
            messagebox.showerror("Error", "Train ID not found.")

    def view_trains(self):
        if not self.app_system.trains:
            messagebox.showinfo("Trains", "No trains available.")
            return
        info = ""
        for tid, data in self.app_system.trains.items():
            info += f"ID: {tid}, Name: {data['name']}, From: {data['source']} To: {data['destination']}, Seats: {data['seats']}, Fare: {data['fare']}\n"
        messagebox.showinfo("Available Trains", info)

    def search_and_book(self):
        self.search_window = tk.Toplevel(self.root)
        self.search_window.title("Search Train")
        self.search_window.geometry("350x200")

        tk.Label(self.search_window, text="Source").pack(pady=5)
        self.source_entry = tk.Entry(self.search_window)
        self.source_entry.pack(pady=5)

        tk.Label(self.search_window, text="Destination").pack(pady=5)
        self.destination_entry = tk.Entry(self.search_window)
        self.destination_entry.pack(pady=5)

        tk.Button(self.search_window, text="Search", command=self.perform_train_search).pack(pady=10)

    def perform_train_search(self):
        source = self.source_entry.get().strip()
        destination = self.destination_entry.get().strip()
        self.search_window.destroy()

        matching_trains = {
            tid: data for tid, data in self.app_system.trains.items()
            if data['source'].lower() == source.lower() and data['destination'].lower() == destination.lower()
        }

        if not matching_trains:
            messagebox.showinfo("No Trains", "No matching trains found.")
            return

        selection_window = tk.Toplevel(self.root)
        selection_window.title("Select Train")
        selection_window.geometry("500x300")

        tk.Label(selection_window, text="Available Trains", font=("Arial", 12)).pack()

        listbox = tk.Listbox(selection_window, width=80)
        for tid, train in matching_trains.items():
            listbox.insert(tk.END, f"ID: {tid} | {train['name']} | Seats: {train['seats']} | Fare: {train['fare']}")
        listbox.pack(pady=10)

        def confirm_selection():
            selected_index = listbox.curselection()
            if not selected_index:
                messagebox.showwarning("Selection", "No train selected.")
                return
            selected_tid = list(matching_trains.keys())[selected_index[0]]
            seat_count = simpledialog.askinteger("Booking", "Enter number of seats to book")
            if seat_count and seat_count <= matching_trains[selected_tid]['seats']:
                matching_trains[selected_tid]['seats'] -= seat_count
                booking_id = str(uuid.uuid4())[:8]
                food_pref = messagebox.askyesno("Meal", "Do you want food service?")
                details = f"BookingID: {booking_id} | Train: {selected_tid} | Seats: {seat_count} | Meal: {'Yes' if food_pref else 'No'}"
                self.app_system.users[self.username].bookings.append(details)
                messagebox.showinfo("Booked", f"Train booked successfully!\n{details}")
                selection_window.destroy()
            else:
                messagebox.showerror("Seats", "Invalid seat count or not enough seats available.")

        tk.Button(selection_window, text="Confirm Booking", command=confirm_selection).pack()

    def view_bookings(self):
        user = self.app_system.users[self.username]
        if not user.bookings:
            messagebox.showinfo("Bookings", "No bookings found.")
        else:
            messagebox.showinfo("Bookings", "\n".join(user.bookings))

    def cancel_booking(self):
        user = self.app_system.users[self.username]
        if not user.bookings:
            messagebox.showinfo("Cancel", "No bookings to cancel.")
            return

        cancel_win = tk.Toplevel(self.root)
        cancel_win.title("Cancel Booking")
        cancel_win.geometry("400x300")

        listbox = tk.Listbox(cancel_win, width=80)
        for booking in user.bookings:
            listbox.insert(tk.END, booking)
        listbox.pack(pady=10)

        def confirm_cancel():
            selected = listbox.curselection()
            if selected:
                selected_booking = user.bookings.pop(selected[0])
                messagebox.showinfo("Cancelled", f"Cancelled: {selected_booking}")
                cancel_win.destroy()
            else:
                messagebox.showwarning("Select", "Select a booking to cancel.")

        tk.Button(cancel_win, text="Cancel Selected", command=confirm_cancel).pack()

# ------------------ Main Launcher ------------------ #
def launch_app(username, is_admin=False):
    new_root = tk.Tk()
    TrainGUI(new_root, username, app_system, is_admin)
    new_root.mainloop()

# ------------------ Main App Start ------------------ #
if __name__ == "__main__":
    app_system = TrainAppSystem()
    root = tk.Tk()
    LoginWindow(root, app_system, launch_app)
    root.mainloop()
