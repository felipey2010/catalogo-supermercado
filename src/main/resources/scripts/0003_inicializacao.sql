-- ACESSO
INSERT INTO public.acesso(id, descricao, nome, dt_inclusao, dt_remocao) VALUES
     (1, 'Acesso - Mensagem Global', 'MENSAGEM_GLOBAL', current_timestamp , null),
     (2, 'Acesso - Perfis de Acesso', 'PERFIS_DE_ACESSO', current_timestamp , null),
     (3, 'Acesso - Usuário', 'USUARIO', current_timestamp , null);

-- PERFIL
INSERT INTO public.perfil(id, dt_inclusao, dt_remocao, nome, iseditable) VALUES
                  (1,current_timestamp, null, 'SUPER ADMINISTRADOR', false);

-- PERFIL_ACESSO - SUPER ADMINISTRADOR
INSERT INTO public.perfil_acesso(perfil_id, acesso_id) VALUES
    (1, 1),(1, 2),(1, 3);
