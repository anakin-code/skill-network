delete from career_skill;
delete from career;
delete from profile;
delete from app_user;
delete from project;
delete from skill;
delete from division;
delete from company;
delete from rank;

insert into company (company_name)
values
    ('A'),
    ('B'),
    ('C');

insert into rank (rank_name)
values
    ('EP'),
    ('PP'),
    ('AP'),
    ('LD'),
    ('ST'),
    ('AST'),
    ('others');

insert into division (division_name, company_id)
values
    ('A1', 1),
    ('A2', 1),
    ('A3', 1),
    ('A', 2),
    ('B', 2),
    ('R&D', 3);

insert into project (project_name, project_content, division_id)
values
    ('Trading System',
     'Development of a stock trading system for securities companies. Responsible for order management, execution processing, risk control, and real-time data handling using Java, Spring Boot, and PostgreSQL.',
     1),

    ('Risk Analytics Platform',
     'Development of a risk analytics platform for banks and securities firms. Implemented market risk calculation, exposure analysis, and SQL reporting functions.',
     1),

    ('Settlement Management System',
     'Development of a settlement management system for financial institutions. Responsible for daily batch processing, system integration, report generation, and incident handling.',
     1),

    ('AI Demand Forecast',
     'Development of an AI demand forecasting system for retail companies. Responsible for sales analysis, time-series forecasting, and machine learning model improvement.',
     2),

    ('Customer Churn Prediction',
     'Development of a customer churn prediction system for subscription services. Performed behavior log analysis and dashboard development.',
     2),

    ('Data Lake Platform',
     'Development of a company-wide analytics platform. Responsible for ETL pipelines and analytics infrastructure using AWS, Python, and SQL.',
     2),

    ('Cloud Migration',
     'Cloud migration project from on-premise environments to AWS. Responsible for infrastructure automation using Terraform and Docker.',
     3),

    ('Security Monitoring',
     'Development of an internal security monitoring platform. Responsible for anomaly detection, log analysis, and Kubernetes-based monitoring systems.',
     3),

    ('Internal Developer Platform',
     'Development of a shared internal platform for developers. Responsible for CI/CD pipelines, automated deployment, and monitoring infrastructure.',
     3),

    ('DX Consulting Platform',
     'Business analysis and requirement definition support for digital transformation projects. Responsible for client interviews, workflow analysis, and system planning.',
     4),

    ('Marketing Analytics',
     'Marketing analytics support project. Responsible for KPI analysis, customer analytics, and BI dashboard development.',
     4),

    ('Business Process Reform',
     'Business process consulting project. Responsible for workflow improvement, operational optimization, and proposal documentation.',
     5),

    ('Enterprise Dashboard',
     'Enterprise dashboard development project. Responsible for KPI visualization and analytics features using React and TypeScript.',
     5),

    ('Research Prototype',
     'Research and development project using AI, graph analytics, and machine learning. Responsible for proof-of-concept development and technology validation.',
     6),

    ('AI Knowledge Search',
     'Research and development of a knowledge search system using natural language processing and vector search technologies.',
     6);

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
    ('Docker'),
    ('TypeScript'),
    ('SQL'),
    ('Machine Learning'),
    ('Security'),
    ('Linux'),
    ('Kubernetes'),
    ('Project Management'),
    ('Business Analysis'),
    ('Data Analysis'),
    ('Batch Processing'),
    ('CI/CD'),
    ('NLP'),
    ('Graph Analysis');

insert into app_user (is_manager)
values
    (true),
    (false),
    (false),
    (false),
    (true),
    (false),
    (false),
    (false),
    (true),
    (false),
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
    ('A001', 'Taro Yamada', 'taro@example.com',
     'Financial systems engineer specializing in trading systems, Java, Spring Boot, SQL, and performance tuning',
     3, 1),
    ('A002', 'Hanako Sato', 'hanako@example.com',
     'AI engineer specializing in Python, machine learning, forecasting models, and data analysis',
     4, 2),
    ('A003', 'Ichiro Suzuki', 'ichiro@example.com',
     'Cloud infrastructure engineer experienced in AWS, Terraform, Docker, Linux, and Kubernetes',
     4, 3),
    ('A004', 'Yuki Tanaka', 'yuki@example.com',
     'Frontend engineer using React, TypeScript, dashboard development, and UI design',
     5, 4),
    ('A005', 'Kenji Ito', 'kenji@example.com',
     'Project manager with finance domain knowledge, Java, SQL, and delivery management experience',
     2, 5),
    ('A006', 'Mika Kobayashi', 'mika@example.com',
     'Data engineer working on Python, SQL, PostgreSQL, data lake, and batch processing',
     4, 6),
    ('A007', 'Daichi Mori', 'daichi@example.com',
     'Security engineer interested in AWS security, Linux, Docker, Kubernetes, and monitoring',
     5, 7),
    ('A008', 'Rina Kato', 'rina@example.com',
     'Full-stack engineer using Java, Spring Boot, React, TypeScript, Python, and AI applications',
     4, 8),
    ('A009', 'Shota Nakamura', 'shota@example.com',
     'Consultant engineer focusing on business analysis, DX planning, SQL, and project management',
     3, 9),
    ('A010', 'Mai Watanabe', 'mai@example.com',
     'Marketing data analyst using SQL, Python, BI dashboards, and customer segmentation',
     5, 10),
    ('A011', 'Kenta Fujii', 'kenta@example.com',
     'Research engineer working on AI prototypes, NLP, graph analysis, and machine learning',
     4, 11),
    ('A012', 'Saki Ogawa', 'saki@example.com',
     'Platform engineer focusing on CI/CD, Docker, Kubernetes, AWS, and internal developer tools',
     5, 12);

