/*
 Navicat MySQL Data Transfer

 Source Server         : itgou.top
 Source Server Type    : MySQL
 Source Server Version : 50549
 Source Host           : itgou.top
 Source Database       : phone

 Target Server Type    : MySQL
 Target Server Version : 50549
 File Encoding         : utf-8

 Date: 04/27/2018 11:45:43 AM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `callrecords`
-- ----------------------------
DROP TABLE IF EXISTS `callrecords`;
CREATE TABLE `callrecords` (
  `calljiluid` int(11) NOT NULL AUTO_INCREMENT,
  `phone` varchar(255) DEFAULT NULL,
  `calldate` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`calljiluid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `contacts`
-- ----------------------------
DROP TABLE IF EXISTS `contacts`;
CREATE TABLE `contacts` (
  `phoneid` int(11) NOT NULL AUTO_INCREMENT,
  `phonenum` varchar(255) DEFAULT NULL,
  `phonename` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`phoneid`)
) ENGINE=InnoDB AUTO_INCREMENT=1862 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `img`
-- ----------------------------
DROP TABLE IF EXISTS `img`;
CREATE TABLE `img` (
  `imgid` int(11) NOT NULL AUTO_INCREMENT,
  `imgurl` varchar(255) DEFAULT NULL,
  `imgname` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`imgid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `sms`
-- ----------------------------
DROP TABLE IF EXISTS `sms`;
CREATE TABLE `sms` (
  `smsid` int(11) NOT NULL AUTO_INCREMENT,
  `phonenum` varchar(255) DEFAULT NULL,
  `sms` text,
  `smsdate` varchar(255) DEFAULT NULL,
  `sms_id` int(11) DEFAULT NULL,
  `sms_huihua` int(11) DEFAULT NULL,
  PRIMARY KEY (`smsid`)
) ENGINE=InnoDB AUTO_INCREMENT=6335 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Procedure structure for `Upphone`
-- ----------------------------
DROP PROCEDURE IF EXISTS `Upphone`;
delimiter ;;
CREATE DEFINER=`root`@`%` PROCEDURE `Upphone`(name1 VARCHAR(255),num1 VARCHAR(255))
BEGIN
if not exists (select * from contacts where contacts.phonenum = num1 and contacts.phonename = name1) then
	INSERT INTO contacts(`contacts`.`phonename`,`contacts`.`phonenum`) VALUES(name1,num1);
	
else
	SELECT 1;
end if;
END
 ;;
delimiter ;

-- ----------------------------
--  Procedure structure for `Upsms`
-- ----------------------------
DROP PROCEDURE IF EXISTS `Upsms`;
delimiter ;;
CREATE DEFINER=`root`@`%` PROCEDURE `Upsms`(phonenum VARCHAR(255),sms text, sms_id int,smsdate VARCHAR(255),sms_huihua int)
BEGIN
if not exists (select * from sms where sms.sms_id = sms_id) then
	INSERT INTO sms(`sms`.`phonenum`,`sms`.`sms`,`sms`.`sms_id`,`sms`.`smsdate`,`sms`.`sms_huihua`) VALUES(phonenum,sms,sms_id,smsdate,sms_huihua);
	
else
	SELECT 1;
end if;
END
 ;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
