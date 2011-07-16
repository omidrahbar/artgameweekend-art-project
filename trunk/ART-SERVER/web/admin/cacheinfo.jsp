<%-- 
    Document   : cacheinfo
    Created on : 27 mars 2011, 17:30:04
    Author     : pierre
--%>
<%@page import="java.util.List"%>
<%@page import="net.sf.ehcache.*" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cache Info JSP Page</title>
    </head>
    <body>
        <h1>Cache Info</h1>
        <%
                    String[] names = CacheManager.getInstance().getCacheNames();
                    for (String name : names)
                    {
                        out.print("'" + name + "'<br/>");
                        Ehcache ehcache = CacheManager.getInstance().getEhcache(name);
                        if (ehcache != null)
                        {
                            List<String> keys = ehcache.getKeys();
                            out.print("Cache size : " + keys.size() + "<br/>");
                            for (String key : keys)
                            {
                                out.print(key + "<br/>");
                            }
                        }
                    }
        %>

    </body>
</html>
