use library;

CREATE TABLE users (
    username VARCHAR(255) PRIMARY KEY,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(255),
    phone VARCHAR(50),
    role VARCHAR(50),
    active BOOLEAN DEFAULT TRUE
);

-- 2️⃣ PUBLISHERS
CREATE TABLE publishers (
    id BIGINT PRIMARY KEY,
    name VARCHAR(255),
    address VARCHAR(255),
    website VARCHAR(255)
);

-- 3️⃣ CATEGORIES (Self-referencing)
CREATE TABLE categories (
    id BIGINT PRIMARY KEY,
    name VARCHAR(255),
    parent_id BIGINT,
    FOREIGN KEY (parent_id) REFERENCES categories(id) ON DELETE SET NULL
);

-- 4️⃣ AUTHORS
CREATE TABLE authors (
    id BIGINT PRIMARY KEY,
    name VARCHAR(255),
    bio TEXT,
    date_of_birth DATE,
    nationality VARCHAR(100)
);

-- 5️⃣ BOOKS
CREATE TABLE books (
    id BIGINT PRIMARY KEY,
    title VARCHAR(255),
    isbn VARCHAR(100),
    published_date DATE,
    publisher_id BIGINT,
    FOREIGN KEY (publisher_id) REFERENCES publishers(id) ON DELETE SET NULL
);

-- 6️⃣ BOOK-AUTHORS (Many-to-Many)
CREATE TABLE book_authors (
    book_id BIGINT,
    author_id BIGINT,
    PRIMARY KEY (book_id, author_id),
    FOREIGN KEY (book_id) REFERENCES books(id) ON DELETE CASCADE,
    FOREIGN KEY (author_id) REFERENCES authors(id) ON DELETE CASCADE
);

-- 7️⃣ BOOK-CATEGORIES (Many-to-Many)
CREATE TABLE book_categories (
    book_id BIGINT,
    category_id BIGINT,
    PRIMARY KEY (book_id, category_id),
    FOREIGN KEY (book_id) REFERENCES books(id) ON DELETE CASCADE,
    FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE CASCADE
);

-- 8️⃣ BORROWING
CREATE TABLE borrowing (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_id VARCHAR(255),
    book_id BIGINT,
    borrow_date DATE,
    due_date DATE,
    return_date DATE,
    FOREIGN KEY (member_id) REFERENCES users(username) ON DELETE CASCADE,
    FOREIGN KEY (book_id) REFERENCES books(id) ON DELETE CASCADE
);
