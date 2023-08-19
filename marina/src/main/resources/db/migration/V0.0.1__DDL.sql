-- public."location" definition

-- Drop table

-- DROP TABLE public."location";

CREATE TABLE public."location" (
	id int8 NOT NULL GENERATED BY DEFAULT AS IDENTITY,
	latitude float8 NULL,
	longitude float8 NULL,
	CONSTRAINT location_pk PRIMARY KEY (id)
);


-- public.marina definition

-- Drop table

-- DROP TABLE public.marina;

CREATE TABLE public.marina (
	id int8 NOT NULL GENERATED BY DEFAULT AS IDENTITY,
	"name" varchar NULL,
	description varchar NULL,
	location_id int8 NOT NULL,
	CONSTRAINT marina_pk PRIMARY KEY (id),
	CONSTRAINT marina_fk FOREIGN KEY (location_id) REFERENCES public."location"(id)
);


-- public.sailor definition

-- Drop table

-- DROP TABLE public.sailor;

CREATE TABLE public.sailor (
	id int8 NOT NULL GENERATED BY DEFAULT AS IDENTITY,
	account_id int8 NOT NULL,
	"name" varchar NULL,
	location_id int8 NOT NULL,
	description varchar NULL,
	marina_id int8 NOT NULL,
	CONSTRAINT sailor_pk PRIMARY KEY (id),
	CONSTRAINT sailor_location_fk FOREIGN KEY (location_id) REFERENCES public."location"(id),
	CONSTRAINT sailor_marina_fk FOREIGN KEY (marina_id) REFERENCES public.marina(id)
);


-- public.sailortrack definition

-- Drop table

-- DROP TABLE public.sailortrack;

CREATE TABLE public.sailortrack (
	id int8 NOT NULL GENERATED BY DEFAULT AS IDENTITY,
	datetime date NOT NULL,
	sailor_id int8 NOT NULL,
	location_id int8 NOT NULL,
	CONSTRAINT sailortrack_pk PRIMARY KEY (id),
	CONSTRAINT sailortrack_fk FOREIGN KEY (location_id) REFERENCES public."location"(id),
	CONSTRAINT sailortrack_fk_1 FOREIGN KEY (sailor_id) REFERENCES public.sailor(id)
);


INSERT INTO public."location" (latitude,longitude) VALUES
	 (-23.68600570227753,-46.716347233122846),
	 (-23.694269,-46.725243),
	 (-23.69670550869571,-46.741844711809634);

INSERT INTO public.marina ("name",description,location_id) VALUES
	 ('Pera Nautica','Pera Nautica Guarapiranga SP',1),
	 ('YCP','Yatch Club Paulista',3);
	 
INSERT INTO public.sailor (account_id,"name",location_id,description,marina_id) VALUES
	 (1,'Marc',2,'Ilca 7',1),
	 (2,'Ronaldo',2,'Flash 165',2);
	 
INSERT INTO public.sailortrack (datetime,sailor_id,location_id) VALUES
	 ('2022-04-21 00:00:00',1,1),
	 ('2022-04-21 00:00:00',1,2),
	 ('2022-04-21 00:00:00',2,3),
	 ('2022-04-21 00:00:00',2,2);
