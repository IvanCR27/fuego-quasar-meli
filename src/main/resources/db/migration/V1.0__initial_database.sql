CREATE TABLE satellite (
	id bigserial NOT NULL,
	name varchar(255) NOT NULL UNIQUE,
	distance NUMERIC(17,10) NOT NULL,
	message varchar(512) NOT NULL,
	CONSTRAINT cuenta_congelada_pkey PRIMARY KEY (id)
)