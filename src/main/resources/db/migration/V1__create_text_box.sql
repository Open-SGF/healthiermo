-- Existing installations already have this table. Leave their schema and content untouched.
CREATE TABLE IF NOT EXISTS text_box (
    box varchar(255) NOT NULL,
    pie varchar(255) NOT NULL,
    text text,
    title varchar(255) DEFAULT NULL,
    PRIMARY KEY (box, pie)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
