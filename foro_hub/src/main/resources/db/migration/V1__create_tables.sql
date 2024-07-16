-- Creación de la tabla course
CREATE TABLE course (
  course_id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  category VARCHAR(255)
);

-- Creación de la tabla profile
CREATE TABLE profile (
  profile_id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255) NOT NULL
);

-- Creación de la tabla user
CREATE TABLE user (
  user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  email VARCHAR(320) UNIQUE NOT NULL,
  password VARCHAR(255) NOT NULL
);

-- Creación de la tabla intermedia para la relación muchos a muchos entre user y profile
CREATE TABLE user_profile (
  user_id BIGINT NOT NULL,
  profile_id BIGINT NOT NULL,
  PRIMARY KEY (user_id, profile_id),
  FOREIGN KEY (user_id) REFERENCES user(user_id) ON DELETE CASCADE,
  FOREIGN KEY (profile_id) REFERENCES profile(profile_id) ON DELETE CASCADE
);

-- Creación de la tabla topic
CREATE TABLE topic (
  topic_id BIGINT AUTO_INCREMENT PRIMARY KEY,
  title VARCHAR(255) NOT NULL,
  message TEXT NOT NULL,
  creation_year TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  status VARCHAR(50),
  author_id BIGINT NOT NULL,
  course_id BIGINT NOT NULL,
  response VARCHAR(255),
  CONSTRAINT FK_topic_course_id
    FOREIGN KEY (course_id)
      REFERENCES course(course_id) ON DELETE CASCADE,
  CONSTRAINT FK_topic_author_id
    FOREIGN KEY (author_id)
      REFERENCES user(user_id) ON DELETE CASCADE
);

-- Creación de la tabla response
CREATE TABLE response (
  response_id BIGINT AUTO_INCREMENT PRIMARY KEY,
  message TEXT NOT NULL,
  topic_id BIGINT NOT NULL,
  creation_year TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  author_id BIGINT NOT NULL,
  solution BOOLEAN DEFAULT FALSE,
  CONSTRAINT FK_response_author_id
    FOREIGN KEY (author_id)
      REFERENCES user(user_id) ON DELETE CASCADE,
  CONSTRAINT FK_response_topic_id
    FOREIGN KEY (topic_id)
      REFERENCES topic(topic_id) ON DELETE CASCADE
);