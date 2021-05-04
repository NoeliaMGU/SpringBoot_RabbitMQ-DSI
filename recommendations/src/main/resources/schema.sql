DROP TABLE IF EXISTS `recommendation`;

CREATE TABLE `recommendation` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `productId` varchar(256) NOT NULL,
    `author` varchar(255) NOT NULL,
    `rate` int(11) NOT NULL,
    `content` varchar(255) NOT NULL,
    PRIMARY KEY (`id`)
);