insert into career (
    start_time,
    end_time,
    profile_id,
    project_id
)
values
    ('2018-04-01', '2020-03-31', 'A001', 2),
    ('2020-04-01', '2022-03-31', 'A001', 3),
    ('2022-04-01', null,         'A001', 1),

    ('2019-04-01', '2021-03-31', 'A002', 5),
    ('2021-04-01', '2023-03-31', 'A002', 6),
    ('2023-04-01', null,         'A002', 4),

    ('2020-04-01', '2022-03-31', 'A003', 7),
    ('2022-04-01', null,         'A003', 8),

    ('2021-04-01', '2023-03-31', 'A004', 13),
    ('2023-04-01', null,         'A004', 11),

    ('2017-04-01', '2020-03-31', 'A005', 1),
    ('2020-04-01', '2023-03-31', 'A005', 2),
    ('2023-04-01', null,         'A005', 10),

    ('2020-04-01', '2022-03-31', 'A006', 6),
    ('2022-04-01', null,         'A006', 4),

    ('2021-04-01', '2023-03-31', 'A007', 8),
    ('2023-04-01', null,         'A007', 7),

    ('2020-04-01', '2022-03-31', 'A008', 1),
    ('2022-04-01', '2024-03-31', 'A008', 13),
    ('2024-04-01', null,         'A008', 15),

    ('2018-04-01', '2021-03-31', 'A009', 12),
    ('2021-04-01', null,         'A009', 10),

    ('2021-04-01', '2023-03-31', 'A010', 11),
    ('2023-04-01', null,         'A010', 13),

    ('2020-04-01', '2022-03-31', 'A011', 14),
    ('2022-04-01', null,         'A011', 15),

    ('2021-04-01', '2023-03-31', 'A012', 9),
    ('2023-04-01', null,         'A012', 7);

insert into career_skill (career_id, skill_id, skill_year, skill_month)
values
    (1, 11, 1, 2),
    (1, 18, 4, 9),
    (2, 1, 4, 7),
    (2, 8, 3, 2),
    (2, 11, 2, 5),
    (2, 19, 2, 1),
    (3, 1, 1, 10),
    (3, 2, 2, 11),
    (3, 8, 4, 5),
    (3, 11, 3, 8),
    (4, 3, 4, 3),
    (4, 12, 1, 0),
    (4, 18, 3, 6),
    (5, 3, 1, 6),
    (5, 8, 2, 11),
    (5, 11, 1, 2),
    (5, 18, 4, 9),
    (6, 3, 2, 9),
    (6, 4, 3, 10),
    (6, 12, 3, 6),
    (6, 18, 1, 0),
    (7, 5, 1, 2),
    (7, 6, 2, 3),
    (7, 9, 1, 6),
    (7, 14, 2, 11),
    (8, 5, 2, 5),
    (8, 9, 2, 9),
    (8, 13, 2, 1),
    (8, 14, 3, 2),
    (8, 15, 4, 3),
    (9, 7, 1, 10),
    (9, 10, 4, 1),
    (9, 11, 1, 2),
    (10, 7, 2, 1),
    (10, 10, 1, 4),
    (10, 18, 1, 0),
    (11, 1, 1, 10),
    (11, 2, 2, 11),
    (11, 8, 4, 5),
    (11, 11, 3, 8),
    (12, 11, 4, 11),
    (12, 16, 1, 4),
    (12, 17, 2, 5),
    (12, 18, 3, 6),
    (13, 16, 2, 7),
    (13, 17, 3, 8),
    (13, 18, 4, 9),
    (14, 3, 2, 9),
    (14, 8, 3, 2),
    (14, 11, 2, 5),
    (14, 19, 2, 1),
    (15, 3, 3, 0),
    (15, 4, 4, 1),
    (15, 12, 4, 9),
    (15, 18, 2, 3),
    (16, 5, 2, 5),
    (16, 13, 2, 1),
    (16, 14, 3, 2),
    (16, 15, 4, 3),
    (17, 5, 3, 8),
    (17, 6, 4, 9),
    (17, 9, 3, 0),
    (17, 14, 4, 5),
    (18, 1, 4, 7),
    (18, 2, 1, 8),
    (18, 7, 2, 1),
    (18, 10, 1, 4),
    (19, 7, 3, 4),
    (19, 10, 2, 7),
    (19, 11, 3, 8),
    (20, 3, 4, 3),
    (20, 4, 1, 4),
    (20, 12, 1, 0),
    (20, 21, 2, 9),
    (21, 16, 2, 7),
    (21, 17, 3, 8),
    (21, 18, 4, 9),
    (22, 11, 2, 5),
    (22, 16, 3, 10),
    (22, 17, 4, 11),
    (22, 18, 1, 0),
    (23, 3, 3, 0),
    (23, 11, 3, 8),
    (23, 18, 2, 3),
    (24, 3, 4, 3),
    (24, 7, 4, 7),
    (24, 10, 3, 10),
    (24, 18, 3, 6),
    (25, 3, 1, 6),
    (25, 4, 2, 7),
    (25, 12, 2, 3),
    (25, 22, 4, 1),
    (26, 3, 2, 9),
    (26, 4, 3, 10),
    (26, 12, 3, 6),
    (26, 21, 4, 3),
    (26, 22, 1, 4),
    (27, 5, 1, 2),
    (27, 9, 1, 6),
    (27, 15, 3, 0),
    (27, 20, 4, 5),
    (28, 5, 2, 5),
    (28, 6, 3, 6),
    (28, 9, 2, 9),
    (28, 14, 3, 2),
    (28, 15, 4, 3),
    (28, 20, 1, 8);
