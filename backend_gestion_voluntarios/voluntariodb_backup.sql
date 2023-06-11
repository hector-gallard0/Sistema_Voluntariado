PGDMP     0            
        {            voluntariodb    14.7    15.2 s               0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false                       0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false                       0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false                       1262    17564    voluntariodb    DATABASE        CREATE DATABASE voluntariodb WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'Spanish_Spain.1252';
    DROP DATABASE voluntariodb;
                postgres    false                        2615    2200    public    SCHEMA     2   -- *not* creating schema, since initdb creates it
 2   -- *not* dropping schema, since initdb creates it
                postgres    false                       0    0    SCHEMA public    ACL     Q   REVOKE USAGE ON SCHEMA public FROM PUBLIC;
GRANT ALL ON SCHEMA public TO PUBLIC;
                   postgres    false    5                        3079    17732    postgis 	   EXTENSION     ;   CREATE EXTENSION IF NOT EXISTS postgis WITH SCHEMA public;
    DROP EXTENSION postgis;
                   false    5                       0    0    EXTENSION postgis    COMMENT     ^   COMMENT ON EXTENSION postgis IS 'PostGIS geometry and geography spatial types and functions';
                        false    2            �           1255    18770    log_operation()    FUNCTION     �  CREATE FUNCTION public.log_operation() RETURNS trigger
    LANGUAGE plpgsql
    AS $$DECLARE
usuario varchar;
consulta varchar;
BEGIN
SELECT tp.usuario, tp.consulta INTO usuario, consulta
FROM temp_params tp;

INSERT INTO log_query (id_log, usuario, hora, operacion, tabla, consulta)
VALUES ((SELECT nextval('log_seq')),
		usuario, now(), TG_OP, TG_TABLE_NAME, consulta);
		
RETURN NULL;
END;$$;
 &   DROP FUNCTION public.log_operation();
       public          postgres    false    5            �           1255    18771    obtener_reporte_operaciones() 	   PROCEDURE       CREATE PROCEDURE public.obtener_reporte_operaciones()
    LANGUAGE plpgsql
    AS $$
DECLARE fila RECORD;
DECLARE usuario_max_insert varchar;
DECLARE max_insert integer;
DECLARE usuario_max_update varchar;
DECLARE max_update integer;
DECLARE usuario_max_delete varchar;
DECLARE max_delete integer;
BEGIN	
	max_insert := 0;
	usuario_max_insert := 'NONE';
	max_update := 0;
	usuario_max_update := 'NONE';
	max_delete := 0;
	usuario_max_delete := 'NONE';
	FOR fila IN 
	SELECT usuario, operacion, COUNT(*) as cantidad
	FROM log_query
	GROUP BY usuario, operacion	
	ORDER BY cantidad DESC
	LOOP		 
		IF fila.operacion = 'INSERT' AND fila.cantidad > max_insert THEN
			max_insert := fila.cantidad;
			usuario_max_insert := fila.usuario;
		END IF;
		IF fila.operacion = 'UPDATE' AND fila.cantidad > max_update THEN
			max_update := fila.cantidad;
			usuario_max_update := fila.usuario;
		END IF;
		IF fila.operacion = 'DELETE' AND fila.cantidad > max_delete THEN
			max_delete := fila.cantidad;
			usuario_max_delete := fila.usuario;
		END IF;
	END LOOP;
	RAISE NOTICE 'max insert = % / max update = % / max delete = %', usuario_max_insert, usuario_max_update, usuario_max_delete;
	FOR fila IN
	SELECT *
	FROM log_query
	ORDER BY usuario
	LOOP
		IF fila.usuario = usuario_max_insert AND fila.operacion = 'INSERT' THEN
			RAISE NOTICE 'Usuario: % | Operacion: INSERT | Consulta: %', usuario_max_insert, fila.consulta;
		ELSIF fila.usuario = usuario_max_update AND fila.operacion = 'UPDATE' THEN
			RAISE NOTICE 'Usuario: % | Operacion: UPDATE | Consulta: %', usuario_max_update, fila.consulta;
		ELSIF fila.usuario = usuario_max_delete AND fila.operacion = 'DELETE' THEN
			RAISE NOTICE 'Usuario: % | Operacion: DELETE | Consulta: %', usuario_max_delete, fila.consulta;
		END IF;
	END LOOP;
END;
$$;
 5   DROP PROCEDURE public.obtener_reporte_operaciones();
       public          postgres    false    5            �            1259    17565    coordinador    TABLE     �   CREATE TABLE public.coordinador (
    id_coordinador integer NOT NULL,
    id_usuario integer NOT NULL,
    id_institucion integer NOT NULL
);
    DROP TABLE public.coordinador;
       public         heap    postgres    false    5            �            1259    17568    coordinador_seq    SEQUENCE     �   CREATE SEQUENCE public.coordinador_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 &   DROP SEQUENCE public.coordinador_seq;
       public          postgres    false    5            �            1259    17569    eme_habilidad    TABLE     �   CREATE TABLE public.eme_habilidad (
    id_eme_habilidad integer NOT NULL,
    id_emergencia integer,
    id_habilidad integer
);
 !   DROP TABLE public.eme_habilidad;
       public         heap    postgres    false    5            �            1259    18772    eme_habilidad_seq    SEQUENCE     �   CREATE SEQUENCE public.eme_habilidad_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 (   DROP SEQUENCE public.eme_habilidad_seq;
       public          postgres    false    5            �            1259    17572 
   emergencia    TABLE       CREATE TABLE public.emergencia (
    id_emergencia integer NOT NULL,
    nombre character varying(100),
    descripcion character varying(400),
    fecha_inicio date,
    fecha_fin date,
    id_institucion integer NOT NULL,
    geom public.geometry(Point,4326)
);
    DROP TABLE public.emergencia;
       public         heap    postgres    false    5    2    2    5    2    5    5    2    5    2    5    2    5    2    5    2    5            �            1259    17577    emergencia_seq    SEQUENCE     �   CREATE SEQUENCE public.emergencia_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 %   DROP SEQUENCE public.emergencia_seq;
       public          postgres    false    5            �            1259    17578    estado    TABLE     f   CREATE TABLE public.estado (
    id_estado integer NOT NULL,
    descripcion character varying(20)
);
    DROP TABLE public.estado;
       public         heap    postgres    false    5            �            1259    17581 
   estado_seq    SEQUENCE     �   CREATE SEQUENCE public.estado_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 !   DROP SEQUENCE public.estado_seq;
       public          postgres    false    5            �            1259    17582 	   habilidad    TABLE     {   CREATE TABLE public.habilidad (
    id_habilidad numeric(4,0) NOT NULL,
    descripcion character varying(100) NOT NULL
);
    DROP TABLE public.habilidad;
       public         heap    postgres    false    5            �            1259    17585    habilidad_seq    SEQUENCE     �   CREATE SEQUENCE public.habilidad_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 $   DROP SEQUENCE public.habilidad_seq;
       public          postgres    false    5            �            1259    17586    institucion    TABLE     �   CREATE TABLE public.institucion (
    id_institucion numeric(4,0) NOT NULL,
    nombre character varying(100),
    descripcion character varying(400)
);
    DROP TABLE public.institucion;
       public         heap    postgres    false    5            �            1259    18773    institucion_seq    SEQUENCE     �   CREATE SEQUENCE public.institucion_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 &   DROP SEQUENCE public.institucion_seq;
       public          postgres    false    5            �            1259    18774 	   log_query    TABLE     �   CREATE TABLE public.log_query (
    usuario character varying,
    hora time without time zone,
    id_log integer NOT NULL,
    operacion character varying,
    tabla character varying,
    consulta character varying
);
    DROP TABLE public.log_query;
       public         heap    postgres    false    5            �            1259    18779    log_seq    SEQUENCE        CREATE SEQUENCE public.log_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
    DROP SEQUENCE public.log_seq;
       public          postgres    false    5            �            1259    17591    ranking    TABLE     �   CREATE TABLE public.ranking (
    id_ranking integer NOT NULL,
    id_voluntario integer,
    id_tarea integer,
    puntaje numeric(8,0),
    flag_invitado numeric(1,0),
    flag_participa numeric(1,0)
);
    DROP TABLE public.ranking;
       public         heap    postgres    false    5                        0    0    TABLE ranking    COMMENT     u   COMMENT ON TABLE public.ranking IS 'los flgInvitado, flgParticipa 1 si la respuesta es si, 2 si la respuesta es no';
          public          postgres    false    230            �            1259    17594    rol    TABLE     `   CREATE TABLE public.rol (
    id_rol integer NOT NULL,
    nombre character varying NOT NULL
);
    DROP TABLE public.rol;
       public         heap    postgres    false    5            �            1259    18780    rol_seq    SEQUENCE        CREATE SEQUENCE public.rol_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
    DROP SEQUENCE public.rol_seq;
       public          postgres    false    5            �            1259    17599    tarea    TABLE     U  CREATE TABLE public.tarea (
    id_tarea integer NOT NULL,
    nombre character varying(60),
    descripcion character varying(300),
    cant_vol_requeridos numeric(4,0),
    cant_vol_inscritos numeric(4,0),
    id_emergencia integer,
    fecha_inicio date,
    fecha_fin date,
    id_estado integer,
    geom public.geometry(Point,4326)
);
    DROP TABLE public.tarea;
       public         heap    postgres    false    2    2    5    2    5    5    2    5    2    5    2    5    2    5    2    5    5            �            1259    17602    tarea_habilidad    TABLE     �   CREATE TABLE public.tarea_habilidad (
    id_tarea_habilidad integer NOT NULL,
    id_eme_habilidad integer,
    id_tarea integer
);
 #   DROP TABLE public.tarea_habilidad;
       public         heap    postgres    false    5            �            1259    18781    tarea_habilidad_seq    SEQUENCE     �   CREATE SEQUENCE public.tarea_habilidad_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 *   DROP SEQUENCE public.tarea_habilidad_seq;
       public          postgres    false    5            �            1259    17605 	   tarea_seq    SEQUENCE     �   CREATE SEQUENCE public.tarea_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
     DROP SEQUENCE public.tarea_seq;
       public          postgres    false    5            �            1259    17606    usuario    TABLE     �   CREATE TABLE public.usuario (
    id_usuario integer NOT NULL,
    nombre text NOT NULL,
    apellido text NOT NULL,
    email text NOT NULL,
    password text NOT NULL
);
    DROP TABLE public.usuario;
       public         heap    postgres    false    5            �            1259    17611    usuario_rol    TABLE     �   CREATE TABLE public.usuario_rol (
    id_usuario_rol integer NOT NULL,
    id_usuario integer NOT NULL,
    id_rol integer NOT NULL
);
    DROP TABLE public.usuario_rol;
       public         heap    postgres    false    5            �            1259    17614    usuario_rol_seq    SEQUENCE     �   CREATE SEQUENCE public.usuario_rol_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 &   DROP SEQUENCE public.usuario_rol_seq;
       public          postgres    false    5            �            1259    17615    usuario_seq    SEQUENCE     t   CREATE SEQUENCE public.usuario_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 "   DROP SEQUENCE public.usuario_seq;
       public          postgres    false    5            �            1259    17616    vol_habilidad    TABLE     �   CREATE TABLE public.vol_habilidad (
    id_voluntario_habilidad integer NOT NULL,
    id_voluntario integer,
    id_habilidad integer
);
 !   DROP TABLE public.vol_habilidad;
       public         heap    postgres    false    5            �            1259    17619    vol_habilidad_seq    SEQUENCE     �   CREATE SEQUENCE public.vol_habilidad_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 (   DROP SEQUENCE public.vol_habilidad_seq;
       public          postgres    false    5            �            1259    17620 
   voluntario    TABLE     �   CREATE TABLE public.voluntario (
    id_voluntario integer NOT NULL,
    id_usuario integer NOT NULL,
    geom public.geometry(Point,4326)
);
    DROP TABLE public.voluntario;
       public         heap    postgres    false    5    2    2    5    2    5    5    2    5    2    5    2    5    2    5    2    5            �            1259    17623    voluntario_seq    SEQUENCE     �   CREATE SEQUENCE public.voluntario_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 %   DROP SEQUENCE public.voluntario_seq;
       public          postgres    false    5            �          0    17565    coordinador 
   TABLE DATA           Q   COPY public.coordinador (id_coordinador, id_usuario, id_institucion) FROM stdin;
    public          postgres    false    220   ��       �          0    17569    eme_habilidad 
   TABLE DATA           V   COPY public.eme_habilidad (id_eme_habilidad, id_emergencia, id_habilidad) FROM stdin;
    public          postgres    false    222   �       �          0    17572 
   emergencia 
   TABLE DATA           w   COPY public.emergencia (id_emergencia, nombre, descripcion, fecha_inicio, fecha_fin, id_institucion, geom) FROM stdin;
    public          postgres    false    223   3�                  0    17578    estado 
   TABLE DATA           8   COPY public.estado (id_estado, descripcion) FROM stdin;
    public          postgres    false    225   p�                 0    17582 	   habilidad 
   TABLE DATA           >   COPY public.habilidad (id_habilidad, descripcion) FROM stdin;
    public          postgres    false    227   ȑ                 0    17586    institucion 
   TABLE DATA           J   COPY public.institucion (id_institucion, nombre, descripcion) FROM stdin;
    public          postgres    false    229   R�                 0    18774 	   log_query 
   TABLE DATA           V   COPY public.log_query (usuario, hora, id_log, operacion, tabla, consulta) FROM stdin;
    public          postgres    false    250   ��                 0    17591    ranking 
   TABLE DATA           n   COPY public.ranking (id_ranking, id_voluntario, id_tarea, puntaje, flag_invitado, flag_participa) FROM stdin;
    public          postgres    false    230   �                 0    17594    rol 
   TABLE DATA           -   COPY public.rol (id_rol, nombre) FROM stdin;
    public          postgres    false    231   �       +          0    18042    spatial_ref_sys 
   TABLE DATA           X   COPY public.spatial_ref_sys (srid, auth_name, auth_srid, srtext, proj4text) FROM stdin;
    public          postgres    false    244   L�                 0    17599    tarea 
   TABLE DATA           �   COPY public.tarea (id_tarea, nombre, descripcion, cant_vol_requeridos, cant_vol_inscritos, id_emergencia, fecha_inicio, fecha_fin, id_estado, geom) FROM stdin;
    public          postgres    false    232   i�                 0    17602    tarea_habilidad 
   TABLE DATA           Y   COPY public.tarea_habilidad (id_tarea_habilidad, id_eme_habilidad, id_tarea) FROM stdin;
    public          postgres    false    233   ��       
          0    17606    usuario 
   TABLE DATA           P   COPY public.usuario (id_usuario, nombre, apellido, email, password) FROM stdin;
    public          postgres    false    235   &�                 0    17611    usuario_rol 
   TABLE DATA           I   COPY public.usuario_rol (id_usuario_rol, id_usuario, id_rol) FROM stdin;
    public          postgres    false    236   ��                 0    17616    vol_habilidad 
   TABLE DATA           ]   COPY public.vol_habilidad (id_voluntario_habilidad, id_voluntario, id_habilidad) FROM stdin;
    public          postgres    false    239   !�                 0    17620 
   voluntario 
   TABLE DATA           E   COPY public.voluntario (id_voluntario, id_usuario, geom) FROM stdin;
    public          postgres    false    241   >�       !           0    0    coordinador_seq    SEQUENCE SET     >   SELECT pg_catalog.setval('public.coordinador_seq', 45, true);
          public          postgres    false    221            "           0    0    eme_habilidad_seq    SEQUENCE SET     @   SELECT pg_catalog.setval('public.eme_habilidad_seq', 1, false);
          public          postgres    false    248            #           0    0    emergencia_seq    SEQUENCE SET     =   SELECT pg_catalog.setval('public.emergencia_seq', 14, true);
          public          postgres    false    224            $           0    0 
   estado_seq    SEQUENCE SET     9   SELECT pg_catalog.setval('public.estado_seq', 1, false);
          public          postgres    false    226            %           0    0    habilidad_seq    SEQUENCE SET     ;   SELECT pg_catalog.setval('public.habilidad_seq', 8, true);
          public          postgres    false    228            &           0    0    institucion_seq    SEQUENCE SET     =   SELECT pg_catalog.setval('public.institucion_seq', 2, true);
          public          postgres    false    249            '           0    0    log_seq    SEQUENCE SET     7   SELECT pg_catalog.setval('public.log_seq', 123, true);
          public          postgres    false    251            (           0    0    rol_seq    SEQUENCE SET     6   SELECT pg_catalog.setval('public.rol_seq', 36, true);
          public          postgres    false    252            )           0    0    tarea_habilidad_seq    SEQUENCE SET     A   SELECT pg_catalog.setval('public.tarea_habilidad_seq', 5, true);
          public          postgres    false    253            *           0    0 	   tarea_seq    SEQUENCE SET     8   SELECT pg_catalog.setval('public.tarea_seq', 14, true);
          public          postgres    false    234            +           0    0    usuario_rol_seq    SEQUENCE SET     >   SELECT pg_catalog.setval('public.usuario_rol_seq', 64, true);
          public          postgres    false    237            ,           0    0    usuario_seq    SEQUENCE SET     :   SELECT pg_catalog.setval('public.usuario_seq', 91, true);
          public          postgres    false    238            -           0    0    vol_habilidad_seq    SEQUENCE SET     ?   SELECT pg_catalog.setval('public.vol_habilidad_seq', 2, true);
          public          postgres    false    240            .           0    0    voluntario_seq    SEQUENCE SET     =   SELECT pg_catalog.setval('public.voluntario_seq', 52, true);
          public          postgres    false    242            .           2606    17625    coordinador coordinador_pk 
   CONSTRAINT     d   ALTER TABLE ONLY public.coordinador
    ADD CONSTRAINT coordinador_pk PRIMARY KEY (id_coordinador);
 D   ALTER TABLE ONLY public.coordinador DROP CONSTRAINT coordinador_pk;
       public            postgres    false    220            0           2606    17627     eme_habilidad eme_habilidad_pkey 
   CONSTRAINT     l   ALTER TABLE ONLY public.eme_habilidad
    ADD CONSTRAINT eme_habilidad_pkey PRIMARY KEY (id_eme_habilidad);
 J   ALTER TABLE ONLY public.eme_habilidad DROP CONSTRAINT eme_habilidad_pkey;
       public            postgres    false    222            2           2606    17629    emergencia emergencia_pkey 
   CONSTRAINT     c   ALTER TABLE ONLY public.emergencia
    ADD CONSTRAINT emergencia_pkey PRIMARY KEY (id_emergencia);
 D   ALTER TABLE ONLY public.emergencia DROP CONSTRAINT emergencia_pkey;
       public            postgres    false    223            4           2606    17631    estado estado_tarea_pkey 
   CONSTRAINT     ]   ALTER TABLE ONLY public.estado
    ADD CONSTRAINT estado_tarea_pkey PRIMARY KEY (id_estado);
 B   ALTER TABLE ONLY public.estado DROP CONSTRAINT estado_tarea_pkey;
       public            postgres    false    225            6           2606    17633    habilidad habilidad_pkey 
   CONSTRAINT     `   ALTER TABLE ONLY public.habilidad
    ADD CONSTRAINT habilidad_pkey PRIMARY KEY (id_habilidad);
 B   ALTER TABLE ONLY public.habilidad DROP CONSTRAINT habilidad_pkey;
       public            postgres    false    227            8           2606    17635    institucion institucion_pkey 
   CONSTRAINT     f   ALTER TABLE ONLY public.institucion
    ADD CONSTRAINT institucion_pkey PRIMARY KEY (id_institucion);
 F   ALTER TABLE ONLY public.institucion DROP CONSTRAINT institucion_pkey;
       public            postgres    false    229            L           2606    18783    log_query log_pk 
   CONSTRAINT     R   ALTER TABLE ONLY public.log_query
    ADD CONSTRAINT log_pk PRIMARY KEY (id_log);
 :   ALTER TABLE ONLY public.log_query DROP CONSTRAINT log_pk;
       public            postgres    false    250            :           2606    17637    ranking ranking_pkey 
   CONSTRAINT     Z   ALTER TABLE ONLY public.ranking
    ADD CONSTRAINT ranking_pkey PRIMARY KEY (id_ranking);
 >   ALTER TABLE ONLY public.ranking DROP CONSTRAINT ranking_pkey;
       public            postgres    false    230            <           2606    17639 
   rol rol_pk 
   CONSTRAINT     L   ALTER TABLE ONLY public.rol
    ADD CONSTRAINT rol_pk PRIMARY KEY (id_rol);
 4   ALTER TABLE ONLY public.rol DROP CONSTRAINT rol_pk;
       public            postgres    false    231            @           2606    17641 $   tarea_habilidad tarea_habilidad_pkey 
   CONSTRAINT     r   ALTER TABLE ONLY public.tarea_habilidad
    ADD CONSTRAINT tarea_habilidad_pkey PRIMARY KEY (id_tarea_habilidad);
 N   ALTER TABLE ONLY public.tarea_habilidad DROP CONSTRAINT tarea_habilidad_pkey;
       public            postgres    false    233            >           2606    17643    tarea tarea_pkey 
   CONSTRAINT     T   ALTER TABLE ONLY public.tarea
    ADD CONSTRAINT tarea_pkey PRIMARY KEY (id_tarea);
 :   ALTER TABLE ONLY public.tarea DROP CONSTRAINT tarea_pkey;
       public            postgres    false    232            B           2606    17645    usuario user_pkey 
   CONSTRAINT     W   ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT user_pkey PRIMARY KEY (id_usuario);
 ;   ALTER TABLE ONLY public.usuario DROP CONSTRAINT user_pkey;
       public            postgres    false    235            D           2606    17647    usuario_rol usuario_rol_pk 
   CONSTRAINT     d   ALTER TABLE ONLY public.usuario_rol
    ADD CONSTRAINT usuario_rol_pk PRIMARY KEY (id_usuario_rol);
 D   ALTER TABLE ONLY public.usuario_rol DROP CONSTRAINT usuario_rol_pk;
       public            postgres    false    236            F           2606    17649     vol_habilidad vol_habilidad_pkey 
   CONSTRAINT     s   ALTER TABLE ONLY public.vol_habilidad
    ADD CONSTRAINT vol_habilidad_pkey PRIMARY KEY (id_voluntario_habilidad);
 J   ALTER TABLE ONLY public.vol_habilidad DROP CONSTRAINT vol_habilidad_pkey;
       public            postgres    false    239            H           2606    17651    voluntario voluntario_pkey 
   CONSTRAINT     c   ALTER TABLE ONLY public.voluntario
    ADD CONSTRAINT voluntario_pkey PRIMARY KEY (id_voluntario);
 D   ALTER TABLE ONLY public.voluntario DROP CONSTRAINT voluntario_pkey;
       public            postgres    false    241            ]           2620    18784    coordinador audit_trigger    TRIGGER     �   CREATE TRIGGER audit_trigger AFTER INSERT OR DELETE OR UPDATE ON public.coordinador FOR EACH ROW EXECUTE FUNCTION public.log_operation();
 2   DROP TRIGGER audit_trigger ON public.coordinador;
       public          postgres    false    981    220            ^           2620    18785    eme_habilidad audit_trigger    TRIGGER     �   CREATE TRIGGER audit_trigger AFTER INSERT OR DELETE OR UPDATE ON public.eme_habilidad FOR EACH ROW EXECUTE FUNCTION public.log_operation();
 4   DROP TRIGGER audit_trigger ON public.eme_habilidad;
       public          postgres    false    222    981            _           2620    18786    emergencia audit_trigger    TRIGGER     �   CREATE TRIGGER audit_trigger AFTER INSERT OR DELETE OR UPDATE ON public.emergencia FOR EACH ROW EXECUTE FUNCTION public.log_operation();
 1   DROP TRIGGER audit_trigger ON public.emergencia;
       public          postgres    false    981    223            `           2620    18787    estado audit_trigger    TRIGGER     �   CREATE TRIGGER audit_trigger AFTER INSERT OR DELETE OR UPDATE ON public.estado FOR EACH ROW EXECUTE FUNCTION public.log_operation();
 -   DROP TRIGGER audit_trigger ON public.estado;
       public          postgres    false    225    981            a           2620    18788    habilidad audit_trigger    TRIGGER     �   CREATE TRIGGER audit_trigger AFTER INSERT OR DELETE OR UPDATE ON public.habilidad FOR EACH ROW EXECUTE FUNCTION public.log_operation();
 0   DROP TRIGGER audit_trigger ON public.habilidad;
       public          postgres    false    227    981            b           2620    18789    institucion audit_trigger    TRIGGER     �   CREATE TRIGGER audit_trigger AFTER INSERT OR DELETE OR UPDATE ON public.institucion FOR EACH ROW EXECUTE FUNCTION public.log_operation();
 2   DROP TRIGGER audit_trigger ON public.institucion;
       public          postgres    false    981    229            c           2620    18790    ranking audit_trigger    TRIGGER     �   CREATE TRIGGER audit_trigger AFTER INSERT OR DELETE OR UPDATE ON public.ranking FOR EACH ROW EXECUTE FUNCTION public.log_operation();
 .   DROP TRIGGER audit_trigger ON public.ranking;
       public          postgres    false    981    230            d           2620    18791    rol audit_trigger    TRIGGER     �   CREATE TRIGGER audit_trigger AFTER INSERT OR DELETE OR UPDATE ON public.rol FOR EACH ROW EXECUTE FUNCTION public.log_operation();
 *   DROP TRIGGER audit_trigger ON public.rol;
       public          postgres    false    231    981            e           2620    18792    tarea audit_trigger    TRIGGER     �   CREATE TRIGGER audit_trigger AFTER INSERT OR DELETE OR UPDATE ON public.tarea FOR EACH ROW EXECUTE FUNCTION public.log_operation();
 ,   DROP TRIGGER audit_trigger ON public.tarea;
       public          postgres    false    232    981            f           2620    18793    tarea_habilidad audit_trigger    TRIGGER     �   CREATE TRIGGER audit_trigger AFTER INSERT OR DELETE OR UPDATE ON public.tarea_habilidad FOR EACH ROW EXECUTE FUNCTION public.log_operation();
 6   DROP TRIGGER audit_trigger ON public.tarea_habilidad;
       public          postgres    false    981    233            g           2620    18794    usuario audit_trigger    TRIGGER     �   CREATE TRIGGER audit_trigger AFTER INSERT OR DELETE OR UPDATE ON public.usuario FOR EACH ROW EXECUTE FUNCTION public.log_operation();
 .   DROP TRIGGER audit_trigger ON public.usuario;
       public          postgres    false    235    981            h           2620    18795    usuario_rol audit_trigger    TRIGGER     �   CREATE TRIGGER audit_trigger AFTER INSERT OR DELETE OR UPDATE ON public.usuario_rol FOR EACH ROW EXECUTE FUNCTION public.log_operation();
 2   DROP TRIGGER audit_trigger ON public.usuario_rol;
       public          postgres    false    981    236            i           2620    18796    vol_habilidad audit_trigger    TRIGGER     �   CREATE TRIGGER audit_trigger AFTER INSERT OR DELETE OR UPDATE ON public.vol_habilidad FOR EACH ROW EXECUTE FUNCTION public.log_operation();
 4   DROP TRIGGER audit_trigger ON public.vol_habilidad;
       public          postgres    false    239    981            j           2620    18797    voluntario audit_trigger    TRIGGER     �   CREATE TRIGGER audit_trigger AFTER INSERT OR DELETE OR UPDATE ON public.voluntario FOR EACH ROW EXECUTE FUNCTION public.log_operation();
 1   DROP TRIGGER audit_trigger ON public.voluntario;
       public          postgres    false    981    241            M           2606    17652 +   coordinador coordinador_institucion_null_fk    FK CONSTRAINT     �   ALTER TABLE ONLY public.coordinador
    ADD CONSTRAINT coordinador_institucion_null_fk FOREIGN KEY (id_institucion) REFERENCES public.institucion(id_institucion);
 U   ALTER TABLE ONLY public.coordinador DROP CONSTRAINT coordinador_institucion_null_fk;
       public          postgres    false    4152    229    220            N           2606    17657 -   coordinador coordinador_usuario_id_usuario_fk    FK CONSTRAINT     �   ALTER TABLE ONLY public.coordinador
    ADD CONSTRAINT coordinador_usuario_id_usuario_fk FOREIGN KEY (id_usuario) REFERENCES public.usuario(id_usuario);
 W   ALTER TABLE ONLY public.coordinador DROP CONSTRAINT coordinador_usuario_id_usuario_fk;
       public          postgres    false    220    4162    235            O           2606    17662 "   eme_habilidad fk_emehab_emergencia    FK CONSTRAINT     �   ALTER TABLE ONLY public.eme_habilidad
    ADD CONSTRAINT fk_emehab_emergencia FOREIGN KEY (id_emergencia) REFERENCES public.emergencia(id_emergencia);
 L   ALTER TABLE ONLY public.eme_habilidad DROP CONSTRAINT fk_emehab_emergencia;
       public          postgres    false    222    4146    223            P           2606    17667 !   eme_habilidad fk_emehab_habilidad    FK CONSTRAINT     �   ALTER TABLE ONLY public.eme_habilidad
    ADD CONSTRAINT fk_emehab_habilidad FOREIGN KEY (id_habilidad) REFERENCES public.habilidad(id_habilidad);
 K   ALTER TABLE ONLY public.eme_habilidad DROP CONSTRAINT fk_emehab_habilidad;
       public          postgres    false    4150    222    227            Q           2606    17672 $   emergencia fk_emergencia_institucion    FK CONSTRAINT     �   ALTER TABLE ONLY public.emergencia
    ADD CONSTRAINT fk_emergencia_institucion FOREIGN KEY (id_institucion) REFERENCES public.institucion(id_institucion);
 N   ALTER TABLE ONLY public.emergencia DROP CONSTRAINT fk_emergencia_institucion;
       public          postgres    false    223    4152    229            R           2606    17677    ranking fk_ranking_tarea    FK CONSTRAINT     ~   ALTER TABLE ONLY public.ranking
    ADD CONSTRAINT fk_ranking_tarea FOREIGN KEY (id_tarea) REFERENCES public.tarea(id_tarea);
 B   ALTER TABLE ONLY public.ranking DROP CONSTRAINT fk_ranking_tarea;
       public          postgres    false    230    4158    232            S           2606    17682    ranking fk_ranking_voluntario    FK CONSTRAINT     �   ALTER TABLE ONLY public.ranking
    ADD CONSTRAINT fk_ranking_voluntario FOREIGN KEY (id_voluntario) REFERENCES public.voluntario(id_voluntario);
 G   ALTER TABLE ONLY public.ranking DROP CONSTRAINT fk_ranking_voluntario;
       public          postgres    false    230    241    4168            T           2606    17687    tarea fk_tarea_emergencia    FK CONSTRAINT     �   ALTER TABLE ONLY public.tarea
    ADD CONSTRAINT fk_tarea_emergencia FOREIGN KEY (id_emergencia) REFERENCES public.emergencia(id_emergencia);
 C   ALTER TABLE ONLY public.tarea DROP CONSTRAINT fk_tarea_emergencia;
       public          postgres    false    223    4146    232            V           2606    17692 "   tarea_habilidad fk_tareahab_emehab    FK CONSTRAINT     �   ALTER TABLE ONLY public.tarea_habilidad
    ADD CONSTRAINT fk_tareahab_emehab FOREIGN KEY (id_eme_habilidad) REFERENCES public.eme_habilidad(id_eme_habilidad);
 L   ALTER TABLE ONLY public.tarea_habilidad DROP CONSTRAINT fk_tareahab_emehab;
       public          postgres    false    222    233    4144            W           2606    17697 !   tarea_habilidad fk_tareahab_tarea    FK CONSTRAINT     �   ALTER TABLE ONLY public.tarea_habilidad
    ADD CONSTRAINT fk_tareahab_tarea FOREIGN KEY (id_tarea) REFERENCES public.tarea(id_tarea);
 K   ALTER TABLE ONLY public.tarea_habilidad DROP CONSTRAINT fk_tareahab_tarea;
       public          postgres    false    4158    232    233            Z           2606    17702 !   vol_habilidad fk_volhab_habilidad    FK CONSTRAINT     �   ALTER TABLE ONLY public.vol_habilidad
    ADD CONSTRAINT fk_volhab_habilidad FOREIGN KEY (id_habilidad) REFERENCES public.habilidad(id_habilidad);
 K   ALTER TABLE ONLY public.vol_habilidad DROP CONSTRAINT fk_volhab_habilidad;
       public          postgres    false    227    239    4150            [           2606    17707 "   vol_habilidad fk_volhab_voluntario    FK CONSTRAINT     �   ALTER TABLE ONLY public.vol_habilidad
    ADD CONSTRAINT fk_volhab_voluntario FOREIGN KEY (id_voluntario) REFERENCES public.voluntario(id_voluntario);
 L   ALTER TABLE ONLY public.vol_habilidad DROP CONSTRAINT fk_volhab_voluntario;
       public          postgres    false    241    4168    239            U           2606    17712    tarea tarea_estado_id_estado_fk    FK CONSTRAINT     �   ALTER TABLE ONLY public.tarea
    ADD CONSTRAINT tarea_estado_id_estado_fk FOREIGN KEY (id_estado) REFERENCES public.estado(id_estado);
 I   ALTER TABLE ONLY public.tarea DROP CONSTRAINT tarea_estado_id_estado_fk;
       public          postgres    false    225    4148    232            X           2606    17717 %   usuario_rol usuario_rol_rol_id_rol_fk    FK CONSTRAINT     �   ALTER TABLE ONLY public.usuario_rol
    ADD CONSTRAINT usuario_rol_rol_id_rol_fk FOREIGN KEY (id_rol) REFERENCES public.rol(id_rol);
 O   ALTER TABLE ONLY public.usuario_rol DROP CONSTRAINT usuario_rol_rol_id_rol_fk;
       public          postgres    false    231    236    4156            Y           2606    17722 -   usuario_rol usuario_rol_usuario_id_usuario_fk    FK CONSTRAINT     �   ALTER TABLE ONLY public.usuario_rol
    ADD CONSTRAINT usuario_rol_usuario_id_usuario_fk FOREIGN KEY (id_usuario) REFERENCES public.usuario(id_usuario);
 W   ALTER TABLE ONLY public.usuario_rol DROP CONSTRAINT usuario_rol_usuario_id_usuario_fk;
       public          postgres    false    236    4162    235            \           2606    17727 +   voluntario voluntario_usuario_id_usuario_fk    FK CONSTRAINT     �   ALTER TABLE ONLY public.voluntario
    ADD CONSTRAINT voluntario_usuario_id_usuario_fk FOREIGN KEY (id_usuario) REFERENCES public.usuario(id_usuario);
 U   ALTER TABLE ONLY public.voluntario DROP CONSTRAINT voluntario_usuario_id_usuario_fk;
       public          postgres    false    4162    241    235            �   A   x���� C���0UB0�]��5��/�E2=M�2�y�P���lt�/j*��J$gZ�{"�ԡ	      �      x�3�4�4�2�\1z\\\ ;      �   -   x�3�L�M5)��ɜFFƺ&���p���g�W� �
          H   x�3�H�K�L�+I�2�t�SHI-N,*�����2�S�R���9��SsS�L8CR�r3�@�=... ���         z   x�-�11��+������Z�%��Q�'���$��f4{�n����b��V���g:��[85VKbY[��vG�y���tv,�7o1�
X��(c�Q����
�~�p^����z��ND?#�2�         �  x�uQ�j�@>��B��%��X��e!qJa�^Աv�d,9�cHަ�z(}�X5v�BO�4����l��>�-..^
X߰'A��>,1qʎ�?���8��(��>K�������}�h]��l�M�P��p	��ࠡ3�D6��J�$)���
:VA_��k}O�+�B�,K��mٚ�-�$𩈂���F0��E4yL��PHa���z��SR3 ��\fahP�<��j�ov��v��Uiu���(-��pk�Y��k'g�N��~w�,ZX�~�9A����&�l�:-�G�����u�?��b��L�WwMI�:��}J.b�ځ�����ak�*�z�����M�;��n
p�4�N;��b�%��~Lq��$s������1h���[]U�_�C��         �  x���_kc7ş�O��-13�}���aaIK7٧��]����Ů��[�&��$��s���I�sr�V����P�+"�'
����O?W�A�KE�͙R�F�JT��6���Ɲ��B�jp���Y���ު�$l�[	QaW8[�B�Ʉ�rl��.��F"U�T([�EL:iV��!.�
�e/Y����V�����ߟ~\�?���ǣS:��D�a�3�"Z�m)V��*V%y�2c`�B�6��;�����כ90���r�,:�vۇ��l�������n�"�F@f3��1���)[qʌ�
�����a�׌+;�I�Q���v��߬��ݤ�8�ҫ�~��X'[���_C)�,��}�^������뛟�{��~]G�q��>~������������]�(��L������������q����y�=����~�_�WX��rq����|N�KN/�_d
ح���Z�k�^��5h���B�!�R�!wB/��K:��-��.I6��g0��ZH�-�G��#C鑡��KCl!=2��ZH���c/��K:���-��m$���������B��!��ZH�-�G��#C鑡����Bzd�%�zI�^ҹ�t�&�,���`{���i΃<BCI�O ��<��6yTL�f<�s��v����ӄ�rh����S��6O����U��
�4�A^�*t�B�G�f;�sU]&�f�&�,GsN޺���4�A/�/�����+��M�j�b���I���Y<+�^�K�%A?�zw��hOq�0Ͳ�P$Z�Nb�g�D�d#�t��;KWRՇ,	��6�����HH�!�S"|"ވq�<ňV�����g�b���kf�Y��բV�x��K�x�4�E�����R�3�]�A����2&�3�����Z\��ED�|ʋ��5T�r���'��q}��P+�߈5XvXBDc��L��^~V"a�#����C%1�e`��K��(��`8�.��I��*�z�j҄��e�W{uu�|]            x������ � �         0   x�3�tt����2�t��r��st��2���	�q������� ��	%      +      x������ � �         �   x�͎K
�0D��)rKN?�DO���m�m�}�!О��Y<����CK�1�y]^��||&@r�;[;^-�m��&�αf�Q+Y�D����B���ku�@�Ǯ��7����/��D��t2Ƽ�p?            x�3�4�44�2�4Q1z\\\ �w      
   y  x�uT]��:}�c�����-0**�#�u�n��!|P��o��ٹ�u_R�t��I�;�%0ì@E\�=�`�����C��kP�Q�G�,�e�
