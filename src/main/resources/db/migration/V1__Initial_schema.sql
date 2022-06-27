CREATE TABLE IF NOT EXISTS "employees"
(
    id                      SERIAL PRIMARY KEY NOT NULL,
    "name"                  VARCHAR(255) DEFAULT NULL,
    "position"              VARCHAR(255) DEFAULT NULL,
    department              VARCHAR(255) DEFAULT NULL,
    seniority_level         VARCHAR(255) DEFAULT NULL,
    "location"              VARCHAR(255) DEFAULT NULL,
    company_work_experience DATE         DEFAULT NULL,
    position_match          DECIMAL      DEFAULT NULL,
    avatar_id               BIGINT       DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS "images"
(
    id          SERIAL PRIMARY KEY NOT NULL,
    "content"   BYTEA        DEFAULT NULL,
    "name"      VARCHAR(255) DEFAULT NULL,
    is_avatar   BOOLEAN      DEFAULT FALSE,
    employee_id INTEGER
);

alter table images
    add constraint fk_uq1_employees_images
        foreign key (employee_id) references employees (id);