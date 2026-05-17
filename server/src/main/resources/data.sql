insert into rank (rank_name)
values
    ('Manager'),
    ('Member');

insert into division (division_name)
values
    ('Finance'),
    ('AI'),
    ('Cloud');

insert into project (project_name, project_content, division_id)
values
    ('Trading System', 'Core trading platform development', 1),
    ('Demand Forecast AI', 'Machine learning demand forecasting', 2),
    ('AWS Migration', 'Migration from on-premise to AWS', 3);

insert into users (is_manager)
values
    (true),
    (false),
    (false);

insert into profile (
    hrid,
    name,
    mail_address,
    free,
    rank_id,
    user_id
)
values
    ('A001', 'Taro Yamada', 'taro@example.com', 'Backend engineer', 1, 1),
    ('A002', 'Hanako Sato', 'hanako@example.com', 'Machine learning engineer', 2, 2),
    ('A003', 'Ichiro Suzuki', 'ichiro@example.com', 'Cloud infrastructure engineer', 2, 3);

insert into skill (skill_name)
values
    ('Java'),
    ('Spring Boot'),
    ('Python'),
    ('AI'),
    ('AWS'),
    ('Terraform'),
    ('React'),
    ('PostgreSQL'),
    ('Docker');

insert into career (
    start_time,
    end_time,
    profile_id,
    project_id
)
values
    ('2023-04-01', null, 'A001', 1),
    ('2022-04-01', null, 'A002', 2),
    ('2021-04-01', null, 'A003', 3),

    ('2021-04-01', '2023-03-31', 'A001', 2);

insert into career_skill (career_id, skill_id)
values
    -- A001 current
    (1, 1),
    (1, 2),

    -- A002
    (2, 3),
    (2, 4),
    (2, 1),

    -- A003
    (3, 5),
    (3, 6),
    (3, 9),

    -- A001 past AI project
    (4, 1),
    (4, 3),
    (4, 7);