-- many to many projects selection query
SELECT projects.id, projects.name, projects.customer_id
FROM projects
INNER JOIN companies_projects
ON projects.id = companies_projects.project_id
INNER JOIN companies
ON companies_projects.company_id = companies.id
WHERE company_id = 4;

-- left join selection
SELECT *
FROM developers
LEFT JOIN skills ON developers.skill_id = skills.id
WHERE skills.seniority = "MIDDLE";

-- select all salary for project
SELECT developers.salary
FROM developers
INNER JOIN developers_projects
ON developers.id = developers_projects.developer_id
INNER JOIN projects
ON developers_projects.project_id = projects.id
WHERE developer_id = 34;