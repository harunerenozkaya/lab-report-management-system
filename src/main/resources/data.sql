INSERT INTO users (user_id, user_password, user_name ,user_surname,role)
VALUES ('1000000', '$2a$12$9H5lUqFUNOXt0trixQ76auM9naku9bVl0aM00KULIvkHJGOP7R/4a', 'admin','admin','ROLE_MANAGER')
ON CONFLICT DO NOTHING;
