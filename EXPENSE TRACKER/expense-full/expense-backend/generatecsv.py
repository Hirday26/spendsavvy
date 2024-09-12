import csv

expenses = [
    {"categoryNameID": "1", "expenseAmount": 50, "expenseDate": "2024-03-10", "expenseDescription": "Groceries", "expenseName": "Grocery Shopping", "tags": "food"},
    {"categoryNameID": "5", "expenseAmount": 30, "expenseDate": "2024-03-09", "expenseDescription": "Dinner", "expenseName": "Dinner with friends", "tags": "food, social"},
    {"categoryNameID": "2", "expenseAmount": 100, "expenseDate": "2024-03-08", "expenseDescription": "Utilities", "expenseName": "Electricity bill", "tags": "utilities"},
    # Add more expense entries here
    {"categoryNameID": "3", "expenseAmount": 20, "expenseDate": "2024-03-07", "expenseDescription": "Transportation", "expenseName": "Bus fare", "tags": "transport"},
    {"categoryNameID": "4", "expenseAmount": 80, "expenseDate": "2024-03-06", "expenseDescription": "Entertainment", "expenseName": "Movie tickets", "tags": "entertainment"},
    {"categoryNameID": "1", "expenseAmount": 70, "expenseDate": "2024-03-05", "expenseDescription": "Groceries", "expenseName": "Weekly groceries", "tags": "food"},
]

# Define the CSV file path
csv_file_path = "expenses.csv"

# Define the CSV column headers without "userId"
headers = ["categoryNameID", "expenseAmount", "expenseDate", "expenseDescription", "expenseName", "tags"]

# Write data to CSV file
with open(csv_file_path, "w", newline="") as csvfile:
    writer = csv.DictWriter(csvfile, fieldnames=headers)

    # Write header row
    writer.writeheader()

    # Write expense entries
    for expense in expenses:
        writer.writerow(expense)

print("CSV file generated successfully.")
