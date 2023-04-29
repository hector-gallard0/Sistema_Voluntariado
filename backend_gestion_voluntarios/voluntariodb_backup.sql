PGDMP         6                {            voluntariodb    15.1    15.1 Q    t           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            u           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            v           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            w           1262    60261    voluntariodb    DATABASE        CREATE DATABASE voluntariodb WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'Spanish_Chile.1252';
    DROP DATABASE voluntariodb;
                postgres    false            �            1259    60826    coordinador    TABLE     �   CREATE TABLE public.coordinador (
    id_coordinador integer NOT NULL,
    id_usuario integer NOT NULL,
    id_institucion integer NOT NULL
);
    DROP TABLE public.coordinador;
       public         heap    postgres    false            �            1259    60829    coordinador_seq    SEQUENCE     �   CREATE SEQUENCE public.coordinador_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 &   DROP SEQUENCE public.coordinador_seq;
       public          postgres    false            �            1259    60830    eme_habilidad    TABLE     �   CREATE TABLE public.eme_habilidad (
    id_eme_habilidad integer NOT NULL,
    id_emergencia integer,
    id_habilidad integer
);
 !   DROP TABLE public.eme_habilidad;
       public         heap    postgres    false            �            1259    60833 
   emergencia    TABLE     �   CREATE TABLE public.emergencia (
    id_emergencia integer NOT NULL,
    nombre character varying(100),
    descripcion character varying(400),
    fecha_inicio date,
    fecha_fin date,
    id_institucion integer NOT NULL
);
    DROP TABLE public.emergencia;
       public         heap    postgres    false            �            1259    60838    emergencia_seq    SEQUENCE     �   CREATE SEQUENCE public.emergencia_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 %   DROP SEQUENCE public.emergencia_seq;
       public          postgres    false            �            1259    60839    estado    TABLE     f   CREATE TABLE public.estado (
    id_estado integer NOT NULL,
    descripcion character varying(20)
);
    DROP TABLE public.estado;
       public         heap    postgres    false            �            1259    60842 
   estado_seq    SEQUENCE     �   CREATE SEQUENCE public.estado_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 !   DROP SEQUENCE public.estado_seq;
       public          postgres    false            �            1259    60843 	   habilidad    TABLE     {   CREATE TABLE public.habilidad (
    id_habilidad numeric(4,0) NOT NULL,
    descripcion character varying(100) NOT NULL
);
    DROP TABLE public.habilidad;
       public         heap    postgres    false            �            1259    60846    habilidad_seq    SEQUENCE     �   CREATE SEQUENCE public.habilidad_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 $   DROP SEQUENCE public.habilidad_seq;
       public          postgres    false            �            1259    60847    institucion    TABLE     �   CREATE TABLE public.institucion (
    id_institucion numeric(4,0) NOT NULL,
    nombre character varying(100),
    descripcion character varying(400)
);
    DROP TABLE public.institucion;
       public         heap    postgres    false            �            1259    60852    ranking    TABLE     �   CREATE TABLE public.ranking (
    id_ranking integer NOT NULL,
    id_voluntario integer,
    id_tarea integer,
    puntaje numeric(8,0),
    flag_invitado numeric(1,0),
    flag_participa numeric(1,0)
);
    DROP TABLE public.ranking;
       public         heap    postgres    false            x           0    0    TABLE ranking    COMMENT     u   COMMENT ON TABLE public.ranking IS 'los flgInvitado, flgParticipa 1 si la respuesta es si, 2 si la respuesta es no';
          public          postgres    false    224            �            1259    60855    rol    TABLE     `   CREATE TABLE public.rol (
    id_rol integer NOT NULL,
    nombre character varying NOT NULL
);
    DROP TABLE public.rol;
       public         heap    postgres    false            �            1259    60860    tarea    TABLE     /  CREATE TABLE public.tarea (
    id_tarea integer NOT NULL,
    nombre character varying(60),
    descripcion character varying(300),
    cant_vol_requeridos numeric(4,0),
    cant_vol_inscritos numeric(4,0),
    id_emergencia integer,
    fecha_inicio date,
    fecha_fin date,
    id_estado integer
);
    DROP TABLE public.tarea;
       public         heap    postgres    false            �            1259    60863    tarea_habilidad    TABLE     �   CREATE TABLE public.tarea_habilidad (
    id_tarea_habilidad integer NOT NULL,
    id_eme_habilidad integer,
    id_tarea integer
);
 #   DROP TABLE public.tarea_habilidad;
       public         heap    postgres    false            �            1259    60866 	   tarea_seq    SEQUENCE     �   CREATE SEQUENCE public.tarea_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
     DROP SEQUENCE public.tarea_seq;
       public          postgres    false            �            1259    60867    usuario    TABLE     �   CREATE TABLE public.usuario (
    id_usuario integer NOT NULL,
    nombre text NOT NULL,
    apellido text NOT NULL,
    email text NOT NULL,
    password text NOT NULL
);
    DROP TABLE public.usuario;
       public         heap    postgres    false            �            1259    60872    usuario_rol    TABLE     �   CREATE TABLE public.usuario_rol (
    id_usuario_rol integer NOT NULL,
    id_usuario integer NOT NULL,
    id_rol integer NOT NULL
);
    DROP TABLE public.usuario_rol;
       public         heap    postgres    false            �            1259    60875    usuario_rol_seq    SEQUENCE     �   CREATE SEQUENCE public.usuario_rol_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 &   DROP SEQUENCE public.usuario_rol_seq;
       public          postgres    false            �            1259    60876    usuario_seq    SEQUENCE     t   CREATE SEQUENCE public.usuario_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 "   DROP SEQUENCE public.usuario_seq;
       public          postgres    false            �            1259    60877    vol_habilidad    TABLE     �   CREATE TABLE public.vol_habilidad (
    id_voluntario_habilidad integer NOT NULL,
    id_voluntario integer,
    id_habilidad integer
);
 !   DROP TABLE public.vol_habilidad;
       public         heap    postgres    false            �            1259    60880    vol_habilidad_seq    SEQUENCE     �   CREATE SEQUENCE public.vol_habilidad_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 (   DROP SEQUENCE public.vol_habilidad_seq;
       public          postgres    false            �            1259    60881 
   voluntario    TABLE     h   CREATE TABLE public.voluntario (
    id_voluntario integer NOT NULL,
    id_usuario integer NOT NULL
);
    DROP TABLE public.voluntario;
       public         heap    postgres    false            �            1259    60884    voluntario_seq    SEQUENCE     �   CREATE SEQUENCE public.voluntario_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 %   DROP SEQUENCE public.voluntario_seq;
       public          postgres    false            [          0    60826    coordinador 
   TABLE DATA           Q   COPY public.coordinador (id_coordinador, id_usuario, id_institucion) FROM stdin;
    public          postgres    false    214   �_       ]          0    60830    eme_habilidad 
   TABLE DATA           V   COPY public.eme_habilidad (id_eme_habilidad, id_emergencia, id_habilidad) FROM stdin;
    public          postgres    false    216   �_       ^          0    60833 
   emergencia 
   TABLE DATA           q   COPY public.emergencia (id_emergencia, nombre, descripcion, fecha_inicio, fecha_fin, id_institucion) FROM stdin;
    public          postgres    false    217   �_       `          0    60839    estado 
   TABLE DATA           8   COPY public.estado (id_estado, descripcion) FROM stdin;
    public          postgres    false    219   ,`       b          0    60843 	   habilidad 
   TABLE DATA           >   COPY public.habilidad (id_habilidad, descripcion) FROM stdin;
    public          postgres    false    221   �`       d          0    60847    institucion 
   TABLE DATA           J   COPY public.institucion (id_institucion, nombre, descripcion) FROM stdin;
    public          postgres    false    223   a       e          0    60852    ranking 
   TABLE DATA           n   COPY public.ranking (id_ranking, id_voluntario, id_tarea, puntaje, flag_invitado, flag_participa) FROM stdin;
    public          postgres    false    224   �b       f          0    60855    rol 
   TABLE DATA           -   COPY public.rol (id_rol, nombre) FROM stdin;
    public          postgres    false    225   �b       g          0    60860    tarea 
   TABLE DATA           �   COPY public.tarea (id_tarea, nombre, descripcion, cant_vol_requeridos, cant_vol_inscritos, id_emergencia, fecha_inicio, fecha_fin, id_estado) FROM stdin;
    public          postgres    false    226   c       h          0    60863    tarea_habilidad 
   TABLE DATA           Y   COPY public.tarea_habilidad (id_tarea_habilidad, id_eme_habilidad, id_tarea) FROM stdin;
    public          postgres    false    227   +c       j          0    60867    usuario 
   TABLE DATA           P   COPY public.usuario (id_usuario, nombre, apellido, email, password) FROM stdin;
    public          postgres    false    229   Hc       k          0    60872    usuario_rol 
   TABLE DATA           I   COPY public.usuario_rol (id_usuario_rol, id_usuario, id_rol) FROM stdin;
    public          postgres    false    230   Gf       n          0    60877    vol_habilidad 
   TABLE DATA           ]   COPY public.vol_habilidad (id_voluntario_habilidad, id_voluntario, id_habilidad) FROM stdin;
    public          postgres    false    233   �f       p          0    60881 
   voluntario 
   TABLE DATA           ?   COPY public.voluntario (id_voluntario, id_usuario) FROM stdin;
    public          postgres    false    235   �f       y           0    0    coordinador_seq    SEQUENCE SET     >   SELECT pg_catalog.setval('public.coordinador_seq', 11, true);
          public          postgres    false    215            z           0    0    emergencia_seq    SEQUENCE SET     =   SELECT pg_catalog.setval('public.emergencia_seq', 14, true);
          public          postgres    false    218            {           0    0 
   estado_seq    SEQUENCE SET     9   SELECT pg_catalog.setval('public.estado_seq', 1, false);
          public          postgres    false    220            |           0    0    habilidad_seq    SEQUENCE SET     ;   SELECT pg_catalog.setval('public.habilidad_seq', 8, true);
          public          postgres    false    222            }           0    0 	   tarea_seq    SEQUENCE SET     7   SELECT pg_catalog.setval('public.tarea_seq', 7, true);
          public          postgres    false    228            ~           0    0    usuario_rol_seq    SEQUENCE SET     >   SELECT pg_catalog.setval('public.usuario_rol_seq', 29, true);
          public          postgres    false    231                       0    0    usuario_seq    SEQUENCE SET     :   SELECT pg_catalog.setval('public.usuario_seq', 57, true);
          public          postgres    false    232            �           0    0    vol_habilidad_seq    SEQUENCE SET     ?   SELECT pg_catalog.setval('public.vol_habilidad_seq', 2, true);
          public          postgres    false    234            �           0    0    voluntario_seq    SEQUENCE SET     =   SELECT pg_catalog.setval('public.voluntario_seq', 18, true);
          public          postgres    false    236            �           2606    60886    coordinador coordinador_pk 
   CONSTRAINT     d   ALTER TABLE ONLY public.coordinador
    ADD CONSTRAINT coordinador_pk PRIMARY KEY (id_coordinador);
 D   ALTER TABLE ONLY public.coordinador DROP CONSTRAINT coordinador_pk;
       public            postgres    false    214            �           2606    60888     eme_habilidad eme_habilidad_pkey 
   CONSTRAINT     l   ALTER TABLE ONLY public.eme_habilidad
    ADD CONSTRAINT eme_habilidad_pkey PRIMARY KEY (id_eme_habilidad);
 J   ALTER TABLE ONLY public.eme_habilidad DROP CONSTRAINT eme_habilidad_pkey;
       public            postgres    false    216            �           2606    60890    emergencia emergencia_pkey 
   CONSTRAINT     c   ALTER TABLE ONLY public.emergencia
    ADD CONSTRAINT emergencia_pkey PRIMARY KEY (id_emergencia);
 D   ALTER TABLE ONLY public.emergencia DROP CONSTRAINT emergencia_pkey;
       public            postgres    false    217            �           2606    60892    estado estado_tarea_pkey 
   CONSTRAINT     ]   ALTER TABLE ONLY public.estado
    ADD CONSTRAINT estado_tarea_pkey PRIMARY KEY (id_estado);
 B   ALTER TABLE ONLY public.estado DROP CONSTRAINT estado_tarea_pkey;
       public            postgres    false    219            �           2606    60894    habilidad habilidad_pkey 
   CONSTRAINT     `   ALTER TABLE ONLY public.habilidad
    ADD CONSTRAINT habilidad_pkey PRIMARY KEY (id_habilidad);
 B   ALTER TABLE ONLY public.habilidad DROP CONSTRAINT habilidad_pkey;
       public            postgres    false    221            �           2606    60896    institucion institucion_pkey 
   CONSTRAINT     f   ALTER TABLE ONLY public.institucion
    ADD CONSTRAINT institucion_pkey PRIMARY KEY (id_institucion);
 F   ALTER TABLE ONLY public.institucion DROP CONSTRAINT institucion_pkey;
       public            postgres    false    223            �           2606    60898    ranking ranking_pkey 
   CONSTRAINT     Z   ALTER TABLE ONLY public.ranking
    ADD CONSTRAINT ranking_pkey PRIMARY KEY (id_ranking);
 >   ALTER TABLE ONLY public.ranking DROP CONSTRAINT ranking_pkey;
       public            postgres    false    224            �           2606    60900 
   rol rol_pk 
   CONSTRAINT     L   ALTER TABLE ONLY public.rol
    ADD CONSTRAINT rol_pk PRIMARY KEY (id_rol);
 4   ALTER TABLE ONLY public.rol DROP CONSTRAINT rol_pk;
       public            postgres    false    225            �           2606    60902 $   tarea_habilidad tarea_habilidad_pkey 
   CONSTRAINT     r   ALTER TABLE ONLY public.tarea_habilidad
    ADD CONSTRAINT tarea_habilidad_pkey PRIMARY KEY (id_tarea_habilidad);
 N   ALTER TABLE ONLY public.tarea_habilidad DROP CONSTRAINT tarea_habilidad_pkey;
       public            postgres    false    227            �           2606    60904    tarea tarea_pkey 
   CONSTRAINT     T   ALTER TABLE ONLY public.tarea
    ADD CONSTRAINT tarea_pkey PRIMARY KEY (id_tarea);
 :   ALTER TABLE ONLY public.tarea DROP CONSTRAINT tarea_pkey;
       public            postgres    false    226            �           2606    60906    usuario user_pkey 
   CONSTRAINT     W   ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT user_pkey PRIMARY KEY (id_usuario);
 ;   ALTER TABLE ONLY public.usuario DROP CONSTRAINT user_pkey;
       public            postgres    false    229            �           2606    60908    usuario_rol usuario_rol_pk 
   CONSTRAINT     d   ALTER TABLE ONLY public.usuario_rol
    ADD CONSTRAINT usuario_rol_pk PRIMARY KEY (id_usuario_rol);
 D   ALTER TABLE ONLY public.usuario_rol DROP CONSTRAINT usuario_rol_pk;
       public            postgres    false    230            �           2606    60910     vol_habilidad vol_habilidad_pkey 
   CONSTRAINT     s   ALTER TABLE ONLY public.vol_habilidad
    ADD CONSTRAINT vol_habilidad_pkey PRIMARY KEY (id_voluntario_habilidad);
 J   ALTER TABLE ONLY public.vol_habilidad DROP CONSTRAINT vol_habilidad_pkey;
       public            postgres    false    233            �           2606    60912    voluntario voluntario_pkey 
   CONSTRAINT     c   ALTER TABLE ONLY public.voluntario
    ADD CONSTRAINT voluntario_pkey PRIMARY KEY (id_voluntario);
 D   ALTER TABLE ONLY public.voluntario DROP CONSTRAINT voluntario_pkey;
       public            postgres    false    235            �           2606    60913 +   coordinador coordinador_institucion_null_fk    FK CONSTRAINT     �   ALTER TABLE ONLY public.coordinador
    ADD CONSTRAINT coordinador_institucion_null_fk FOREIGN KEY (id_institucion) REFERENCES public.institucion(id_institucion);
 U   ALTER TABLE ONLY public.coordinador DROP CONSTRAINT coordinador_institucion_null_fk;
       public          postgres    false    214    3244    223            �           2606    60918 -   coordinador coordinador_usuario_id_usuario_fk    FK CONSTRAINT     �   ALTER TABLE ONLY public.coordinador
    ADD CONSTRAINT coordinador_usuario_id_usuario_fk FOREIGN KEY (id_usuario) REFERENCES public.usuario(id_usuario);
 W   ALTER TABLE ONLY public.coordinador DROP CONSTRAINT coordinador_usuario_id_usuario_fk;
       public          postgres    false    3254    214    229            �           2606    60923 "   eme_habilidad fk_emehab_emergencia    FK CONSTRAINT     �   ALTER TABLE ONLY public.eme_habilidad
    ADD CONSTRAINT fk_emehab_emergencia FOREIGN KEY (id_emergencia) REFERENCES public.emergencia(id_emergencia);
 L   ALTER TABLE ONLY public.eme_habilidad DROP CONSTRAINT fk_emehab_emergencia;
       public          postgres    false    217    216    3238            �           2606    60928 !   eme_habilidad fk_emehab_habilidad    FK CONSTRAINT     �   ALTER TABLE ONLY public.eme_habilidad
    ADD CONSTRAINT fk_emehab_habilidad FOREIGN KEY (id_habilidad) REFERENCES public.habilidad(id_habilidad);
 K   ALTER TABLE ONLY public.eme_habilidad DROP CONSTRAINT fk_emehab_habilidad;
       public          postgres    false    221    3242    216            �           2606    60933 $   emergencia fk_emergencia_institucion    FK CONSTRAINT     �   ALTER TABLE ONLY public.emergencia
    ADD CONSTRAINT fk_emergencia_institucion FOREIGN KEY (id_institucion) REFERENCES public.institucion(id_institucion);
 N   ALTER TABLE ONLY public.emergencia DROP CONSTRAINT fk_emergencia_institucion;
       public          postgres    false    217    223    3244            �           2606    60938    ranking fk_ranking_tarea    FK CONSTRAINT     ~   ALTER TABLE ONLY public.ranking
    ADD CONSTRAINT fk_ranking_tarea FOREIGN KEY (id_tarea) REFERENCES public.tarea(id_tarea);
 B   ALTER TABLE ONLY public.ranking DROP CONSTRAINT fk_ranking_tarea;
       public          postgres    false    224    3250    226            �           2606    60943    ranking fk_ranking_voluntario    FK CONSTRAINT     �   ALTER TABLE ONLY public.ranking
    ADD CONSTRAINT fk_ranking_voluntario FOREIGN KEY (id_voluntario) REFERENCES public.voluntario(id_voluntario);
 G   ALTER TABLE ONLY public.ranking DROP CONSTRAINT fk_ranking_voluntario;
       public          postgres    false    235    3260    224            �           2606    60948    tarea fk_tarea_emergencia    FK CONSTRAINT     �   ALTER TABLE ONLY public.tarea
    ADD CONSTRAINT fk_tarea_emergencia FOREIGN KEY (id_emergencia) REFERENCES public.emergencia(id_emergencia);
 C   ALTER TABLE ONLY public.tarea DROP CONSTRAINT fk_tarea_emergencia;
       public          postgres    false    226    3238    217            �           2606    60953 "   tarea_habilidad fk_tareahab_emehab    FK CONSTRAINT     �   ALTER TABLE ONLY public.tarea_habilidad
    ADD CONSTRAINT fk_tareahab_emehab FOREIGN KEY (id_eme_habilidad) REFERENCES public.eme_habilidad(id_eme_habilidad);
 L   ALTER TABLE ONLY public.tarea_habilidad DROP CONSTRAINT fk_tareahab_emehab;
       public          postgres    false    227    3236    216            �           2606    60958 !   tarea_habilidad fk_tareahab_tarea    FK CONSTRAINT     �   ALTER TABLE ONLY public.tarea_habilidad
    ADD CONSTRAINT fk_tareahab_tarea FOREIGN KEY (id_tarea) REFERENCES public.tarea(id_tarea);
 K   ALTER TABLE ONLY public.tarea_habilidad DROP CONSTRAINT fk_tareahab_tarea;
       public          postgres    false    226    227    3250            �           2606    60963 !   vol_habilidad fk_volhab_habilidad    FK CONSTRAINT     �   ALTER TABLE ONLY public.vol_habilidad
    ADD CONSTRAINT fk_volhab_habilidad FOREIGN KEY (id_habilidad) REFERENCES public.habilidad(id_habilidad);
 K   ALTER TABLE ONLY public.vol_habilidad DROP CONSTRAINT fk_volhab_habilidad;
       public          postgres    false    233    3242    221            �           2606    60968 "   vol_habilidad fk_volhab_voluntario    FK CONSTRAINT     �   ALTER TABLE ONLY public.vol_habilidad
    ADD CONSTRAINT fk_volhab_voluntario FOREIGN KEY (id_voluntario) REFERENCES public.voluntario(id_voluntario);
 L   ALTER TABLE ONLY public.vol_habilidad DROP CONSTRAINT fk_volhab_voluntario;
       public          postgres    false    235    233    3260            �           2606    60973    tarea tarea_estado_id_estado_fk    FK CONSTRAINT     �   ALTER TABLE ONLY public.tarea
    ADD CONSTRAINT tarea_estado_id_estado_fk FOREIGN KEY (id_estado) REFERENCES public.estado(id_estado);
 I   ALTER TABLE ONLY public.tarea DROP CONSTRAINT tarea_estado_id_estado_fk;
       public          postgres    false    226    3240    219            �           2606    60978 %   usuario_rol usuario_rol_rol_id_rol_fk    FK CONSTRAINT     �   ALTER TABLE ONLY public.usuario_rol
    ADD CONSTRAINT usuario_rol_rol_id_rol_fk FOREIGN KEY (id_rol) REFERENCES public.rol(id_rol);
 O   ALTER TABLE ONLY public.usuario_rol DROP CONSTRAINT usuario_rol_rol_id_rol_fk;
       public          postgres    false    230    3248    225            �           2606    60983 -   usuario_rol usuario_rol_usuario_id_usuario_fk    FK CONSTRAINT     �   ALTER TABLE ONLY public.usuario_rol
    ADD CONSTRAINT usuario_rol_usuario_id_usuario_fk FOREIGN KEY (id_usuario) REFERENCES public.usuario(id_usuario);
 W   ALTER TABLE ONLY public.usuario_rol DROP CONSTRAINT usuario_rol_usuario_id_usuario_fk;
       public          postgres    false    3254    229    230            �           2606    60988 +   voluntario voluntario_usuario_id_usuario_fk    FK CONSTRAINT     �   ALTER TABLE ONLY public.voluntario
    ADD CONSTRAINT voluntario_usuario_id_usuario_fk FOREIGN KEY (id_usuario) REFERENCES public.usuario(id_usuario);
 U   ALTER TABLE ONLY public.voluntario DROP CONSTRAINT voluntario_usuario_id_usuario_fk;
       public          postgres    false    235    229    3254            [   A   x���� C���0UB0�]��5��/�E2=M�2�y�P���lt�/j*��J$gZ�{"�ԡ	      ]      x������ � �      ^   *   x�3�L�M5)��ɜFFƺ&���p���W� �>	T      `   H   x�3�H�K�L�+I�2�t�SHI-N,*�����2�S�R���9��SsS�L8CR�r3�@�=... ���      b   z   x�-�11��+������Z�%��Q�'���$��f4{�n����b��V���g:��[85VKbY[��vG�y���tv,�7o1�
