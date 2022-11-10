SELECT students.name, students.age, houses.name
FROM students
INNER JOIN houses ON students.house_id = houses.id;

SELECT students.name, students.age
FROM students
RIGHT JOIN avatars ON avatars.student_id = students.id;