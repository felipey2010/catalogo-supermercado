-- PESSOA FÍSICA
INSERT INTO public.pessoa(
    codigo, dt_inclusao, dt_remocao, email, nome, genero, naturalidade, nacionalidade_id)
VALUES ('26913916043', current_timestamp, null, 'test@gmail.com', 'Felipey', 'MASCULINO', 'Boa Vista', 76);

-- USUÁRIO
INSERT INTO public.usuario(login, ativo, dt_inclusao, dt_remocao, dt_ultimo_login,
    senha, pessoa_id)
VALUES ('26913916043', true, current_timestamp, null, null,
        '202cb962ac59075b964b07152d234b70', 1);

--USUARIO PERFIL
INSERT INTO public.usuario_perfil(usuario_id, perfil_id) VALUES(1, 1);
