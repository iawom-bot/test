CREATE TABLE student
(
	id INT,
	NAME VARCHAR(5),
	sex VARCHAR(2),
	age INT,
	class VARCHAR(10)
)

TRUNCATE TABLE student;

INSERT INTO student VALUES (1,'小明' , '男' ,11 , '四年（1）班');
INSERT INTO student VALUES (2,'小红' , '女' ,12 , '四年（2）班');
INSERT INTO student VALUES (3,'晓东' , '男' ,11 , '四年（1）班');
INSERT INTO student VALUES (4,'小华' , '女' ,11 , '四年（2）班');
INSERT INTO student VALUES (5,'小花' , '女' ,12 , '四年（1）班'); 
			
			
SELECT * FROM student;
 