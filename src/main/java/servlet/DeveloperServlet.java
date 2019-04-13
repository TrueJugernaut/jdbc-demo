package servlet;

import dao.Impl.DeveloperDaoImpl;
import service.DeveloperService;
import service.Impl.DeveloperServiceImpl;
import util.ConnectionFactory;

import javax.servlet.http.HttpServlet;
import java.sql.Connection;

public class DeveloperServlet extends HttpServlet {
    Connection connection = ConnectionFactory.getConnection();

    DeveloperService developerService = new DeveloperServiceImpl(new DeveloperDaoImpl(connection));

}
