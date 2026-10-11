CREATE TABLE instance_state (
    id SMALLINT PRIMARY KEY CHECK (id = 1),
    initialized BOOLEAN NOT NULL DEFAULT FALSE
);

INSERT INTO instance_state (id, initialized) VALUES (1, FALSE);

CREATE TABLE app_user (
    id UUID PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password_hash VARCHAR(100) NOT NULL,
    display_name VARCHAR(80) NOT NULL,
    instance_admin BOOLEAN NOT NULL,
    created_at TIMESTAMPTZ NOT NULL,
    CONSTRAINT ck_app_user_username_lower
        CHECK (username = lower(username)),
    CONSTRAINT ck_app_user_username_format
        CHECK (username ~ '^[a-z0-9._-]{3,50}$'),
    CONSTRAINT ck_app_user_display_name
        CHECK (length(btrim(display_name)) BETWEEN 1 AND 80),
    CONSTRAINT ck_app_user_password_hash
        CHECK (length(password_hash) > 0)
);

CREATE UNIQUE INDEX uq_app_user_single_instance_admin
    ON app_user (instance_admin)
    WHERE instance_admin = TRUE;
