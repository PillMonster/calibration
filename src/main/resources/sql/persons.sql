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
( '10001', '', 'Candy', 'aaabbbccc@gmail.com', 'BRS', '專員', '保管人員');
INSERT INTO persons
( job_number, password, username, email, department, position, identity) VALUES
( '10002', '', 'Bob', 'abcabc123@gmail.com', 'BRS', '課長', '保管主管');
INSERT INTO persons
( job_number, password, username, email, department, position, identity) VALUES
( '10003', 'dylan1234', 'Dylan', 'dylan9342@gmail.com', 'BQC1', '專員', '校驗人員');
INSERT INTO persons
( job_number, password, username, email, department, position, identity) VALUES
( '10004', 'david1234', 'David', 'abcabc111@gmail.com', 'BQC1', '課長', '校驗主管');

INSERT INTO persons
( job_number, password, username, email, department, position, identity) VALUES
( '10005', '', 'Kevin', 'aaabbbccc@gmail.com', 'BQC1', '專員', '保管人員');
INSERT INTO persons
( job_number, password, username, email, department, position, identity) VALUES
( '10006', '', 'Alan', 'abcabc123@gmail.com', 'BQC1', '課長', '保管主管');
INSERT INTO persons
( job_number, password, username, email, department, position, identity) VALUES
( '10007', 'gordon1234', 'Gordon', 'dylan9342@gmail.com', 'BQC3', '專員', '校驗人員');
INSERT INTO persons
( job_number, password, username, email, department, position, identity) VALUES
( '10004', '', 'David', 'abcabc111@gmail.com', 'BQC1', '課長', '保管主管');