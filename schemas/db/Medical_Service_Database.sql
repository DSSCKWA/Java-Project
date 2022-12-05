-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Czas generowania: 05 Gru 2022, 21:18
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
  `address` varchar(255) NOT NULL,
  `city` varchar(255) NOT NULL,
  PRIMARY KEY (`clinic_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `doctors`
--

CREATE TABLE IF NOT EXISTS `doctors` (
  `doctor_id` int(11) NOT NULL,
  `clinic_id` int(11) NOT NULL,
  PRIMARY KEY (`doctor_id`,`clinic_id`),
  KEY `doctors_ibfk_1` (`clinic_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

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
  KEY `clinic_id` (`clinic_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `expertise`
--

CREATE TABLE IF NOT EXISTS `expertise` (
  `doctor_id` int(11) NOT NULL,
  `area_of_expertise` varchar(255) NOT NULL,
  PRIMARY KEY (`doctor_id`,`area_of_expertise`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

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
  KEY `clinic_id` (`clinic_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

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
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `visits`
--

CREATE TABLE IF NOT EXISTS `visits` (
  `status` enum('completed','pending','canceled','in_progress') NOT NULL,
  `date` date NOT NULL,
  `time` time NOT NULL,
  `duration` int(5) NOT NULL,
  `rating` int(1) NOT NULL,
  `client_id` int(11) NOT NULL,
  `doctor_id` int(11) NOT NULL,
  PRIMARY KEY (`date`,`time`,`client_id`,`doctor_id`),
  KEY `visits_ibfk_1` (`client_id`),
  KEY `visits_ibfk_2` (`doctor_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

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
  ADD CONSTRAINT `expertise_ibfk_1` FOREIGN KEY (`doctor_id`) REFERENCES `doctors` (`doctor_id`);

--
-- Ograniczenia dla tabeli `schedule`
--
ALTER TABLE `schedule`
  ADD CONSTRAINT `schedule_ibfk_1` FOREIGN KEY (`doctor_id`) REFERENCES `doctors` (`doctor_id`),
  ADD CONSTRAINT `schedule_ibfk_2` FOREIGN KEY (`clinic_id`) REFERENCES `clinics` (`clinic_id`);

--
-- Ograniczenia dla tabeli `visits`
--
ALTER TABLE `visits`
  ADD CONSTRAINT `visits_ibfk_1` FOREIGN KEY (`client_id`) REFERENCES `users` (`user_id`),
  ADD CONSTRAINT `visits_ibfk_2` FOREIGN KEY (`doctor_id`) REFERENCES `doctors` (`doctor_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
