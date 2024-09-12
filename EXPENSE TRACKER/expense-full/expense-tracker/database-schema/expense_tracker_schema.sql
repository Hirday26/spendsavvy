-- database
CREATE DATABASE expense_tracker;
USE expense_tracker
--tables
--user table
CREATE TABLE users(
	user_id INT PRIMARY KEY,
	 username VARCHAR(100),
	 fullname VARCHAR(100),
	 email VARCHAR(100),
	 mobile_no INT,
	 password NVARCHAR(MAX),
	 age NVARCHAR(MAX),
	 income INT,
	 active_yn INT DEFAULT 1,
	 created_at DATE DEFAULT GETDATE(),
	 edited_at DATE DEFAULT GETDATE()
)
--categories table
CREATE TABLE categories (
category_id INT PRIMARY KEY,
category_name VARCHAR(100)
)
--tags table
CREATE TABLE tags (
tag_id INT PRIMARY KEY,
tag_name VARCHAR(100)
)
--expense table
CREATE TABLE expense
(
	expense_id INT PRIMARY KEY,
	user_id INT FOREIGN KEY REFERENCES users(user_id),
	category_id INT FOREIGN KEY REFERENCES categories(Category_id),
	expense_name varchar(100),
	expense_amount INT ,
	expense_description NVARCHAR(MAX),
	expense_date  DATE DEFAULT GETDATE(),
	tag_id INT FOREIGN KEY REFERENCES tags(tag_id)
)
--expense tags table
CREATE TABLE expense_tags
(
	expense_tag_id INT PRIMARY KEY,
	user_id INT FOREIGN KEY REFERENCES users(user_id),
	expense_id INT FOREIGN KEY REFERENCES expense (expense_id),
	tag_id INT FOREIGN KEY REFERENCES tags(tag_id)
)
--budget table
CREATE TABLE budgets
(
	budget_id INT PRIMARY KEY,
	user_id INT FOREIGN KEY REFERENCES users(user_id),
	category_id INT FOREIGN KEY REFERENCES categories(Category_id),
	budget_name VARCHAR(100),
	target_amount INT ,
	target_achieved  INT,
	budget_date_added  DATE DEFAULT GETDATE(),
	budget_date_to_achieve DATE,
	tag_id INT FOREIGN KEY REFERENCES tags(tag_id),
)
--income type table
CREATE TABLE income_type(
	income_type_id INT PRIMARY KEY,
	income_type VARCHAR(100)
	);
--income table
CREATE TABLE income
(
	income_id INT PRIMARY KEY,
	user_id INT FOREIGN KEY REFERENCES users(user_id),
	amount INT ,
	income_type_id INT FOREIGN KEY REFERENCES income_type(income_type_id)
)
--budget tags table
CREATE TABLE budget_tags
(
	budget_tag_id INT PRIMARY KEY,
	user_id INT FOREIGN KEY REFERENCES users(user_id),
	budget_id INT FOREIGN KEY REFERENCES budgets (budget_id),
	tag_id INT FOREIGN KEY REFERENCES tags(tag_id)
)
--budget tracking table
CREATE TABLE budget_tracking
(
	id INT PRIMARY KEY,
	user_id INT FOREIGN KEY REFERENCES users(user_id),
	budget_id INT FOREIGN KEY REFERENCES budgets(budget_id),
	updated_date DATE DEFAULT GETDATE(),
	updated_amount INT
)


