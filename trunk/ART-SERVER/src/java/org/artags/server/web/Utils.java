/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.artags.server.web;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author pierre@artags.org
 */
public class Utils
{

    public static double getDouble(HttpServletRequest request, String parameter, double def)
    {
        double value = def;
        String sValue = request.getParameter(parameter);
        if (sValue != null)
        {
            try
            {
                value = Double.parseDouble(sValue);
            } catch (NumberFormatException e)
            {
            }
        }
        return value;

    }

    public static int getInt(HttpServletRequest request, String parameter, int def)
    {
        int value = def;
        String sValue = request.getParameter(parameter);
        if (sValue != null)
        {
            try
            {
                value = Integer.parseInt(sValue);
            } catch (NumberFormatException e)
            {
            }
        }
        return value;

    }
}
