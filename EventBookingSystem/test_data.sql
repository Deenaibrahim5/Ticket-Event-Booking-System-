-- Event Booking System Test Data
-- Oracle Database XE
-- CS313 Advanced Programming Language Project

-- Insert Sample Users
-- Admin user
INSERT INTO USERS (USER_ID, USERNAME, PASSWORD, FULL_NAME, EMAIL, ROLE)
VALUES (USER_SEQ.NEXTVAL, 'admin', 'admin123', 'System Administrator', 'admin@eventbooking.com', 'Admin');

-- Event Managers
INSERT INTO USERS (USER_ID, USERNAME, PASSWORD, FULL_NAME, EMAIL, ROLE)
VALUES (USER_SEQ.NEXTVAL, 'manager1', 'pass123', 'Ahmed Salem', 'ahmed.salem@events.com', 'Manager');

INSERT INTO USERS (USER_ID, USERNAME, PASSWORD, FULL_NAME, EMAIL, ROLE)
VALUES (USER_SEQ.NEXTVAL, 'manager2', 'pass123', 'Fatima Ali', 'fatima.ali@events.com', 'Manager');

-- Customers
INSERT INTO USERS (USER_ID, USERNAME, PASSWORD, FULL_NAME, EMAIL, ROLE)
VALUES (USER_SEQ.NEXTVAL, 'customer1', 'pass123', 'Mohammed Hassan', 'mohammed.h@email.com', 'Customer');

INSERT INTO USERS (USER_ID, USERNAME, PASSWORD, FULL_NAME, EMAIL, ROLE)
VALUES (USER_SEQ.NEXTVAL, 'customer2', 'pass123', 'Sara Ibrahim', 'sara.i@email.com', 'Customer');

INSERT INTO USERS (USER_ID, USERNAME, PASSWORD, FULL_NAME, EMAIL, ROLE)
VALUES (USER_SEQ.NEXTVAL, 'customer3', 'pass123', 'Omar Khalid', 'omar.k@email.com', 'Customer');

INSERT INTO USERS (USER_ID, USERNAME, PASSWORD, FULL_NAME, EMAIL, ROLE)
VALUES (USER_SEQ.NEXTVAL, 'customer4', 'pass123', 'Layla Mansour', 'layla.m@email.com', 'Customer');

-- Insert Sample Events
-- No events added in this test data file

-- Commit the changes
COMMIT;

-- Display summary
SELECT 'Test data inserted successfully!' AS STATUS FROM DUAL;
SELECT COUNT(*) AS TOTAL_USERS FROM USERS;
SELECT COUNT(*) AS TOTAL_EVENTS FROM EVENTS;
SELECT COUNT(*) AS TOTAL_BOOKINGS FROM BOOKINGS;
