-- many to many projects selection query
SELECT projects.id, projects.name, projects.customer_id
                FROM projects
                INNER JOIN companies_projects
                ON projects.id = companies_projects.project_id
                INNER JOIN companies
                ON companies_projects.company_id = companies.id
                WHERE company_id = 4;