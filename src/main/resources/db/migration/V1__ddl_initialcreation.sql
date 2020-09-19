CREATE TABLE public.st_address (
	id uuid NOT NULL,
	street_name varchar(2000) NOT NULL,
	"number" varchar (1000) NOT NULL,
	complement varchar(1000),
	neighbourhood varchar(1000) NOT NULL,
	city varchar(1000) NOT NULL,
	state varchar(1000) NOT NULL,
	country varchar(1000) NOT NULL,
	zipcode varchar(1000) NOT NULL,
	latitude varchar(1000) ,
	longitude varchar(1000),
	CONSTRAINT address_pk PRIMARY KEY (id)
);