X��(c�Q����
�~�p^����z��ND?#�2�      d   �  x�uQ�j�@>��B��%��X��e!qJa�^Աv�d,9�cHަ�z(}�X5v�BO�4����l��>�-..^
X߰'A��>,1qʎ�?���8��(��>K�������}�h]��l�M�P��p	��ࠡ3�D6��J�$)���
:VA_��k}O�+�B�,K��mٚ�-�$𩈂���F0��E4yL��PHa���z��SR3 ��\fahP�<��j�ov��v��Uiu���(-��pk�Y��k'g�N��~w�,ZX�~�9A����&�l�:-�G�����u�?��b��L�WwMI�:��}J.b�ځ�����ak�*�z�����M�;��n
p�4�N;��b�%��~Lq��$s������1h���[]U�_�C��      e      x������ � �      f   0   x�3�tt����2�t��r��st��2���	�q������� ��	%      g      x������ � �      h      x������ � �      j   �  x�uQK��8\��q�A���GEQk6�C�+���~8g�35�lR�_%����0�P���P�1E ��!���:�0&����P����N6_��D`ⸯ)0!�N�7��o����G��co�!z���B�-2ߑ��9���]����
��?���t�jT0�4�@������a�L�̀ӄ�s�YQ/,h���؝Fc��.�xb�f���:�X�GE�aD���USHu�ȴK�g�?�7����g����3����O��N�-���H.L�6g�p���v�˹� F���^��`5����7m2>e�O8lo�x�K���+�s��R�~�5.��u�*4�r�^f$���JT���a���̱�8���8�mCsx\�$��؋����4�E���h�nό�����
��O�oF+ه�5�
=n#�c5�����O�8j���zp�%ӏ�|��ZF�L�s�״Q��������^�y�И�E�E�$���.���|��T�,I:*��W�;�D`���]��vq��n�>�Y0w�&���Qɮ$f���'s��ֲ�!u<F��q����&��B�Za�&�&KN�IO2���þ��NV�]p[}���y�]ۖ�k�@z,v�Ք����(����~�2<�ԅ����?tƝ�p!�����2/
�Fp�\�mY��Z>iP�� }�
ﲣK�T�,��C�ڏO�`�r��*�5�k�U^	%��3A�a�rKzH      k   b   x�ͱ�0�,��$EY������f`�K/�8궮z9�ЌL��vk]��=�oj��ć�W�I=4�R�M�[�_-j{������Ƈ��{����      n      x������ � �      p   ?   x����0���&��z��:`�kuQ��è����V.Ÿ�£4�2x+�<�Ʒrx/��B     