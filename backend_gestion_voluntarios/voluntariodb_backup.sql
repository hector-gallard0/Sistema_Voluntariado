PGDMP     $    ;                {            voluntariodb3    15.1    15.1 s               0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false                       0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false                       0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false                       1262    72706    voluntariodb3    DATABASE     �   CREATE DATABASE voluntariodb3 WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'Spanish_Chile.1252';
    DROP DATABASE voluntariodb3;
                postgres    false                        2615    2200    public    SCHEMA     2   -- *not* creating schema, since initdb creates it
 2   -- *not* dropping schema, since initdb creates it
                postgres    false                       0    0    SCHEMA public    ACL     Q   REVOKE USAGE ON SCHEMA public FROM PUBLIC;
GRANT ALL ON SCHEMA public TO PUBLIC;
                   postgres    false    6                        3079    72707    postgis 	   EXTENSION     ;   CREATE EXTENSION IF NOT EXISTS postgis WITH SCHEMA public;
    DROP EXTENSION postgis;
                   false    6                        0    0    EXTENSION postgis    COMMENT     ^   COMMENT ON EXTENSION postgis IS 'PostGIS geometry and geography spatial types and functions';
                        false    2            �           1255    73751    log_operation()    FUNCTION     �  CREATE FUNCTION public.log_operation() RETURNS trigger
    LANGUAGE plpgsql
    AS $$DECLARE
usuario varchar;
consulta varchar;
temp_params_exists integer;
BEGIN
temp_params_exists := (SELECT 1 FROM information_schema.tables WHERE table_name = 'temp_params');
IF temp_params_exists = 1 THEN
	SELECT tp.usuario, tp.consulta INTO usuario, consulta
	FROM temp_params tp;
ELSE
	usuario := 'query tool';
	consulta := 'query tool';
END IF;

INSERT INTO log_query (id_log, usuario, hora, operacion, tabla, consulta)
VALUES ((SELECT nextval('log_seq')),
		usuario, now(), TG_OP, TG_TABLE_NAME, consulta);

IF temp_params_exists = 1 THEN
	DROP TABLE temp_params;
END IF;

RETURN NULL;
END;$$;
 &   DROP FUNCTION public.log_operation();
       public          postgres    false    6            �           1255    73752    obtener_reporte_operaciones() 	   PROCEDURE       CREATE PROCEDURE public.obtener_reporte_operaciones()
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
       public          postgres    false    6            �            1259    73753    coordinador    TABLE     �   CREATE TABLE public.coordinador (
    id_coordinador integer NOT NULL,
    id_usuario integer NOT NULL,
    id_institucion integer NOT NULL
);
    DROP TABLE public.coordinador;
       public         heap    postgres    false    6            �            1259    73756    coordinador_seq    SEQUENCE     �   CREATE SEQUENCE public.coordinador_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 &   DROP SEQUENCE public.coordinador_seq;
       public          postgres    false    6            �            1259    73757    eme_habilidad    TABLE     �   CREATE TABLE public.eme_habilidad (
    id_eme_habilidad integer NOT NULL,
    id_emergencia integer,
    id_habilidad integer
);
 !   DROP TABLE public.eme_habilidad;
       public         heap    postgres    false    6            �            1259    73760    eme_habilidad_seq    SEQUENCE     �   CREATE SEQUENCE public.eme_habilidad_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 (   DROP SEQUENCE public.eme_habilidad_seq;
       public          postgres    false    6            �            1259    73761 
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
       public         heap    postgres    false    6    2    2    6    2    6    6    2    6    2    6    2    6    2    6    2    6            �            1259    73766    emergencia_seq    SEQUENCE     �   CREATE SEQUENCE public.emergencia_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 %   DROP SEQUENCE public.emergencia_seq;
       public          postgres    false    6            �            1259    73767    estado    TABLE     f   CREATE TABLE public.estado (
    id_estado integer NOT NULL,
    descripcion character varying(20)
);
    DROP TABLE public.estado;
       public         heap    postgres    false    6            �            1259    73770 
   estado_seq    SEQUENCE     �   CREATE SEQUENCE public.estado_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 !   DROP SEQUENCE public.estado_seq;
       public          postgres    false    6            �            1259    73771 	   habilidad    TABLE     {   CREATE TABLE public.habilidad (
    id_habilidad numeric(4,0) NOT NULL,
    descripcion character varying(100) NOT NULL
);
    DROP TABLE public.habilidad;
       public         heap    postgres    false    6            �            1259    73774    habilidad_seq    SEQUENCE     �   CREATE SEQUENCE public.habilidad_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 $   DROP SEQUENCE public.habilidad_seq;
       public          postgres    false    6            �            1259    73775    institucion    TABLE     �   CREATE TABLE public.institucion (
    id_institucion numeric(4,0) NOT NULL,
    nombre character varying(100),
    descripcion character varying(400)
);
    DROP TABLE public.institucion;
       public         heap    postgres    false    6            �            1259    73780    institucion_seq    SEQUENCE     �   CREATE SEQUENCE public.institucion_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 &   DROP SEQUENCE public.institucion_seq;
       public          postgres    false    6            �            1259    73781 	   log_query    TABLE     �   CREATE TABLE public.log_query (
    usuario character varying,
    hora time without time zone,
    id_log integer NOT NULL,
    operacion character varying,
    tabla character varying,
    consulta character varying
);
    DROP TABLE public.log_query;
       public         heap    postgres    false    6            �            1259    73786    log_seq    SEQUENCE        CREATE SEQUENCE public.log_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
    DROP SEQUENCE public.log_seq;
       public          postgres    false    6            �            1259    73787    ranking    TABLE     �   CREATE TABLE public.ranking (
    id_ranking integer NOT NULL,
    id_voluntario integer,
    id_tarea integer,
    puntaje numeric(8,0),
    flag_invitado numeric(1,0),
    flag_participa numeric(1,0)
);
    DROP TABLE public.ranking;
       public         heap    postgres    false    6            !           0    0    TABLE ranking    COMMENT     u   COMMENT ON TABLE public.ranking IS 'los flgInvitado, flgParticipa 1 si la respuesta es si, 2 si la respuesta es no';
          public          postgres    false    236            �            1259    73790    rol    TABLE     `   CREATE TABLE public.rol (
    id_rol integer NOT NULL,
    nombre character varying NOT NULL
);
    DROP TABLE public.rol;
       public         heap    postgres    false    6            �            1259    73795    rol_seq    SEQUENCE        CREATE SEQUENCE public.rol_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
    DROP SEQUENCE public.rol_seq;
       public          postgres    false    6            �            1259    73796    tarea    TABLE     U  CREATE TABLE public.tarea (
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
       public         heap    postgres    false    6    2    2    6    2    6    6    2    6    2    6    2    6    2    6    2    6            �            1259    73801    tarea_habilidad    TABLE     �   CREATE TABLE public.tarea_habilidad (
    id_tarea_habilidad integer NOT NULL,
    id_eme_habilidad integer,
    id_tarea integer
);
 #   DROP TABLE public.tarea_habilidad;
       public         heap    postgres    false    6            �            1259    73804    tarea_habilidad_seq    SEQUENCE     �   CREATE SEQUENCE public.tarea_habilidad_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 *   DROP SEQUENCE public.tarea_habilidad_seq;
       public          postgres    false    6            �            1259    73805 	   tarea_seq    SEQUENCE     �   CREATE SEQUENCE public.tarea_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
     DROP SEQUENCE public.tarea_seq;
       public          postgres    false    6            �            1259    73806    usuario    TABLE     �   CREATE TABLE public.usuario (
    id_usuario integer NOT NULL,
    nombre text NOT NULL,
    apellido text NOT NULL,
    email text NOT NULL,
    password text NOT NULL,
    geom public.geometry(Point,4326)
);
    DROP TABLE public.usuario;
       public         heap    postgres    false    6    2    2    6    2    6    6    2    6    2    6    2    6    2    6    2    6            �            1259    73811    usuario_rol    TABLE     �   CREATE TABLE public.usuario_rol (
    id_usuario_rol integer NOT NULL,
    id_usuario integer NOT NULL,
    id_rol integer NOT NULL
);
    DROP TABLE public.usuario_rol;
       public         heap    postgres    false    6            �            1259    73814    usuario_rol_seq    SEQUENCE     �   CREATE SEQUENCE public.usuario_rol_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 &   DROP SEQUENCE public.usuario_rol_seq;
       public          postgres    false    6            �            1259    73815    usuario_seq    SEQUENCE     t   CREATE SEQUENCE public.usuario_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 "   DROP SEQUENCE public.usuario_seq;
       public          postgres    false    6            �            1259    73816    vol_habilidad    TABLE     �   CREATE TABLE public.vol_habilidad (
    id_voluntario_habilidad integer NOT NULL,
    id_voluntario integer,
    id_habilidad integer
);
 !   DROP TABLE public.vol_habilidad;
       public         heap    postgres    false    6            �            1259    73819    vol_habilidad_seq    SEQUENCE     �   CREATE SEQUENCE public.vol_habilidad_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 (   DROP SEQUENCE public.vol_habilidad_seq;
       public          postgres    false    6            �            1259    73820 
   voluntario    TABLE     h   CREATE TABLE public.voluntario (
    id_voluntario integer NOT NULL,
    id_usuario integer NOT NULL
);
    DROP TABLE public.voluntario;
       public         heap    postgres    false    6            �            1259    73825    voluntario_seq    SEQUENCE     �   CREATE SEQUENCE public.voluntario_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 %   DROP SEQUENCE public.voluntario_seq;
       public          postgres    false    6            �          0    73753    coordinador 
   TABLE DATA           Q   COPY public.coordinador (id_coordinador, id_usuario, id_institucion) FROM stdin;
    public          postgres    false    222   ߑ       �          0    73757    eme_habilidad 
   TABLE DATA           V   COPY public.eme_habilidad (id_eme_habilidad, id_emergencia, id_habilidad) FROM stdin;
    public          postgres    false    224   0�                  0    73761 
   emergencia 
   TABLE DATA           w   COPY public.emergencia (id_emergencia, nombre, descripcion, fecha_inicio, fecha_fin, id_institucion, geom) FROM stdin;
    public          postgres    false    226   W�                 0    73767    estado 
   TABLE DATA           8   COPY public.estado (id_estado, descripcion) FROM stdin;
    public          postgres    false    228   ��                 0    73771 	   habilidad 
   TABLE DATA           >   COPY public.habilidad (id_habilidad, descripcion) FROM stdin;
    public          postgres    false    230   �                 0    73775    institucion 
   TABLE DATA           J   COPY public.institucion (id_institucion, nombre, descripcion) FROM stdin;
    public          postgres    false    232   v�                 0    73781 	   log_query 
   TABLE DATA           V   COPY public.log_query (usuario, hora, id_log, operacion, tabla, consulta) FROM stdin;
    public          postgres    false    234   �       
          0    73787    ranking 
   TABLE DATA           n   COPY public.ranking (id_ranking, id_voluntario, id_tarea, puntaje, flag_invitado, flag_participa) FROM stdin;
    public          postgres    false    236   %�                 0    73790    rol 
   TABLE DATA           -   COPY public.rol (id_rol, nombre) FROM stdin;
    public          postgres    false    237   B�       )          0    73020    spatial_ref_sys 
   TABLE DATA           X   COPY public.spatial_ref_sys (srid, auth_name, auth_srid, srtext, proj4text) FROM stdin;
    public          postgres    false    218   ��                 0    73796    tarea 
   TABLE DATA           �   COPY public.tarea (id_tarea, nombre, descripcion, cant_vol_requeridos, cant_vol_inscritos, id_emergencia, fecha_inicio, fecha_fin, id_estado, geom) FROM stdin;
    public          postgres    false    239   ��                 0    73801    tarea_habilidad 
   TABLE DATA           Y   COPY public.tarea_habilidad (id_tarea_habilidad, id_eme_habilidad, id_tarea) FROM stdin;
    public          postgres    false    240   E�                 0    73806    usuario 
   TABLE DATA           V   COPY public.usuario (id_usuario, nombre, apellido, email, password, geom) FROM stdin;
    public          postgres    false    243   m�                 0    73811    usuario_rol 
   TABLE DATA           I   COPY public.usuario_rol (id_usuario_rol, id_usuario, id_rol) FROM stdin;
    public          postgres    false    244   �                 0    73816    vol_habilidad 
   TABLE DATA           ]   COPY public.vol_habilidad (id_voluntario_habilidad, id_voluntario, id_habilidad) FROM stdin;
    public          postgres    false    247   t�                 0    73820 
   voluntario 
   TABLE DATA           ?   COPY public.voluntario (id_voluntario, id_usuario) FROM stdin;
    public          postgres    false    249   ��       "           0    0    coordinador_seq    SEQUENCE SET     >   SELECT pg_catalog.setval('public.coordinador_seq', 45, true);
          public          postgres    false    223            #           0    0    eme_habilidad_seq    SEQUENCE SET     @   SELECT pg_catalog.setval('public.eme_habilidad_seq', 1, false);
          public          postgres    false    225            $           0    0    emergencia_seq    SEQUENCE SET     =   SELECT pg_catalog.setval('public.emergencia_seq', 14, true);
          public          postgres    false    227            %           0    0 
   estado_seq    SEQUENCE SET     9   SELECT pg_catalog.setval('public.estado_seq', 1, false);
          public          postgres    false    229            &           0    0    habilidad_seq    SEQUENCE SET     ;   SELECT pg_catalog.setval('public.habilidad_seq', 8, true);
          public          postgres    false    231            '           0    0    institucion_seq    SEQUENCE SET     =   SELECT pg_catalog.setval('public.institucion_seq', 2, true);
          public          postgres    false    233            (           0    0    log_seq    SEQUENCE SET     7   SELECT pg_catalog.setval('public.log_seq', 138, true);
          public          postgres    false    235            )           0    0    rol_seq    SEQUENCE SET     6   SELECT pg_catalog.setval('public.rol_seq', 36, true);
          public          postgres    false    238            *           0    0    tarea_habilidad_seq    SEQUENCE SET     A   SELECT pg_catalog.setval('public.tarea_habilidad_seq', 5, true);
          public          postgres    false    241            +           0    0 	   tarea_seq    SEQUENCE SET     8   SELECT pg_catalog.setval('public.tarea_seq', 15, true);
          public          postgres    false    242            ,           0    0    usuario_rol_seq    SEQUENCE SET     >   SELECT pg_catalog.setval('public.usuario_rol_seq', 64, true);
          public          postgres    false    245            -           0    0    usuario_seq    SEQUENCE SET     :   SELECT pg_catalog.setval('public.usuario_seq', 91, true);
          public          postgres    false    246            .           0    0    vol_habilidad_seq    SEQUENCE SET     ?   SELECT pg_catalog.setval('public.vol_habilidad_seq', 2, true);
          public          postgres    false    248            /           0    0    voluntario_seq    SEQUENCE SET     =   SELECT pg_catalog.setval('public.voluntario_seq', 52, true);
          public          postgres    false    250            .           2606    73827    coordinador coordinador_pk 
   CONSTRAINT     d   ALTER TABLE ONLY public.coordinador
    ADD CONSTRAINT coordinador_pk PRIMARY KEY (id_coordinador);
 D   ALTER TABLE ONLY public.coordinador DROP CONSTRAINT coordinador_pk;
       public            postgres    false    222            0           2606    73829     eme_habilidad eme_habilidad_pkey 
   CONSTRAINT     l   ALTER TABLE ONLY public.eme_habilidad
    ADD CONSTRAINT eme_habilidad_pkey PRIMARY KEY (id_eme_habilidad);
 J   ALTER TABLE ONLY public.eme_habilidad DROP CONSTRAINT eme_habilidad_pkey;
       public            postgres    false    224            2           2606    73831    emergencia emergencia_pkey 
   CONSTRAINT     c   ALTER TABLE ONLY public.emergencia
    ADD CONSTRAINT emergencia_pkey PRIMARY KEY (id_emergencia);
 D   ALTER TABLE ONLY public.emergencia DROP CONSTRAINT emergencia_pkey;
       public            postgres    false    226            4           2606    73833    estado estado_tarea_pkey 
   CONSTRAINT     ]   ALTER TABLE ONLY public.estado
    ADD CONSTRAINT estado_tarea_pkey PRIMARY KEY (id_estado);
 B   ALTER TABLE ONLY public.estado DROP CONSTRAINT estado_tarea_pkey;
       public            postgres    false    228            6           2606    73835    habilidad habilidad_pkey 
   CONSTRAINT     `   ALTER TABLE ONLY public.habilidad
    ADD CONSTRAINT habilidad_pkey PRIMARY KEY (id_habilidad);
 B   ALTER TABLE ONLY public.habilidad DROP CONSTRAINT habilidad_pkey;
       public            postgres    false    230            8           2606    73837    institucion institucion_pkey 
   CONSTRAINT     f   ALTER TABLE ONLY public.institucion
    ADD CONSTRAINT institucion_pkey PRIMARY KEY (id_institucion);
 F   ALTER TABLE ONLY public.institucion DROP CONSTRAINT institucion_pkey;
       public            postgres    false    232            :           2606    73839    log_query log_pk 
   CONSTRAINT     R   ALTER TABLE ONLY public.log_query
    ADD CONSTRAINT log_pk PRIMARY KEY (id_log);
 :   ALTER TABLE ONLY public.log_query DROP CONSTRAINT log_pk;
       public            postgres    false    234            <           2606    73841    ranking ranking_pkey 
   CONSTRAINT     Z   ALTER TABLE ONLY public.ranking
    ADD CONSTRAINT ranking_pkey PRIMARY KEY (id_ranking);
 >   ALTER TABLE ONLY public.ranking DROP CONSTRAINT ranking_pkey;
       public            postgres    false    236            >           2606    73843 
   rol rol_pk 
   CONSTRAINT     L   ALTER TABLE ONLY public.rol
    ADD CONSTRAINT rol_pk PRIMARY KEY (id_rol);
 4   ALTER TABLE ONLY public.rol DROP CONSTRAINT rol_pk;
       public            postgres    false    237            B           2606    73845 $   tarea_habilidad tarea_habilidad_pkey 
   CONSTRAINT     r   ALTER TABLE ONLY public.tarea_habilidad
    ADD CONSTRAINT tarea_habilidad_pkey PRIMARY KEY (id_tarea_habilidad);
 N   ALTER TABLE ONLY public.tarea_habilidad DROP CONSTRAINT tarea_habilidad_pkey;
       public            postgres    false    240            @           2606    73847    tarea tarea_pkey 
   CONSTRAINT     T   ALTER TABLE ONLY public.tarea
    ADD CONSTRAINT tarea_pkey PRIMARY KEY (id_tarea);
 :   ALTER TABLE ONLY public.tarea DROP CONSTRAINT tarea_pkey;
       public            postgres    false    239            D           2606    73849    usuario user_pkey 
   CONSTRAINT     W   ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT user_pkey PRIMARY KEY (id_usuario);
 ;   ALTER TABLE ONLY public.usuario DROP CONSTRAINT user_pkey;
       public            postgres    false    243            F           2606    73851    usuario_rol usuario_rol_pk 
   CONSTRAINT     d   ALTER TABLE ONLY public.usuario_rol
    ADD CONSTRAINT usuario_rol_pk PRIMARY KEY (id_usuario_rol);
 D   ALTER TABLE ONLY public.usuario_rol DROP CONSTRAINT usuario_rol_pk;
       public            postgres    false    244            H           2606    73853     vol_habilidad vol_habilidad_pkey 
   CONSTRAINT     s   ALTER TABLE ONLY public.vol_habilidad
    ADD CONSTRAINT vol_habilidad_pkey PRIMARY KEY (id_voluntario_habilidad);
 J   ALTER TABLE ONLY public.vol_habilidad DROP CONSTRAINT vol_habilidad_pkey;
       public            postgres    false    247            J           2606    73855    voluntario voluntario_pkey 
   CONSTRAINT     c   ALTER TABLE ONLY public.voluntario
    ADD CONSTRAINT voluntario_pkey PRIMARY KEY (id_voluntario);
 D   ALTER TABLE ONLY public.voluntario DROP CONSTRAINT voluntario_pkey;
       public            postgres    false    249            [           2620    73856    coordinador audit_trigger    TRIGGER     �   CREATE TRIGGER audit_trigger AFTER INSERT OR DELETE OR UPDATE ON public.coordinador FOR EACH ROW EXECUTE FUNCTION public.log_operation();
 2   DROP TRIGGER audit_trigger ON public.coordinador;
       public          postgres    false    222    985            \           2620    73857    eme_habilidad audit_trigger    TRIGGER     �   CREATE TRIGGER audit_trigger AFTER INSERT OR DELETE OR UPDATE ON public.eme_habilidad FOR EACH ROW EXECUTE FUNCTION public.log_operation();
 4   DROP TRIGGER audit_trigger ON public.eme_habilidad;
       public          postgres    false    224    985            ]           2620    73858    emergencia audit_trigger    TRIGGER     �   CREATE TRIGGER audit_trigger AFTER INSERT OR DELETE OR UPDATE ON public.emergencia FOR EACH ROW EXECUTE FUNCTION public.log_operation();
 1   DROP TRIGGER audit_trigger ON public.emergencia;
       public          postgres    false    985    226            ^           2620    73859    estado audit_trigger    TRIGGER     �   CREATE TRIGGER audit_trigger AFTER INSERT OR DELETE OR UPDATE ON public.estado FOR EACH ROW EXECUTE FUNCTION public.log_operation();
 -   DROP TRIGGER audit_trigger ON public.estado;
       public          postgres    false    985    228            _           2620    73860    habilidad audit_trigger    TRIGGER     �   CREATE TRIGGER audit_trigger AFTER INSERT OR DELETE OR UPDATE ON public.habilidad FOR EACH ROW EXECUTE FUNCTION public.log_operation();
 0   DROP TRIGGER audit_trigger ON public.habilidad;
       public          postgres    false    985    230            `           2620    73861    institucion audit_trigger    TRIGGER     �   CREATE TRIGGER audit_trigger AFTER INSERT OR DELETE OR UPDATE ON public.institucion FOR EACH ROW EXECUTE FUNCTION public.log_operation();
 2   DROP TRIGGER audit_trigger ON public.institucion;
       public          postgres    false    985    232            a           2620    73862    ranking audit_trigger    TRIGGER     �   CREATE TRIGGER audit_trigger AFTER INSERT OR DELETE OR UPDATE ON public.ranking FOR EACH ROW EXECUTE FUNCTION public.log_operation();
 .   DROP TRIGGER audit_trigger ON public.ranking;
       public          postgres    false    985    236            b           2620    73863    rol audit_trigger    TRIGGER     �   CREATE TRIGGER audit_trigger AFTER INSERT OR DELETE OR UPDATE ON public.rol FOR EACH ROW EXECUTE FUNCTION public.log_operation();
 *   DROP TRIGGER audit_trigger ON public.rol;
       public          postgres    false    985    237            c           2620    73864    tarea audit_trigger    TRIGGER     �   CREATE TRIGGER audit_trigger AFTER INSERT OR DELETE OR UPDATE ON public.tarea FOR EACH ROW EXECUTE FUNCTION public.log_operation();
 ,   DROP TRIGGER audit_trigger ON public.tarea;
       public          postgres    false    985    239            d           2620    73865    tarea_habilidad audit_trigger    TRIGGER     �   CREATE TRIGGER audit_trigger AFTER INSERT OR DELETE OR UPDATE ON public.tarea_habilidad FOR EACH ROW EXECUTE FUNCTION public.log_operation();
 6   DROP TRIGGER audit_trigger ON public.tarea_habilidad;
       public          postgres    false    985    240            e           2620    73866    usuario audit_trigger    TRIGGER     �   CREATE TRIGGER audit_trigger AFTER INSERT OR DELETE OR UPDATE ON public.usuario FOR EACH ROW EXECUTE FUNCTION public.log_operation();
 .   DROP TRIGGER audit_trigger ON public.usuario;
       public          postgres    false    243    985            f           2620    73867    usuario_rol audit_trigger    TRIGGER     �   CREATE TRIGGER audit_trigger AFTER INSERT OR DELETE OR UPDATE ON public.usuario_rol FOR EACH ROW EXECUTE FUNCTION public.log_operation();
 2   DROP TRIGGER audit_trigger ON public.usuario_rol;
       public          postgres    false    244    985            g           2620    73868    vol_habilidad audit_trigger    TRIGGER     �   CREATE TRIGGER audit_trigger AFTER INSERT OR DELETE OR UPDATE ON public.vol_habilidad FOR EACH ROW EXECUTE FUNCTION public.log_operation();
 4   DROP TRIGGER audit_trigger ON public.vol_habilidad;
       public          postgres    false    985    247            h           2620    73869    voluntario audit_trigger    TRIGGER     �   CREATE TRIGGER audit_trigger AFTER INSERT OR DELETE OR UPDATE ON public.voluntario FOR EACH ROW EXECUTE FUNCTION public.log_operation();
 1   DROP TRIGGER audit_trigger ON public.voluntario;
       public          postgres    false    985    249            K           2606    73870 +   coordinador coordinador_institucion_null_fk    FK CONSTRAINT     �   ALTER TABLE ONLY public.coordinador
    ADD CONSTRAINT coordinador_institucion_null_fk FOREIGN KEY (id_institucion) REFERENCES public.institucion(id_institucion);
 U   ALTER TABLE ONLY public.coordinador DROP CONSTRAINT coordinador_institucion_null_fk;
       public          postgres    false    4152    232    222            L           2606    73875 -   coordinador coordinador_usuario_id_usuario_fk    FK CONSTRAINT     �   ALTER TABLE ONLY public.coordinador
    ADD CONSTRAINT coordinador_usuario_id_usuario_fk FOREIGN KEY (id_usuario) REFERENCES public.usuario(id_usuario);
 W   ALTER TABLE ONLY public.coordinador DROP CONSTRAINT coordinador_usuario_id_usuario_fk;
       public          postgres    false    243    4164    222            M           2606    73880 "   eme_habilidad fk_emehab_emergencia    FK CONSTRAINT     �   ALTER TABLE ONLY public.eme_habilidad
    ADD CONSTRAINT fk_emehab_emergencia FOREIGN KEY (id_emergencia) REFERENCES public.emergencia(id_emergencia);
 L   ALTER TABLE ONLY public.eme_habilidad DROP CONSTRAINT fk_emehab_emergencia;
       public          postgres    false    4146    224    226            N           2606    73885 !   eme_habilidad fk_emehab_habilidad    FK CONSTRAINT     �   ALTER TABLE ONLY public.eme_habilidad
    ADD CONSTRAINT fk_emehab_habilidad FOREIGN KEY (id_habilidad) REFERENCES public.habilidad(id_habilidad);
 K   ALTER TABLE ONLY public.eme_habilidad DROP CONSTRAINT fk_emehab_habilidad;
       public          postgres    false    4150    224    230            O           2606    73890 $   emergencia fk_emergencia_institucion    FK CONSTRAINT     �   ALTER TABLE ONLY public.emergencia
    ADD CONSTRAINT fk_emergencia_institucion FOREIGN KEY (id_institucion) REFERENCES public.institucion(id_institucion);
 N   ALTER TABLE ONLY public.emergencia DROP CONSTRAINT fk_emergencia_institucion;
       public          postgres    false    232    4152    226            P           2606    73895    ranking fk_ranking_tarea    FK CONSTRAINT     ~   ALTER TABLE ONLY public.ranking
    ADD CONSTRAINT fk_ranking_tarea FOREIGN KEY (id_tarea) REFERENCES public.tarea(id_tarea);
 B   ALTER TABLE ONLY public.ranking DROP CONSTRAINT fk_ranking_tarea;
       public          postgres    false    4160    236    239            Q           2606    73900    ranking fk_ranking_voluntario    FK CONSTRAINT     �   ALTER TABLE ONLY public.ranking
    ADD CONSTRAINT fk_ranking_voluntario FOREIGN KEY (id_voluntario) REFERENCES public.voluntario(id_voluntario);
 G   ALTER TABLE ONLY public.ranking DROP CONSTRAINT fk_ranking_voluntario;
       public          postgres    false    249    236    4170            R           2606    73905    tarea fk_tarea_emergencia    FK CONSTRAINT     �   ALTER TABLE ONLY public.tarea
    ADD CONSTRAINT fk_tarea_emergencia FOREIGN KEY (id_emergencia) REFERENCES public.emergencia(id_emergencia);
 C   ALTER TABLE ONLY public.tarea DROP CONSTRAINT fk_tarea_emergencia;
       public          postgres    false    4146    226    239            T           2606    73910 "   tarea_habilidad fk_tareahab_emehab    FK CONSTRAINT     �   ALTER TABLE ONLY public.tarea_habilidad
    ADD CONSTRAINT fk_tareahab_emehab FOREIGN KEY (id_eme_habilidad) REFERENCES public.eme_habilidad(id_eme_habilidad);
 L   ALTER TABLE ONLY public.tarea_habilidad DROP CONSTRAINT fk_tareahab_emehab;
       public          postgres    false    4144    240    224            U           2606    73915 !   tarea_habilidad fk_tareahab_tarea    FK CONSTRAINT     �   ALTER TABLE ONLY public.tarea_habilidad
    ADD CONSTRAINT fk_tareahab_tarea FOREIGN KEY (id_tarea) REFERENCES public.tarea(id_tarea);
 K   ALTER TABLE ONLY public.tarea_habilidad DROP CONSTRAINT fk_tareahab_tarea;
       public          postgres    false    240    4160    239            X           2606    73920 !   vol_habilidad fk_volhab_habilidad    FK CONSTRAINT     �   ALTER TABLE ONLY public.vol_habilidad
    ADD CONSTRAINT fk_volhab_habilidad FOREIGN KEY (id_habilidad) REFERENCES public.habilidad(id_habilidad);
 K   ALTER TABLE ONLY public.vol_habilidad DROP CONSTRAINT fk_volhab_habilidad;
       public          postgres    false    4150    247    230            Y           2606    73925 "   vol_habilidad fk_volhab_voluntario    FK CONSTRAINT     �   ALTER TABLE ONLY public.vol_habilidad
    ADD CONSTRAINT fk_volhab_voluntario FOREIGN KEY (id_voluntario) REFERENCES public.voluntario(id_voluntario);
 L   ALTER TABLE ONLY public.vol_habilidad DROP CONSTRAINT fk_volhab_voluntario;
       public          postgres    false    249    4170    247            S           2606    73930    tarea tarea_estado_id_estado_fk    FK CONSTRAINT     �   ALTER TABLE ONLY public.tarea
    ADD CONSTRAINT tarea_estado_id_estado_fk FOREIGN KEY (id_estado) REFERENCES public.estado(id_estado);
 I   ALTER TABLE ONLY public.tarea DROP CONSTRAINT tarea_estado_id_estado_fk;
       public          postgres    false    239    228    4148            V           2606    73935 %   usuario_rol usuario_rol_rol_id_rol_fk    FK CONSTRAINT     �   ALTER TABLE ONLY public.usuario_rol
    ADD CONSTRAINT usuario_rol_rol_id_rol_fk FOREIGN KEY (id_rol) REFERENCES public.rol(id_rol);
 O   ALTER TABLE ONLY public.usuario_rol DROP CONSTRAINT usuario_rol_rol_id_rol_fk;
       public          postgres    false    4158    244    237            W           2606    73940 -   usuario_rol usuario_rol_usuario_id_usuario_fk    FK CONSTRAINT     �   ALTER TABLE ONLY public.usuario_rol
    ADD CONSTRAINT usuario_rol_usuario_id_usuario_fk FOREIGN KEY (id_usuario) REFERENCES public.usuario(id_usuario);
 W   ALTER TABLE ONLY public.usuario_rol DROP CONSTRAINT usuario_rol_usuario_id_usuario_fk;
       public          postgres    false    243    4164    244            Z           2606    73945 +   voluntario voluntario_usuario_id_usuario_fk    FK CONSTRAINT     �   ALTER TABLE ONLY public.voluntario
    ADD CONSTRAINT voluntario_usuario_id_usuario_fk FOREIGN KEY (id_usuario) REFERENCES public.usuario(id_usuario);
 U   ALTER TABLE ONLY public.voluntario DROP CONSTRAINT voluntario_usuario_id_usuario_fk;
       public          postgres    false    249    4164    243            �   A   x���� C���0UB0�]��5��/�E2=M�2�y�P���lt�/j*��J$gZ�{"�ԡ	      �      x�3�4�4�2�\1z\\\ ;          -   x�3�L�M5)��ɜFFƺ&���p���g�W� �
         H   x�3�H�K�L�+I�2�t�SHI-N,*�����2�S�R���9��SsS�L8CR�r3�@�=... ���         z   x�-�11��+������Z�%��Q�'���$��f4{�n����b��V���g:��[85VKbY[��vG�y���tv,�7o1�
X��(c�Q����
�~�p^����z��ND?#�2�         �  x�uQ�j�@>��B��%��X��e!qJa�^Աv�d,9�cHަ�z(}�X5v�BO�4����l��>�-..^
X߰'A��>,1qʎ�?���8��(��>K�������}�h]��l�M�P��p	��ࠡ3�D6��J�$)���
:VA_��k}O�+�B�,K��mٚ�-�$𩈂���F0��E4yL��PHa���z��SR3 ��\fahP�<��j�ov��v��Uiu���(-��pk�Y��k'g�N��~w�,ZX�~�9A����&�l�:-�G�����u�?��b��L�WwMI�:��}J.b�ځ�����ak�*�z�����M�;��n
p�4�N;��b�%��~Lq��$s������1h���[]U�_�C��         �  x���Oo�8��ʧ�1�3��S�M�ݢI�T Pm5Ѯcue{���G'�-ǲ$	 ��#�Hj���:��*#����xi=e�}���w�n$.*ي��&���q�jŁ2uXb�TB[����A��Q���L�y/�2���R�;��Ò�pDpR+��wN-�B��j��,����������u�?������s^�Ŵz��ѥh��f$�!��L�Zo%F�4f�d2�2j'|�Z��Tv�����]�	�Hh��E/�1��|38�j��Z/�E� 0F@&�T.���]�4F�6���z��q%�ט(�kZU��\䳪�a�,9�ߋ���kmm��vx9*,)@68�s�;�/����wLp?9/g���ɢz�^�??~���������KGo{�Wf�_�>�]o:{����}�����l��r�DT!*�	nI�L��vX�����l5o5�X�5��=�`��X�k�ca���%mǒ�cI�!��-�³Jk�!�2!�2!�2!�2!�2!�kpCH�CH�CH�cI����X�n,i7�4�/���v7�t�0����!�2!�2!�2!�2!�2!�2!�2�%�ǒ�cI���C7�W�1�Xa�PY����]�H(%�Ȼ)7�ԁ���,t3n�!J�t���,t�ȡ�)]u�-����o#G.�Fʩ!���SV����īv�m�!UY&�f�&�*Gr.�P�hd:$��6zNy!�����
�Fi%����"�z�*b8�=d�{-�׆=�=h�W�R�)6��l�Fvb����!	�,]�����>�m`�� %�Wן���Q�ڵ�^*��"���H琊�Ǯt�U�1}�P(�S�N�pQW����G�x,��1k�E�ʱ�qNə2ʧBs[4n\�a��J����uTVH"��m�x�q��j��Rm�-;w��}H�6E�_�k+HaQSw�����'�ILy���x�i�!�2V�Ʊ��	�Z9�xS>4�)m��k�����?��5YU��}�w�SHo�Y��V�oL;��!02�p�Q�S�t��O5�S��T�9�h�	s�z�B��;b|*�����|�׳��z�O��ͨ�(��Ko=c��E��W��͓͙��9��̊�.N�jq9�����C]��Y��yX.�t���(�O9��5w?J����~,�2�]��D�E��>�y�$6��V,�`0�P4�N܉'�w":��g添����m���zsu����/U�X��y�x,W0��U������...ξ�������9-      
      x������ � �         0   x�3�tt����2�t��r��st��2���	�q������� ��	%      )      x������ � �         �   x�͐a
� ��S��m^b'��j`��g�JYw�^���;l~�}��y��uyO�빏 H�!�͠�5�ire>>�k>�}"�,Ĝ8yNRf�_Wc���b_m-H��9�U<�� �����=]k���.�����{�            x�3�4�44�2�4Q1z\\\ �w         �  x���M��8�ϙ�1� �(�FE|A��j+@���-���~��ggk��lyI��9�n�;��8�s�R��$���_���P����w����.x���.Ÿ�Ď%���R�u+>͵�e�6�.���d����7qf���O�� �5;���Uqi�En@����������g�OE{�jx��Y�.�kFw�z0�2��j�����.���d�7O����(X�8�T�ś�`��'a��F[�~.As;�Jsݦc+D�h �yvG4�g5�@=���MZ��{���y�e˦�e�:�s"�}.U>��e2#���Z���`٠l0�w��IX<�G�"Ba���M�I�`g��2���lzzs=�0���:J�]�wي��5g{� ,T#J�Od�&?��:-�~y_b�Z�_ǁcue;�܉� ���4wW�t����jQ�,t����9+d�f�t���l�Ʈy�i
]YPuB�p6�iUrP�󮂦i{4���M���3�z�g����USOV�Z��-�c���>�g�v4�9�	��1ږ����e���w�}�0J�)%]��4��3g�K5�i��6���p-�ƺvyS��!s.IY̫�L�]���:Yg߅�@��>� ��"<#�ĕ��L��rH�]5��6&b��3�5Ml��e�^N�e��G����1�
#����s�vKI$UT��I�.��Oha���]f-&^Ty�soA�Fڗ�Ϲ�M��M"���$8�@����$I�H��߉���PC��bi�8 �����xA:�B���c.���`��V2��n=}>��		<���K} (�A���<T/^�
�-���� ��)E]H��^���e(��lG.���q������&H`C�8�HE�GDIV�y�_�R����۟�]�g         b   x�ͱ�0�,��$EY������f`�K/�8궮z9�ЌL��vk]��=�oj��ć�W�I=4�R�M�[�_-j{������Ƈ��{����            x������ � �         Q   x�ʱ�0���8$�����+�4Rja.%tQFE55h��Cm�?��=�E�т/m��z�M.�!B`     