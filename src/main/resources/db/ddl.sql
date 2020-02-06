
CREATE TABLE teacher (
	id bigserial NOT NULL,
	"name" varchar(45) NOT NULL,
	surname varchar(45) NOT NULL,
	gender varchar(20) NOT NULL,
	birthday_date timestamp NOT NULL,
	phone varchar(20) NOT NULL UNIQUE,
	UNIQUE(name, surname),
	PRIMARY key (id)
);
CREATE UNIQUE INDEX teacher_phone_uindex ON public.teacher USING btree (phone);


CREATE TABLE public.children_group (
	id bigserial NOT NULL,
	group_number int NULL UNIQUE,
	group_name varchar(30) NOT NULL,
	teacher_id int4 NULL,
	age_group int NOT NULL,
	CONSTRAINT children_group_pk PRIMARY KEY (id),
	CONSTRAINT children_group_teacher_id_fk FOREIGN KEY (teacher_id) REFERENCES teacher(id) ON DELETE SET NULL);

CREATE UNIQUE INDEX children_group_group_name_uindex ON children_group USING btree (group_name);


CREATE TABLE child (
	id bigserial NOT NULL,
	"name" varchar(40) NOT NULL,
	surname varchar(40) NOT NULL,
	age int NOT NULL,
	phone varchar(20) NULL UNIQUE,
	group_id int NOT NULL,
	CONSTRAINT children_pk PRIMARY KEY (id),
	uniue(name, surname),
	CONSTRAINT child_children_group_id_fk FOREIGN KEY (group_id) REFERENCES children_group(id));


CREATE TABLE specialization (
	id bigserial NOT NULL,
	"name" varchar(50) NOT NULL UNIQUE,
	description varchar(200) NOT NULL,
	CONSTRAINT specialization_pk PRIMARY KEY (id)
);
CREATE UNIQUE INDEX specialization_name_uindex ON specialization USING btree (name);

CREATE TABLE teacher_specialization (
	teacher_id int NOT NULL,
	specialization_id int NOT NULL,
	CONSTRAINT teacher_specialization_pk PRIMARY KEY (teacher_id, specialization_id),
	CONSTRAINT teacher_specialization_specialization_id_fk FOREIGN KEY (specialization_id) REFERENCES specialization(id) ON DELETE CASCADE,
	CONSTRAINT teacher_specialization_teacher_id_fk FOREIGN KEY (teacher_id) REFERENCES teacher(id) ON DELETE CASCADE
);
