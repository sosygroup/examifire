--
-- Insert predefined roles. DO NOT CHANGE THESE DEFAULT VALUES.
--

INSERT INTO `examifire`.`role` (`role_id`, `created_at`, `created_by`, `last_modified_by`, `updated_at`, `description`, `name`) VALUES ('1', NOW(), '1', '1', NOW(), 'System administrator', 'ADMIN');
INSERT INTO `examifire`.`role` (`role_id`, `created_at`, `created_by`, `last_modified_by`, `updated_at`, `description`, `name`) VALUES ('2', NOW(), '1', '1', NOW(), 'Teacher user', 'TEACHER');
INSERT INTO `examifire`.`role` (`role_id`, `created_at`, `created_by`, `last_modified_by`, `updated_at`, `description`, `name`) VALUES ('3', NOW(), '1', '1', NOW(), 'Student user', 'STUDENT');

INSERT INTO `examifire`.`user` (`user_id`, `created_at`, `created_by`, `last_modified_by`, `updated_at`, `active`, `email`, `first_name`, `last_name`, `password`, `password_expired`, `username`) VALUES ('1', NOW(), '1', '1', NOW(), 1, 'admin.admin@examifire.com', 'Admin', 'Admin', '$2a$10$vxWwpAKkyRElI.dyUfNKvun844GWAm9b4WLEQZiXVb6FHwh.Uzj9i', 0, 'admin');
INSERT INTO `examifire`.`user` (`user_id`, `created_at`, `created_by`, `last_modified_by`, `updated_at`, `active`, `email`, `first_name`, `last_name`, `password`, `password_expired`, `username`) VALUES ('2', NOW(), '2', '2', NOW(), 1, 'user.user@examifire.com', 'User', 'User', '$2a$10$ByIUiNaRfBKSV6urZoBBxe4UbJ/sS6u1ZaPORHF9AtNWAuVPVz1by', 0, 'user');

INSERT INTO `examifire`.`user_role` (`user_id`, `role_id`) VALUES ('1', '1');
INSERT INTO `examifire`.`user_role` (`user_id`, `role_id`) VALUES ('1', '2');
INSERT INTO `examifire`.`user_role` (`user_id`, `role_id`) VALUES ('2', '2');


INSERT INTO `examifire`.`quiz_type` (`quiz_type_id`, `created_at`, `created_by`, `last_modified_by`, `updated_at`, `type`) VALUES ('1', NOW(), '1', '1', NOW(), 'FIRST_MID_TERM');
INSERT INTO `examifire`.`quiz_type` (`quiz_type_id`, `created_at`, `created_by`, `last_modified_by`, `updated_at`, `type`) VALUES ('2', NOW(), '1', '1', NOW(), 'SECOND_MID_TERM');
INSERT INTO `examifire`.`quiz_type` (`quiz_type_id`, `created_at`, `created_by`, `last_modified_by`, `updated_at`, `type`) VALUES ('3', NOW(), '1', '1', NOW(), 'FINAL');

INSERT INTO `examifire`.`answer_type` (`answer_type_id`, `created_at`, `created_by`, `last_modified_by`, `updated_at`, `type`) VALUES ('1', NOW(), '1', '1', NOW(), 'MULTIPLE_ANSWER_QUESTION');
INSERT INTO `examifire`.`answer_type` (`answer_type_id`, `created_at`, `created_by`, `last_modified_by`, `updated_at`, `type`) VALUES ('2', NOW(), '1', '1', NOW(), 'OPEN_ANSWER_QUESTION');
INSERT INTO `examifire`.`answer_type` (`answer_type_id`, `created_at`, `created_by`, `last_modified_by`, `updated_at`, `type`) VALUES ('3', NOW(), '1', '1', NOW(), 'TRUE_FALSE_ANSWER_QUESTION');