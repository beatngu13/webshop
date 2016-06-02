-- Exportiere Datenbank Struktur für microservice_webshop
DROP DATABASE IF EXISTS `microservice_webshop`;
CREATE DATABASE IF NOT EXISTS `microservice_webshop` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `microservice_webshop`;


-- Exportiere Struktur von Tabelle microservice_webshop.category
DROP TABLE IF EXISTS `category`;
CREATE TABLE IF NOT EXISTS `category` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Exportiere Daten aus Tabelle microservice_webshop.category: ~0 rows (ungefähr)
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` (`id`, `name`) VALUES
	(1, 'Nahrungsmittel'),
	(2, 'Spiele');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;


-- Exportiere Struktur von Tabelle microservice_webshop.customer
DROP TABLE IF EXISTS `customer`;
CREATE TABLE IF NOT EXISTS `customer` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `lastname` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  `role` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Exportiere Daten aus Tabelle microservice_webshop.customer: ~0 rows (ungefähr)
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` (`id`, `name`, `lastname`, `password`, `username`, `role`) VALUES
	(1, 'admin', 'admin', 'admin', 'admin', 1),
	(2, 'Max', 'Mustermann', 'max', 'maxmustermann', 2);
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;


-- Exportiere Struktur von Tabelle microservice_webshop.product
DROP TABLE IF EXISTS `product`;
CREATE TABLE IF NOT EXISTS `product` (
  `id` int(11) NOT NULL,
  `details` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `category_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Exportiere Daten aus Tabelle microservice_webshop.product: ~0 rows (ungefähr)
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` (`id`, `details`, `name`, `price`, `category_id`) VALUES
	(1, 'gelbe, reife Banane. Krumm.', 'Banane', 99.99, 1),
	(2, 'Geiles Spiel.', 'Word of Warcraft', 12.95, 2),
	(3, 'Schweineschnitzel mit leckerer Panade. Pressfleisch-Qualität.', 'Schnitzel', 3.72, 1);
/*!40000 ALTER TABLE `product` ENABLE KEYS */;


-- Exportiere Struktur von Tabelle microservice_webshop.role
DROP TABLE IF EXISTS `role`;
CREATE TABLE IF NOT EXISTS `role` (
  `id` int(11) NOT NULL,
  `level1` int(11) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Exportiere Daten aus Tabelle microservice_webshop.role: ~0 rows (ungefähr)
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` (`id`, `level1`, `type`) VALUES
	(1, 0, 'admin'),
	(2, 1, 'user');
