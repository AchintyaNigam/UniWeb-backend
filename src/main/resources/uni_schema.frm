-- Table for User Profiles
CREATE TABLE profiles (
    user_id INT PRIMARY KEY,
    role ENUM('student', 'teacher', 'admin') NOT NULL,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    -- Add other profile details common to all roles
    full_name VARCHAR(255),
    email VARCHAR(255),
    birthdate DATE
);

-- Table for Student-specific details
CREATE TABLE student_profile (
    user_id INT PRIMARY KEY,
    roll_number INT UNIQUE,
    branch VARCHAR(255),
    -- Add other student-specific details
    CONSTRAINT fk_student_profile FOREIGN KEY (user_id) REFERENCES profiles(user_id)
);

-- Table for Student Marks
CREATE TABLE student_marks (
    user_id INT PRIMARY KEY,
    subject VARCHAR(255),
    marks INT,
    -- Add other fields related to student marks
    CONSTRAINT fk_student_marks FOREIGN KEY (user_id) REFERENCES student_profile(user_id)
);

-- Table for Student Home Address
CREATE TABLE student_address (
    user_id INT PRIMARY KEY,
    street VARCHAR(255),
    city VARCHAR(255),
    zip_code VARCHAR(10),
    -- Add other fields related to student address
    CONSTRAINT fk_student_address FOREIGN KEY (user_id) REFERENCES student_profile(user_id)
);

-- Table for Teacher-specific details
CREATE TABLE teacher_profile (
    user_id INT PRIMARY KEY,
    department VARCHAR(255),
    -- Add other teacher-specific details
    CONSTRAINT fk_teacher_profile FOREIGN KEY (user_id) REFERENCES profiles(user_id)
);

-- Table for Admin-specific details
CREATE TABLE admin_profile (
    user_id INT PRIMARY KEY,
    department VARCHAR(255),
    -- Add other admin-specific details
    CONSTRAINT fk_admin_profile FOREIGN KEY (user_id) REFERENCES profiles(user_id)
);
