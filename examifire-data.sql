--
-- Insert predefined roles. DO NOT CHANGE THESE DEFAULT VALUES.
--
INSERT INTO `examifire`.`role` (`role_id`, `created_at`, `created_by`, `last_updated_by`, `last_updated_at`, `description`, `name`) VALUES ('1', NOW(), '1', '1', NOW(), 'System administrator', 'ADMIN');
INSERT INTO `examifire`.`role` (`role_id`, `created_at`, `created_by`, `last_updated_by`, `last_updated_at`, `description`, `name`) VALUES ('2', NOW(), '1', '1', NOW(), 'Teacher user', 'TEACHER');
INSERT INTO `examifire`.`role` (`role_id`, `created_at`, `created_by`, `last_updated_by`, `last_updated_at`, `description`, `name`) VALUES ('3', NOW(), '1', '1', NOW(), 'Student user', 'STUDENT');
--
-- Insert predefined users.
--
INSERT INTO `examifire`.`user` (`user_id`, `created_at`, `created_by`, `last_updated_by`, `last_updated_at`, `account_enabled`, `email`, `first_name`, `last_name`, `password`, `password_non_expired`) VALUES ('1', NOW(), '1', '1', NOW(), 1, 'admin.admin@examifire.it', 'Admin', 'Admin', '$2a$10$vxWwpAKkyRElI.dyUfNKvun844GWAm9b4WLEQZiXVb6FHwh.Uzj9i', 1);
INSERT INTO `examifire`.`user` (`user_id`, `created_at`, `created_by`, `last_updated_by`, `last_updated_at`, `account_enabled`, `email`, `first_name`, `last_name`, `password`, `password_non_expired`) VALUES ('2', NOW(), '1', '1', NOW(), 1, 'teacher.teacher@examifire.it', 'Teacher', 'Teacher', '$2a$10$PoeB41Kws14p1pIqpDNJw.z9lYS2a86v4zJugoaDC2bpXHvIQb9QW', 1);
INSERT INTO `examifire`.`user` (`user_id`, `created_at`, `created_by`, `last_updated_by`, `last_updated_at`, `account_enabled`, `email`, `first_name`, `last_name`, `password`, `password_non_expired`) VALUES ('3', NOW(), '1', '1', NOW(), 1, 'student.student@examifire.it', 'Student', 'Student', '$2a$10$/RkFMD2qLZK3FN/31JN4XOgrMpNqYps0SJFYOGh5DZ7q9pT/MoO/K', 1);

INSERT INTO `examifire`.`teacher` (`user_id`) VALUES ('2');
INSERT INTO `examifire`.`student` (`user_id`, `identification_number`) VALUES ('3', '123456');
--
-- Insert predefined user role.
--
INSERT INTO `examifire`.`user_role` (`user_id`, `role_id`) VALUES ('1', '1');
INSERT INTO `examifire`.`user_role` (`user_id`, `role_id`) VALUES ('2', '2');
INSERT INTO `examifire`.`user_role` (`user_id`, `role_id`) VALUES ('3', '3');
--
-- Insert predefined quiz types.
--
INSERT INTO `examifire`.`quiz_type` (`quiz_type_id`, `created_at`, `created_by`, `last_updated_by`, `last_updated_at`, `type`) VALUES ('1', NOW(), '1', '1', NOW(), 'FIRST_MID_TERM');
INSERT INTO `examifire`.`quiz_type` (`quiz_type_id`, `created_at`, `created_by`, `last_updated_by`, `last_updated_at`, `type`) VALUES ('2', NOW(), '1', '1', NOW(), 'SECOND_MID_TERM');
INSERT INTO `examifire`.`quiz_type` (`quiz_type_id`, `created_at`, `created_by`, `last_updated_by`, `last_updated_at`, `type`) VALUES ('3', NOW(), '1', '1', NOW(), 'FINAL');
--
-- Insert predefined answer types.
--
INSERT INTO `examifire`.`answer_type` (`answer_type_id`, `created_at`, `created_by`, `last_updated_by`, `last_updated_at`, `type`) VALUES ('1', NOW(), '1', '1', NOW(), 'MULTIPLE_ANSWER_QUESTION');
INSERT INTO `examifire`.`answer_type` (`answer_type_id`, `created_at`, `created_by`, `last_updated_by`, `last_updated_at`, `type`) VALUES ('2', NOW(), '1', '1', NOW(), 'OPEN_ANSWER_QUESTION');
INSERT INTO `examifire`.`answer_type` (`answer_type_id`, `created_at`, `created_by`, `last_updated_by`, `last_updated_at`, `type`) VALUES ('3', NOW(), '1', '1', NOW(), 'TRUE_FALSE_ANSWER_QUESTION');