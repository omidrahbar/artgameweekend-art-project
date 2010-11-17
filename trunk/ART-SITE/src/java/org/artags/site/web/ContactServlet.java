/* Copyright (c) 2010 ARTags project owners (see http://www.artags.org)
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.artags.site.web;

import java.io.IOException;
import java.io.Writer;
import java.net.MalformedURLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Pierre Levy
 */
public class ContactServlet extends HttpServlet
{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, MalformedURLException
    {

        String sendTo = req.getParameter("SendTo");
        String name = req.getParameter("Name");
        String email = req.getParameter("Email");
        String subject = req.getParameter("Subject");
        String message = req.getParameter("Msg");

        System.out.println("ContactServlet");
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        try
        {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress( email, name ));
            msg.addRecipient(Message.RecipientType.TO,
                    new InternetAddress( sendTo, "[ARTags contact form]"));
            msg.setSubject(subject);
            msg.setText(message);
            Transport.send(msg);

        } catch (AddressException e)
        {
            Logger.getLogger(ContactServlet.class.getName()).log(Level.SEVERE, null, e);
        } catch (MessagingException e)
        {
            Logger.getLogger(ContactServlet.class.getName()).log(Level.SEVERE, null, e);
        }

        Writer out = resp.getWriter();
        resp.setContentType( "text/plain");
        out.write("sent=success");
        out.close();



    }
}
