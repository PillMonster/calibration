USE calibration;	

DROP TABLE IF EXISTS persons;	
CREATE TABLE persons (		
  person_id INT AUTO_INCREMENT COMMENT 'id',
  job_number VARCHAR(32) COMMENT '工號',
  password VARCHAR(50) COMMENT '密碼',
  username VARCHAR(32) COMMENT '使用者名字',
  email VARCHAR(50) COMMENT '郵箱',
  department VARCHAR(32) COMMENT '部門',
  position VARCHAR(32) COMMENT '階級',
  identity VARCHAR(32) COMMENT '權限',
  create_date DATETIME DEFAULT CURRENT_TIMESTAMP() COMMENT '創建日期',
  
  PRIMARY KEY (person_id)
);
INSERT INTO persons
( job_number, password, username, email, department, position, identity) VALUES
( '14717', 'bob123456', '陳小偉', 'aaabbbccc@gmail.com', '研發', '專員', '保管人員');
INSERT INTO persons
( job_number, password, username, email, department, position, identity) VALUES
( '14505', 'candy123456', '林慧雯', 'abcabc123@gmail.com', '研發', '課長', '保管主管');
INSERT INTO persons
( job_number, password, username, email, department, position, identity) VALUES
( '13234', 'dylan1234', '簡茗家', 'dylan9342@gmail.com', '品管', '專員', '校驗人員');
INSERT INTO persons
( job_number, password, username, email, department, position, identity) VALUES
( '10256', 'davie123456', '李大同', 'abcabc111@gmail.com', '品管', '課長', '校驗主管');