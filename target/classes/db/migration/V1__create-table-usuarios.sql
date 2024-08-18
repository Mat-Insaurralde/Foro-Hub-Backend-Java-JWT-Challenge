
CREATE TABLE usuarios (
    id BIGINT NOT NULL AUTO_INCREMENT,

     nombre VARCHAR(100) NOT NULL,
     email VARCHAR(100) NOT NULL UNIQUE,
     username VARCHAR(30) NOT NULL UNIQUE,
     password VARCHAR(100) NOT NULL ,

     is_enabled BOOLEAN NULL DEFAULT NULL,
     account_No_Expired BOOLEAN NULL DEFAULT NULL,
     account_No_Locked BOOLEAN NULL DEFAULT NULL,
     credentials_No_Expired BOOLEAN NULL DEFAULT NULL,

    PRIMARY KEY (id)
);
