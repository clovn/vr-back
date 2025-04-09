CREATE TABLE IF NOT EXISTS tokens (
    id BIGSERIAL PRIMARY KEY,
    token int NOT NULL,
    activation_count int NOT NULL
);