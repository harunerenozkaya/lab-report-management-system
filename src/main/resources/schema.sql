-- Table: public.reports

-- DROP TABLE IF EXISTS public.reports;

CREATE TABLE IF NOT EXISTS public.reports
(
    report_id bigint NOT NULL,
    patient_name character(30) COLLATE pg_catalog."default",
    patient_surname character(30) COLLATE pg_catalog."default",
    patient_tc bigint,
    diagnosis_title character(30) COLLATE pg_catalog."default",
    diagnosis_detail text COLLATE pg_catalog."default",
    user_id bigint,
    report_date date,
    user_name character(30) COLLATE pg_catalog."default",
    user_surname character(30) COLLATE pg_catalog."default",
    CONSTRAINT reports_pkey PRIMARY KEY (report_id)
)

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.reports
    OWNER to postgres;


-- Table: public.users

-- DROP TABLE IF EXISTS public.users;

CREATE TABLE IF NOT EXISTS public.users
(
    user_id bigint NOT NULL,
    user_password character(100) COLLATE pg_catalog."default",
    user_name character(100) COLLATE pg_catalog."default",
    user_surname character(100) COLLATE pg_catalog."default",
    role character(100) COLLATE pg_catalog."default",
    CONSTRAINT users_pkey PRIMARY KEY (user_id)
)

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.users
    OWNER to postgres;