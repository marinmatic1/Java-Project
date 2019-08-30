-- phpMyAdmin SQL Dump
-- version 4.9.0.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Aug 30, 2019 at 04:31 PM
-- Server version: 10.4.6-MariaDB
-- PHP Version: 7.1.31

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `najam`
--

-- --------------------------------------------------------

--
-- Table structure for table `korisnik`
--

CREATE TABLE `korisnik` (
  `id_vlasnik` int(11) NOT NULL,
  `ime` varchar(25) COLLATE utf8_unicode_ci NOT NULL,
  `prezime` varchar(25) COLLATE utf8_unicode_ci NOT NULL,
  `korisnickoIme` varchar(25) COLLATE utf8_unicode_ci NOT NULL,
  `lozinka` varchar(25) COLLATE utf8_unicode_ci NOT NULL,
  `uloga` enum('ADMIN','VLASNIK','KLIJENT','') COLLATE utf8_unicode_ci NOT NULL,
  `brojTelefona` varchar(25) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `korisnik`
--

INSERT INTO `korisnik` (`id_vlasnik`, `ime`, `prezime`, `korisnickoIme`, `lozinka`, `uloga`, `brojTelefona`) VALUES
(1, 'Anamarija', 'Dumancic', 'anamarija', 'anamarija', 'ADMIN', '063907554'),
(2, 'Marin', 'Matic', 'marin', 'marin', 'VLASNIK', '123456789'),
(3, 'Pero', 'Peric', 'pero', 'pero', 'KLIJENT', '063624864'),
(7, 'Boris', 'Mati?', 'boris', 'boris', 'ADMIN', '063773270');

-- --------------------------------------------------------

--
-- Table structure for table `mjesto`
--

CREATE TABLE `mjesto` (
  `id_mjesto` int(11) NOT NULL,
  `nazivMjesta` varchar(25) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `mjesto`
--

INSERT INTO `mjesto` (`id_mjesto`, `nazivMjesta`) VALUES
(1, 'Mostar'),
(2, 'Kupres'),
(3, 'Sarajevo'),
(4, 'Zagreb'),
(5, 'Ljubuški'),
(7, 'Široki Brijeg'),
(9, 'Split'),
(10, 'Makarska');

-- --------------------------------------------------------

--
-- Table structure for table `stan`
--

CREATE TABLE `stan` (
  `id_stan` int(11) NOT NULL,
  `vlasnik_fk` int(11) NOT NULL,
  `adresa` varchar(55) COLLATE utf8_unicode_ci NOT NULL,
  `brojKvadrata` varchar(25) COLLATE utf8_unicode_ci NOT NULL,
  `brojSoba` varchar(25) COLLATE utf8_unicode_ci NOT NULL,
  `cijena` varchar(25) COLLATE utf8_unicode_ci NOT NULL,
  `mjesto_fk` int(11) NOT NULL,
  `vrstaStana_fk` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `stan`
--

INSERT INTO `stan` (`id_stan`, `vlasnik_fk`, `adresa`, `brojKvadrata`, `brojSoba`, `cijena`, `mjesto_fk`, `vrstaStana_fk`) VALUES
(1, 1, 'Zagrebacka 126', '100', '3', '600 KM', 1, 1),
(2, 2, 'mostar', '188', '4', '400', 1, 1),
(6, 2, 'mostar', '120', '4', '600 KM', 1, 2);

-- --------------------------------------------------------

--
-- Table structure for table `ugovor`
--

CREATE TABLE `ugovor` (
  `id_ugovor` int(11) NOT NULL,
  `opis` text COLLATE utf8_unicode_ci NOT NULL,
  `datum` date NOT NULL,
  `stan_fk` int(11) NOT NULL,
  `Potvrda` tinyint(1) NOT NULL,
  `imeKlijenta` varchar(45) CHARACTER SET utf8 COLLATE utf8_croatian_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `ugovor`
--

INSERT INTO `ugovor` (`id_ugovor`, `opis`, `datum`, `stan_fk`, `Potvrda`, `imeKlijenta`) VALUES
(8, 'ovo je neki opis', '2019-08-21', 6, 0, 'pero'),
(10, 'Neki opis 2', '2019-08-06', 6, 0, 'pero'),
(11, 'Ovo je neki opis 3', '2019-08-12', 6, 0, 'pero'),
(17, 'neki opis 4', '2019-08-06', 6, 0, 'pero'),
(18, 'neki opis 4', '2019-08-06', 6, 0, 'pero'),
(19, 'neki opis 4', '2019-08-20', 6, 0, 'pero'),
(20, 'neki opiis 54', '2019-08-06', 2, 0, 'pero'),
(21, 'neki opiis 54', '2019-08-06', 1, 0, 'pero');

-- --------------------------------------------------------

--
-- Table structure for table `vlasnikugovor`
--

CREATE TABLE `vlasnikugovor` (
  `id_vlasnikUgovor` int(11) NOT NULL,
  `ime` varchar(45) NOT NULL,
  `prezime` varchar(45) NOT NULL,
  `ugovor_fk` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `vlasnikugovor`
--

INSERT INTO `vlasnikugovor` (`id_vlasnikUgovor`, `ime`, `prezime`, `ugovor_fk`) VALUES
(1, 'Marin', 'Matic', 20),
(2, 'Anamarija', 'Dumancic', 21);

-- --------------------------------------------------------

--
-- Table structure for table `vrstastana`
--

CREATE TABLE `vrstastana` (
  `id_vrstaStana` int(11) NOT NULL,
  `vrstaStana` varchar(25) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `vrstastana`
--

INSERT INTO `vrstastana` (`id_vrstaStana`, `vrstaStana`) VALUES
(1, 'namješten'),
(2, 'nenamješten');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `korisnik`
--
ALTER TABLE `korisnik`
  ADD PRIMARY KEY (`id_vlasnik`);

--
-- Indexes for table `mjesto`
--
ALTER TABLE `mjesto`
  ADD PRIMARY KEY (`id_mjesto`);

--
-- Indexes for table `stan`
--
ALTER TABLE `stan`
  ADD PRIMARY KEY (`id_stan`),
  ADD KEY `vlasnik_fk` (`vlasnik_fk`,`mjesto_fk`,`vrstaStana_fk`),
  ADD KEY `mjesto_fk` (`mjesto_fk`),
  ADD KEY `vrstaStana_fk` (`vrstaStana_fk`);

--
-- Indexes for table `ugovor`
--
ALTER TABLE `ugovor`
  ADD PRIMARY KEY (`id_ugovor`),
  ADD KEY `stan_fk` (`stan_fk`);

--
-- Indexes for table `vlasnikugovor`
--
ALTER TABLE `vlasnikugovor`
  ADD PRIMARY KEY (`id_vlasnikUgovor`),
  ADD KEY `ugovor_fk` (`ugovor_fk`);

--
-- Indexes for table `vrstastana`
--
ALTER TABLE `vrstastana`
  ADD PRIMARY KEY (`id_vrstaStana`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `korisnik`
--
ALTER TABLE `korisnik`
  MODIFY `id_vlasnik` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `mjesto`
--
ALTER TABLE `mjesto`
  MODIFY `id_mjesto` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `stan`
--
ALTER TABLE `stan`
  MODIFY `id_stan` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `ugovor`
--
ALTER TABLE `ugovor`
  MODIFY `id_ugovor` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- AUTO_INCREMENT for table `vlasnikugovor`
--
ALTER TABLE `vlasnikugovor`
  MODIFY `id_vlasnikUgovor` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `vrstastana`
--
ALTER TABLE `vrstastana`
  MODIFY `id_vrstaStana` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `stan`
--
ALTER TABLE `stan`
  ADD CONSTRAINT `stan_ibfk_1` FOREIGN KEY (`mjesto_fk`) REFERENCES `mjesto` (`id_mjesto`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `stan_ibfk_2` FOREIGN KEY (`vlasnik_fk`) REFERENCES `korisnik` (`id_vlasnik`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `stan_ibfk_3` FOREIGN KEY (`vrstaStana_fk`) REFERENCES `vrstastana` (`id_vrstaStana`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `ugovor`
--
ALTER TABLE `ugovor`
  ADD CONSTRAINT `ugovor_ibfk_2` FOREIGN KEY (`stan_fk`) REFERENCES `stan` (`id_stan`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `vlasnikugovor`
--
ALTER TABLE `vlasnikugovor`
  ADD CONSTRAINT `vlasnikugovor_ibfk_1` FOREIGN KEY (`ugovor_fk`) REFERENCES `ugovor` (`id_ugovor`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
