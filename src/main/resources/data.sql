INSERT INTO user_roles (id, user_role)
values
    (1, 'ADMIN'),
    (2, 'USER');

INSERT INTO users (id, username, first_name, last_name, age, email, password, is_active)
VALUES
    (1, 'petko', 'Admincho', 'Adminov', 18,'admin@example.com', '57e7759fd2d59275fc3c3cd5dd2ace5013b39ee972999412f3f5f5c3382b6765c2571ef86648abe2', 1),
    (2, 'shestko', 'Usercho', 'Userov', 18,'user@example.com', '57e7759fd2d59275fc3c3cd5dd2ace5013b39ee972999412f3f5f5c3382b6765c2571ef86648abe2', 1);


INSERT INTO users_user_roles (user_id, user_roles_id)
VALUES
    (1, 1),
    (2, 2);