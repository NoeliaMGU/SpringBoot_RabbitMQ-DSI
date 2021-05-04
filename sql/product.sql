DROP TABLE IF EXISTS `review`;
CREATE TABLE `review` (
    `reviewId` int(11) NOT NULL AUTO_INCREMENT,
    `productId` varchar(256) NOT NULL,
    `author` varchar(45) NOT NULL,
    `subject` varchar(255) NOT NULL,
    `content` varchar(255) NOT NULL,
    PRIMARY KEY (`reviewId`)
);
INSERT INTO review (productId, author, subject, content) VALUES ('1', 'John','Interiors', 'Molt bonic tot!');
INSERT INTO review (productId, author, subject, content) VALUES ('2', 'Amparo','Jardineria', 'Esplendit');
DROP TABLE IF EXISTS `recommendation`;

CREATE TABLE `recommendation` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `productId` varchar(256) NOT NULL,
    `author` varchar(255) NOT NULL,
    `rate` int(11) NOT NULL,
    `content` varchar(255) NOT NULL,
    PRIMARY KEY (`id`)
);
INSERT INTO recommendation (productId, author, rate, content) VALUES ('1', 'Judith', 3, 'Muy buen producto');
INSERT INTO recommendation (productId, author, rate, content) VALUES ('1', 'Carmen', 3, 'Pica un poco');
INSERT INTO recommendation (productId, author, rate, content) VALUES ('2', 'Judith', 3, 'Muy mal producto :( ');
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
    `id` varchar(256) NOT NULL,
    `name` varchar(45) NOT NULL,
    `description` varchar(255) NOT NULL,
    `weight` int(11) NOT NULL,
    PRIMARY KEY (`id`)
);
INSERT INTO product (id, name, description, weight) VALUES ('1', 'Soap','The finest soap you can face', '7');
INSERT INTO product (id, name, description, weight) VALUES ('2', 'After sun','When there is sun, there is aftersun', '12');
