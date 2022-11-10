Alter table students ADD CONSTRAINT age_constraint CHECK (age >= 16);
Alter table students ADD CONSTRAINT name_unique UNIQUE (name);
ALTER TABLE students ALTER COLUMN name SET NOT NULL;
ALTER TABLE houses ADD CONSTRAINT name_color_unique UNIQUE (name, color);
ALTER TABLE students ALTER COLUMN age SET Default 20;