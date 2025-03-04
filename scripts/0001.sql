CREATE TABLE person
(
    id       varchar(26) PRIMARY KEY,
    type     varchar(12)  NOT NULL,
    document varchar(18),
    name     varchar(255) NOT NULL
);

CREATE UNIQUE INDEX idx_person_document ON person (type, document);

CREATE TABLE contact
(
    id        varchar(26) PRIMARY KEY,
    email     varchar(255),
    phone varchar(255),
    cellphone varchar(255),
    person_id varchar(26) NOT NULL
);

CREATE UNIQUE INDEX idx_contact_person ON contact (person_id);

CREATE TABLE "user"
(
    id        varchar(26) PRIMARY KEY,
    username  varchar(255) NOT NULL,
    person_id varchar(26)  NOT NULL
);

CREATE UNIQUE INDEX idx_user_username ON "user" (username);

CREATE UNIQUE INDEX idx_user_person ON "user" (person_id);
