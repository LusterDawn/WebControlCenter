

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for car_cycle_data
-- ----------------------------
DROP TABLE IF EXISTS `car_cycle_data`;
/*num, id, control_cycle, encoder_left, encoder_right, 
motor_control_left, motor_control_right, 
motor_target_left, motor_target_right, 
steering_gear_control, off_center, 
electromagnetic_left, electromagnetic_mid, electromagnetic_right, 
acceleration_x, acceleration_y, acceleration_z, 
gyroscope_x, gyroscope_y, gyroscope_z, 
magnetometer_x, magnetometer_y, magnetometer_z, 
steering_KP, steering_KI, steering_KD, 
servo_KP, servo_KI, servo_KD, 
speed, extra_one, extra_two
*/
CREATE TABLE `car_cycle_data` (
  `num` varchar(255) DEFAULT NULL,
  `id` varchar(255) DEFAULT NULL,
  `control_cycle` varchar(255) DEFAULT NULL, 
  `encoder_right` varchar(255) DEFAULT NULL,
  `encoder_left` varchar(255) DEFAULT NULL,
  `motor_control_left` varchar(255) DEFAULT NULL, 
  `motor_control_right` varchar(255) DEFAULT NULL,  
  `motor_target_left` varchar(255) DEFAULT NULL, 
  `motor_target_right` varchar(255) DEFAULT NULL,
  `steering_gear_control` varchar(255) DEFAULT NULL,  
  `off_center` varchar(255) DEFAULT NULL,    
  `electromagnetic_left` varchar(255) DEFAULT NULL,
  `electromagnetic_mid` varchar(255) DEFAULT NULL,
  `electromagnetic_right` varchar(255) DEFAULT NULL,
  `acceleration_x` varchar(255) DEFAULT NULL,
  `acceleration_y` varchar(255) DEFAULT NULL,
  `acceleration_z` varchar(255) DEFAULT NULL,
  `gyroscope_x` varchar(255) DEFAULT NULL,
  `gyroscope_y` varchar(255) DEFAULT NULL,
  `gyroscope_z` varchar(255) DEFAULT NULL,
  `magnetometer_x` varchar(255) DEFAULT NULL,
  `magnetometer_y` varchar(255) DEFAULT NULL,
  `magnetometer_z` varchar(255) DEFAULT NULL,
  `steering_KP` varchar(255) DEFAULT NULL,
  `steering_KI` varchar(255) DEFAULT NULL,
  `steering_KD` varchar(255) DEFAULT NULL,
  `servo_KP` varchar(255) DEFAULT NULL,
  `servo_KI` varchar(255) DEFAULT NULL,
  `servo_KD` varchar(255) DEFAULT NULL,
  `speed` varchar(255) DEFAULT NULL,
  `extra_one` varchar(255) DEFAULT NULL,
  `extra_two` varchar(255) DEFAULT NULL  
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for car_user
-- ----------------------------
DROP TABLE IF EXISTS `car_user`;
CREATE TABLE `car_user` (
  `id` bigint NOT NULL,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

SET FOREIGN_KEY_CHECKS = 1;
