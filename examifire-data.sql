INSERT INTO `examifire`.`role` (`role_id`, `created_at`, `updated_at`, `description`, `name`) VALUES ('1', '2020-01-09 15:41:00', '2020-01-09 15:41:00', 'System administrator', 'ADMIN');
INSERT INTO `examifire`.`role` (`role_id`, `created_at`, `updated_at`, `description`, `name`) VALUES ('2', '2020-01-09 15:41:00', '2020-01-09 15:41:00', 'Simple user', 'USER');

INSERT INTO `examifire`.`user` (`user_id`, `created_at`, `updated_at`, `active`, `email`, `first_name`, `last_name`, `password`, `password_expired`, `username`) VALUES ('1', '2020-01-09 15:41:00', '2020-01-09 15:41:00', TRUE, 'admin.admin@gmail.com', 'Admin', 'Admin', '$2a$10$vxWwpAKkyRElI.dyUfNKvun844GWAm9b4WLEQZiXVb6FHwh.Uzj9i', FALSE, 'admin');
INSERT INTO `examifire`.`user` (`user_id`, `created_at`, `updated_at`, `active`, `email`, `first_name`, `last_name`, `password`, `password_expired`, `username`) VALUES ('2', '2020-01-09 15:41:00', '2020-01-09 15:41:00', TRUE, 'user.user@gmail.com', 'User', 'User', '$2a$10$ByIUiNaRfBKSV6urZoBBxe4UbJ/sS6u1ZaPORHF9AtNWAuVPVz1by', FALSE, 'user');

INSERT INTO `examifire`.`user_role` (`user_id`, `role_id`) VALUES ('1', '1');
INSERT INTO `examifire`.`user_role` (`user_id`, `role_id`) VALUES ('1', '2');
INSERT INTO `examifire`.`user_role` (`user_id`, `role_id`) VALUES ('2', '2');
