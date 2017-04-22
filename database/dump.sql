--
-- PostgreSQL database dump
--

-- Dumped from database version 9.4.11
-- Dumped by pg_dump version 9.5.6

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: commands; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE commands (
    id integer NOT NULL,
    name text
);


ALTER TABLE commands OWNER TO postgres;

--
-- Name: commands_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE commands_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE commands_id_seq OWNER TO postgres;

--
-- Name: commands_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE commands_id_seq OWNED BY commands.id;


--
-- Name: events; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE events (
    id integer NOT NULL,
    name text
);


ALTER TABLE events OWNER TO postgres;

--
-- Name: events_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE events_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE events_id_seq OWNER TO postgres;

--
-- Name: events_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE events_id_seq OWNED BY events.id;


--
-- Name: parameters; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE parameters (
    id integer NOT NULL,
    name text,
    id_command integer NOT NULL
);


ALTER TABLE parameters OWNER TO postgres;

--
-- Name: parameters_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE parameters_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE parameters_id_seq OWNER TO postgres;

--
-- Name: parameters_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE parameters_id_seq OWNED BY parameters.id;


--
-- Name: processors; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE processors (
    id integer NOT NULL,
    name text,
    version_id integer NOT NULL
);


ALTER TABLE processors OWNER TO postgres;

--
-- Name: processors_commands; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE processors_commands (
    id integer NOT NULL,
    id_processor integer NOT NULL,
    id_command integer NOT NULL
);


ALTER TABLE processors_commands OWNER TO postgres;

--
-- Name: processors_commands_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE processors_commands_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE processors_commands_id_seq OWNER TO postgres;

--
-- Name: processors_commands_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE processors_commands_id_seq OWNED BY processors_commands.id;


--
-- Name: processors_events; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE processors_events (
    id integer NOT NULL,
    id_processor integer NOT NULL,
    id_event integer NOT NULL
);


ALTER TABLE processors_events OWNER TO postgres;

--
-- Name: processors_events_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE processors_events_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE processors_events_id_seq OWNER TO postgres;

--
-- Name: processors_events_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE processors_events_id_seq OWNED BY processors_events.id;


--
-- Name: processors_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE processors_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE processors_id_seq OWNER TO postgres;

--
-- Name: processors_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE processors_id_seq OWNED BY processors.id;


--
-- Name: processors_versions; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE processors_versions (
    id integer NOT NULL,
    name text
);


ALTER TABLE processors_versions OWNER TO postgres;

--
-- Name: processorsversions_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE processorsversions_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE processorsversions_id_seq OWNER TO postgres;

--
-- Name: processorsversions_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE processorsversions_id_seq OWNED BY processors_versions.id;


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY commands ALTER COLUMN id SET DEFAULT nextval('commands_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY events ALTER COLUMN id SET DEFAULT nextval('events_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY parameters ALTER COLUMN id SET DEFAULT nextval('parameters_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY processors ALTER COLUMN id SET DEFAULT nextval('processors_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY processors_commands ALTER COLUMN id SET DEFAULT nextval('processors_commands_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY processors_events ALTER COLUMN id SET DEFAULT nextval('processors_events_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY processors_versions ALTER COLUMN id SET DEFAULT nextval('processorsversions_id_seq'::regclass);


--
-- Data for Name: commands; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY commands (id, name) FROM stdin;
1	last error
2	pull cash
3	count cash
4	shutdown
5	reboot
6	get status
\.


--
-- Name: commands_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('commands_id_seq', 6, true);


--
-- Data for Name: events; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY events (id, name) FROM stdin;
1	error
2	log
3	caching
\.


--
-- Name: events_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('events_id_seq', 3, true);


--
-- Data for Name: parameters; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY parameters (id, name, id_command) FROM stdin;
1	count	2
2	latency	4
3	latency	5
4	proc	6
5	admin	4
\.


--
-- Name: parameters_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('parameters_id_seq', 4, true);


--
-- Data for Name: processors; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY processors (id, name, version_id) FROM stdin;
2	cash in proc	1
3	cash out proc	1
4	monitor proc	2
5	admin proc	3
\.


--
-- Data for Name: processors_commands; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY processors_commands (id, id_processor, id_command) FROM stdin;
1	2	1
3	3	1
4	4	1
6	2	2
7	3	2
8	2	3
10	5	4
11	5	5
12	5	6
9	3	3
\.


--
-- Name: processors_commands_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('processors_commands_id_seq', 12, true);


--
-- Data for Name: processors_events; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY processors_events (id, id_processor, id_event) FROM stdin;
1	2	1
2	3	1
3	4	1
4	5	1
5	4	2
6	2	3
7	3	3
\.


--
-- Name: processors_events_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('processors_events_id_seq', 7, true);


--
-- Name: processors_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('processors_id_seq', 5, true);


--
-- Data for Name: processors_versions; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY processors_versions (id, name) FROM stdin;
1	first version
2	second version
3	deprecated
\.


--
-- Name: processorsversions_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('processorsversions_id_seq', 3, true);


--
-- Name: commands_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY commands
    ADD CONSTRAINT commands_pkey PRIMARY KEY (id);


--
-- Name: events_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY events
    ADD CONSTRAINT events_pkey PRIMARY KEY (id);


--
-- Name: parameters_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY parameters
    ADD CONSTRAINT parameters_pkey PRIMARY KEY (id);


--
-- Name: processors_commands_id_command_id_processor_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY processors_commands
    ADD CONSTRAINT processors_commands_id_command_id_processor_pk PRIMARY KEY (id_command, id_processor);


--
-- Name: processors_events_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY processors_events
    ADD CONSTRAINT processors_events_pkey PRIMARY KEY (id);


--
-- Name: processors_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY processors
    ADD CONSTRAINT processors_pkey PRIMARY KEY (id);


--
-- Name: processorsversions_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY processors_versions
    ADD CONSTRAINT processorsversions_pkey PRIMARY KEY (id);


--
-- Name: Events_name_uindex; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX "Events_name_uindex" ON events USING btree (name);


--
-- Name: ProcessorsVersions_name_uindex; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX "ProcessorsVersions_name_uindex" ON processors_versions USING btree (name);


--
-- Name: commands_name_uindex; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX commands_name_uindex ON commands USING btree (name);


--
-- Name: processors_events_id_processor_id_event_index; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX processors_events_id_processor_id_event_index ON processors_events USING btree (id_processor, id_event);


--
-- Name: processors_name_uindex; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX processors_name_uindex ON processors USING btree (name);


--
-- Name: parameters_commands_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY parameters
    ADD CONSTRAINT parameters_commands_id_fk FOREIGN KEY (id_command) REFERENCES commands(id);


--
-- Name: processors_commands_commands_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY processors_commands
    ADD CONSTRAINT processors_commands_commands_id_fk FOREIGN KEY (id_command) REFERENCES commands(id);


--
-- Name: processors_commands_processors_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY processors_commands
    ADD CONSTRAINT processors_commands_processors_id_fk FOREIGN KEY (id_processor) REFERENCES processors(id);


--
-- Name: processors_events_events_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY processors_events
    ADD CONSTRAINT processors_events_events_id_fk FOREIGN KEY (id_event) REFERENCES events(id);


--
-- Name: processors_events_processors_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY processors_events
    ADD CONSTRAINT processors_events_processors_id_fk FOREIGN KEY (id_processor) REFERENCES processors(id);


--
-- Name: processors_processorsversions_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY processors
    ADD CONSTRAINT processors_processorsversions_id_fk FOREIGN KEY (version_id) REFERENCES processors_versions(id);


--
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

