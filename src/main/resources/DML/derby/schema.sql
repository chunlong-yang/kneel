-- start
 
--DROP TABLE users;
--DROP TABLE role;   
--DROP TABLE user_role;
--DROP TABLE menus;
--DROP TABLE role_menu;
--DROP TABLE plm_properties;
--DROP TABLE codelookup;

-- base user
CREATE TABLE users (
  id                 VARCHAR(25)  NOT NULL,
  name               VARCHAR(50)  NOT NULL, 
  email              VARCHAR(100) NOT NULL,
  phone              VARCHAR(25)  ,
  password           VARCHAR(25)  , 
  active             CHAR(1)      DEFAULT 'Y',
  lastlogin          DATE         NOT NULL 
);

ALTER TABLE users
   ADD CONSTRAINT users_PK Primary Key (
      id);
       

-- base role
CREATE TABLE role (
  id   Integer  NOT NULL, 
  name VARCHAR(255) NOT NULL
);

-- base userrole
CREATE TABLE user_role (
  id Integer NOT NULL,
  userid     VARCHAR(25) NOT NULL,
  roleid Integer NOT NULL
);

-- base menu      
CREATE TABLE menus (
  id         Integer NOT NULL,
  parentid   Integer NOT NULL,
  name       VARCHAR(25) NOT NULL,
  code       VARCHAR(50) NOT NULL,
  path       VARCHAR(50) ,
  icon       VARCHAR(50) ,
  createdate DATE         ,
  active     CHAR(1)      DEFAULT 'Y' NOT NULL,
  comments   VARCHAR(25) 
);     

ALTER TABLE menus
   ADD CONSTRAINT menus_PK Primary Key (
      id);
      
-- base rolemenu
CREATE TABLE role_menu (
  id Integer NOT NULL,
  roleid  Integer NOT NULL,
  menuid  Integer NOT NULL
);      


-- base properties
CREATE TABLE plm_properties (
  propertyid    Integer  NOT NULL,
  property      VARCHAR(255) NOT NULL,
  value         VARCHAR(255) NOT NULL,
  env           VARCHAR(5) NOT NULL,
  propcategory  VARCHAR(255) NOT NULL,
  override      VARCHAR(255) DEFAULT 'default',
  priority      Integer DEFAULT 10
);

ALTER TABLE plm_properties
   ADD CONSTRAINT plm_properties_PK Primary Key (
      propertyid);
      
-- base code
CREATE TABLE codelookup (
  codetype VARCHAR(255) NOT NULL,
  "DECODE"   VARCHAR(255) NOT NULL,
  active   CHAR(1)       DEFAULT 'Y' NOT NULL,
  id   Integer  NOT NULL
);

ALTER TABLE codelookup
   ADD CONSTRAINT codelookup_PK Primary Key (
      id); 