������z�g�X8jK�RĸA��a�o�!����֒t�	�r�L�Q��e5Ɩ����Z��x_Q՘�䗈�¯@I��Aw~��1�/�!/�wy0$�d��n�7�n�>p�S�ذ��9p ]���;�Ji�d�i	*x��Q~��+��ȋ�t�ICîX��z��h��a�N&q]��<�lq���n8�כ>;1�!�����:\����%7�o��
8�3w��xǖ]�j�s��!���mi�q�.�1FLܴ����ӹ$G�¨̿�w}j�y���~�X��lX��D���v��	�,���un룠H�A-�}��@-�����io�#��+Ի�j4��+����ٌb�T�d��V��0Lg�E-(2��i����%�4�e씹r���mE��*K_PcB�x6��yS��)}MsA�"�zA �6��p��rx5��S�6��V���W�̚�u����}��c�x����0���̀^aJ	/��
���LE��-M�v�mT�޴��6y���ռ�뜺��+���8��QAQ�!� ��Bz���
�>��!����"��ȑ��o/����ܿ�N�:Ϗ�Mo�2��� }��_��;J�jC5S0�ٟw��:X;��>s��/������(�)�C@q	r�4�>��"��4�EE��9�C��I)i�-�3z{,Q���Y3+T�08>&)�>F�I1{�Q>,�0N`����%V��D�/8�?iU�{��hڲ��JlQE��D�T����ϴl���S��$�K         b   x�ͱ�0�,��$EY������f`�K/�8궮z9�ЌL��vk]��=�oj��ć�W�I=4�R�M�[�_-j{������Ƈ��{����            x������ � �         �  x�m�=��*�k�Ŝ���U|�_����I��f6�Xo=Ǿ@�{I'��91c� �?j�.���0	����P�K��X��uGX^.s�Ns)N�N�u|b��:�q�8�]�8�&�N�2�?WPLӁ�X���g��12�"��
;��@j�p����_E��}C�	_����Pu�Mj�*��yE������ˌy�ٙ�p`R�#c�G��ܨ�J2��n��k�R�)����eǆ3q�����=feƱ�]�7��٥&��T�#�&���{S.v�H�/p�"^�����_>2A�9!|��%YM�&x�_>�8b�o��n����<���6zE.h��(�l_źcٚ9���Q)��A��W����ꎴ�tAn�Ψ��B�Н\�+�*��[,�^v��<ԅ�GOm��QṠ6z �E`P-���T�I0(e����~����     