PROMPT CREATE TABLE plm_properties
CREATE TABLE plm_properties (
  propertyid    NUMBER(19,0)  NOT NULL,
  property      VARCHAR2(255) NOT NULL,
  value         VARCHAR2(255) NOT NULL,
  env           VARCHAR2(3) NOT NULL,
  propcategory  VARCHAR2(255) NOT NULL,
  override      VARCHAR2(255) DEFAULT 'default',
  priority      NUMBER(19,0) DEFAULT 10
)
  STORAGE (
    NEXT       1024 K
  )
/

PROMPT ALTER TABLE plm_properties ADD PRIMARY KEY
ALTER TABLE plm_properties
  ADD PRIMARY KEY (
    propertyid
  )
  USING INDEX
    STORAGE (
      NEXT       1024 K
    )
/

CREATE SEQUENCE plm_properties_seq;
