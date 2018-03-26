package com.goosvandenbekerom;

import javax.annotation.security.DeclareRoles;
import javax.security.enterprise.identitystore.DatabaseIdentityStoreDefinition;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@DatabaseIdentityStoreDefinition(
        dataSourceLookup = "java:app/mysql/poc",
        callerQuery = "SELECT PASSWORD FROM USER WHERE USERNAME = ?",
        groupsQuery = "SELECT groups_NAME FROM USER_GROUP WHERE users_USERNAME = ?",
        hashAlgorithmParameters = {
                "Pbkdf2PasswordHash.Iterations=3072",
                "Pbkdf2PasswordHash.Algorithm=PBKDF2WithHmacSHA512",
                "Pbkdf2PasswordHash.SaltSizeBytes=64"
        }
)
@DeclareRoles({"test", "admin"})
@WebServlet("/servlet")
public class PocServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.getWriter().write("This is a servlet \n");

        String webName = null;
        if (request.getUserPrincipal() != null) {
            webName = request.getUserPrincipal().getName();
        }

        response.getWriter().write("web username: " + webName + "\n");

        response.getWriter().write("web user has role \"test\": " + request.isUserInRole("test") + "\n");
        response.getWriter().write("web user has role \"admin\": " + request.isUserInRole("admin") + "\n");
    }
}
