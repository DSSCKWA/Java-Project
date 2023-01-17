-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Czas generowania: 03 Sty 2023, 22:52
-- Wersja serwera: 10.4.24-MariaDB
-- Wersja PHP: 7.4.29

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Baza danych: `medical_service`
--
DROP DATABASE IF EXISTS `medical_service`;
CREATE DATABASE IF NOT EXISTS `medical_service` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `medical_service`;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `clinics`
--

CREATE TABLE IF NOT EXISTS `clinics` (
  `clinic_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `address` varchar(255) NOT NULL,
  `city` varchar(255) NOT NULL,
  PRIMARY KEY (`clinic_id`),
  KEY `city` (`city`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;


INSERT INTO `clinics` (`clinic_id`, `name`, `address`, `city`) VALUES
(1, 'PKmed', 'Warszawska 1', 'Cracov'),
(2, 'ScanMed', 'Szlak 52', 'Katowice'),
(3, 'SpecialMed', 'Kasztanowa 3', 'Warsaw'),
(4, 'LuxMed', 'Basztowa 9', 'Cracov');
-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `doctors`
--

CREATE TABLE IF NOT EXISTS `doctors` (
  `doctor_id` int(11) NOT NULL,
  `clinic_id` int(11) NOT NULL,
  PRIMARY KEY (`doctor_id`,`clinic_id`),
  KEY `doctors_ibfk_1` (`clinic_id`) USING BTREE,
  KEY `doctor_id` (`doctor_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `doctors` (`doctor_id`, `clinic_id`) VALUES
(3, 1),
(3, 2),
(3, 3),
(3, 4),
(4, 1),
(4, 2);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `equipment`
--

CREATE TABLE IF NOT EXISTS `equipment` (
  `equipment_id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `status` enum('IN_USE','IN_REPAIR','DECOMISSIONED','BROKEN') NOT NULL,
  `clinic_id` int(10) NOT NULL,
  PRIMARY KEY (`equipment_id`),
  KEY `clinic_id` (`clinic_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4;

INSERT INTO `equipment` (`equipment_id`, `name`, `status`, `clinic_id`) VALUES
(1, 'X-ray', 'IN_USE', 4),
(2, 'Glukometr', 'IN_USE', 1),
(3, 'Aparat EKG', 'BROKEN', 1),
(4, 'Inhalator', 'IN_REPAIR', 1),
(5, 'Termometr', 'IN_USE', 2),
(6, 'Pulsoksymetr', 'IN_USE', 4),
(7, 'Apteczka', 'DECOMISSIONED', 3),
(8, 'Holder EKG', 'IN_USE', 1),
(9, 'Licznik Geigera', 'BROKEN', 1),
(10, 'Zestaw ratowniczy', 'IN_USE', 2),
(11, 'X-ray', 'IN_USE', 1),
(12, 'Apteczka', 'IN_USE', 1),
(13, 'Termometr', 'BROKEN', 1),
(14, 'Kule', 'IN_USE', 1);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `expertise`
--

CREATE TABLE IF NOT EXISTS `expertise` (
  `doctor_id` int(11) NOT NULL,
  `area_of_expertise` varchar(255) NOT NULL,
  PRIMARY KEY (`doctor_id`,`area_of_expertise`),
  KEY `doctor_id` (`doctor_id`) USING BTREE,
  KEY `area_of_expertise` (`area_of_expertise`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `expertise` (`doctor_id`, `area_of_expertise`) VALUES
(3, 'Dermatolog'),
(3, 'Kardiolog'),
(3, 'Urolog'),
(4, 'Dermatolog'),
(4, 'Okulista'),
(4, 'Pediatra'),
(4, 'Stomatolog');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `schedule`
--

CREATE TABLE IF NOT EXISTS `schedule` (
  `doctor_id` int(11) NOT NULL,
  `clinic_id` int(11) NOT NULL,
  `day` enum('monday','tuesday','wednesday','thursday','friday','saturday','sunday') NOT NULL,
  `start_hour` time NOT NULL,
  `end_hour` time NOT NULL,
  PRIMARY KEY (`doctor_id`,`clinic_id`,`day`),
  KEY `clinic_id` (`clinic_id`) USING BTREE,
  KEY `doctor_id` (`doctor_id`) USING BTREE,
  KEY `day` (`day`) USING BTREE,
  KEY `doctor_clinic` (`doctor_id`,`clinic_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `schedule` (`doctor_id`, `clinic_id`, `day`, `start_hour`, `end_hour`) VALUES
(3, 1, 'monday', '09:00:00', '16:00:00'),
(3, 1, 'wednesday', '08:00:00', '13:00:00'),
(3, 3, 'tuesday', '11:00:00', '18:00:00'),
(3, 3, 'thursday', '11:00:00', '13:00:00');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `surname` varchar(255) NOT NULL,
  `address` varchar(255) NOT NULL,
  `city` varchar(255) NOT NULL,
  `phone_number` int(9) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(30) NOT NULL,
  `permissions` enum('admin','moderator','patient','doctor','guest') NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `email` (`email`) USING BTREE,
  KEY `permissions` (`permissions`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4;

--
-- Zrzut danych tabeli `users`
--

INSERT INTO `users` (`user_id`, `name`, `surname`, `address`, `city`, `phone_number`, `email`, `password`, `permissions`) VALUES
(1, 'Admin', 'Adminovitz', 'Warszawska 1', 'Cracov', 123456789, 'admin@admin.pk', 'admin', 'admin'),
(2, 'Moderator', 'Moder', 'Warszawska 1', 'Cracov', 987654321, 'moderator@moderator.pk', 'moderator', 'moderator'),
(3, 'Krzysztof', 'Nowak', 'Warszawska 2', 'Cracov', 123123123, 'karolina.jez@outlook.com', 'tina', 'doctor'),
(4, 'Anna', 'Kowalska', 'Parkowa 3', 'Warsaw', 321321321, 'anna.kowalska@anna.pk', 'anna123', 'doctor'),
(5, 'Wojciech', 'Malicki', 'Akademikowa 3', 'Rastenburg', 133742069, 'wojciech.malicki09@gmail.com', 'wojtek123', 'patient'),
(6, 'Davido', 'Mountaino', 'Mostowa 1', 'Breslau', 694201337, 'dawidgorski0000@gmail.com', 'dawid123', 'patient');


--
-- Wyzwalacze `users`
--
DELIMITER $$
CREATE TRIGGER `permission_guard` BEFORE UPDATE ON `users` FOR EACH ROW BEGIN
SET @doctors = (SELECT doctors.doctor_id FROM `doctors` WHERE doctors.doctor_id = OLD.user_id LIMIT 1);
SET @schedules = (SELECT schedule.doctor_id FROM `schedule` WHERE schedule.doctor_id = OLD.user_id LIMIT 1);
SET @expertise = (SELECT expertise.doctor_id FROM `expertise` WHERE expertise.doctor_id = OLD.user_id LIMIT 1);
SET @visits = (SELECT visits.doctor_id FROM `visits` WHERE visits.doctor_id = OLD.user_id LIMIT 1);
IF OLD.permissions = 'doctor' AND NEW.permissions != 'doctor'
AND (@doctors IS NOT null
OR @schedules IS NOT null
OR @expertise IS NOT null
OR @visits IS NOT null
)
THEN
SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'ERROR: Trying to change permissions of an active doctor';
END IF;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `visits`
--

CREATE TABLE IF NOT EXISTS `visits` (
  `visit_id` int(10) NOT NULL AUTO_INCREMENT,
  `status` enum('completed','pending','canceled','in_progress','pending_waiting_approval','canceled_waiting_approval') NOT NULL,
  `date` date NOT NULL,
  `time` time NOT NULL,
  `duration` int(5) NOT NULL,
  `rating` int(1) NOT NULL,
  `client_id` int(11) NOT NULL,
  `doctor_id` int(11) NOT NULL,
  PRIMARY KEY (`visit_id`),
  KEY `visits_ibfk_1` (`client_id`) USING BTREE,
  KEY `visits_ibfk_2` (`doctor_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4;


INSERT INTO `visits` (`visit_id`, `status`, `date`, `time`, `duration`, `rating`, `client_id`, `doctor_id`) VALUES
(1, 'completed', '2023-01-03', '12:30:00', 30, 4, 6, 3),
(2, 'canceled', '2023-01-05', '08:00:00', 30, 0, 5, 4),
(3, 'pending', '2023-01-18', '12:00:00', 30, 0, 6, 3),
(4, 'completed', '2023-01-06', '13:00:00', 30, 4, 3, 4),
(5, 'pending_waiting_approval', '2023-01-18', '09:00:00', 30, 0, 5, 3),
(6, 'pending', '2023-01-30', '09:00:00', 30, 0, 3, 4),
(7, 'pending', '2023-01-27', '15:00:00', 30, 0, 5, 3),
(8, 'completed', '2023-01-02', '09:30:00', 30, 0, 5, 3);
--
-- Ograniczenia dla zrzutów tabel
--

--
-- Ograniczenia dla tabeli `doctors`
--
ALTER TABLE `doctors`
  ADD CONSTRAINT `doctors_ibfk_1` FOREIGN KEY (`clinic_id`) REFERENCES `clinics` (`clinic_id`),
  ADD CONSTRAINT `doctors_ibfk_3` FOREIGN KEY (`doctor_id`) REFERENCES `users` (`user_id`);

--
-- Ograniczenia dla tabeli `equipment`
--
ALTER TABLE `equipment`
  ADD CONSTRAINT `equipment_ibfk_1` FOREIGN KEY (`clinic_id`) REFERENCES `clinics` (`clinic_id`);

--
-- Ograniczenia dla tabeli `expertise`
--
ALTER TABLE `expertise`
  ADD CONSTRAINT `expertise_ibfk_1` FOREIGN KEY (`doctor_id`) REFERENCES `users` (`user_id`);

--
-- Ograniczenia dla tabeli `schedule`
--
ALTER TABLE `schedule`
  ADD CONSTRAINT `schedule_ibfk_1` FOREIGN KEY (`doctor_id`,`clinic_id`) REFERENCES `doctors` (`doctor_id`,`clinic_id`);
  
--
-- Ograniczenia dla tabeli `visits`
--
ALTER TABLE `visits`
  ADD CONSTRAINT `visits_ibfk_1` FOREIGN KEY (`client_id`) REFERENCES `users` (`user_id`),
  ADD CONSTRAINT `visits_ibfk_2` FOREIGN KEY (`doctor_id`) REFERENCES `users` (`user_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
