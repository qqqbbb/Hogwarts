-- liquibase formatted sql

-- changeSet K:1
CREATE INDEX student_name_index ON students (name);
-- changeSet K:2
CREATE INDEX house_name_color_index ON houses (name, color);
